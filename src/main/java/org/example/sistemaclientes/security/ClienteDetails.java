package org.example.sistemaclientes.security;

import lombok.AllArgsConstructor;
import org.example.sistemaclientes.model.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// Lombok genera automáticamente un constructor con el atributo "cliente".
// Gracias a esto podemos inyectar el objeto Cliente al crear esta clase.
@AllArgsConstructor
public class ClienteDetails implements UserDetails {

    // Entidad Cliente obtenida desde la base de datos.
    // Contiene toda la información del usuario autenticado.
    private final Cliente cliente;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        // Devuelve la lista de permisos (roles) que tiene el usuario.
        // Spring Security espera recibir una colección de GrantedAuthority.

        // En este caso el usuario posee un único rol, por ejemplo:
        // ROLE_ADMIN
        // ROLE_CLIENT

        // El prefijo "ROLE_" es el formato que Spring Security utiliza
        // para reconocer los roles de un usuario.
        return List.of(new SimpleGrantedAuthority(
                "ROLE_" + cliente.getRol().name()
        ));
    }

    @Override
    public String getUsername(){

        // Devuelve el identificador con el que el usuario inicia sesión.
        // En este proyecto se utiliza el correo electrónico.
        return cliente.getCorreo();
    }

    @Override
    public String getPassword(){

        // Devuelve la contraseña almacenada en la base de datos.
        // Esta contraseña normalmente está cifrada con BCrypt.
        return cliente.getPassword();
    }

    @Override
    public boolean isAccountNonExpired(){

        // true = la cuenta no ha expirado y puede seguir utilizándose.
        // false = la cuenta está vencida.
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){

        // true = la cuenta no está bloqueada.
        // false = la cuenta fue bloqueada y no puede iniciar sesión.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){

        // true = la contraseña sigue siendo válida.
        // false = la contraseña expiró y debe cambiarse.
        return true;
    }

    @Override
    public boolean isEnabled(){

        // true = la cuenta está habilitada.
        // false = la cuenta fue deshabilitada.
        return true;
    }

}
