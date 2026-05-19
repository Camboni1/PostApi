package hr.spring.postapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @NotEmpty
    private String email;

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Builder.Default
    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> userRoles = new ArrayList<>(List.of("USER"));
}
