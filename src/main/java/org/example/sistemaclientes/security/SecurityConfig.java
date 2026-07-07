package org.example.sistemaclientes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Indica que esta clase contiene configuraciones de Spring.
@Configuration

// Habilita el sistema de seguridad de Spring Security.
@EnableWebSecurity
public class SecurityConfig {

    // Define la configuración principal del filtro de seguridad.
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider,
            JwtAuthFilter jwtAuthFilter) throws Exception {

        http

                // Deshabilita la protección CSRF.
                // En aplicaciones REST que utilizan JWT normalmente
                // no es necesaria porque no se utilizan sesiones.
                .csrf(csrf -> csrf.disable())

                // Indica cuál será el proveedor encargado
                // de autenticar a los usuarios.
                .authenticationProvider(authenticationProvider)

                // Configura la aplicación para que no utilice sesiones.
                // Cada petición deberá traer su propio JWT.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Agrega nuestro filtro JWT antes del filtro de autenticación
                // de Spring Security.
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                // Configura las reglas de autorización para las rutas.
                .authorizeHttpRequests(auth -> auth

                        // Cualquier persona puede consultar los clientes.
                        .requestMatchers(HttpMethod.GET, "/clientes")
                        .permitAll()

                        // Cualquier persona puede registrar un cliente.
                        .requestMatchers(HttpMethod.POST, "/clientes")
                        .permitAll()

                        // Solo usuarios con rol CLIENT
                        // pueden modificar un cliente.
                        .requestMatchers(HttpMethod.PUT, "/clientes/**")
                        .hasRole("CLIENT")

                        // Solo usuarios con rol ADMIN
                        // pueden eliminar un cliente.
                        .requestMatchers(HttpMethod.DELETE, "/clientes/**")
                        .hasRole("ADMIN")

                        // Todos pueden iniciar sesión.
                        .requestMatchers(HttpMethod.POST, "/auth/login")
                        .permitAll()

                        // Cualquier otra petición requiere autenticación.
                        .anyRequest()
                        .authenticated());

        // Construye y devuelve la configuración de seguridad.
        return http.build();
    }

    // Define el AuthenticationProvider que utilizará Spring Security
    // para autenticar usuarios.
    @Bean
    public AuthenticationProvider authenticationProvider(
            ClienteDetailsService clienteDetailsService){

        // Crea un proveedor basado en acceso a base de datos.
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(clienteDetailsService);

        // Indica cómo comparar las contraseñas.
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    // Expone el AuthenticationManager como un Bean para poder
    // utilizarlo en AuthService durante el login.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config){

        return config.getAuthenticationManager();
    }

    // Define el algoritmo que se utilizará para cifrar
    // y verificar las contraseñas.
    @Bean
    public PasswordEncoder passwordEncoder(){

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        return encoder;
    }

}

