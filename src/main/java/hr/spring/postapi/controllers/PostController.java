package hr.spring.postapi.controllers;

import hr.spring.postapi.entities.Post;
import hr.spring.postapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService service;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) String localisation, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) String title) {
        List<Post> posts = new ArrayList<>();
        if (date != null) {
            posts = service.getPostsDate(date);
        }
        else if (localisation != null) {
            posts = service.getPostsByLocationContainingIgnoreCase(localisation);
        }
        else if (title != null) {
            posts = service.getPostsByTilteLike(title);
        }
        else {
            posts = service.getPosts();
        }

        if(posts.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }
        return  ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/between")
    public ResponseEntity<List<Post>> getPostBetweenDates(@RequestParam(required = true) LocalDate start, @RequestParam(required = true) LocalDate end) {
        List<Post> posts = new ArrayList<>();
        if (start != null || end != null) {
            if(start == null) {
                start = LocalDate.ofEpochDay(1700-01-01);
            }
            else if(end == null) {
                end = LocalDate.ofEpochDay(9999-01-01);
            }
            posts = service.getPostsBetweenDate(start, end);
        }
        else {
            return ResponseEntity.noContent().build(); //204
        }
        return  ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id){
        Post pst = service.getPost(id);
        return ResponseEntity.ok(pst);
    }
    @GetMapping("/posts/oldest")
    public ResponseEntity<Object> getOldestPost(){
        Post pst = service.getOldestPost();
        return ResponseEntity.ok(pst);
    }
    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post pst){
        Post post = new Post(pst.getTitle(),  pst.getDescription(), pst.getUrl(), pst.getLike(), pst.getLocation());
        Post savedPost = service.savePost(post);
        return ResponseEntity.created(URI.create("/api/post/"+savedPost.getId())).body(savedPost);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> replace(@PathVariable int id, @RequestBody Post post){
        Post existingPost = service.getPost(id);
        existingPost.setTitle(post.getTitle());
        existingPost.setDescription(post.getDescription());
        existingPost.setUrl(post.getUrl());
        existingPost.setLike(post.getLike());
        existingPost.setLocation(post.getLocation());

        Post savedPost = service.savePost(existingPost);
        return ResponseEntity.ok(savedPost);
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<Post> updatePartiel(@PathVariable int id, @RequestBody Post post){
        Post existingPost = service.getPost(id);
        if(post.getTitle() != null){
            existingPost.setTitle(post.getTitle());
        }
        if(post.getDescription() != null){
            existingPost.setDescription(post.getDescription());
        }
        if(post.getUrl() != null){
            existingPost.setUrl(post.getUrl());
        }
        if(post.getLike() != 0){
            existingPost.setLike(post.getLike());
        }
        if(post.getLocation() != null){
            existingPost.setLocation(post.getLocation());
        }
        Post savedPost = service.savePost(existingPost);
        return ResponseEntity.ok(savedPost);
    }

    @PatchMapping("/posts/{id}/like")
    public ResponseEntity<Post> like(@PathVariable int id){
        Post existingPost = service.getPost(id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }
            existingPost.setLike(existingPost.getLike() + 1);
        Post savedPost = service.savePost(existingPost);
        return ResponseEntity.ok(savedPost);
    }
    @PatchMapping("/posts/{id}/dislike")
    public ResponseEntity<Post> dislike(@PathVariable int id){
        Post existingPost = service.getPost(id);
        if (existingPost.getLike() >= 1) {
            existingPost.setLike(existingPost.getLike() - 1);
        }
        Post savedPost = service.savePost(existingPost);
        return ResponseEntity.ok(savedPost);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        service.deletePost(id);
        return ResponseEntity.noContent().build();//204
    }
    @DeleteMapping("/posts")
    public ResponseEntity<Object> deleteAll(){
        service.deleteAllPosts();
        return ResponseEntity.noContent().build();//204
    }
}
