package hr.spring.postapi.service;

import hr.spring.postapi.repository.PostRepository;
import hr.spring.postapi.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getPosts() {
        List<Post> Posts = new ArrayList<>();
        for (Post pst : postRepository.findAll()) {
            Posts.add(pst);
        }
        return Posts;
    }
    public List<Post> getPostsLocalisation(String localisation) {
        List<Post> Posts = new ArrayList<>();
        for (Post pst : postRepository.findByLocation(localisation)) {
            Posts.add(pst);
        }
        return Posts;
    }
    public List<Post> getPostsDate(LocalDate date) {
        List<Post> Posts = new ArrayList<>();
        for (Post pst : postRepository.findByDate(date)) {
            Posts.add(pst);
        }
        return Posts;
    }
    public Post getOldestPost() {
        return postRepository.orderByDateAscLimit1();
    }
    public List<Post> getPostsByTilteLike(String title) {
        return new ArrayList<>(postRepository.findByTitleLike(title));
    }
    public List<Post> getPostsByLocationContainingIgnoreCase(String location) {
        return new ArrayList<>(postRepository.findByLocationContainingIgnoreCase(location));
    }
    public List<Post> getPostsBetweenDate(LocalDate startDate, LocalDate endDate) {
        List<Post> Posts = new ArrayList<>(postRepository.findByDateGreaterThanEqual(startDate, endDate));
        return Posts;
    }
    public Post getPost(int id) {
        return postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id));
    }
    public void deletePost(int id) {
        if (postRepository.findById(id).isEmpty()) {
            throw new PostNotFoundException(id);
        }
        postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        List<Post> list = getPosts();
        for (Post pst : list) {
            deletePost(pst.getId());
        }
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

}
