package towerdefense.components.enemies;

import towerdefense.graphics.Level;

/**
 * The factory class of the enemies. This class method getEnemy is the method which is called upon when
 * an enemy needs to be created. getEnemy-method is the factory method.
 */
public class EnemyFactory {

    private Level level = null;
    public EnemyFactory(Level level) {
        this.level = level;
    }

    public Enemy getEnemy(int points) { //Use getEnemy method to get object of an Enemy-type. This is the factory method!

        switch (points) {

            case Enemy.POINT_SLIME:
                return new EnemySlime(level);
            case Enemy.POINT_BLUE:
                return new EnemyBlue(level);
            case Enemy.POINT_TOMATO:
                return new EnemyTomato(level);
            case Enemy.POINT_SUN:
                return new EnemySun(level);
            default:

                return null;

        }
    }
}




