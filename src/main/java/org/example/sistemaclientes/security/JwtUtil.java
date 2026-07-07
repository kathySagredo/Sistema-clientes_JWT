package org.example.sistemaclientes.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;

// Clase de utilidad utilizada únicamente para generar
// una clave secreta para JWT.
//
// No forma parte de la aplicación en ejecución.
// Se ejecuta una sola vez para obtener una clave
// que luego se copia en application.yml.
public class JwtUtil {

    public static void main(String[] args){

        // Genera una clave secreta aleatoria utilizando
        // el algoritmo HS256 (HMAC con SHA-256).
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Convierte la clave generada a formato Base64
        // para poder almacenarla como texto.
        String encoded = Base64.getEncoder()
                .encodeToString(key.getEncoded());

        // Muestra la clave por consola.
        // Esta será la que copiaremos en la configuración
        // de la aplicación como jwt.secret.
        System.out.println(encoded);
    }
}