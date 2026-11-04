import ch.bbw.zork.Item;
import ch.bbw.zork.Kitten;

import static org.junit.Assert.*;

import org.junit.Test;

public class KittenTest {

    @Test
    public void kittenIsAnItem() {
        assertTrue(new Kitten("Mimi") instanceof Item);
    }

    @Test
    public void kittenHasFixedWeight() {
        assertEquals(Kitten.KITTEN_WEIGHT, new Kitten("Mimi").getWeight(), 0.0001);
    }

    @Test
    public void kittenKeepsItsName() {
        assertEquals("Mimi", new Kitten("Mimi").getName());
    }

    @Test
    public void kittenMeows() {
        assertEquals("Mimi says: Meow!", new Kitten("Mimi").meow());
    }

    @Test
    public void kittenCanBeFoundByNameIgnoringCase() {
        assertTrue(new Kitten("Mimi").hasName("mimi"));
    }

    @Test
    public void kittenHasADescription() {
        assertNotNull(new Kitten("Mimi").getDescription());
    }
}
