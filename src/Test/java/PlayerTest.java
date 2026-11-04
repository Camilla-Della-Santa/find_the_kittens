import ch.bbw.zork.Item;
import ch.bbw.zork.Kitten;
import ch.bbw.zork.Player;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void newPlayerCarriesNothing() {
        Player p = new Player(5);
        assertTrue(p.getInventory().isEmpty());
        assertEquals(0.0, p.getTotalWeight(), 0.0001);
    }

    @Test
    public void pickUpAddsItemAndWeight() {
        Player p = new Player(5);
        assertTrue(p.pickUp(new Item("vase", "x", 3)));
        assertEquals(3.0, p.getTotalWeight(), 0.0001);
    }

    @Test
    public void tooHeavyItemIsRejected() {
        Player p = new Player(5);
        assertFalse(p.pickUp(new Item("toolbox", "x", 6)));
        assertTrue(p.getInventory().isEmpty());
    }

    @Test
    public void maximumWeightIsAllowedExactly() {
        Player p = new Player(5);
        assertTrue(p.pickUp(new Item("a", "x", 3)));
        assertTrue(p.pickUp(new Item("b", "x", 2)));
        assertFalse(p.pickUp(new Item("c", "x", 0.5)));
    }

    @Test
    public void dropReturnsItemAndFreesWeight() {
        Player p = new Player(5);
        Item vase = new Item("vase", "x", 3);
        p.pickUp(vase);
        assertSame(vase, p.drop("vase"));
        assertEquals(0.0, p.getTotalWeight(), 0.0001);
    }

    @Test
    public void dropUnknownItemReturnsNull() {
        assertNull(new Player(5).drop("vase"));
    }

    @Test
    public void countKittensCountsOnlyKittens() {
        Player p = new Player(10);
        p.pickUp(new Kitten("Mimi"));
        p.pickUp(new Kitten("Luna"));
        p.pickUp(new Item("vase", "x", 1));
        assertEquals(2, p.countKittens());
    }

    @Test
    public void findItemIgnoresCase() {
        Player p = new Player(5);
        Kitten k = new Kitten("Mimi");
        p.pickUp(k);
        assertSame(k, p.findItem("mimi"));
    }
}
