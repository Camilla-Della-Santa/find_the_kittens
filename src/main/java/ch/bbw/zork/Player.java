package ch.bbw.zork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The player carries items. The total weight of the carried items
 * must never be greater than the maximum weight of the player.
 */
public class Player {

    private final double maxWeight;
    private final List<Item> inventory;

    /**
     * Creates a player with an empty inventory.
     *
     * @param maxWeight the maximum weight in kg the player can carry
     */
    public Player(double maxWeight) {
        this.maxWeight = maxWeight;
        this.inventory = new ArrayList<>();
    }

    /** @return the maximum weight the player can carry in kg */
    public double getMaxWeight() {
        return maxWeight;
    }

    /** @return the weight of all carried items in kg */
    public double getTotalWeight() {
        double sum = 0;
        for (Item item : inventory) {
            sum += item.getWeight();
        }
        return sum;
    }

    /**
     * @param item the item the player wants to pick up
     * @return true if the player can carry the item in addition to what he already carries
     */
    public boolean canCarry(Item item) {
        return getTotalWeight() + item.getWeight() <= maxWeight;
    }

    /**
     * Picks up an item if it is not too heavy.
     *
     * @param item the item to pick up
     * @return true if the item is now in the inventory, false if it was too heavy
     */
    public boolean pickUp(Item item) {
        if (!canCarry(item)) {
            return false;
        }
        inventory.add(item);
        return true;
    }

    /**
     * Drops an item by its name.
     *
     * @param name the name of the item
     * @return the dropped item or null if the player does not carry such an item
     */
    public Item drop(String name) {
        Item item = findItem(name);
        if (item != null) {
            inventory.remove(item);
        }
        return item;
    }

    /**
     * @param name the name of the item
     * @return the carried item with this name or null
     */
    public Item findItem(String name) {
        for (Item item : inventory) {
            if (item.hasName(name)) {
                return item;
            }
        }
        return null;
    }

    /** @return the number of kittens in the inventory */
    public int countKittens() {
        int count = 0;
        for (Item item : inventory) {
            if (item instanceof Kitten) {
                count++;
            }
        }
        return count;
    }

    /** @return a read-only view of the inventory */
    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }
}
