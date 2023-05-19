package main.domain.modelo;

import lombok.*;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Artist implements Serializable, Comparable<Artist> {
    private int id;
    private String nombre;
    private boolean tour;

    public Artist(String nombre) {
        this.id = 0;
        this.nombre = nombre;
        this.tour = false;
    }

    public Artist(String nombre, boolean tour) {
        this.id = 0;
        this.nombre = nombre;
        this.tour = tour;
    }

    public String toFileString() {
        return id + ";" + nombre + ";" + tour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id == artist.id || Objects.equals(nombre, artist.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, tour);
    }

    @Override
    public int compareTo(Artist artist) {
        //compare by id and name
        return Comparator.comparing(Artist::getNombre)
                .thenComparing(Artist::getId)
                .compare(this, artist);
    }

    @Override
    public String toString() {
        return "\n" + nombre;
    }
}