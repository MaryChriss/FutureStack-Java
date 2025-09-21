package br.com.fiap.sprint3.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario") 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank(message = "{user.nome.notblank}")
    @Pattern(regexp = "^[A-Z].*", message = "{user.nome.pattern}")
    public String nomeUser;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    private String password;

    @NotBlank(message = "{user.phone.notblank}")
    @Pattern(regexp = "^\\+?\\d{10,14}$", message = "{user.phone.pattern}")
    private String phone;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }


}
