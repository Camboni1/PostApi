package hr.spring.postapi.repository;

import hr.spring.postapi.entities.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByLocation(String localisation);
    List<Post> findByDate(LocalDate date);

    //List<Post> findByDateGreaterThanEqual(LocalDate date);

    @Query(
            value = "SELECT * FROM posts WHERE date BETWEEN :start AND :end",
            nativeQuery = true
    )
    List<Post> findByDateGreaterThanEqual(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(
            value = "SELECT * FROM posts ORDER BY date ASC LIMIT 1",
            nativeQuery = true
    )
    Post orderByDateAscLimit1();

    List<Post> findByLocationContainingIgnoreCase(String pays);

    @Query("From Post WHERE title LIKE %?1%")
    List<Post> findByTitleLike(String title);

}
