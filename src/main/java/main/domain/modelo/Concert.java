package main.domain.modelo;

import lombok.*;
import main.common.Constantes;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class Concert implements Serializable, Comparable<Concert> {
    private int id;
    private String artistName;
    private String city;
    private String venue;
    private LocalDate localDate;
    private LocalTime localTime;
    private int availableTickets;

    public Concert(int id, String artistName, String city, LocalDate date, LocalTime time) {
        this.id = id;
        this.artistName = artistName;
        this.city = city;
        this.localDate = date;
        this.localTime = time;
        this.availableTickets = 100;
    }

    public String getLocalDateWithFormat() {
        return localDate.format(DateTimeFormatter.ofPattern(Constantes.DATE_FORMAT));
    }

    public String getLocalTimeWithFormat() {
        return localTime.format(DateTimeFormatter.ofPattern(Constantes.TIME_FORMAT));
    }

    public Concert(int id, String artistName, String city) {
        this.id = id;
        this.artistName = artistName;
        this.city = city;
        this.availableTickets = 100;
    }

    public Concert(int id) {
        this.id = id;
        this.availableTickets = 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Concert concert = (Concert) o;
        return id == concert.id || (Objects.equals(artistName, concert.artistName) && Objects.equals(city, concert.city) && Objects.equals(localDate, concert.localDate) && Objects.equals(localTime, concert.localTime));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artistName, city, venue, localDate, localTime);
    }

    @Override
    public int compareTo(Concert concert) {
        return Integer.compare(this.id, concert.id);
    }

    @Override
    public String toString() {
        return "\n" + artistName + " - " + city + " - " + localDate.format(DateTimeFormatter.ofPattern(Constantes.DATE_FORMAT)) + " a las " + localTime.format(DateTimeFormatter.ofPattern(Constantes.TIME_FORMAT)) + " - " + availableTickets + " entradas disponibles" ;
    }
}