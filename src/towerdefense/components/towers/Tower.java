package towerdefense.components.towers;

import towerdefense.components.enemies.Enemy;
import towerdefense.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The tower class has all the information of the different towers.
 * The CalculateEnemy-fuction calculates what the tower can attack.
 * The texture file of the towers are called upon in this class.
 *
 */
public class Tower implements Cloneable{


    private Image texture = null;

    /**
     * The number of towers that can exist in the tower shop.
     */
    public static final int NUMBER_OF_TOWERS = 7;
    /**
     * This list stores all towers that exists.
     */
    public static final Tower[] TOWER_LIST = new Tower[NUMBER_OF_TOWERS];
    private int id;
    private int cost;
    private int range;
    private int damage;

    private int attackDelay; // (timer) Pause between each attack (reload)

    private int maxAttackDelay;


    public String getDescriptionDamage() {
        return "Damage: " + damage;
    }

    public String getDescriptionRange() {
        return "Range: " + range;
    }

    /*

    maxAttackDelay: Fast: 100, Medium: 200, Slow: 500, Really slow: 1000

     */

    /**
     * Lightning tower icon
     */
    public static final Tower LIGHTNING_TOWER = TowerLightning(0, 10, 2, 1, 200, "LightningTower");
    /**
     * Arrow tower icon
     */
    public static final Tower ARROW_TOWER = TowerLightning(1, 25, 4, 2, 100,"ArrowTower");
    /**
     * Fire tower icon
     */
    public static final Tower FIRE_TOWER = TowerLightning(2, 30, 3, 20, 200, "FireTower");
    /**
     * Arcane tower icon
     */
    public static final Tower ARCANE_TOWER = TowerLightning(3, 150, 1, 50, 750, "ArcaneTower");

    Tower(int id, int cost, int range, int damage, int maxAttackDelay, String texture) {

        if (TOWER_LIST[id] != null) {

            System.out.println("[TowerInitialization] 2 towers with same id! id =" + id);
        } else {

            TOWER_LIST[id] = this;

            this.id = id;
            this.cost = cost;
            this.range = range;
            this.damage = damage;
            this.maxAttackDelay = maxAttackDelay;
            this.texture = new ImageIcon("res/tower/" + texture + ".png").getImage();
        }
    }

    public Enemy calculateEnemy(Iterable<Enemy> enemies, int x, int y) {
        //enemies in our range
        List<Enemy> enemiesInRange = new ArrayList<>();

        int towerRadius = range;

        for (Enemy enemy: enemies) { //Checks if enemy i is in range of the tower.
            if (enemy != null) {
                int enemyX = (int) (enemy.getEnemyAI().getxPos() / Screen.SQUARE);
                int enemyY = (int) (enemy.getEnemyAI().getyPos() / Screen.SQUARE);

                int dx = enemyX - x;
                int dy = enemyY - y;
                int dRadius = towerRadius + 1;

                if ((dx * dx) + (dy * dy) < (dRadius * dRadius)) {

                    enemiesInRange.add(enemy);
                }
            }
        }

        return enemiesInRange.isEmpty() ? null : enemiesInRange.get(0); //returns the first enemy it spots
    }

    public Object clone(){ // To be able to clone towers without disturbing each other
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Image getTexture() {
        return texture;
    }



    public int getId() {
        return id;
    }



    public int getCost() {
        return cost;
    }



    public int getRange() {
        return range;
    }



    public int getDamage() {
        return damage;
    }



    public int getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(int attackDelay) {
        this.attackDelay = attackDelay;
    }


    public int getMaxAttackDelay() {
        return maxAttackDelay;
    }


}



