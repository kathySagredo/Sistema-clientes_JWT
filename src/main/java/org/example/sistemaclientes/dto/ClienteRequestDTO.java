package org.example.sistemaclientes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.sistemaclientes.model.Rol;

public record ClienteRequestDTO(
        @NotBlank(message = "Dato invalido")
        String nombre,
        @NotNull
        String apellido,
        @NotNull
        @Email(message = "Formato incorrecto en correo")
        String correo,
        @Size(min = 3, max = 12)
        String telefono,
        @NotBlank
        String direccion,
        String password,
        Rol rol
) { }
