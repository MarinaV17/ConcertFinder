package main.domain.servicios;

import main.common.ElementNotFoundException;
import main.domain.modelo.Artist;

import java.io.IOException;
import java.util.List;

public interface IServiciosArtist {
    public String insertArtist(Artist artist);
    public List<Artist> getAllArtists();
    public String updateArtist(Artist artist) throws ElementNotFoundException;
    public String deleteArtist(Artist artist) throws ElementNotFoundException;
    public boolean deleteArtistByID(int id) throws ElementNotFoundException;
    public void loadArtistsFromFile() throws IOException;
    public void saveArtistsInFile() throws IOException;
}