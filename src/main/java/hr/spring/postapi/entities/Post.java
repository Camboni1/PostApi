package hr.spring.postapi.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data // Permet à Lombok de générer les getters et les setters
@Entity
@Table(name = "posts")
public class Post {
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

    public Post() {}

    public Post(String titre, String description, String url, int like, String localisation) {
        this.title = titre;
        this.description = description;
        this.url = url;
        this.like = like;
        this.location = localisation;
        this.date = LocalDate.now();
    }

    public Post(int id, String titre, String description, String url, int like, String localisation) {
        this.id = id;
        this.title = titre;
        this.description = description;
        this.url = url;
        this.like = like;
        this.location = localisation;
    }

}
