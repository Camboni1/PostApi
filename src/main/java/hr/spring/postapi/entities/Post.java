package hr.spring.postapi.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "posts")
@AllArgsConstructor
@RequiredArgsConstructor public class Post {
    /**
     * Un identifiant
     * Un titre,
     * Une description
     * Une URL
     * Un nombre de like (nombre de j’aime)
     * Une localisation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private String url;

    @Column(name = "jaime")
    private int like;

    private String location;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Post(String title, String description, String url, int like, String location) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.like = like;
        this.location = location;
    }
}
