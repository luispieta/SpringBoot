package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
//Essa interface representa um usuário autenticado no sistema
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    //Irá retornar o login do usuário
    @Override
    public String getPassword() {
        return senha;
    }

    //Irá retornar a senha do usuário
    @Override
    public String getUsername() {
        return login;
    }

    //Vê se a conta expirou
    @Override
    public boolean isAccountNonExpired() {
        //O código return UserDetails.super.isAccountNonExpired(); é utilizado para a lógica (EX: se já expirou e está ativa)
        return true;
    }

    //Vê se a conta está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        //O código return UserDetails.super.isAccountNonLocked(); é utilizado para a lógica (EX: se expirou e está ativa)
        return true;
    }

    //Vê se a senha da conta está ainda válida
    @Override
    public boolean isCredentialsNonExpired() {
        //O código return UserDetails.super.isCredentialsNonExpired(); é utilizado para a lógica (EX: se já válida e se expirou)
        return true;
    }

    //Vê se a conta está ativa
    @Override
    public boolean isEnabled() {
        //O código return UserDetails.super.isEnabled(); é utilizado para a lógica (EX: se já expirou e está ativa)
        return true;
    }
}
