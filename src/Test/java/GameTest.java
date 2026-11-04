import ch.bbw.zork.Game;
import ch.bbw.zork.Room;
import ch.bbw.zork.Command;
import ch.bbw.zork.Item;
import ch.bbw.zork.Kitten;
import ch.bbw.zork.Player;
import ch.bbw.zork.Parser;
import ch.bbw.zork.CommandWords;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private ByteArrayOutputStream buffer;
    private Game game;

    @Before
    public void setUp() {
        buffer = new ByteArrayOutputStream();
        game = new Game(new ByteArrayInputStream(new byte[0]), new PrintStream(buffer), new Random(42));
    }

    private boolean run(String command, String second) {
        return game.processCommand(new Command(command, second));
    }

    private String output() {
        return buffer.toString();
    }

    private Room findRoom(String text) {
        for (Room room : game.getRooms()) {
            if (room.shortDescription().contains(text)) {
                return room;
            }
        }
        throw new AssertionError("room not found: " + text);
    }

    private int kittensInHouse() {
        int count = 0;
        for (Room room : game.getRooms()) {
            for (Item item : room.getItems()) {
                if (item instanceof Kitten) {
                    count++;
                }
            }
        }
        return count;
    }

    @Test
    public void fiveKittensAreHiddenInDifferentRooms() {
        assertEquals(Game.KITTEN_COUNT, kittensInHouse());
        for (Room room : game.getRooms()) {
            int inRoom = 0;
            for (Item item : room.getItems()) {
                if (item instanceof Kitten) {
                    inRoom++;
                }
            }
            assertTrue(inRoom <= 1);
        }
    }

    @Test
    public void noKittenInTheEntrance() {
        for (long seed = 0; seed < 50; seed++) {
            Game g = new Game(new ByteArrayInputStream(new byte[0]), new PrintStream(new ByteArrayOutputStream()),
                    new Random(seed));
            for (Room room : g.getRooms()) {
                if (room.isWinningRoom()) {
                    assertNull(room.findItem("Mimi"));
                    assertTrue(room.getItems().isEmpty());
                }
            }
        }
    }

    @Test
    public void goMovesToTheNextRoom() {
        run("go", "east");
        assertTrue(game.getCurrentRoom().shortDescription().contains("living room"));
    }

    @Test
    public void goIntoAWallDoesNotMove() {
        Room before = game.getCurrentRoom();
        run("go", "north");
        assertSame(before, game.getCurrentRoom());
        assertTrue(output().contains("There is no door!"));
    }

    @Test
    public void upGoesToTheUpperFloor() {
        run("go", "up");
        assertTrue(game.getCurrentRoom().shortDescription().contains("upper floor"));
    }

    @Test
    public void backGoesToThePreviousRoomStepByStep() {
        Room start = game.getCurrentRoom();
        run("go", "east");
        run("go", "east");
        run("back", null);
        assertTrue(game.getCurrentRoom().shortDescription().contains("living room"));
        run("back", null);
        assertSame(start, game.getCurrentRoom());
    }

    @Test
    public void backAtTheStartStaysInTheRoom() {
        Room start = game.getCurrentRoom();
        run("back", null);
        assertSame(start, game.getCurrentRoom());
        assertTrue(output().contains("cannot go back"));
    }

    @Test
    public void bathroomHasAnOpenWindow() {
        run("go", "up");
        run("go", "west");
        run("go", "north");
        assertTrue(output().contains("The window is open, brrrrrrr"));
    }

    @Test
    public void takeMovesItemFromRoomToPlayer() {
        Room kitchen = findRoom("kitchen");
        Item milk = kitchen.findItem("milk");
        run("go", "east");
        run("go", "east");
        run("go", "south");
        run("take", "milk");
        assertNull(kitchen.findItem("milk"));
        assertSame(milk, game.getPlayer().findItem("milk"));
    }

    @Test
    public void tooHeavyItemStaysInTheRoom() {
        Room basement = findRoom("basement");
        run("go", "down");
        game.getPlayer().pickUp(new Item("stone", "x", 7));
        run("take", "toolbox");
        assertNotNull(basement.findItem("toolbox"));
        assertNull(game.getPlayer().findItem("toolbox"));
        assertTrue(output().contains("too heavy"));
    }

    @Test
    public void dropPutsItemIntoTheCurrentRoom() {
        game.getPlayer().pickUp(new Item("stone", "x", 1));
        run("drop", "stone");
        assertNotNull(game.getCurrentRoom().findItem("stone"));
        assertNull(game.getPlayer().findItem("stone"));
    }

    @Test
    public void mapMarksTheCurrentRoom() {
        run("map", null);
        String text = output();
        assertTrue(text.contains("-> the corridor on the ground floor"));
        assertTrue(text.contains("(not visited yet)"));
    }

    @Test
    public void leavingWithMissingKittensDoesNotWin() {
        run("go", "west");
        assertFalse(run("go", "east"));
        assertTrue(output().contains("still missing"));
    }

    @Test
    public void bringingAllKittensToTheEntranceWins() {
        for (Room room : game.getRooms()) {
            for (Item item : new java.util.ArrayList<>(room.getItems())) {
                if (item instanceof Kitten) {
                    room.removeItem(item);
                    game.getPlayer().pickUp(item);
                }
            }
        }
        assertTrue(run("go", "west"));
        assertTrue(output().contains("You won!"));
    }

    @Test
    public void quitFinishesTheGame() {
        assertTrue(run("quit", null));
        assertFalse(run("quit", "now"));
    }
}
