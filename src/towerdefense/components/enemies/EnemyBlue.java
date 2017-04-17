package towerdefense.components.enemies;

import towerdefense.graphics.Level;

/**
 * The enemy Mr Blue Berry and its attributes. Extends the abstract enemy class.
 */
public class EnemyBlue extends Enemy {

    private static final int ID_ENEMY = 1;
    private static final int PRICE = 25;
    private static final int HEALTH = 7;
    private static final int DAMAGE = 10;
    private static final double SPEED = 10;

    public EnemyBlue(Level level) {

        super(ID_ENEMY, PRICE, HEALTH, Enemy.POINT_BLUE, DAMAGE, SPEED, "mrblueberry", level);
    }

}

