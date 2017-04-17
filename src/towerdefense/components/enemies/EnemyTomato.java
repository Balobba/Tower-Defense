package towerdefense.components.enemies;

import towerdefense.graphics.Level;

/**
 * The enemy killer tomato and its attributes. Extends the abstract enemy class.
 */
public class EnemyTomato extends Enemy {

    private static final int ID_ENEMY = 2;
    private static final int PRICE = 75;
    private static final int HEALTH = 75;
    private static final int DAMAGE = 15; //15
    private static final double SPEED = 3;

    public EnemyTomato(Level level) {

        super(ID_ENEMY, PRICE, HEALTH, Enemy.POINT_TOMATO, DAMAGE, SPEED, "killertomato", level);
    }

}