package hr.spring.postapi.dto;

import hr.spring.postapi.entities.Post;

import java.time.LocalDate;

public record PostResponse(
        int id,
        String title,
        String description,
        String url,
        int like,
        String location,
        LocalDate date,
        UserResponse user
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getUrl(),
                post.getLike(),
                post.getLocation(),
                post.getDate(),
                UserResponse.from(post.getUser())
        );
    }
}
