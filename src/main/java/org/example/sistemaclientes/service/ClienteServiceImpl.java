package org.example.sistemaclientes.service;

import lombok.AllArgsConstructor;
import org.example.sistemaclientes.dto.ClienteRequestDTO;
import org.example.sistemaclientes.dto.ClienteResponseDTO;
import org.example.sistemaclientes.exception.RecursoNoEncontradoException;
import org.example.sistemaclientes.mapper.ClienteMapper;
import org.example.sistemaclientes.model.Cliente;
import org.example.sistemaclientes.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ClienteResponseDTO crearCliente(ClienteRequestDTO request){
        Cliente cliente = mapper.toModel(request);
        cliente.setPassword(passwordEncoder.encode(request.password()));
        repository.save(cliente);
        return mapper.toDTO(cliente);
    }

    @Override
    public List<ClienteResponseDTO> obtenerClientes(){
        return repository.findAll()
                .stream()
                .map(mapper::toDTO) // cliente -> mapper.toDTO(cliente)
                .toList();
    }

    @Override
    public ClienteResponseDTO clienteById(Long id_cliente){
        Cliente cliente = repository.findById(id_cliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        return mapper.toDTO(cliente);
    }

    @Override
    public ClienteResponseDTO actualizarCliente(Long id_cliente, ClienteRequestDTO request){
        Cliente cliente = repository.findById(id_cliente).orElseThrow(() ->new RecursoNoEncontradoException("Cliente no encontrado"));
        cliente.setNombre(request.nombre());
        cliente.setDireccion(request.direccion());
        cliente.setTelefono(request.telefono());
        cliente.setCorreo(request.correo());
        cliente.setApellido(request.apellido());
        cliente.setRol(request.rol());
        cliente.setPassword(passwordEncoder.encode(request.password()));
        Cliente actualizado = repository.save(cliente);
        return mapper.toDTO(actualizado);
    }

    @Override
    public ClienteResponseDTO eliminarCliente(Long id_cliente){
        Cliente cliente = repository.findById(id_cliente)
                .orElseThrow(()->new RuntimeException("No encontrado"));
        repository.delete(cliente);
        return mapper.toDTO(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findByDireccion(String direccion) {
        return repository.findByDireccion(direccion)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }


    @Override
    public List<ClienteResponseDTO> buscarByNombre(String nombre) {
        return repository.buscarByNombre(nombre)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
    

}
