package pl.kurs.bestiariusz.exceptions;

public class ArmorNotFoundException extends Exception {
    public ArmorNotFoundException(Long id) {
        super("Armor with id " + id + " not found");
    }
}
