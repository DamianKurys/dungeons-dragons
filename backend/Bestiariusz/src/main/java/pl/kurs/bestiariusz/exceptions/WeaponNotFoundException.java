package pl.kurs.bestiariusz.exceptions;

public class WeaponNotFoundException extends Exception {
    public WeaponNotFoundException(Long id) {
        super("Weapon with id " + id + " not found");
    }
}
