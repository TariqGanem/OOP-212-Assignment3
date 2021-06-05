import BusinessLayer.Board;
import BusinessLayer.Player.Hunter;
import BusinessLayer.Player.Player;
import View.ConsoleInput;
import View.ConsoleOutput;
import View.Printer;
import View.Reader;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class BoardTest {
    private Board board;
    private Reader reader;
    private Printer printer;

    @BeforeEach
    void setUp() {
        reader = new ConsoleInput();
        printer = new ConsoleOutput();
        board = new Board(printer);
    }

    @Test
    void buildBoardWithoutPlayer() {
        List<String> level = new LinkedList<>();
        Collections.addAll(level, "#########################",
                "#b.....................b#",
                "#...###...........###...#",
                "#...zzz...........zzz...#",
                "#...###g...w.w...g###...#",
                "#.......................#",
                "########.ww.K.ww.########",
                "#########################");
        // Should throw an error due to a null player
        try {
            board.buildMe(null, level);
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            // SUCCESS
        }
    }

    @Test
    void buildBoardWithoutEnemies() {
        List<String> level = new LinkedList<>();
        Collections.addAll(level, "#########################",
                "#.......................#",
                "#...###...........###...#",
                "#.......................#",
                "#...###...........###...#",
                "########.........########",
                "#########################");
        Player player = new Hunter(reader, printer, "Ygritte", 220, 220, 30, 2, 6);
        try {
            board.buildMe(player, level);
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException e) {
            // SUCCESS
        }
    }

    @Test
    void buildBoardNormally() {
        List<String> level = new LinkedList<>();
        Collections.addAll(level, "#########################",
                "#b..........@..........b#",
                "#...zzz...........zzz...#",
                "#...###g...w.w...g###...#",
                "#.......................#",
                "########.ww.K.ww.########",
                "#########################");
        Player player = new Hunter(reader, printer, "Ygritte", 220, 220, 30, 2, 6);
        board.buildMe(player, level);
        Assertions.assertEquals("\n" + "#########################\n" +
                "#b..........@..........b#\n" +
                "#...zzz...........zzz...#\n" +
                "#...###g...w.w...g###...#\n" +
                "#.......................#\n" +
                "########.ww.K.ww.########\n" +
                "#########################\n", board.toString());
    }
}