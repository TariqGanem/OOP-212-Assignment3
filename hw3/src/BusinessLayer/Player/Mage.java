package BusinessLayer.Player;

import BusinessLayer.Tiles.Unit;
import View.Printer;
import View.Reader;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    public Mage(Reader reader, Printer writer, String name, int healthAmount, int healthPool, int attackPoints, int defensePoints, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(reader, writer, name, 0, 0, healthAmount, healthPool, attackPoints, defensePoints);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    @Override
    public void castAbility() {
        if (currentMana < manaCost) {
            getWriter().write(getName() + " tried to cast Blizzard, but there was not enough mana: " + currentMana + "/" + manaCost);
        } else {
            currentMana = currentMana - manaCost;
            int hits = 0;
            List<Unit> lst = new LinkedList<>();
            for (Unit u : this.getEnemies()) {
                int x = getX();
                int y = getY();
                double range = Math.sqrt((Math.pow(u.getX() - x, 2)) + (Math.pow(u.getY() - y, 2)));
                if (range < abilityRange)
                    lst.add(u);
            }
            if (lst.isEmpty())
                getWriter().write(getName() + " cast Blizzard , but no enemies in its range");
            else
                getWriter().write(getName() + " cast Blizzard.");
            while (hits < hitsCount & (!lst.isEmpty())) {
                Unit u = lst.get(new Random().nextInt(lst.size()));
                int attack = spellPower;
                int defense = u.rollPoints_defense();
                int damage = Math.max(0, attack - defense);
                u.reduceHealth(Math.max(0, attack - defense));
                this.getWriter().write(getName() + " hit " + u.getName() + " for " + damage + " ability damage");
                if (u.getHealthAmount() == 0) {
                    lst.remove(u);
                    getWriter().write(u.getName() + " died");
                    addExperience(u.getExperience());
                }
                hits = hits + 1;
            }
        }
    }

    public void levelUp() {
        super.levelUp();
        manaPool = manaPool + 25 * getPlayerLevel();
        currentMana = Math.min((currentMana + manaPool / 4), manaPool);
        spellPower = spellPower + 10 * getPlayerLevel();
        getWriter().write("And gained: " + 25 * getPlayerLevel() + " Mana pool , " + manaPool / 4 + " Current Mana , " + 10 * getPlayerLevel() + " Spell power");
    }

    @Override
    public int[] onGameTick() {
        currentMana = Math.min(manaPool, currentMana + getPlayerLevel());
        return super.onGameTick();
    }

    public String description() {
        return super.description() + "Mana: " + currentMana + "/" + manaPool + "    " +
                "Spell Power: " + spellPower;
    }

}
