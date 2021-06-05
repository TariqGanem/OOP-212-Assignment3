package BusinessLayer.Tiles;

import View.ConsoleOutput;
import View.Printer;

import java.util.Random;

public abstract class Unit extends Tile {
    private String name;
    private int healthPool;
    private int healthAmount;
    private int attackPoints;
    private int defensePoints;
    private int experience;
    private Printer printer = new ConsoleOutput();

    public Unit(char symbole, String name, int x, int y, int healthPool, int healthAmount, int attackPoints, int defensePoints, int experience) {
        super(symbole, x, y);
        this.name = name;
        this.healthPool = healthPool;
        this.healthAmount = healthAmount;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public abstract int[] onGameTick();

    public void attack(Unit other) {
        printer.write(name + " engaged in combat with " + other.getName() + "\n" + description() + "\n" + other.description());
        int damage;
        if ((damage = rollPoints_attack() - other.rollPoints_defense()) > 0) {
            other.reduceHealth(damage);
            printer.write(name + " dealt " + damage + " damage to " + other.getName() + ".");
        }
        if (other.getHealthAmount() == 0)
            printer.write(other.getName() + " died.");
    }

    public int rollPoints_attack() {
        int rand = new Random().nextInt(attackPoints + 1);
        printer.write(name + " rolled " + rand + " attack points.");
        return rand;
    }

    public int rollPoints_defense() {
        int rand = new Random().nextInt(defensePoints + 1);
        printer.write(name + " rolled " + rand + " defense points.");
        return rand;
    }

    public void reduceHealth(int damage) {
        if (healthAmount < damage)
            healthAmount = 0;
        else
            healthAmount -= damage;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public int getHealthPool() {
        return healthPool;
    }

    public void setHealthPool(int pool) {
        healthPool = pool;
    }

    public int getHealthAmount() {
        return healthAmount;
    }

    public void setHealthAmount(int amount) {
        healthAmount = Math.min(healthPool, amount);
    }

    public String getName() {
        return name;
    }

    public String description() {
        return name + ":\n" + "Health: " + healthAmount + "/" + healthPool + "    Attack: " + attackPoints
                + "    Defense: " + defensePoints + "    ";
    }
}
