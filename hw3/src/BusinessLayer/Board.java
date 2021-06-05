package BusinessLayer;

import BusinessLayer.Enemies.Boss;
import BusinessLayer.Enemies.Monster;
import BusinessLayer.Enemies.Trap;
import BusinessLayer.Tiles.Empty;
import BusinessLayer.Tiles.Tile;
import BusinessLayer.Tiles.Unit;
import BusinessLayer.Tiles.Wall;
import View.Printer;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private Tile[][] tiles;
    private Unit player;
    private List<Unit> enemies;
    private Printer printer;

    public Board(Printer printer) {
        this.printer = printer;
    }

    public void buildMe(Unit player, List<String> board) {
        if (player == null)
            throw new IllegalArgumentException("You can't start the game without a player.");
        tiles = new Tile[board.size()][board.get(0).length()];
        this.player = player;
        enemies = new LinkedList<>();
        for (int i = 0; i < tiles.length; i++) {
            String s = board.get(i);
            for (int j = 0; j < tiles[0].length; j++) {
                char c = s.charAt(j);
                if (c == '#')
                    tiles[i][j] = new Wall(j, i);
                else if (c == '.')
                    tiles[i][j] = new Empty(j, i);
                else if (c == '@') {
                    tiles[i][j] = player;
                    player.setX(j);
                    player.setY(i);
                } else if (c == 'B' || c == 'Q' || c == 'D') {
                    handleTraps(c, j, i);
                } else {
                    handleMonsters(c, j, i);
                }
            }
        }
        if (enemies.isEmpty())
            throw new IllegalArgumentException("Could'nt start the game, there are no enemies.");
    }

    private void handleTraps(char c, int x, int y) {
        Unit unit = null;
        if (c == 'B')
            unit = new Trap(250, 'B', x, y, "Bonus Trap", 1, 1, 1, 1, 1, 5, player);
        if (c == 'Q')
            unit = new Trap(100, 'Q', x, y, "Queen’s Trap", 250, 250, 50, 10, 3, 7, player);
        if (c == 'D')
            unit = new Trap(250, 'D', x, y, "Death Trap", 500, 500, 100, 20, 1, 10, player);
        enemies.add(unit);
        tiles[y][x] = unit;
    }

    private void handleMonsters(char c, int x, int y) {
        Unit unit = null;
        switch (c) {
            case 's':
                unit = new Monster(3, 25, 's', "Lannister Solider", x, y, 80, 80, 8, 3, player);
                break;
            case 'k':
                unit = new Monster(4, 50, 'k', "Lannister Knight ", x, y, 200, 200, 14, 8, player);
                break;
            case 'q':
                unit = new Monster(5, 100, 'q', "Queen’s Guard", x, y, 400, 400, 20, 15, player);
                break;
            case 'z':
                unit = new Monster(3, 100, 'z', "Wright", x, y, 600, 600, 30, 15, player);
                break;
            case 'b':
                unit = new Monster(4, 250, 'b', "Bear-Wright", x, y, 1000, 1000, 75, 30, player);
                break;
            case 'g':
                unit = new Monster(5, 500, 'g', "Giant-Wright", x, y, 1500, 1500, 100, 40, player);
                break;
            case 'w':
                unit = new Monster(6, 1000, 'w', "White Walker", x, y, 2000, 2000, 150, 50, player);
                break;
            case 'M':
                unit = new Monster(6, 500, 'M', "The Mountain", x, y, 1000, 1000, 60, 25, player);
                break;
            case 'C':
                unit = new Monster(1, 1000, 'C', "Queen Cersei", x, y, 100, 100, 10, 10, player);
                break;
            case 'K':
                unit = new Monster(8, 5000, 'K', "Night’s King ", x, y, 5000, 5000, 300, 150, player);
                break;
            case 'T': // indicates the Boss
                unit = new Boss(printer, 10, 5, 10000, 'T', "Boss", x, y, 10000, 10000, 500, 500, player);
                break;
        }
        enemies.add(unit);
        tiles[y][x] = unit;
    }

    public List<Unit> getEnemies() {
        return enemies;
    }

    public void start() {
        while (player.getHealthAmount() > 0 && !enemies.isEmpty()) {
            printer.write(toString() + player.description());
            update(player);
            List<Unit> deadBodies = new LinkedList<>();
            for (Unit u : enemies) {
                if (u.getHealthAmount() == 0) {
                    tiles[u.getY()][u.getX()] = new Empty(u.getX(), u.getY());
                    deadBodies.add(u);
                }
            }
            for (Unit u : deadBodies)
                enemies.remove(u);
            for (Unit u : enemies)
                update(u);
        }
        if (player.getHealthAmount() == 0)
            printer.write("You have lost the game!\n" + toString() + player.description());
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (Tile[] tile : tiles) {
            st.append("\n");
            for (int j = 0; j < tiles[0].length; j++)
                st.append(tile[j].toString());
        }
        st.append("\n");
        return st.toString();
    }

    private void update(Unit u) {
        int unitX = u.getX();
        int unitY = u.getY();
        int[] pos = u.onGameTick();
        int newX = pos[0];
        int newY = pos[1];
        if (unitX != newX || unitY != newY) {
            Tile t = tiles[newY][newX];
            u.interAct(t);
            if (u.getX() != unitX || u.getY() != unitY) {
                tiles[u.getY()][u.getX()] = u;
                tiles[t.getY()][t.getX()] = t;
            }
        }
    }

}
