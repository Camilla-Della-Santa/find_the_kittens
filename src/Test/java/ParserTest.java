import ch.bbw.zork.Command;
import ch.bbw.zork.Parser;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ParserTest {

    private Parser parserFor(String input) {
        return new Parser(new ByteArrayInputStream(input.getBytes()), new PrintStream(new ByteArrayOutputStream()));
    }

    @Test
    public void validCommandWithSecondWord() {
        Command c = parserFor("go east\n").getCommand();
        assertEquals("go", c.getCommandWord());
        assertEquals("east", c.getSecondWord());
    }

    @Test
    public void commandWordIsCaseInsensitive() {
        assertEquals("take", parserFor("TAKE Mimi\n").getCommand().getCommandWord());
    }

    @Test
    public void unknownCommandIsMarkedUnknown() {
        assertTrue(parserFor("dance\n").getCommand().isUnknown());
    }

    @Test
    public void emptyLineIsUnknown() {
        assertTrue(parserFor("\n").getCommand().isUnknown());
    }

    @Test
    public void severalLinesAreReadOneAfterTheOther() {
        Parser p = parserFor("map\nback\n");
        assertEquals("map", p.getCommand().getCommandWord());
        assertEquals("back", p.getCommand().getCommandWord());
    }

    @Test
    public void endOfInputMeansQuit() {
        assertEquals("quit", parserFor("").getCommand().getCommandWord());
    }
}
