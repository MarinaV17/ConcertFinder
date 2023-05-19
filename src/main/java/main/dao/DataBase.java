package main.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import main.common.Comprobacion;
import main.common.Constantes;
import main.common.ElementNotFoundException;
import main.config.Configuration;
import main.domain.modelo.Artist;
import main.domain.modelo.Concert;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class DataBase {
    private static DataBase instance;
    private Configuration configuration;
    private HashMap<Integer, Artist> artistList;
    private List<Concert> concertList;

    private DataBase(Configuration configuration) {
        this.configuration = configuration;
        this.artistList = new HashMap<>();
        this.concertList = new ArrayList<>();
    }

    public boolean insertArtist(Artist artist) {
        if (!artistList.isEmpty()) {
            List<Artist> orderedArtists = artistList.values().stream().sorted(Comparator.comparing(Artist::getId)).toList();
            artist.setId(orderedArtists.get(orderedArtists.size() - 1).getId() + 1);
        } else {
            artist.setId(1);
        }
        artistList.put(artist.getId(), artist);

        return true;
    }

    public List<Artist> getAllArtists() {
        return new ArrayList<>(artistList.values());
    }

    public boolean updateArtist(Artist artist) throws ElementNotFoundException {
        boolean success = false;
        try {
            Comprobacion.artistFoundOK(artistList, artist.getId());
            List<Artist> artistArrayList = getAllArtists();
            int index = artistArrayList.indexOf(artist);
            if (index < artistArrayList.size() && index >= 0) {
                success = true;
                Artist oldArtist = artistArrayList.get(index);
                //actualizamos el nombre en sus conciertos
                concertList.forEach(concert -> {
                    if (concert.getArtistName().equals(oldArtist.getNombre())) {
                        concert.setArtistName(artist.getNombre());
                    }
                });
                //actualizamos el artista
                artistList.put(artist.getId(), artist);
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return success;
    }

    public boolean deleteArtist(Artist artist) throws ElementNotFoundException {
        boolean success;
        try {
            Comprobacion.artistFoundOK(artistList, artist.getId());
            success = artistList.values().remove(artist);
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return success;
    }

    public boolean deleteArtistByID(int id) throws ElementNotFoundException {
        boolean success;
        Artist artist = new Artist();
        artist.setId(id);
        try {
            Comprobacion.artistFoundOK(artistList, artist.getId());
            Artist artistToRemove = artistList.get(artistList.keySet().stream().filter(key -> artistList.get(key).equals(artist)).findFirst().orElse(null));
            if (artistToRemove!=null) {
                //borramos sus conciertos
                concertList.removeIf(concert -> concert.getArtistName().equals(artistToRemove.getNombre()));
                //borramos el artista
                success = artistList.values().remove(artist);
            } else {
                success = false;
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return success;
    }

    public boolean insertConcert(Concert concert) {
        if (!concertList.isEmpty()) {
            List<Concert> orderedConcerts = concertList.stream().sorted(Comparator.comparing(Concert::getId)).toList();
            concert.setId(orderedConcerts.get(orderedConcerts.size() - 1).getId() + 1);
        } else {
            concert.setId(1);
        }
        return concertList.add(concert);
    }

    public List<Concert> getAllConcerts() {
        return concertList;
    }

    public boolean updateConcert(Concert concert) throws ElementNotFoundException {
        boolean success = false;
        try {
            Comprobacion.concertFoundOK(concertList, concert.getId());
            List<Concert> concertArrayList = getAllConcerts();
            int index = concertArrayList.indexOf(concert);
            if (index < concertArrayList.size() && index >= 0) {
                success = true;
                concertList.set(index, concert);
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return success;
    }

    public boolean deleteConcert(Concert concert) throws ElementNotFoundException {
        boolean success;
        try {
            Comprobacion.concertFoundOK(concertList, concert.getId());
            success = concertList.remove(concert);
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return success;
    }

    public boolean deleteConcertByID(int id) throws ElementNotFoundException {
        Concert concert = new Concert(id);
        concert.setId(id);
        boolean success;
        try {
            Comprobacion.concertFoundOK(concertList, concert.getId());
            success = concertList.remove(concert);
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return success;
    }

    public boolean isLoginAdmin(String userName, String password) {
        return userName.equals(configuration.getAdminUser()) && password.equals(configuration.getAdminPass());
    }

    public static DataBase getInstance(Configuration configuration) {
        if (instance == null) {
            instance = new DataBase(configuration);
        }
        return instance;
    }

    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalTime.class,
                        (JsonDeserializer<LocalTime>) (json, type, jsonDeserializationContext) ->
                                LocalTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(Constantes.TIME_FORMAT)))
                .registerTypeAdapter(LocalTime.class,
                        (JsonSerializer<LocalTime>) (localTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localTime.format(DateTimeFormatter.ofPattern(Constantes.TIME_FORMAT))))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern(Constantes.DATE_FORMAT)))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(Constantes.DATE_FORMAT))))
                .setPrettyPrinting()
                .create();
    }

    public boolean createFile(String fileName) throws IOException {
        boolean fileCreated;
        File file = new File(configuration.getPathFile() + fileName);
        try {
            fileCreated = file.createNewFile();
            if (file.exists()) {
                fileCreated = true;
            }
        } catch (IOException e) {
            throw new IOException("File not found");
        }
        return fileCreated;
    }

    public void saveArtistsInFile() throws IOException {
        try {
            PrintWriter printWriter = new PrintWriter(configuration.getPathFile() + Constantes.ARTISTS);
            artistList.forEach((artistID, artist) -> printWriter.write(artist.toFileString() + "\n"));
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }

    public void saveConcertsInFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(configuration.getPathFile() + Constantes.CONCERTS)) {
            createGson().toJson(concertList, fileWriter);
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }

    public void loadArtistsFromFile() throws IOException {
        try (Scanner scanner = new Scanner(new File(configuration.getPathFile() + Constantes.ARTISTS))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(Constantes.SEPARADOR_PUNTO_Y_COMA);
                Artist artist = new Artist(Integer.parseInt(parts[0]), parts[1], Boolean.parseBoolean(parts[2]));
                artistList.put(artist.getId(), artist);
            }
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }

    public void loadConcertsFromFile() throws IOException {
        Type concertListType = new TypeToken<ArrayList<Concert>>() {
        }.getType();
        try (FileReader fileReader = new FileReader(configuration.getPathFile() + Constantes.CONCERTS)) {
            concertList = createGson().fromJson(fileReader, concertListType);
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        }
    }

    public List<Artist> listarArtistasDeGira() {
        return artistList.values().stream().filter(Artist::isTour).toList();
    }

    public List<Concert> buscarConciertosEnCiudad(String ciudad) {
        return concertList.stream().filter(concert -> concert.getCity().equalsIgnoreCase(ciudad)).toList();
    }

    public List<Artist> buscarArtistasDeGiraPorNombre(String nombreArtista) {
        return artistList.values().stream().filter(artist -> artist.getNombre().contains(nombreArtista) && artist.isTour()).toList();
    }

    public List<Concert> buscarConciertosEnCiudadEnFecha(String ciudad, String fecha) {
        return concertList.stream().filter(concert -> concert.getCity().equals(ciudad) && concert.getLocalDateWithFormat().equals(fecha)).toList();
    }

    public List<Artist> buscarArtistasGiraConConciertosAntesDeHora(String hora) {
        return artistList.values().stream().filter(artist -> artist.isTour() &&
                concertList.stream()
                        .filter(concert -> concert.getLocalTime().isBefore(LocalTime.parse(hora)))
                        .anyMatch(concert -> concert.getArtistName().equals(artist.getNombre()))
        ).toList();
    }

    public List<Concert> buscarConciertosEnCiudadDeArtista(String ciudad, String nombreArtista) {
        return concertList.stream().filter(concert -> concert.getCity().equals(ciudad) && concert.getArtistName().equals(nombreArtista)).toList();
    }

    public Map<Integer, Artist> getArtistsHashMap() {
        return artistList;
    }

    public boolean artistNameAlreadyExists(String nombre) {
        return artistList.values().stream().anyMatch(artist -> artist.getNombre().equals(nombre));
    }

    public Concert getConcertByID(int id) throws ElementNotFoundException {
        Concert concertByID = new Concert(id);
        try {
            Comprobacion.concertFoundOK(concertList, id);
            if (concertList.stream().anyMatch(concert -> concert.getId() == id)) {
                concertByID = concertList.stream().filter(concert -> concert.getId() == id).findFirst().orElseThrow(ElementNotFoundException::new);
            }
        } catch (ElementNotFoundException e) {
            throw new ElementNotFoundException();
        }
        return concertByID;
    }

    public List<Concert> buscarConciertosEnMesSiguiente() {
        return concertList.stream().filter(concert -> concert.getLocalDate().isAfter(LocalDate.now()) && concert.getLocalDate().isBefore(LocalDate.now().plusMonths(1))).toList();
    }

    public boolean getConcertsByArtistName(String nombreArtista) {
        return concertList.stream().anyMatch(concert -> concert.getArtistName().equals(nombreArtista));
    }

    public boolean isUserPremium(String userName) {
        return !configuration.getPremiumNames().isEmpty() && configuration.getPremiumNames().contains(userName);
    }




}