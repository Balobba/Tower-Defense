package towerdefense.components.enemies;

import towerdefense.graphics.Level;
import towerdefense.logic.EnemyAI;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Enemy class, which has all the information of what properties an enemy has, including a constructor.
 *
 * This class is called by the other unique enemy classes.
 *
 */
public class Enemy {

    private Image texture = null;
    private EnemyAI enemyAI = null;

    /**
     * Amount of unique enemies available
     */
    public static final Enemy[] ENEMY_LIST = new Enemy[10];

    /**
     * The enemy "slime" is worth POINT_SLIME points.
     */
    public static final int POINT_SLIME = 1;
    /**
     * The enemy "blueberry" is worth POINT_BLUE points.
     */
    public static final int POINT_BLUE = 3;
    /**
     * The enemy "tomato" is worth POINT_TOMATO points.
     */
    public static final int POINT_TOMATO = 5;
    /**
     * The enemy "sun" is worth POINT_SUN points.
     */
    public static final int POINT_SUN = 11;


    /**
     * This id represents the individual enemies
     */
    public final int id;
    /**
     * The gold reward for killing an enemy
     */
    public final int price;
    /**
     * The speed of the enemy
     */
    public final double speed;
    /**
     * The damage of the enemy
     */
    public final int damage;
    /**
     * The health of the enemy
     */
    private int health;
    /**
     * The amount of points that the enemy is worth when spawning. The level must be over the amount of points to spawn
     */
    public final int points;

    private final Level level;




    Enemy(int id, int price, int health, int points, int damage, double speed, String textureFile, Level level) {

	this.id = id;
	this.price = price;
	this.speed = speed;
	this.damage = damage;
	this.health = health;
	this.points = points;
	this.texture = new ImageIcon("res/enemies/" + textureFile + ".png").getImage();
	this.level = level;
	this.enemyAI = new EnemyAI (level);
    }


    public Image getTexture() {
	return texture;
    }

    public EnemyAI getEnemyAI() {
	return enemyAI;
    }

    public Boolean enemyAlive(){

	return health > 0;
    }

    public int getHealth() {
	return health;
    }

    public void setHealth(final int health) {
	this.health = health;
    }

    public Level getLevel() {
	return level;
    }
}

