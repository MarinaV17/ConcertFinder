package main.common;

import main.domain.modelo.Artist;
import main.domain.modelo.Concert;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Comprobacion {

    private Comprobacion() {
    }

    public static void artistFoundOK(Map<Integer, Artist> artistList, int id) throws ElementNotFoundException {
        if (id > artistList.values().size() && artistList.values().stream().noneMatch(artist -> artist.getId() == id)) {
            throw new ElementNotFoundException(id);
        }
    }

    public static void concertFoundOK(List<Concert> concertList, int id) throws ElementNotFoundException {
        Concert concert = new Concert(id);
        if (id > concertList.size() || !concertList.contains(concert)) {
            throw new ElementNotFoundException(id);
        }
    }

    public static boolean comprobarEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}