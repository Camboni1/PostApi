package hr.spring.postapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.spring.postapi.enums.AppRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false,  length = 20)
    private AppRole role = AppRole.USER;

}
