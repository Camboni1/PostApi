package hr.spring.postapi.service;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(int id) {
        super("Le post avec l'id " + id+" n'existe pas");
    }
}
