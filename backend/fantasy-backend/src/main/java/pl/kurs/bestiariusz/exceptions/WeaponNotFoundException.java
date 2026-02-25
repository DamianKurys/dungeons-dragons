package pl.kurs.bestiariusz.exceptions;

/**
 * Exception thrown when an ({@link pl.kurs.bestiariusz.models.Weapon}
 * entity cannot be found in the database.
 */

public class WeaponNotFoundException extends Exception {
    public WeaponNotFoundException(Long id) {
        super("Weapon with id " + id + " not found");
    }
}
