package hr.spring.postapi.logic;

import org.springframework.stereotype.Service;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(int id) {
        super("Le post avec l'id " + id+" n'existe pas");
    }
}
