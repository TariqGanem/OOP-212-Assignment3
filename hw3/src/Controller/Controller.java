package Controller;

import BusinessLayer.GameLevels;
import View.ConsoleInput;
import View.ConsoleOutput;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length == 0) {
            System.out.println("Please insert GameLevels path as argument, and try again.\n");
            System.exit(-1);
            return;
        }
        File levelsDir = new File(args[0]);
        File[] gameLevels = levelsDir.listFiles();
        if (gameLevels == null || gameLevels.length == 0) {
            System.out.println("There are no game levels, try again.\n");
            System.exit(-1);
            return;
        }
        List<List<String>> levelsContent = new LinkedList<>();
        for (File lvl : gameLevels) {
            String levelPath = lvl.getAbsolutePath();
            List<String> lvlContent = Files.readAllLines(Paths.get(levelPath));
            levelsContent.add(lvlContent);
        }
        new GameLevels(levelsContent, new ConsoleInput(), new ConsoleOutput()).startGame();
    }
}
