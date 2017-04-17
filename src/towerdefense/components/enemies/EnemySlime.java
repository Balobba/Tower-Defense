package towerdefense.components.enemies;

import towerdefense.graphics.Level;

/**
 * The enemy slime and its attributes. Extends the abstract enemy class.
 */
public class EnemySlime extends Enemy {


    private static final int ID_ENEMY = 0;
    private static final int PRICE = 10;
    private static final int HEALTH = 10;
    private static final int DAMAGE = 5; //5
    private static final double SPEED = 4;

    public EnemySlime(Level level) {
        super(ID_ENEMY, PRICE, HEALTH, Enemy.POINT_SLIME, DAMAGE, SPEED, "EnemySlime", level);

    }

}