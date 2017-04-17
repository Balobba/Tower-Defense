package towerdefense.logic;

import towerdefense.components.enemies.Enemy;
import towerdefense.components.enemies.EnemyFactory;
import towerdefense.graphics.Screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class has all the information of how, where and when to spawn enemies onto the field.
 */
public class Wave {


    private final Screen screen;
    private int waveNumber;
    private boolean waveSpawning;

    private int currentDelay = 0;
    private int pointsThisRound;

    private static final int OFFSET_WAVES = 10;

    private final EnemyFactory factory;

    private int currentPoints;

    private final List<Enemy> enemyInWave;

    public Wave(Screen screen) {
        this.screen = screen;
        this.factory = new EnemyFactory(screen.getLevel());
        this.enemyInWave = new ArrayList<>();
    }



    public void nextWave() { //This method is called after each completed wave.

        this.waveNumber = waveNumber + 1;

        this.pointsThisRound = waveNumber * 7;
        this.currentPoints = pointsThisRound;

        screen.getLevel().getEnemies().clear(); //Reseting enemies
        createNextWave();

    }

    private void createNextWave() { //Creates a wave beforehand, and is then used in the spawnEnemies function.

        for (int i = Enemy.ENEMY_LIST.length - 1; i >= 0; i--) {

            System.out.println(Enemy.ENEMY_LIST[i]);

            if (Enemy.ENEMY_LIST[i] != null && Enemy.ENEMY_LIST[i].points <= waveNumber + OFFSET_WAVES && currentPoints > 0) {

                int amt = (currentPoints / Enemy.ENEMY_LIST[i].points);

                currentPoints -= amt * Enemy.ENEMY_LIST[i].points;

                for (int j = 0; j < amt; j++) {
                    enemyInWave.add(factory.getEnemy(Enemy.ENEMY_LIST[i].points)); //Calls the factory for an enemy and adds it to the array.
                }

            }
        }
        Collections.shuffle(enemyInWave);

        screen.getLevel().getEnemies().clear();

        this.waveSpawning = true;

        if(!screen.getLevel().getEnemies().isEmpty()){
            screen.getLevel().setWaveInProgress(true);
        }



    }



    public void spawnEnemies() {//Spawns enemies based upon if the enemies have enough points to be spawned in the current round/level

        int spawnRate = 1000;
        if (currentDelay < spawnRate) {
            currentDelay++;
        } else {
            currentDelay = 0;

            if (!enemyInWave.isEmpty()) {

                this.screen.spawnEnemy(enemyInWave.get(0));
                System.out.println("Enemy Spawned");
                enemyInWave.remove(0);

                if(enemyInWave.size()<=0){
                    this.waveSpawning = false;

                }
            }
        }
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }

    public boolean isWaveSpawning() {
        return waveSpawning;
    }


}
