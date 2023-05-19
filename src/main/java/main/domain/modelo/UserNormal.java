package main.domain.modelo;

import java.util.List;

public class UserNormal extends User {
    public UserNormal(int id, String userName, List<Artist> artistList, List<Concert> concertList) {
        super(id, userName, artistList, concertList);
    }

    public UserNormal(String userName) {
        super(userName);
    }


}