package org.example.sistemaclientes.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime fecha,
        Integer status,
        String mensaje
) {
}
