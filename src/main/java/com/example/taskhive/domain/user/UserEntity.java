package com.example.taskhive.domain.user;

import com.example.taskhive.domain.board.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "username")
    private String user;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Board> createdBoards;

    @ManyToMany(mappedBy = "collaborators")
    @JsonIgnore
    private List<Board> collaborativeBoards;

    public UserEntity(String name, String user, String email, String password, UserRole role) {
        this.name = name;
        this.user = user;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UserRole.ADMIN) {
            return List.of(() -> "SCOPE_ADMIN", () -> "SCOPE_STAFF", () -> "SCOPE_USER");
        } else if (role == UserRole.STAFF) {
            return List.of(() -> "SCOPE_STAFF", () -> "SCOPE_USER");
        } else if (role == UserRole.GUEST) {
            return List.of(() -> "SCOPE_GUEST");
        } else {
            return List.of(() -> "SCOPE_USER");
        }
    }

    @Override
    public String getUsername() {
        return user;
    }

}
