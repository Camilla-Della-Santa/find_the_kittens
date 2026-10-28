package ch.bbw.zork;

/**
 * A kitten is a special {@link Item}: it is the thing the player has to find.
 * It is light (1 kg) and can say "Meow".
 */
public class Kitten extends Item {

    /** The weight of every kitten in kg. */
    public static final double KITTEN_WEIGHT = 1.0;

    /**
     * Creates a kitten with the given name.
     *
     * @param name the name of the kitten, e.g. "Mimi"
     */
    public Kitten(String name) {
        super(name, "a tiny kitten", KITTEN_WEIGHT);
    }

    /** @return the sound this kitten makes */
    public String meow() {
        return getName() + " says: Meow!";
    }
}