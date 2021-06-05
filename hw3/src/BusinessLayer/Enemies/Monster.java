package BusinessLayer.Enemies;

import BusinessLayer.Tiles.Unit;

public class Monster extends Enemy {
    private int visionRange;

    public Monster(int visionRange, int experienceValue, char T, String name, int x, int y, int healthAmount, int healthPool, int attackPoints, int defensePoints, Unit player) {
        super(experienceValue, T, name, x, y, healthAmount, healthPool, attackPoints, defensePoints, player);
        this.visionRange = visionRange;
    }

    @Override
    public int[] onGameTick() {
        int[] arr;
        int myX = getX();
        int myY = getY();
        int dx = myX - getPlayer().getX();
        int dy = myY - getPlayer().getY();
        double range = Math.sqrt((Math.pow(dx, 2)) + (Math.pow(dy, 2)));
        if (range < visionRange) {
            arr = movingResult(dx, dy, myX, myY);
        } else {
            arr = randomMovingResult(myX, myY);
        }
        return arr;
    }
}
