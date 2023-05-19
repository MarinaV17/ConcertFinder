package main.dao;

import main.common.ElementNotFoundException;
import main.domain.modelo.Concert;

import java.io.IOException;
import java.util.List;

public interface IDaoConcert {
    boolean insertConcert(Concert concert);

    List<Concert> listConcerts();

    boolean updateConcert(Concert concert) throws ElementNotFoundException;

    boolean deleteConcert(Concert concert) throws ElementNotFoundException;
    void loadConcertsFromFile() throws IOException;
    void saveConcertsInFile() throws IOException;
}