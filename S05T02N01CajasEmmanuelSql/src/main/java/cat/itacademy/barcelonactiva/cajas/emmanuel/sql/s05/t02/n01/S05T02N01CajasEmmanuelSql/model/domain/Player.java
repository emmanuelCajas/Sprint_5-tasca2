package cat.itacademy.barcelonactiva.cajas.emmanuel.sql.s05.t02.n01.S05T02N01CajasEmmanuelSql.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int player_id;
    @Column (nullable = false)
    private String name;
    @Column (nullable = false, unique = true)
    private String email;
    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @Column(nullable = false, length = 120)
    private String password;
    @Enumerated
    private Role role;

    public Player(String name) {
        this.name = name;
    }

    public Player(String email, String password, Collection<GrantedAuthority> mapRolesToAuthorities) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
