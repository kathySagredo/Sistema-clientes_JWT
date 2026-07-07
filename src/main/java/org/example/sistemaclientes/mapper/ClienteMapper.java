package org.example.sistemaclientes.mapper;

import org.example.sistemaclientes.dto.ClienteRequestDTO;
import org.example.sistemaclientes.dto.ClienteResponseDTO;
import org.example.sistemaclientes.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toModel(ClienteRequestDTO request){
        Cliente cliente = new Cliente();
        cliente.setNombre(request.nombre());
        cliente.setApellido(request.apellido());
        cliente.setDireccion(request.direccion());
        cliente.setCorreo(request.correo());
        cliente.setTelefono(request.telefono());
        cliente.setRol(request.rol());
        return cliente;
    }

    public ClienteResponseDTO toDTO(Cliente cliente){
        return ClienteResponseDTO.builder()
                .id_cliente(cliente.getId_cliente())
                .nombre(cliente.getNombre())
                .correo(cliente.getCorreo())
                .build();
    }

}
