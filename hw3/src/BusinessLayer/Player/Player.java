package BusinessLayer.Player;

import BusinessLayer.Enemies.Enemy;
import BusinessLayer.HeroicUnit;
import BusinessLayer.Tiles.Unit;
import BusinessLayer.Visitor;
import View.Printer;
import View.Reader;

import java.util.List;

public abstract class Player extends Unit implements HeroicUnit {
    private static final char symbole = '@';
    private int experience = 0;
    private int playerLevel;
    private List<Unit> enemies = null;
    private Reader reader;
    private Printer printer;


    public Player(Reader reader, Printer printer, String name, int x, int y, int healthAmount, int healthPool, int attackPoints, int defensePoints) {
        super(symbole, name, x, y, healthAmount, healthPool, attackPoints, defensePoints, 0);
        this.printer = printer;
        this.reader = reader;
        playerLevel = 1;
    }

    public List<Unit> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Unit> enemies) {
        this.enemies = enemies;
    }

    public Printer getWriter() {
        return printer;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public void visit(Enemy enemy) {
        this.attack(enemy);
        if (enemy.getHealthAmount() == 0)
            addExperience(enemy.getExperience());
    }

    public int[] onGameTick() {
        int[] arr = new int[2];
        String input = reader.read();
        char c = input.charAt(0);
        int myX = getX();
        int myY = getY();
        switch (c) {
            case 'a':
                arr[0] = myX - 1;
                arr[1] = myY;
                break;
            case 's':
                arr[0] = myX;
                arr[1] = myY + 1;
                break;
            case 'd':
                arr[0] = myX + 1;
                arr[1] = myY;
                break;
            case 'w':
                arr[0] = myX;
                arr[1] = myY - 1;
                break;
            case 'e': {
                castAbility();
                arr[0] = myX;
                arr[1] = myY;
                break;
            }
            // contains 'q'
            default:
                arr[0] = myX;
                arr[1] = myY;
                break;
        }
        return arr;
    }

    public String description() {
        return super.description() + "Level: " + playerLevel + "    Experience: " + experience + "    ";
    }

    public void addExperience(int exp) {
        experience += exp;
        if (experience >= 50 * playerLevel)
            levelUp();
    }

    public void levelUp() {
        experience = experience - (50 * playerLevel);
        playerLevel = playerLevel + 1;
        setHealthPool(getHealthPool() + (10 * playerLevel));
        setHealthAmount(getHealthPool());
        setAttackPoints(getAttackPoints() + (4 * playerLevel));
        setDefensePoints(getDefensePoints() + playerLevel);
        printer.write(getName() + " reached Level " + playerLevel + " and gained: " + 10 * playerLevel + " Health pool, " + 4 * playerLevel + " AttackPoints, " + playerLevel + " DefensePoints");
    }

    public String toString() {
        if (getHealthAmount() > 0)
            return super.toString();
        else
            return "X";
    }
}