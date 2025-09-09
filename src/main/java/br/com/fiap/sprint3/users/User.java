package br.com.fiap.sprint3.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario") 
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    private String name;

    @NotBlank
    private String password;

    private String avatarUrl;

    private int score = 0;

    public User(OAuth2User principal) {
        this.email = principal.getAttributes().get("email").toString();
        this.name = principal.getAttributes().get("name").toString();
        var avatarUrl = principal.getAttributes().get("picture") != null ?
                principal.getAttributes().get("picture") :
                principal.getAttributes().get("avatar_url");

        this.avatarUrl = avatarUrl.toString();
    }

    public User() {

    }
}
