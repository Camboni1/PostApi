package hr.spring.postapi.entities;

import hr.spring.postapi.enums.AppRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.Getter;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false,  length = 20)
    private AppRole role = AppRole.USER;

}
