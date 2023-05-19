package main.dao;

import main.common.Constantes;
import main.common.ElementNotFoundException;
import main.domain.modelo.Artist;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DaoArtist implements IDaoArtist {
    private final DataBase dataBase;
    public DaoArtist(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean insertArtist(Artist artist) {
        return dataBase.insertArtist(artist);
    }

    @Override
    public List<Artist> getAllArtists() {
        return dataBase.getAllArtists();
    }

    public Map<Integer, Artist> getArtistsHashMap() {
        return dataBase.getArtistsHashMap();
    }

    @Override
    public boolean updateArtist(Artist artist) throws ElementNotFoundException {
        return dataBase.updateArtist(artist);
    }

    @Override
    public boolean deleteArtist(Artist artist) throws ElementNotFoundException {
        return dataBase.deleteArtist(artist);
    }

    public boolean deleteArtistByID(int id) throws ElementNotFoundException {
        return dataBase.deleteArtistByID(id);
    }

    @Override
    public void loadArtistsFromFile() throws IOException {
        dataBase.loadArtistsFromFile();
    }

    @Override
    public void saveArtistsInFile() throws IOException {
        dataBase.saveArtistsInFile();
    }

    public boolean loadFileArtists() throws IOException {
        return dataBase.createFile(Constantes.ARTISTS);
    }

    public List<Artist> listarArtistasDeGira() {
        return dataBase.listarArtistasDeGira();
    }

    public List<Artist> buscarArtistasDeGiraPorNombre(String nombreArtista) {
        return dataBase.buscarArtistasDeGiraPorNombre(nombreArtista);
    }

    public List<Artist> buscarArtistasGiraConConciertosAntesDeHora(String hora) {
        return dataBase.buscarArtistasGiraConConciertosAntesDeHora(hora);
    }

    public boolean artistNameAlreadyExists(String nombre) {
        return dataBase.artistNameAlreadyExists(nombre);
    }

}