package hr.spring.postapi.common;

import hr.spring.postapi.entities.Post;
import hr.spring.postapi.entities.User;
import hr.spring.postapi.enums.AppRole;
import hr.spring.postapi.repository.UserRepository;
import hr.spring.postapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostService postService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@postapi.com");
        admin.setPasswordHash(passwordEncoder.encode("Admin1234!"));
        admin.setRole(AppRole.ADMIN);
        userRepository.save(admin);

        User alice = new User();
        alice.setUsername("alice");
        alice.setEmail("alice@postapi.com");
        alice.setPasswordHash(passwordEncoder.encode("Alice1234!"));
        userRepository.save(alice);

        User bob = new User();
        bob.setUsername("bob");
        bob.setEmail("bob@postapi.com");
        bob.setPasswordHash(passwordEncoder.encode("Bob12345!"));
        userRepository.save(bob);

        postService.createPost(buildPost("Lever de soleil à Kyoto", "Un matin magique dans les rues de Gion.", "https://example.com/kyoto.jpg", "Kyoto, Japon"), alice);
        postService.createPost(buildPost("Randonnée en Patagonie", "Trekking W dans le parc Torres del Paine.", "https://example.com/patagonie.jpg", "Patagonie, Chili"), alice);
        postService.createPost(buildPost("Street food à Bangkok", "Les meilleurs stands de Khao San Road.", "https://example.com/bangkok.jpg", "Bangkok, Thaïlande"), alice);
        postService.createPost(buildPost("Désert du Sahara", "Nuit sous les étoiles dans les dunes.", "https://example.com/sahara.jpg", "Merzouga, Maroc"), bob);
        postService.createPost(buildPost("Aurores boréales en Islande", "Spectacle incroyable près de Reykjavik.", "https://example.com/islande.jpg", "Islande"), bob);
        postService.createPost(buildPost("Carnaval de Rio", "La fête la plus folle du monde.", "https://example.com/rio.jpg", "Rio de Janeiro, Brésil"), bob);
    }

    private Post buildPost(String title, String description, String url, String location) {
        Post p = new Post();
        p.setTitle(title);
        p.setDescription(description);
        p.setUrl(url);
        p.setLocation(location);
        p.setLike(0);
        return p;
    }
}
