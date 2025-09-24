package pl.kurs.bestiariusz.exceptions;

/**
 * Exception thrown when an ({@link pl.kurs.bestiariusz.models.Armor}
 * entity cannot be found in the database.
 */

public class ArmorNotFoundException extends Exception {
    public ArmorNotFoundException(Long id) {
        super("Armor with id " + id + " not found");
    }
}
