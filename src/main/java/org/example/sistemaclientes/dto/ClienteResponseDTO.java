package org.example.sistemaclientes.dto;

import lombok.Builder;

@Builder
public record ClienteResponseDTO(
        Long id_cliente,
        String nombre,
        String correo
) {
}
