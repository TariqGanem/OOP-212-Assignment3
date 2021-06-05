package BusinessLayer;

import BusinessLayer.Player.*;
import BusinessLayer.Tiles.Unit;
import View.Printer;
import View.Reader;

import java.util.List;

public class GameLevels {
    private final String[] players = {"", "Jon Snow", "The Hound",
            "Melisandre", "Thoros of Myr", "Arya Stark", "Bronn", "Ygritte"};
    private List<List<String>> gameLevels;
    private Player player;
    private Printer printer;
    private Reader reader;

    public GameLevels(List<List<String>> gameLevels, Reader reader, Printer printer) {
        this.printer = printer;
        this.reader = reader;
        this.gameLevels = gameLevels;
        printer.write(getStartMsg());
        String choice = reader.read();
        switch (choice) {
            case "1":
            case "2": {
                handleWarrior(choice);
                break;
            }
            case "3":
            case "4": {
                handleMage(choice);
                break;
            }
            case "5":
            case "6": {
                handleRogue(choice);
                break;
            }
            case "7": {
                player = new Hunter(reader, printer, players[7], 220, 220, 30, 2, 6);
                break;
            }
        }
        if (Integer.parseInt(choice) >= 1 && Integer.parseInt(choice) <= 6)
            printer.write("you have Selected:\n" + players[Integer.parseInt(choice)]);
    }

    private String getStartMsg() {
        return "Select player:\n" +
                "1. Jon Snow \t\t\tHealth: 300/300\t\tAttack: 30\t\tDefense: 4\t\tLevel: 1\t\tExperience: 0/50\t\tCooldown: 0/3\n" +
                "2. The Hound\t\t\tHealth: 400/400\t\tAttack: 20\t\tDefense: 6\t\tLevel: 1\t\tExperience: 0/50\t\tCooldown: 0/5\n" +
                "3. Melisandre   \t\tHealth: 100/100\t\tAttack: 5 \t\tDefense: 1\t\tLevel: 1\t\tExperience: 0/50\t\tMana: 75/300\t\tSpell Power: 15\n" +
                "4. Thoros of Myr\t\tHealth: 250/250\t\tAttack: 25\t\tDefense: 4\t\tLevel: 1\t\tExperience: 0/50\t\tMana: 37/150\t\tSpell Power: 20\n" +
                "5. Arya Stark\t\t\tHealth: 150/150\t\tAttack: 40\t\tDefense: 2\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "6. Bronn\t\t\t\tHealth: 250/250\t\tAttack: 35\t\tDefense: 3\t\tLevel: 1\t\tExperience: 0/50\t\tEnergy: 100/100\n" +
                "7. Ygritte      \t\tHealth: 220/220\t\tAttack: 30\t\tDefense: 2\t\tLevel: 1\t\tExperience: 0/50\t\tArrows: 10\t\tRange: 6\n";
    }

    private void handleWarrior(String choice) {
        player = choice.equals("1") ? new Warrior(reader, printer, players[1], 300, 300, 30, 4, 3)
                : new Warrior(reader, printer, players[2], 400, 400, 20, 6, 5);
    }

    private void handleMage(String choice) {
        player = choice.equals("3") ? new Mage(reader, printer, players[3], 100, 100, 5, 1, 300, 30, 15, 5, 6)
                : new Mage(reader, printer, players[4], 250, 250, 25, 4, 150, 20, 20, 3, 4);
    }

    private void handleRogue(String choice) {
        player = choice.equals("5") ? new Rogue(reader, printer, players[5], 150, 150, 40, 2, 20)
                : new Rogue(reader, printer, players[6], 250, 250, 35, 3, 50);
    }

    public void startGame() {
        int i;
        for (i = 0; i < gameLevels.size(); i++) {
            Board board = new Board(printer);
            List<String> lvl = gameLevels.get(i);
            board.buildMe(player, lvl);
            List<Unit> enemies = board.getEnemies();
            player.setEnemies(enemies);
            board.start();
            if (player.getHealthAmount() == 0)
                break;
            if (i + 1 < gameLevels.size())
                printer.write("Level " + (i + 1) + " is done!\nNow level " + (i + 2) + " starts!");
        }
        if (i == gameLevels.size())
            printer.write("Congrats, you won the game!");
    }
}
