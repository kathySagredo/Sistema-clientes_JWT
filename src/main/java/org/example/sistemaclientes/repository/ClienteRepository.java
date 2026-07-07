package org.example.sistemaclientes.repository;

import org.example.sistemaclientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    // Query Method
    List<Cliente> findByDireccion(String direccion);
    Optional<Cliente> findByCorreo(String correo);

    // Query
    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    List<Cliente> buscarByNombre(@Param("nombre") String nombre);

}
