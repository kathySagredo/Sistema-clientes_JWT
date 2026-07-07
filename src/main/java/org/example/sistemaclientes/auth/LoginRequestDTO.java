package org.example.sistemaclientes.auth;

// DTO (Data Transfer Object) utilizado para recibir los datos
// enviados por el cliente durante el inicio de sesión.
//
// Al ser un "record", Java genera automáticamente:
// - Un constructor.
// - Los métodos de acceso (correo() y password()).
// - equals(), hashCode() y toString().
//
// Su única función es transportar los datos desde la petición HTTP
// hasta el backend, sin contener lógica de negocio.
public record LoginRequestDTO(

        // Correo electrónico ingresado por el usuario.
        String correo,

        // Contraseña ingresada por el usuario.
        String password

) {
}
