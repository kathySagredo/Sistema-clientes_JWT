package org.example.sistemaclientes.auth;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Indica que esta clase es un controlador REST.
// Spring la detecta automáticamente y los métodos devolverán datos (generalmente JSON)
@RestController

// Define la ruta base para todos los endpoints de este controlador.
// Todos los métodos comenzarán con "/auth".
@RequestMapping("/auth")

// Anotación de Lombok que genera automáticamente un constructor
// con todos los atributos finales de la clase.
// Gracias a esto Spring puede inyectar las dependencias sin escribir el constructor.
@AllArgsConstructor
public class AuthController {

    // Servicio que contiene la lógica de autenticación (login).
    // La palabra "final" indica que la referencia no cambiará
    // y será inyectada mediante el constructor generado por Lombok.
    private final AuthService authService;

    // Este método responderá a las peticiones HTTP POST en la ruta:
    // POST /auth/login
    @PostMapping("/login")
    public LoginResponseDTO login(

            // Convierte automáticamente el JSON recibido en el cuerpo de la petición
            // en un objeto LoginRequestDTO.
            // Ejemplo del JSON recibido:
            // {
            //   "email": "usuario@email.com",
            //   "password": "123456"
            // }
            @RequestBody LoginRequestDTO request){

        // El controlador no valida usuarios ni genera tokens.
        // Su única responsabilidad es recibir la petición
        // y delegar el trabajo al servicio de autenticación.
        return authService.login(request);
    }
}
