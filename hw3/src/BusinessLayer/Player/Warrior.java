package BusinessLayer.Player;

import BusinessLayer.Tiles.Unit;
import View.Printer;
import View.Reader;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(Reader reader, Printer writer, String name, int healthAmount, int healthPool, int attackPoints, int defensePoints, int abilityCooldown) {
        super(reader, writer, name, 0, 0, healthAmount, healthPool, attackPoints, defensePoints);
        this.abilityCooldown = abilityCooldown;
        remainingCooldown = 0;
    }

    @Override
    public void castAbility() {
        if (remainingCooldown > 0)
            this.getWriter().write(this.getName() + " tried to cast Avenger's Shield, but there is a cooldown: " + remainingCooldown);
        else {
            remainingCooldown = abilityCooldown;
            int healingAmount = Math.min(getHealthAmount() + (10 * getDefensePoints()), getHealthPool());
            setHealthAmount(healingAmount);
            List<Unit> enemiesWithinRange = new LinkedList<>();
            String str = this.getName() + " used Avenger's Shield, healing for: " + healingAmount + "";
            for (Unit u : this.getEnemies()) {
                int x = getX();
                int y = getY();
                double range = Math.sqrt((Math.pow(u.getX() - x, 2)) + (Math.pow(u.getY() - y, 2)));
                if (range < 3)
                    enemiesWithinRange.add(u);
            }
            if (!enemiesWithinRange.isEmpty()) {
                Random r = new Random();
                Unit u = enemiesWithinRange.get(r.nextInt(enemiesWithinRange.size()));
                str = str + " And Attacked " + u.getName() + "";
                this.getWriter().write(str);
                int Attack = (int) (getHealthPool() * 0.1);
                int defense = u.rollPoints_defense();
                int damage = Math.max(0, Attack - defense);
                u.reduceHealth(damage);
                if (u.getHealthAmount() == 0) {
                    this.getWriter().write(u.getName() + " died");
                    this.addExperience(u.getExperience());
                }
            } else this.getWriter().write(str);

        }
    }

    public String description() {
        return super.description() + "Cooldown: " + remainingCooldown + "/" + abilityCooldown;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.remainingCooldown = 0;
        setHealthPool(getHealthPool() + (5 * this.getPlayerLevel()));
        setAttackPoints(getAttackPoints() + (2 * getPlayerLevel()));
        setDefensePoints(getDefensePoints() + getPlayerLevel());
        getWriter().write("And gained: " + 5 * getPlayerLevel() + " Health Pool , " + 2 * getPlayerLevel() + " Attack Points , " + getPlayerLevel() + " defense Points");
    }

    @Override
    public int[] onGameTick() {
        this.remainingCooldown = Math.max(remainingCooldown - 1, 0);
        return super.onGameTick();
    }
}
