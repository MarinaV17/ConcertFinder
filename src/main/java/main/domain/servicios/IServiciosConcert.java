package main.domain.servicios;

import main.common.ElementNotFoundException;
import main.domain.modelo.Concert;

import java.io.IOException;
import java.util.List;

public interface IServiciosConcert {
    public boolean insertConcert(Concert concert);
    public List<Concert> listConcerts();
    public boolean updateConcert(Concert concert) throws ElementNotFoundException;
    public boolean deleteConcert(Concert concert) throws ElementNotFoundException;
    public void loadConcertsFromFile() throws IOException;
    public void saveConcertsInFile() throws IOException;
}