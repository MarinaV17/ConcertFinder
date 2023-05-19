package main.domain.servicios;

import lombok.AllArgsConstructor;
import main.common.Comprobacion;
import main.common.ElementNotFoundException;
import main.dao.DaoConcert;
import main.domain.modelo.Concert;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class ServiciosConcert implements IServiciosConcert {
    private DaoConcert daoConcert;

    @Override
    public boolean insertConcert(Concert concert) {
        return daoConcert.insertConcert(concert);
    }

    @Override
    public List<Concert> listConcerts() {
        return daoConcert.listConcerts();
    }

    @Override
    public boolean updateConcert(Concert concert) throws ElementNotFoundException {
        return daoConcert.updateConcert(concert);
    }

    @Override
    public boolean deleteConcert(Concert concert) throws ElementNotFoundException {
        return daoConcert.deleteConcert(concert);
    }

    public boolean deleteConcertByID(int id) throws ElementNotFoundException {
        return daoConcert.deleteConcertByID(id);
    }

    @Override
    public void saveConcertsInFile() throws IOException {
        daoConcert.saveConcertsInFile();
    }

    @Override
    public void loadConcertsFromFile() throws IOException {
        daoConcert.loadConcertsFromFile();
    }

    public boolean loadFileConcerts() throws IOException {
        return daoConcert.loadFileConcerts();
    }

    public List<Concert> buscarConciertosEnCiudad(String ciudad) {
        return daoConcert.buscarConciertosEnCiudad(ciudad);
    }

    public List<Concert> buscarConciertosEnCiudadEnFecha(String ciudad, String fecha) {
        return daoConcert.buscarConciertosEnCiudadEnFecha(ciudad, fecha);
    }

    public List<Concert> buscarConciertosEnCiudadDeArtista(String ciudad, String nombreArtista) {
        return daoConcert.buscarConciertosEnCiudadDeArtista(ciudad, nombreArtista);
    }

    public String comprarEntradas(String nombreArtista, int idConcierto, int numEntradas, boolean userPremium, String email) throws ElementNotFoundException {
        String mensaje = "No se ha podido comprar las entradas";
        if (!Comprobacion.comprobarEmail(email)) {
            mensaje = "El email no es válido";
        } else {
            try {
                Concert concertByID = daoConcert.getConcertByID(idConcierto);
                Concert concert = concertByID == null ? null : checkArtistName(nombreArtista, concertByID);
                if (concert != null) {
                    int entradasDisponibles = concert.getAvailableTickets();
                    if (entradasDisponibles >= numEntradas) {
                        concert.setAvailableTickets(entradasDisponibles - numEntradas);
                        mensaje = cambiarMensajePorUser(numEntradas, userPremium, concert);
                    } else {
                        mensaje = "No hay suficientes entradas disponibles";
                    }
                }
            } catch (ElementNotFoundException e) {
                throw new ElementNotFoundException();
            }
        }
        return mensaje;
    }

    private String cambiarMensajePorUser(int numEntradas, boolean userPremium, Concert concert) {
        String mensaje;
        if (userPremium) {
            mensaje = "Se han comprado " + numEntradas + " entradas para el concierto de " + concert.getArtistName() + " con un descuento del 20%";
        } else {
            mensaje = "Se han comprado " + numEntradas + " entradas para el concierto de " + concert.getArtistName();
        }
        return mensaje;
    }

    public List<Concert> buscarConciertosEnMesSiguiente() {
        return daoConcert.buscarConciertosEnMesSiguiente();
    }
    private static Concert checkArtistName(String nombreArtista, Concert concertByID) {
        return concertByID.getArtistName().equalsIgnoreCase(nombreArtista) ? concertByID : null;
    }
}