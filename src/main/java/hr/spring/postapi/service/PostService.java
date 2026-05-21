package hr.spring.postapi.service;

import hr.spring.postapi.dto.PostResponse;
import hr.spring.postapi.entities.Post;
import hr.spring.postapi.entities.User;
import hr.spring.postapi.enums.AppRole;
import hr.spring.postapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostResponse> getPosts(String localisation, LocalDate date, String title) {
        if (date != null) return toResponseList(postRepository.findByDate(date));
        if (localisation != null) return toResponseList(postRepository.findByLocationContainingIgnoreCase(localisation));
        if (title != null) return toResponseList(postRepository.findByTitleLike(title));
        return toResponseList(postRepository.findAll());
    }

    public List<PostResponse> getPostsBetween(LocalDate start, LocalDate end) {
        return toResponseList(postRepository.findByDateGreaterThanEqual(start, end));
    }

    public PostResponse getPost(int id) {
        return PostResponse.from(getPostEntity(id));
    }

    public PostResponse getOldestPost() {
        return PostResponse.from(postRepository.orderByDateAscLimit1());
    }

    public PostResponse createPost(Post pst, User author) {
        Post post = new Post(pst.getTitle(), pst.getDescription(), pst.getUrl(), pst.getLike(), pst.getLocation());
        post.setUser(author);
        post.setDate(LocalDate.now());
        return PostResponse.from(postRepository.save(post));
    }

    public PostResponse replacePost(int id, Post update, User currentUser) {
        Post existing = getPostEntity(id);
        ensureAuthor(existing, currentUser);
        existing.setTitle(update.getTitle());
        existing.setDescription(update.getDescription());
        existing.setUrl(update.getUrl());
        existing.setLike(update.getLike());
        existing.setLocation(update.getLocation());
        return PostResponse.from(postRepository.save(existing));
    }

    public PostResponse partialUpdate(int id, Post update, User currentUser) {
        Post existing = getPostEntity(id);
        ensureAuthor(existing, currentUser);
        if (update.getTitle() != null) existing.setTitle(update.getTitle());
        if (update.getDescription() != null) existing.setDescription(update.getDescription());
        if (update.getUrl() != null) existing.setUrl(update.getUrl());
        if (update.getLike() != 0) existing.setLike(update.getLike());
        if (update.getLocation() != null) existing.setLocation(update.getLocation());
        return PostResponse.from(postRepository.save(existing));
    }

    public PostResponse likePost(int id) {
        Post post = getPostEntity(id);
        post.setLike(post.getLike() + 1);
        return PostResponse.from(postRepository.save(post));
    }

    public PostResponse dislikePost(int id) {
        Post post = getPostEntity(id);
        if (post.getLike() >= 1) post.setLike(post.getLike() - 1);
        return PostResponse.from(postRepository.save(post));
    }

    public void deletePost(int id, User currentUser) {
        Post existing = getPostEntity(id);
        ensureAuthorOrAdmin(existing, currentUser);
        postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    private void ensureAuthor(Post post, User currentUser) {
        if (!isAuthor(post, currentUser)) {
            throw new AccessDeniedException("Seul l'utilisateur qui a créé le post peut le modifier");
        }
    }

    private void ensureAuthorOrAdmin(Post post, User currentUser) {
        if (!isAuthor(post, currentUser) && !isAdmin(currentUser)) {
            throw new AccessDeniedException("Seul l'utilisateur qui a créé le post ou un admin peut le supprimer");
        }
    }

    private boolean isAuthor(Post post, User currentUser) {
        return currentUser != null
                && post.getUser() != null
                && post.getUser().getId().equals(currentUser.getId());
    }

    private boolean isAdmin(User currentUser) {
        return currentUser != null && currentUser.getRole() == AppRole.ADMIN;
    }

    private Post getPostEntity(int id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    private List<PostResponse> toResponseList(List<Post> posts) {
        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }
}
