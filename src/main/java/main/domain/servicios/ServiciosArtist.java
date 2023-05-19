package main.domain.servicios;

import lombok.AllArgsConstructor;
import main.common.Constantes;
import main.common.ElementNotFoundException;
import main.dao.DaoArtist;
import main.domain.modelo.Artist;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class ServiciosArtist implements IServiciosArtist {
    private DaoArtist daoArtist;

    @Override
    public String insertArtist(Artist artist) {
        String mensaje;
        if (artist.getNombre() != null && !artist.getNombre().isEmpty()) {
            if (artistNameAlreadyExists(artist.getNombre())) {
                mensaje = "El artista ya existe";
            } else {
                if (daoArtist.insertArtist(artist)) {
                    mensaje = "Artista insertado correctamente";
                } else {
                    mensaje = "El artista no se ha podido insertar";
                }
            }
        } else {
            mensaje = Constantes.EL_NOMBRE_DEL_ARTISTA_NO_PUEDE_ESTAR_VACIO;
        }
        return mensaje;
    }

    private boolean artistNameAlreadyExists(String nombre) {
        return daoArtist.artistNameAlreadyExists(nombre);
    }

    @Override
    public List<Artist> getAllArtists() {
        return daoArtist.getAllArtists();
    }

    @Override
    public String updateArtist(Artist artist) throws ElementNotFoundException {
        String mensaje;
        if (artist.getNombre() == null || artist.getNombre().isEmpty()) {
            mensaje = Constantes.EL_NOMBRE_DEL_ARTISTA_NO_PUEDE_ESTAR_VACIO;
        } else if (daoArtist.updateArtist(artist)) {
            mensaje = "Artista actualizado correctamente";
        } else {
            mensaje = "El artista no se ha podido actualizar";
        }
        return mensaje;
    }

    @Override
    public String deleteArtist(Artist artist) throws ElementNotFoundException {
        String message;
        if (artist.getNombre() == null || artist.getNombre().isEmpty()) {
            message = Constantes.EL_NOMBRE_DEL_ARTISTA_NO_PUEDE_ESTAR_VACIO;
        } else if (daoArtist.deleteArtist(artist)) {
            message = "Artista eliminado correctamente";
        } else {
            message = "El artista no se ha podido eliminar";
        }
        return message;
    }

    public boolean deleteArtistByID(int id) throws ElementNotFoundException {
        return daoArtist.deleteArtistByID(id);
    }

    public void saveArtistsInFile() throws IOException {
        daoArtist.saveArtistsInFile();
    }

    public void loadArtistsFromFile() throws IOException {
        daoArtist.loadArtistsFromFile();
    }

    public boolean loadFileArtists() throws IOException {
        return daoArtist.loadFileArtists();
    }

    public List<Artist> listarArtistasDeGira() {
        return daoArtist.listarArtistasDeGira();
    }

    public List<Artist> buscarArtistasDeGiraPorNombre(String nombreArtista) {
        return daoArtist.buscarArtistasDeGiraPorNombre(nombreArtista);
    }

    public List<Artist> buscarArtistasGiraConConciertosAntesDeHora(String hora) {
        return daoArtist.buscarArtistasGiraConConciertosAntesDeHora(hora);
    }
}