package com.ginasiouniforagenda.AgendamentoWeb.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users") // Nome da tabela no banco de dados
@Entity(name = "users") // Nome da entidade JPA
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails { // Implementa UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Ou GenerationType.IDENTITY se for numérico
    private String id; // Mudei para String para corresponder a GenerationType.UUID

    @Column(nullable = false, unique = true)
    private String login; // Campo usado como username para Spring Security

    private String email;

    private String password; // Já criptografada

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Construtor usado no registro
    public User(String login, String email, String password, UserRole role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return login; // Retorna o campo 'login' como o username para Spring Security
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}