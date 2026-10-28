package ch.bbw.zork;

/**
 * An item that lies in a {@link Room} or is carried by a {@link Player}.
 * Every item has a name, a short description and a weight in kilograms.
 */
public class Item {

    private final String name;
    private final String description;
    private final double weight;

    /**
     * Creates a new item.
     *
     * @param name        the name the player types to refer to this item
     * @param description a short description of the item
     * @param weight      the weight in kg, must not be negative
     * @throws IllegalArgumentException if the name is empty or the weight is negative
     */
    public Item(String name, String description, double weight) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("An item needs a name");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("The weight must not be negative");
        }
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /** @return the name of the item */
    public String getName() {
        return name;
    }

    /** @return the description of the item */
    public String getDescription() {
        return description;
    }

    /** @return the weight of the item in kg */
    public double getWeight() {
        return weight;
    }

    /**
     * Checks if this item has the given name. The comparison ignores upper and lower case.
     *
     * @param otherName the name to compare with
     * @return true if the names are equal
     */
    public boolean hasName(String otherName) {
        return otherName != null && name.equalsIgnoreCase(otherName.trim());
    }

    @Override
    public String toString() {
        return name + " (" + weight + " kg)";
    }
}