package BusinessLayer.Enemies;

import BusinessLayer.Tiles.Empty;
import BusinessLayer.Tiles.Unit;

public class Trap extends Enemy {
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(int experienceValue, char T, int x, int y, String name, int healthAmount, int healthPool, int attackPoints, int defensePoints, int visibilityTime, int invisibilityTime, Unit player) {
        super(experienceValue, T, name, x, y, healthAmount, healthPool, attackPoints, defensePoints, player);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        ticksCount = 0;
        visible = true;
    }

    @Override
    public int[] onGameTick() {
        visible = ticksCount < visibilityTime;
        ticksCount = (ticksCount == visibilityTime + invisibilityTime) ? 0 : ticksCount + 1;
        double range = Math.sqrt((Math.pow(getX() - getPlayer().getX(), 2)) + (Math.pow(getY() - getPlayer().getY(), 2)));
        if (range < 2)
            attack(getPlayer());
        int[] arr = new int[2];
        arr[0] = getX();
        arr[1] = getY();
        return arr;
    }

    @Override
    public String toString() {
        return visible ? super.toString() : String.valueOf(Empty.symbole);
    }
}

