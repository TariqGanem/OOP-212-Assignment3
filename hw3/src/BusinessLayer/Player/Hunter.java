package BusinessLayer.Player;

import BusinessLayer.Tiles.Unit;
import View.Printer;
import View.Reader;

import java.util.HashMap;
import java.util.Map;

public class Hunter extends Player {
    private int range;
    private int arrowsCount;
    private int ticksCount;

    public Hunter(Reader reader, Printer writer, String name, int healthAmount, int healthPool, int attackPoints, int defensePoints, int range) {
        super(reader, writer, name, 0, 0, healthAmount, healthPool, attackPoints, defensePoints);
        this.range = range;
        this.arrowsCount = 10 * getPlayerLevel();
        this.ticksCount = 0;
    }

    @Override
    public void castAbility() {
        Map<Double, Unit> enemiesWithinRange = new HashMap<>();
        for (Unit u : this.getEnemies()) {
            int x = getX();
            int y = getY();
            double range = Math.sqrt((Math.pow(u.getX() - x, 2)) + (Math.pow(u.getY() - y, 2)));
            if (range <= this.range)
                enemiesWithinRange.put(range, u);
        }
        if (enemiesWithinRange.isEmpty() || arrowsCount == 0)
            getWriter().write(getName() + " could'nt Shoot");
        else {
            getWriter().write(getName() + " shot Arrow.");
            arrowsCount = arrowsCount - 1;
            Unit closestEnemy = getClosest(enemiesWithinRange);
            int attack = getAttackPoints();
            int defense = closestEnemy.rollPoints_defense();
            int damage = Math.max(0, attack - defense);
            closestEnemy.reduceHealth(Math.max(0, attack - defense));
            this.getWriter().write(getName() + " hit " + closestEnemy.getName() + " for " + damage + " ability damage");
            if (closestEnemy.getHealthAmount() == 0) {
                this.getWriter().write(closestEnemy.getName() + " died");
                this.addExperience(closestEnemy.getExperience());
            }
        }
    }

    private Unit getClosest(Map<Double, Unit> enemiesWithinRange) {
        double minVal = Double.MAX_VALUE;
        for (double val : enemiesWithinRange.keySet()) {
            if (val < minVal) {
                minVal = val;
            }
        }
        return enemiesWithinRange.get(minVal);
    }

    public void levelUp() {
        super.levelUp();
        arrowsCount = arrowsCount + 10 * getPlayerLevel();
        this.setAttackPoints(this.getAttackPoints() + (2 * getPlayerLevel()));
        this.setDefensePoints(this.getDefensePoints() + getPlayerLevel());
        getWriter().write("And gained: " + (2 * getPlayerLevel()) + " Attack Points, " + getPlayerLevel() + " Defense Points");
    }

    @Override
    public int[] onGameTick() {
        if (ticksCount == 10) {
            arrowsCount = arrowsCount + getPlayerLevel();
            ticksCount = 0;
        } else {
            ticksCount = ticksCount + 1;
        }
        return super.onGameTick();
    }

    public String description() {
        return super.description() + "Arrows: " + arrowsCount + "    Range: " + range;
    }
}

