package org.example.sistemaclientes.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

// Indica que esta clase pertenece a la capa de servicios.
// Contiene toda la lógica relacionada con los JWT.
@Service
public class JwtService {

    // Obtiene desde application.yml
    // la clave secreta utilizada para firmar y verificar los JWT.
    @Value("${jwt.secret}")
    private String secret;

    // Obtiene el tiempo de expiración del token
    // configurado en la aplicación (normalmente en milisegundos).
    @Value("${jwt.expiration}")
    private long expiration;

    // Convierte la clave secreta almacenada como texto (Base64)
    // en un objeto SecretKey que la librería JWT necesita para
    // firmar y verificar los tokens.
    private SecretKey getKey() {

        // Decodifica el texto Base64 a un arreglo de bytes.
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        // Construye la clave criptográfica utilizando el algoritmo HMAC.
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Genera un nuevo JWT para el usuario autenticado.
    public String generateToken(String username) {

        return Jwts.builder()

                // Guarda el nombre de usuario (correo)
                // como el "subject" del token.
                .subject(username)

                // Fecha y hora en que se crea el token.
                .issuedAt(new Date())

                // Fecha y hora en que el token dejará de ser válido.
                .expiration(
                        new Date(System.currentTimeMillis() + expiration)
                )

                // Firma digitalmente el token utilizando
                // la clave secreta.
                .signWith(getKey())

                // Convierte toda la información en un String JWT.
                .compact();
    }

    // Extrae el nombre de usuario almacenado dentro del token.
    public String extractUsername(String token) {

        return Jwts.parser()

                // Utiliza la misma clave secreta para verificar
                // que el token no haya sido modificado.
                .verifyWith(getKey())

                // Construye el parser.
                .build()

                // Analiza el token firmado.
                .parseSignedClaims(token)

                // Obtiene la información (payload).
                .getPayload()

                // Devuelve el subject (correo del usuario).
                .getSubject();
    }

    // Comprueba si el token pertenece realmente al usuario.
    public boolean isTokenValid(String token, UserDetails userDetails) {

        // Extrae el usuario almacenado en el JWT.
        String username = extractUsername(token);

        // Compara el usuario del token con el usuario
        // obtenido desde la base de datos.
        return username.equals(userDetails.getUsername());
    }

}