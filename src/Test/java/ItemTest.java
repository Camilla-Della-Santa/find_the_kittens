import ch.bbw.zork.Item;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTest {

    @Test
    public void gettersReturnValues() {
        Item item = new Item("vase", "a nice vase", 3);
        assertEquals("vase", item.getName());
        assertEquals("a nice vase", item.getDescription());
        assertEquals(3.0, item.getWeight(), 0.0001);
    }

    @Test
    public void hasNameIgnoresCase() {
        Item item = new Item("Vase", "a nice vase", 3);
        assertTrue(item.hasName("vase"));
        assertTrue(item.hasName("VASE"));
    }

    @Test
    public void hasNameIgnoresSurroundingSpaces() {
        assertTrue(new Item("vase", "x", 1).hasName(" vase "));
    }

    @Test
    public void hasNameFalseForOtherNameOrNull() {
        Item item = new Item("vase", "x", 1);
        assertFalse(item.hasName("lamp"));
        assertFalse(item.hasName(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeWeightIsNotAllowed() {
        new Item("vase", "x", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameIsNotAllowed() {
        new Item("  ", "x", 1);
    }

    @Test
    public void toStringContainsNameAndWeight() {
        assertEquals("vase (3.0 kg)", new Item("vase", "x", 3).toString());
    }
}
