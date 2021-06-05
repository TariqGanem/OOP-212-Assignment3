package BusinessLayer.Tiles;

import BusinessLayer.Enemies.Enemy;
import BusinessLayer.Player.Player;
import BusinessLayer.Visitor;

public abstract class Tile implements Visitor {
    private char tile;
    private int x;
    private int y;

    public Tile(char tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public void interAct(Tile tile) {
        tile.accept(this);
    }

    @Override
    public void visit(Empty empty) {
        int oldX = x, oldY = y;
        setX(empty.getX());
        setY(empty.getY());
        empty.setX(oldX);
        empty.setY(oldY);
    }

    @Override
    public void visit(Enemy Enemy) {
    }

    @Override
    public void visit(Player player) {
    }

    @Override
    public void visit(Wall wall) {
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
