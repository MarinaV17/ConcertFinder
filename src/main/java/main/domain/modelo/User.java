package main.domain.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public abstract class User {
    private int id;
    private String userName;
    private List<Artist> artistList;
    private List<Concert> concertList;

    protected User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    protected User(String userName) {
        this.userName = userName;
    }
}