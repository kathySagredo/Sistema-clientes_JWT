package org.example.sistemaclientes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Indica que esta clase será administrada por Spring como un Bean.
@Component

// Lombok genera automáticamente un constructor con todos los atributos final.
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    // Servicio encargado de trabajar con los JWT:
    // generar, validar y extraer información del token.
    private final JwtService jwtService;

    // Servicio que permite obtener los datos del usuario
    // desde la base de datos.
    private final ClienteDetailsService clienteDetailsService;

    // Método que se ejecuta automáticamente cada vez que llega
    // una petición HTTP al servidor.
    //
    // Recibe:
    // - request: contiene toda la información de la petición
    //   (headers, parámetros, cuerpo, etc.).
    // - response: representa la respuesta que se enviará al cliente.
    // - filterChain: permite continuar con el siguiente filtro
    //   o llegar finalmente al controlador.
    //
    // Se sobrescribe porque OncePerRequestFilter define este método
    // como el lugar donde implementamos la lógica de nuestro filtro.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el valor del encabezado Authorization enviado
        // por el cliente.
        String authHeader = request.getHeader("Authorization");

        // Si el encabezado no existe o no comienza con "Bearer ",
        // significa que la petición no contiene un JWT válido.
        // En ese caso se continúa con el siguiente filtro.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Elimina el prefijo "Bearer " para quedarse únicamente
        // con el token JWT.
        String token = authHeader.substring(7);

        // Extrae el nombre de usuario (correo) almacenado dentro del token.
        String username = jwtService.extractUsername(token);

        // Verifica que el token contenga un usuario y que aún no exista
        // un usuario autenticado en el contexto de seguridad.
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // Busca al usuario en la base de datos.
            UserDetails userDetails =
                    clienteDetailsService.loadUserByUsername(username);

            // Comprueba que el token sea válido para ese usuario.
            if (jwtService.isTokenValid(token, userDetails)) {

                // Crea un objeto de autenticación que representa
                // al usuario autenticado.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                // Agrega información adicional de la petición HTTP
                // (como dirección IP).
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                // Guarda la autenticación en el contexto de seguridad.
                // A partir de este momento Spring considera
                // que el usuario está autenticado.
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // Continúa con el siguiente filtro de la cadena
        // hasta llegar finalmente al controlador.
        filterChain.doFilter(request, response);
    }

}
