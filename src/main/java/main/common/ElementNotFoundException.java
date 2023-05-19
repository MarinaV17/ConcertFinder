package main.common;

public class ElementNotFoundException extends Exception {
    public ElementNotFoundException() {
        super("El elemento no existe en la lista");
    }

    public ElementNotFoundException(int id) {
        super("El elemento con id " + id + " no existe en la lista");
    }
}