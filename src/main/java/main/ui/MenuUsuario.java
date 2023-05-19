package main.ui;

import lombok.AllArgsConstructor;
import main.common.Constantes;
import main.common.ElementNotFoundException;
import main.domain.modelo.User;
import main.domain.modelo.UserNormal;
import main.domain.modelo.UserPremium;
import main.domain.servicios.ServiciosArtist;
import main.domain.servicios.ServiciosConcert;
import main.domain.servicios.ServiciosUserAdmin;

import java.util.InputMismatchException;
import java.util.Scanner;

@AllArgsConstructor
public class MenuUsuario {
    private final ServiciosUserAdmin serviciosUserAdmin;
    private final ServiciosArtist serviciosArtist;
    private final ServiciosConcert serviciosConcert;
    private final String userName;

    public boolean displayMenuUsuario() {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
        boolean exit = false;
        try {
            do {
                System.out.println("Introduce una opción:");
                System.out.println("1. Listar artistas");
                System.out.println("2. Listar conciertos");
                System.out.println("3. Ver artistas que están de gira");
                System.out.println("4. Buscar conciertos en una ciudad");
                System.out.println("5. Buscar artistas que estén de gira y cuyo nombre contenga algo");
                System.out.println("6. Buscar conciertos en una ciudad en una fecha");
                System.out.println("7. Artistas que estén de gira y que tengan conciertos que empiecen antes de una hora");
                System.out.println("8. Buscar conciertos en una ciudad de un artista");
                System.out.println("9. Ver los conciertos que habrá el próximo mes.");
                System.out.println("10. Comprar entradas para un concierto");
                System.out.println("0. Salir");
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1:
                        System.out.println(serviciosArtist.getAllArtists().toString());
                        break;
                    case 2:
                        System.out.println(serviciosConcert.listConcerts());
                        break;
                    case 3:
                        System.out.println(serviciosArtist.listarArtistasDeGira());
                        break;
                    case 4:
                        System.out.println(Constantes.INTRODUCE_UNA_CIUDAD);
                        String ciudad = scanner.nextLine();
                        System.out.println(serviciosConcert.buscarConciertosEnCiudad(ciudad));
                        break;
                    case 5:
                        System.out.println("Introduce el letras que deba contener el artista:");
                        String nombre = scanner.nextLine();
                        System.out.println(serviciosArtist.buscarArtistasDeGiraPorNombre(nombre));
                        break;
                    case 6:
                        System.out.println(Constantes.INTRODUCE_UNA_CIUDAD);
                        ciudad = scanner.nextLine();
                        System.out.println("Introduce una fecha:");
                        String fecha = scanner.nextLine();
                        System.out.println(serviciosConcert.buscarConciertosEnCiudadEnFecha(ciudad, fecha));
                        break;
                    case 7:
                        System.out.println("Introduce una hora máxima de comienzo del concierto:");
                        String hora = scanner.nextLine();
                        System.out.println(serviciosArtist.buscarArtistasGiraConConciertosAntesDeHora(hora));
                        break;
                    case 8:
                        System.out.println(Constantes.INTRODUCE_UNA_CIUDAD);
                        ciudad = scanner.nextLine();
                        System.out.println("Introduce el nombre del Artista:");
                        nombre = scanner.nextLine();
                        System.out.println(serviciosConcert.buscarConciertosEnCiudadDeArtista(ciudad, nombre));
                        break;
                    case 9:
                        System.out.println(serviciosConcert.buscarConciertosEnMesSiguiente());
                        break;
                    case 10:
                        introducirDatosComprarEntradas();
                        System.out.println();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        exit = true;
                        success = true;
                        break;
                    default:
                        System.out.println("Opción incorrecta");
                        break;
                }
            } while (!exit);
        } catch (InputMismatchException e) {
            System.out.println("Introduce un número");
        }
        return success;
    }

    private void introducirDatosComprarEntradas() {
        try {
            boolean userPremium = serviciosUserAdmin.isUserPremium(userName);
            User user = userPremium ? new UserPremium(userName) : new UserNormal(userName);
            if (user.getClass().equals(UserPremium.class)) {
                System.out.println("Eres usuario premium, tienes un descuento del " + ((UserPremium) user).getPDiscount() + "%");
            }
            System.out.println(comprarEntradas(userPremium));
        } catch (ElementNotFoundException e) {
            System.out.println("Datos introducidos incorrectamente, el concierto no existe");
        }
    }

    private String comprarEntradas(boolean userPremium) throws ElementNotFoundException {
        System.out.println("Introduce el nombre del artista:");
        Scanner scanner = new Scanner(System.in);
        String nombreArtista = scanner.nextLine();
        System.out.println("Introduce el id del concierto:");
        int idConcierto = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduce el número de entradas:");
        int numEntradas = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduce un correo electrónico:");
        String email = scanner.nextLine();
        return serviciosConcert.comprarEntradas(nombreArtista, idConcierto, numEntradas, userPremium, email);
    }
}