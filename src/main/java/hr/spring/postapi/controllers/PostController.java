package hr.spring.postapi.controllers;

import hr.spring.postapi.entities.Post;
import hr.spring.postapi.entities.UserDetailsImplementation;
import hr.spring.postapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAll(
            @RequestParam(required = false) String localisation,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String title) {
        List<Post> posts = service.getPosts(localisation, date, title);
        return posts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/between")
    public ResponseEntity<List<Post>> getPostBetweenDates(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        List<Post> posts = service.getPostsBetween(start, end);
        return posts.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getPost(id));
    }

    @GetMapping("/posts/oldest")
    public ResponseEntity<Post> getOldestPost() {
        return ResponseEntity.ok(service.getOldestPost());
    }

    @PostMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> create(@RequestBody Post pst, @AuthenticationPrincipal UserDetailsImplementation currentUser) {
        Post savedPost = service.createPost(pst, currentUser.getUser());
        return ResponseEntity.created(URI.create("/api/posts/" + savedPost.getId())).body(savedPost);
    }

    @PutMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> replace(@PathVariable int id, @RequestBody Post post) {
        return ResponseEntity.ok(service.replacePost(id, post));
    }

    @PatchMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> updatePartiel(@PathVariable int id, @RequestBody Post post) {
        return ResponseEntity.ok(service.partialUpdate(id, post));
    }

    @PatchMapping("/posts/{id}/like")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> like(@PathVariable int id) {
        return ResponseEntity.ok(service.likePost(id));
    }

    @PatchMapping("/posts/{id}/dislike")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Post> dislike(@PathVariable int id) {
        return ResponseEntity.ok(service.dislikePost(id));
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAllPosts();
        return ResponseEntity.noContent().build();
    }
}
