package org.example.sistemaclientes.auth;


import lombok.AllArgsConstructor;
import org.example.sistemaclientes.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

// Indica que esta clase pertenece a la capa de servicios.
// Aquí se implementa la lógica de negocio relacionada con la autenticación.
@Service

// Lombok genera automáticamente un constructor con todos los atributos final,
// permitiendo que Spring inyecte las dependencias.
@AllArgsConstructor
public class AuthService {

    // Componente de Spring Security encargado de autenticar al usuario.
    // Verifica que las credenciales (correo y contraseña) sean correctas.
    private final AuthenticationManager authenticationManager;

    // Servicio propio encargado de generar el token JWT.
    private final JwtService jwtService;

    // Método que recibe las credenciales enviadas desde el controlador
    // y devuelve un objeto con el token JWT.
    public LoginResponseDTO login(LoginRequestDTO request){

        // Intenta autenticar al usuario utilizando el correo y la contraseña.
        // UsernamePasswordAuthenticationToken es un objeto que agrupa
        // las credenciales para que Spring Security las pueda validar.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.correo(),
                        request.password()
                )
        );

        // Si las credenciales son válidas, se genera un token JWT
        // utilizando el correo del usuario.
        String token = jwtService.generateToken(request.correo());

        // Se crea y devuelve el DTO de respuesta que contiene el token.
        // Spring lo convertirá automáticamente a formato JSON.
        return new LoginResponseDTO(token);
    }

}
