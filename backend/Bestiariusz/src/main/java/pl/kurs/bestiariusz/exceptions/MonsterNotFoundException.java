package pl.kurs.bestiariusz.exceptions;

public class MonsterNotFoundException extends Exception {
    public MonsterNotFoundException(Long id) {
        super("Monster with id: " + id + "not found");

    }
}
