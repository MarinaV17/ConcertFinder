package main.domain.modelo;

import java.util.List;

public class UserPremium extends User {
    private int discountPercentage;

    public UserPremium(int id, String userName, List<Artist> artistList, List<Concert> concertList, int discountPercentage) {
        super(id, userName, artistList, concertList);
        this.discountPercentage = discountPercentage;
    }

    public UserPremium(String userName) {
        super(userName);
        this.discountPercentage = 20;
    }

    public int getPDiscount() {
        return discountPercentage;
    }

    public void setPDiscount(int pDiscount) {
        this.discountPercentage = pDiscount;
    }

    @Override
    public String toString() {
        return "UserPremium{" + super.toString() +
                "discountPercentage=" + discountPercentage +
                '}';
    }
}