package main.dao;

import main.common.Constantes;
import main.common.ElementNotFoundException;
import main.domain.modelo.Concert;

import java.io.IOException;
import java.util.List;

public class DaoConcert implements IDaoConcert {
    private final DataBase dataBase;
    public DaoConcert(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public boolean insertConcert(Concert concert) {
        return dataBase.insertConcert(concert);
    }

    @Override
    public List<Concert> listConcerts() {
        return dataBase.getAllConcerts();
    }

    @Override
    public boolean updateConcert(Concert concert) throws ElementNotFoundException {
        return dataBase.updateConcert(concert);
    }

    @Override
    public boolean deleteConcert(Concert concert) throws ElementNotFoundException {
        return dataBase.deleteConcert(concert);
    }

    public boolean deleteConcertByID(int id) throws ElementNotFoundException {
        return dataBase.deleteConcertByID(id);
    }

    public void loadConcertsFromFile() throws IOException {
        dataBase.loadConcertsFromFile();
    }

    public void saveConcertsInFile() throws IOException {
        dataBase.saveConcertsInFile();
    }

    public boolean loadFileConcerts() throws IOException {
        return dataBase.createFile(Constantes.CONCERTS);
    }

    public List<Concert> buscarConciertosEnCiudad(String ciudad) {
        return dataBase.buscarConciertosEnCiudad(ciudad);
    }

    public List<Concert> buscarConciertosEnCiudadEnFecha(String ciudad, String fecha) {
        return dataBase.buscarConciertosEnCiudadEnFecha(ciudad, fecha);
    }

    public List<Concert> buscarConciertosEnCiudadDeArtista(String ciudad, String nombreArtista) {
        return dataBase.buscarConciertosEnCiudadDeArtista(ciudad, nombreArtista);
    }

    public Concert getConcertByID(int idConcierto) throws ElementNotFoundException {
        return dataBase.getConcertByID(idConcierto);
    }

    public List<Concert> buscarConciertosEnMesSiguiente() {
        return dataBase.buscarConciertosEnMesSiguiente();
    }

    public boolean getConcertsByArtistName(String nombreArtista) {
        return dataBase.getConcertsByArtistName(nombreArtista);
    }
}