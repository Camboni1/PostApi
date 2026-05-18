package hr.spring.postapi;

import hr.spring.postapi.data.PostRepository;
import hr.spring.postapi.entities.Post;
import hr.spring.postapi.logic.PostService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PostapiApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(PostapiApplication.class, args);
        PostRepository emplRepo = context.getBean("postRepository",  PostRepository.class);

        PostService pstSvc = context.getBean("postService", PostService.class);

//        Post post1 = new Post("Vacances à la mer","En Espagne","www.blablabla.be",2,"Barcelone");
//        pstSvc.savePost(post1);

        //emplSvc.deleteEmployee(4L);

        Iterable<Post> Posts = emplRepo.findAll();
        for (Post pst : Posts) {
            System.out.println(pst.getTitle());
        }

    }
}
