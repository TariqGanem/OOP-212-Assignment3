package BusinessLayer.Tiles;

import BusinessLayer.Visitor;

public class Wall extends Tile {

    public static final char symbole = '#';

    public Wall(int x, int y) {
        super(symbole, x, y);
    }

    @Override
    public String toString() {
        return String.valueOf(symbole);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
