package org.example.sistemaclientes.security;

import lombok.AllArgsConstructor;
import org.example.sistemaclientes.exception.RecursoNoEncontradoException;
import org.example.sistemaclientes.model.Cliente;
import org.example.sistemaclientes.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

// Indica que esta clase pertenece a la capa de servicios.
// Spring la detectará automáticamente como un Bean.
@Service

// Lombok genera un constructor con todos los atributos final,
// permitiendo la inyección de dependencias.
@AllArgsConstructor
public class ClienteDetailsService implements UserDetailsService {

    // Repositorio utilizado para consultar los clientes en la base de datos.
    private final ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws RecursoNoEncontradoException {

        // Busca un cliente utilizando el correo recibido.
        // Si no existe un cliente con ese correo,
        // se lanza una excepción indicando que no fue encontrado.
        Cliente cliente = repository.findByCorreo(username)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Cliente no encontrado"));

        // Convierte la entidad Cliente en un objeto ClienteDetails,
        // ya que Spring Security trabaja con objetos que implementan
        // la interfaz UserDetails.
        return new ClienteDetails(cliente);
    }

}
