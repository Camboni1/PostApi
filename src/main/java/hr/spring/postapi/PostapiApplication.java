package hr.spring.postapi;

import hr.spring.postapi.repository.PostRepository;
import hr.spring.postapi.service.PostService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PostapiApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(PostapiApplication.class, args);
        PostRepository emplRepo = context.getBean("postRepository",  PostRepository.class);

        PostService pstSvc = context.getBean("postService", PostService.class);

    }
}
