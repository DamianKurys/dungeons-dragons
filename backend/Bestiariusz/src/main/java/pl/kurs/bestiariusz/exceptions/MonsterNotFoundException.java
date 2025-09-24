package pl.kurs.bestiariusz.exceptions;

/**
 * Exception thrown when an ({@link pl.kurs.bestiariusz.models.Monster}
 * entity cannot be found in the database.
 */

public class MonsterNotFoundException extends Exception {
    public MonsterNotFoundException(Long id) {
        super("Monster with id: " + id + "not found");

    }
}
