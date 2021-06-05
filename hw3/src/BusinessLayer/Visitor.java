package BusinessLayer;

import BusinessLayer.Enemies.Enemy;
import BusinessLayer.Player.Player;
import BusinessLayer.Tiles.Empty;
import BusinessLayer.Tiles.Wall;

public interface Visitor {

    void visit(Empty empty);

    void visit(Wall wall);

    void visit(Enemy Enemy);

    void visit(Player player);

    void accept(Visitor v);
}
