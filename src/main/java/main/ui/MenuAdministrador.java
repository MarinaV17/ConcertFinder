package main.ui;

import lombok.AllArgsConstructor;
import main.common.Constantes;
import main.common.ElementNotFoundException;
import main.domain.modelo.Artist;
import main.domain.modelo.Concert;
import main.domain.servicios.ServiciosArtist;
import main.domain.servicios.ServiciosConcert;
import main.domain.servicios.ServiciosUserAdmin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

@AllArgsConstructor
public class MenuAdministrador {
    private final ServiciosUserAdmin serviciosAdministrador;
    private final ServiciosArtist serviciosArtist;
    private final ServiciosConcert serviciosConcert;

    public boolean loginAdmin(String username, String password) {
        return serviciosAdministrador.isLoginAdmin(username, password);
    }

    public boolean loadFileArtists() {
        boolean loadSuccess = false;
        try {
            loadSuccess = serviciosArtist.loadFileArtists();
            loadArtists();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return loadSuccess;
    }

    public void loadArtists() {
        try {
            serviciosArtist.loadArtistsFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean loadFileConcerts() {
        boolean loadSuccess = false;
        try {
            loadSuccess = serviciosConcert.loadFileConcerts();
            loadConcerts();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return loadSuccess;
    }

    public void loadConcerts() {
        try {
            serviciosConcert.loadConcertsFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean displayMenuAdmin() {
        Scanner scanner = new Scanner(System.in);
        boolean success;
        boolean exit = false;
        try {
            do {
                System.out.println("Elige el menú de gestión de:");
                System.out.println("1- Artistas");
                System.out.println("2- Conciertos");
                System.out.println("3- Usuarios");
                System.out.println("0- Salir");
                int option = scanner.nextInt();
                scanner.nextLine();
                success = option == 1 || option == 2 || option == 3 || option == 0;
                switch (option) {
                    case 1 -> {
                        do {
                            success = menuGestionArtistas();
                        } while (!success);
                    }
                    case 2 -> {
                        do {
                            success = menuGestionConciertos();
                        } while (!success);
                    }
                    case 0 -> exit = true;
                    default -> System.out.println(Constantes.INTRODUCE_UNA_OPCION_CORRECTA);
                }
            } while (!exit);
        } catch (InputMismatchException e) {
            System.out.println(Constantes.INTRODUCE_UNA_OPCION_CORRECTA);
            success = false;
        }
        return success;
    }

    public boolean menuGestionArtistas() {
        Scanner scanner = new Scanner(System.in);
        boolean optionSuccess = true;
        boolean exit = false;
        try {
            do {
                System.out.println("Menú de gestión de ARTISTAS:");
                System.out.println(Constantes.ELIGE_LA_OPCION_QUE_QUIERAS);
                System.out.println("1- Añadir un nuevo artista");
                System.out.println("2- Visualizar el listado completo de artistas");
                System.out.println("3- Modificar un artista existente");
                System.out.println("4- Eliminar un artista del listado");
                System.out.println("0- Volver al menú principal");
                int option = scanner.nextInt();
                scanner.nextLine();
                optionSuccess = option == 1 || option == 2 || option == 3 || option == 4 || option == 0;
                switch (option) {
                    case 1 -> System.out.println(addArtist());
                    case 2 -> listArtists();
                    case 3 -> {
                        if (updateArtist()) {
                            System.out.println("Artista modificado correctamente");
                        } else {
                            System.out.println("No se ha podido modificar el artista");
                        }
                    }
                    case 4 -> {
                        if (deleteArtist()) {
                            System.out.println("Artista eliminado correctamente");
                        } else {
                            System.out.println("No se ha podido eliminar el artista");
                        }
                    }
                    case 0 -> exit = true;
                    default -> System.out.println(Constantes.INTRODUCE_UNA_OPCION_CORRECTA);
                }
            } while (!exit);
        } catch (InputMismatchException e) {
            System.out.println(Constantes.INTRODUCE_UNA_OPCION_CORRECTA);
        }
        return optionSuccess;
    }

    public String addArtist() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nombre del artista:");
        String artistName = scanner.nextLine();
        Artist artist = new Artist(0, artistName, false);
        return serviciosArtist.insertArtist(artist);
    }

    private void listArtists() {
        System.out.println(serviciosArtist.getAllArtists().toString());
    }

    private boolean updateArtist() {
        boolean updated = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID del artista a modificar:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nuevo nombre del artista:");
        String artistName = scanner.nextLine();
        boolean tourOK = false;
        boolean tour = false;
        do {
            System.out.println("¿Está el artista de gira? (S/N)");
            String onTour = scanner.nextLine();
            if (onTour.equalsIgnoreCase("S")) {
                tour = true;
                tourOK = true;
            } else if (onTour.equalsIgnoreCase("N")) {
                tourOK = true;
            } else {
                System.out.println("Opción incorrecta");
            }
        } while (!tourOK);
        Artist artist = new Artist(id, artistName, tour);
        try {
            if (serviciosArtist.updateArtist(artist).equals("Artista actualizado correctamente")) {
                updated = true;
            }
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return updated;
    }

    private boolean deleteArtist() {
        Scanner scanner = new Scanner(System.in);
        boolean deleted = false;
        System.out.println("ID del artista a eliminar:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            deleted = serviciosArtist.deleteArtistByID(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    private boolean menuGestionConciertos() {
        Scanner scanner = new Scanner(System.in);
        boolean optionSuccess = true;
        System.out.println("Menú de gestión de CONCIERTOS:");
        System.out.println(Constantes.ELIGE_LA_OPCION_QUE_QUIERAS);
        System.out.println("1- Añadir un nuevo concierto");
        System.out.println("2- Visualizar el listado completo de conciertos");
        System.out.println("3- Modificar un concierto existente");
        System.out.println("4- Eliminar un concierto del listado");
        try {
            int option = scanner.nextInt();
            scanner.nextLine();
            optionSuccess = option == 1 || option == 2 || option == 3 || option == 4;
            switch (option) {
                case 1 -> {
                    if (addConcert()) {
                        System.out.println("Concierto añadido correctamente");
                    } else {
                        System.out.println("No se ha podido añadir el concierto");
                    }
                }
                case 2 -> listConcerts();
                case 3 -> {
                    if (updateConcert()) {
                        System.out.println("Concierto modificado correctamente");
                    } else {
                        System.out.println("No se ha podido modificar el concierto");
                    }
                }
                case 4 -> {
                    if (deleteConcert()) {
                        System.out.println("Concierto eliminado correctamente");
                    } else {
                        System.out.println("No se ha podido eliminar el concierto");
                    }
                }
                default -> System.out.println(Constantes.INTRODUCE_UNA_OPCION_CORRECTA);
            }
        } catch (InputMismatchException e) {
            System.out.println(Constantes.INTRODUCE_UNA_OPCION_CORRECTA);
        }
        return optionSuccess;
    }

    public boolean addConcert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nombre del artista:");
        String artistName = scanner.nextLine();
        System.out.println("Ciudad del concierto:");
        String city = scanner.nextLine();
        LocalDate date;
        LocalTime time;
        try {
            System.out.println("Fecha del concierto:");
            date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern(Constantes.DATE_FORMAT));
            System.out.println("Hora del concierto:");
            time = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern(Constantes.TIME_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha u hora incorrecto");
            return false;
        }
        Concert concert = new Concert(0, artistName, city, date, time);
        return serviciosConcert.insertConcert(concert);
    }

    private void listConcerts() {
        System.out.println(serviciosConcert.listConcerts());
    }

    private boolean updateConcert() {
        Scanner scanner = new Scanner(System.in);
        boolean updated = false;
        System.out.println("ID del concierto a modificar:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nuevo nombre del artista:");
        String artistName = scanner.nextLine();
        System.out.println("Nueva ciudad del concierto:");
        String city = scanner.nextLine();
        LocalDate date;
        LocalTime time;
        try {
            System.out.println("Fecha del concierto:");
            date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern(Constantes.DATE_FORMAT));
            System.out.println("Hora del concierto:");
            time = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern(Constantes.TIME_FORMAT));
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha u hora incorrecto");
            return false;
        }
        Concert concert = new Concert(id, artistName, city, date, time);
        try {
            updated = serviciosConcert.updateConcert(concert);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return updated;
    }

    private boolean deleteConcert() {
        Scanner scanner = new Scanner(System.in);
        boolean deleted = false;
        System.out.println("ID del concierto a eliminar:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            deleted = serviciosConcert.deleteConcertByID(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return deleted;
    }

    public void saveArtists() {
        try {
            serviciosArtist.saveArtistsInFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveConcerts() {
        try {
            serviciosConcert.saveConcertsInFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}