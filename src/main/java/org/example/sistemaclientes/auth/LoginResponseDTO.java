package org.example.sistemaclientes.auth;

// DTO (Data Transfer Object) utilizado para enviar la respuesta
// al cliente después de una autenticación exitosa.
//
// Contiene únicamente el token JWT generado por el servidor.
//
// Al ser un "record", Java genera automáticamente:
// - Un constructor.
// - El método de acceso token().
// - equals(), hashCode() y toString().
//
// Su función es transportar la información desde el backend
// hacia el cliente, sin incluir lógica de negocio.
public record LoginResponseDTO(

        // Token JWT que el cliente deberá almacenar y enviar
        // en las siguientes peticiones para acceder a recursos protegidos.
        String token

) {
}
