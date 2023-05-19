package main.dao;

import main.common.ElementNotFoundException;
import main.domain.modelo.Artist;

import java.io.IOException;
import java.util.List;

public interface IDaoArtist {
    boolean insertArtist(Artist artist);
    List<Artist> getAllArtists();
    boolean updateArtist(Artist artist) throws ElementNotFoundException;
    boolean deleteArtist(Artist artist) throws ElementNotFoundException;
    void loadArtistsFromFile() throws IOException;
    void saveArtistsInFile() throws IOException;
}