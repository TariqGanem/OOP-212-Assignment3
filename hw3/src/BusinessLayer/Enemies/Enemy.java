package BusinessLayer.Enemies;

import BusinessLayer.Player.Player;
import BusinessLayer.Tiles.Unit;
import BusinessLayer.Visitor;

import java.util.Random;

public abstract class Enemy extends Unit {

    private Unit player;

    public Enemy(int experienceValue, char T, String name, int x, int y, int healthAmount, int healthPool, int attackPoints, int defensePoints, Unit player) {
        super(T, name, x, y, healthAmount, healthPool, attackPoints, defensePoints, experienceValue);
        this.player = player;
    }

    public Unit getPlayer() {
        return player;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public void visit(Player player) {
        attack(player);
    }

    public abstract int[] onGameTick();

    public int[] movingResult(int dx, int dy, int myX, int myY) {
        int[] arr = new int[2];
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                arr[0] = myX - 1;
                arr[1] = myY;
            } else {
                arr[0] = myX + 1;
                arr[1] = myY;
            }
        } else {
            if (dy > 0) {
                arr[0] = myX;
                arr[1] = myY - 1;
            } else {
                arr[0] = myX;
                arr[1] = myY + 1;
            }
        }
        return arr;
    }

    public int[] randomMovingResult(int myX, int myY) {
        int[] arr = new int[2];
        int rand = new Random().nextInt(5);
        switch (rand) {
            case 0:
                arr[0] = myX - 1;
                arr[1] = myY;
                break;
            case 1:
                arr[0] = myX + 1;
                arr[1] = myY;
                break;
            case 2:
                arr[0] = myX;
                arr[1] = myY + 1;
                break;
            case 3:
                arr[0] = myX;
                arr[1] = myY - 1;
                break;
            case 4:
                arr[0] = myX;
                arr[1] = myY;
                break;
        }
        return arr;
    }
}
