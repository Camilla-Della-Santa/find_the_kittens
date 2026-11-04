import ch.bbw.zork.Room;
import ch.bbw.zork.Item;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoomTest {

    @Test
    public void nextRoomReturnsRoomBehindExit() {
        Room a = new Room("a");
        Room b = new Room("b");
        a.setExit("east", b);
        assertSame(b, a.nextRoom("east"));
    }

    @Test
    public void nextRoomReturnsNullWithoutExit() {
        assertNull(new Room("a").nextRoom("north"));
    }

    @Test
    public void setExitsIgnoresNullRooms() {
        Room a = new Room("a");
        Room b = new Room("b");
        a.setExits(null, b, null, null);
        assertSame(b, a.nextRoom("east"));
        assertFalse(a.longDescription().contains("north"));
    }

    @Test
    public void upAndDownExitsWork() {
        Room a = new Room("a");
        Room b = new Room("b");
        a.setExit("up", b);
        assertSame(b, a.nextRoom("up"));
        assertTrue(a.longDescription().contains("up"));
    }

    @Test
    public void roomIsNotWinningRoomByDefault() {
        Room a = new Room("a");
        assertFalse(a.isWinningRoom());
        a.setWinningRoom(true);
        assertTrue(a.isWinningRoom());
    }

    @Test
    public void findItemIgnoresCaseAndReturnsNullIfMissing() {
        Room a = new Room("a");
        Item vase = new Item("vase", "x", 1);
        a.addItem(vase);
        a.addItem(new Item("lamp2", "x", 1));
        assertEquals(2, a.getItems().size());
        assertSame(vase, a.findItem("VASE"));
        assertNull(a.findItem("lamp"));
    }

    @Test
    public void removeItemTakesItOutOfTheRoom() {
        Room a = new Room("a");
        Item vase = new Item("vase", "x", 1);
        a.addItem(vase);
        assertTrue(a.removeItem(vase));
        assertTrue(a.getItems().isEmpty());
        assertFalse(a.removeItem(vase));
    }

    @Test
    public void longDescriptionShowsItems() {
        Room a = new Room("the kitchen");
        a.addItem(new Item("milk", "x", 1));
        String text = a.longDescription();
        assertTrue(text.contains("the kitchen"));
        assertTrue(text.contains("milk"));
    }
}
