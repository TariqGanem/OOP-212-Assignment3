package BusinessLayer.Enemies;

import BusinessLayer.HeroicUnit;
import BusinessLayer.Tiles.Unit;
import View.Printer;

public class Boss extends Enemy implements HeroicUnit {
    private int visionRange;
    private int abilityFrequency;
    private int combatTicks;
    private Printer printer;

    public Boss(Printer printer, int visionRange, int abilityFrequency, int experienceValue, char T, String name, int x, int y, int healthAmount, int healthPool, int attackPoints, int defensePoints, Unit player) {
        super(experienceValue, T, name, x, y, healthAmount, healthPool, attackPoints, defensePoints, player);
        this.printer = printer;
        this.visionRange = visionRange;
        this.abilityFrequency = abilityFrequency;
        combatTicks = 0;
    }

    @Override
    public int[] onGameTick() {
        int[] arr = new int[2];
        arr[0] = getX();
        arr[1] = getY();
        double range = Math.sqrt(Math.pow(getX() - getPlayer().getX(), 2) + Math.pow(getY() - getPlayer().getY(), 2));
        if (range < visionRange) {
            if (combatTicks == abilityFrequency) {
                combatTicks = 0;
                castAbility();
            } else {
                combatTicks = combatTicks + 1;
                int dx = getX() - getPlayer().getX();
                int dy = getY() - getPlayer().getY();
                arr = movingResult(dx, dy, getX(), getY());
            }
        } else {
            combatTicks = 0;
            arr = randomMovingResult(getX(), getY());
        }
        return arr;
    }

    @Override
    public void castAbility() {
        double range = Math.sqrt(Math.pow(getX() - getPlayer().getX(), 2) + Math.pow(getY() - getPlayer().getY(), 2));
        if (range < visionRange) {
            printer.write("Boss activated his Ability and started shooting at you");
            int attack = getAttackPoints();
            int defense = getPlayer().rollPoints_defense();
            int damage = Math.max(0, attack - defense);
            getPlayer().reduceHealth(damage);
            printer.write(getName() + " dealt " + damage + " damage to " + getPlayer().getName() + ".");
        } else {
            printer.write("The player is not in range enough to cast Boss Ability");
        }
    }
}