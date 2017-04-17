package towerdefense.components.enemies;

import towerdefense.graphics.Level;

/**
 * The enemy Nuclear sun and its attributes. Extends the abstract enemy class.
 */
public class EnemySun extends Enemy {

    private static final int ID_ENEMY = 3;
    private static final int PRICE = 200;
    private static final int HEALTH = 100;
    private static final int DAMAGE = 20;
    private static final double SPEED = 1;

    public EnemySun(Level level) {

        super(ID_ENEMY, PRICE, HEALTH, Enemy.POINT_SUN, DAMAGE, SPEED, "nuclearsun", level);
    }

}