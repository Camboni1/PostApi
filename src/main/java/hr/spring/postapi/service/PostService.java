package hr.spring.postapi.service;

import hr.spring.postapi.entities.Post;
import hr.spring.postapi.entities.User;
import hr.spring.postapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getPosts(String localisation, LocalDate date, String title) {
        if (date != null) return new ArrayList<>(postRepository.findByDate(date));
        if (localisation != null) return new ArrayList<>(postRepository.findByLocationContainingIgnoreCase(localisation));
        if (title != null) return new ArrayList<>(postRepository.findByTitleLike(title));
        return new ArrayList<>(postRepository.findAll());
    }

    public List<Post> getPostsBetween(LocalDate start, LocalDate end) {
        return new ArrayList<>(postRepository.findByDateGreaterThanEqual(start, end));
    }

    public Post getPost(int id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post getOldestPost() {
        return postRepository.orderByDateAscLimit1();
    }

    public Post createPost(Post pst, User author) {
        Post post = new Post(pst.getTitle(), pst.getDescription(), pst.getUrl(), pst.getLike(), pst.getLocation());
        post.setUser(author);
        post.setDate(LocalDate.now());
        return postRepository.save(post);
    }

    public Post replacePost(int id, Post update) {
        Post existing = getPost(id);
        existing.setTitle(update.getTitle());
        existing.setDescription(update.getDescription());
        existing.setUrl(update.getUrl());
        existing.setLike(update.getLike());
        existing.setLocation(update.getLocation());
        return postRepository.save(existing);
    }

    public Post partialUpdate(int id, Post update) {
        Post existing = getPost(id);
        if (update.getTitle() != null) existing.setTitle(update.getTitle());
        if (update.getDescription() != null) existing.setDescription(update.getDescription());
        if (update.getUrl() != null) existing.setUrl(update.getUrl());
        if (update.getLike() != 0) existing.setLike(update.getLike());
        if (update.getLocation() != null) existing.setLocation(update.getLocation());
        return postRepository.save(existing);
    }

    public Post likePost(int id) {
        Post post = getPost(id);
        post.setLike(post.getLike() + 1);
        return postRepository.save(post);
    }

    public Post dislikePost(int id) {
        Post post = getPost(id);
        if (post.getLike() >= 1) post.setLike(post.getLike() - 1);
        return postRepository.save(post);
    }

    public void deletePost(int id) {
        getPost(id);
        postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }
}
