package main.ui;

import main.common.Constantes;
import main.config.Configuration;
import main.dao.DaoArtist;
import main.dao.DaoConcert;
import main.dao.DaoUserAdmin;
import main.dao.DataBase;
import main.domain.servicios.ServiciosArtist;
import main.domain.servicios.ServiciosConcert;
import main.domain.servicios.ServiciosUserAdmin;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        bienvenida();
        String userName = getUserCredentials(Constantes.USER_DOS_PUNTOS);
        String password = getUserCredentials(Constantes.PASSWORD_DOS_PUNTOS);
        MenuAdministrador menuAdministrador = getMenuAdministrador();
        MenuUsuario menuUsuario = getMenuUsuario(userName);
        cargarFichero(menuAdministrador.loadFileArtists(), Constantes.ARTISTS);
        cargarFichero(menuAdministrador.loadFileConcerts(), Constantes.CONCERTS);
        loadLists(menuAdministrador);
        if (menuAdministrador.loginAdmin(userName, password)) {
            System.out.println(Constantes.BIENVENIDO_ADMINISTRADOR);
            boolean success;
            do {
                success = menuAdministrador.displayMenuAdmin();
            } while (!success);
        } else {
            System.out.println(Constantes.BIENVENIDO_USUARIO);
            boolean success;
            do {
                success = menuUsuario.displayMenuUsuario();
            } while (!success);
        }
        guardarArtistasYConciertos(menuAdministrador);
    }

    private static void guardarArtistasYConciertos(MenuAdministrador menuAdministrador) {
        menuAdministrador.saveArtists();
        menuAdministrador.saveConcerts();
    }

    private static void loadLists(MenuAdministrador menuAdministrador) {
        menuAdministrador.loadArtists();
        menuAdministrador.loadConcerts();
    }

    private static void cargarFichero(boolean loadFile, String artists) {
        if (loadFile) {
            System.out.println("Fichero " + artists + " cargado correctamente");
        } else {
            System.out.println("No se ha podido cargar el fichero " + artists);
        }
    }

    private static MenuUsuario getMenuUsuario(String userName) {
        return new MenuUsuario(new ServiciosUserAdmin(new DaoUserAdmin(DataBase.getInstance(Configuration.getInstance()))),
                new ServiciosArtist(
                new DaoArtist(DataBase.getInstance(Configuration.getInstance()))),
                new ServiciosConcert(
                        new DaoConcert(DataBase.getInstance(Configuration.getInstance()))), userName);
    }

    private static MenuAdministrador getMenuAdministrador() {
        return new MenuAdministrador(
                new ServiciosUserAdmin(
                        new DaoUserAdmin(DataBase.getInstance(Configuration.getInstance()))),
                new ServiciosArtist(
                        new DaoArtist(DataBase.getInstance(Configuration.getInstance()))),
                new ServiciosConcert(
                        new DaoConcert(DataBase.getInstance(Configuration.getInstance()))));
    }

    private static String getUserCredentials(String credential) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(credential);
        return scanner.nextLine();
    }

    public static void bienvenida() {
        System.out.println(Constantes.BIENVENIDO_A_CONCERT_FINDER);
        System.out.println("Inicia sesión o regístrate como nuevo usuario");
    }
}