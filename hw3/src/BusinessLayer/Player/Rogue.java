package BusinessLayer.Player;

import BusinessLayer.Tiles.Unit;
import View.Printer;
import View.Reader;

import java.util.LinkedList;
import java.util.List;

public class Rogue extends Player {
    private int cost;
    private int currentEnergy;


    public Rogue(Reader reader, Printer printer, String name, int healthAmount, int healthPool, int attackPoints, int defensePoints, int cost) {
        super(reader, printer, name, 0, 0, healthAmount, healthPool, attackPoints, defensePoints);
        this.cost = cost;
        currentEnergy = 100;
    }

    @Override
    public void castAbility() {
        if (currentEnergy < cost)
            getWriter().write(getName() + "  tried to cast Fan of Knives, but there was not enough energy: " + currentEnergy + "/" + cost);
        else {
            currentEnergy = currentEnergy - cost;
            List<Unit> enemiesWithinRange = new LinkedList<>();
            for (Unit u : this.getEnemies()) {
                int x = getX();
                int y = getY();
                double range = Math.sqrt((Math.pow(u.getX() - x, 2)) + (Math.pow(u.getY() - y, 2)));
                if (range < 2)
                    enemiesWithinRange.add(u);
            }
            if (enemiesWithinRange.isEmpty())
                getWriter().write(getName() + " cast Fan of Knives, but there were no enemies around.");
            else
                getWriter().write(getName() + " cast Fan of Knives.");
            for (Unit u : enemiesWithinRange) {
                int attack = getAttackPoints();
                int def = u.rollPoints_defense();
                int damage = Math.max(0, attack - def);
                u.reduceHealth(damage);
                getWriter().write(this.getName() + " dealt " + damage + " to " + u.getName());
                if (u.getHealthAmount() == 0) {
                    getWriter().write(u.getName() + " died");
                    addExperience(u.getExperience());
                }
            }
        }
    }

    public void levelUp() {
        super.levelUp();
        currentEnergy = 100;
        this.setAttackPoints(this.getAttackPoints() + (3 * getPlayerLevel()));
        getWriter().write("And gained: 100 Energy , " + 3 * getPlayerLevel() + " Attack Points");
    }

    @Override
    public int[] onGameTick() {
        currentEnergy = Math.min(currentEnergy + 10, 100);
        return super.onGameTick();
    }

    public String description() {
        return super.description() + "Current Energy: " + currentEnergy + "/100";
    }
}
