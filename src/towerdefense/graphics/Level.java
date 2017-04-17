package towerdefense.graphics;

import towerdefense.components.enemies.Enemy;
import towerdefense.components.towers.Tower;
import towerdefense.logic.Player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * The level class locates the spawn- and basepoint of the map. The map is read and constructed by the LevelFile class.
 * This class also has information of all the ongoing actions that is executed on during the gameplay, such as the update methods
 * for enemies and towers.
 */
public class Level {

    private Player player = null;
    private Point spawnPoint = null;
    private Point basePoint = null;

    /**
     * The map size in x-axle
     */
    public static final int MAP_SIZE_X = 22;
    /**
     * The map size in y-axle
     */
    public static final int MAP_SIZE_Y = 14;

    private final Tower[][] towerMap = new Tower[22][14];

    private int[][] map = new int[MAP_SIZE_X][MAP_SIZE_Y]; //The array of the map in game.


    private List <Enemy> enemies = new ArrayList<>();
    private List<Enemy> enemiesToDestroy = new ArrayList<>();

    private boolean waveInProgress = false;


    public void createPlayer(Player player){
	this.player = player;

    }

    public void findSpawnAndBase() {

	for (int x = 0; x < map.length; x++) {
	    for (int y = 0; y < map[0].length; y++) {

		if (map[x][y] == 2) {

		    this.spawnPoint = new Point(x, y);

		}

		if (map[x][y] == 3) {

		    this.basePoint = new Point(x, y);
		}
	    }
	}
    }

    public void addEnemy(Enemy enemy){
	enemies.add(enemy);
    }

    public void updateLevel(){
	enemyUpdate();
	towerUpdate();

    }

    private void removeEnemies(){

	for(Enemy enemy: enemiesToDestroy){

	    enemies.remove(enemy);
	}
	enemiesToDestroy.clear();


    }

    private void enemyUpdate() {

	enemies.stream().filter(enemy -> enemy != null).forEach(enemy -> {
	    int healthLost = enemy.damage;
	    int moneyEarnIfDead = enemy.price;

	    if (!enemy.enemyAlive()) { //if not dead
		player.setMoney(player.getMoney() + moneyEarnIfDead);
		enemiesToDestroy.add(enemy); //removes them from the game when killed by towers
	    } else {
		if (!enemy.getEnemyAI().isAttack()) {

		    enemy.getEnemyAI().move(enemy);

		} else if (!enemy.getEnemyAI().isHasAttacked()) {

		    player.setHealth(player.getHealth() - healthLost);

		    enemy.getEnemyAI().setHasAttacked(true);

		    enemy.getEnemyAI().setAttack(false);
		    enemiesToDestroy.add(enemy); //removes them from the game when reached base

		}
	    }
	});

	removeEnemies(); //removes the enemies that were ment to die!


	if(enemies.isEmpty()){ //when all enemies have died (when the round has finished)
	    waveInProgress = false; //prevents the user from starting a new wave before the previous one is finished

	}



    }


    private void towerAttack(int x, int y) {

	Tower tower = this.towerMap[x][y];
	Enemy target=tower.calculateEnemy(enemies, x, y);

	if(target != null){

	    if(tower.getAttackDelay() > tower.getMaxAttackDelay()){


		target.setHealth(target.getHealth() - tower.getDamage()); //fires
		tower.setAttackDelay(0); //resets attack delay

		System.out.println("[Tower] Enemy attacked! health: " + target.getHealth() + " x: " + x + " " + "y: " + y);
	    }else{

		tower.setAttackDelay(tower.getAttackDelay() + 1);
	    }
	}
    }



    private void towerUpdate() {
	for (int x = 0; x < MAP_SIZE_X; x++) {
	    for (int y = 0; y < MAP_SIZE_Y; y++) {
		if(towerMap[x][y] !=null){
		    towerAttack(x, y);
		}
	    }
	}
    }





    public int[][] getMap() {
	return map;
    }

    public void setMap(int[][] map) {
	this.map = map;
    }

    public Point getSpawnPoint() {
	return spawnPoint;
    }

    public Point getBasePoint() {
	return basePoint;
    }

    public void setBasePoint(Point basePoint) {
	this.basePoint = basePoint;
    }

    public Tower[][] getTowerMap() {
	return towerMap;
    }

    public List<Enemy> getEnemies() {
	return enemies;
    }

    public boolean isWaveInProgress() {
	return waveInProgress;
    }

    public void setWaveInProgress(boolean waveInProgress) {
	this.waveInProgress = waveInProgress;
    }
}
