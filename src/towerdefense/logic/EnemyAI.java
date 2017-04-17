package towerdefense.logic;
import towerdefense.components.enemies.Enemy;
import towerdefense.graphics.Level;
import towerdefense.graphics.Screen;


/**
 * The AI class for the enemies. Handles the movement of the AI-enemies with the move-method.
 */
public class EnemyAI {

    private EnemyRoute enemyRoute = null;

    private int basePosX = 0;
    private int basePosY = 0;

    private double xPos;
    private double yPos;
    private boolean attack; //If the enemy has reached the goal
    private int routePosX;
    private int routePosY;

    private boolean hasAttacked = false;

    public boolean isHasAttacked() {
	return hasAttacked;
    }

    public void setHasAttacked(final boolean hasAttacked) {
	this.hasAttacked = hasAttacked;
    }

    public EnemyAI(Level level) {

	enemyRoute = new EnemyRoute(level);

	basePosX = level.getBasePoint().x;
	basePosY = level.getBasePoint().y;
	this.attack = false;

	this.routePosX = level.getSpawnPoint().x;
	this.routePosY = level.getSpawnPoint().y;

	this.xPos =  (int) (level.getSpawnPoint().getX() * Screen.SQUARE);
	this.yPos =  (int) (level.getSpawnPoint().getY() * Screen.SQUARE);



    }


    public double getxPos() {
	return xPos;
    }

    public double getyPos() {
	return yPos;
    }

    public void setAttack(final boolean attack) {
	this.attack = attack;
    }

    private static final int SPEED_DIVIDER = 24;



    public void move(Enemy enemy) { //the move function of the enemies

	if(routePosX == xPos / (int) Screen.SQUARE && routePosY == yPos / (int) Screen.SQUARE) {

	    if (routePosX == basePosX && routePosY == basePosY) { // if the enemy found the base then the enemy dies and you get hurt

		this.attack = true;

	    } else if (enemyRoute.getRoute()[routePosX][routePosY] == EnemyRoute.UPP) {//UPP
		this.routePosY = routePosY - 1;
	    } else if (enemyRoute.getRoute()[routePosX][routePosY] == EnemyRoute.DOWN) {//DOWN
		this.routePosY = routePosY + 1;
	    } else if (enemyRoute.getRoute()[routePosX][routePosY] == EnemyRoute.RIGHT) {//RIGHT
		this.routePosX = routePosX + 1;
	    } else if (enemyRoute.getRoute()[routePosX][routePosY] == EnemyRoute.LEFT) {//LEFT
		this.routePosX = routePosX - 1;

	    }
	    else{

		cantFindRoute();
	    }
	}

	else{

	    double xGridPos = xPos / Screen.SQUARE;
	    double yGridPos = yPos / Screen.SQUARE;


	    if (xGridPos > routePosX) {
		this.xPos -= enemy.speed / SPEED_DIVIDER;
		if(xPos < routePosX * (int)Screen.SQUARE){
		    this.xPos = (routePosX * (int) Screen.SQUARE);
		}
	    }
	    if (xGridPos < routePosX) {
		this.xPos = xPos + enemy.speed / SPEED_DIVIDER;
		if(xPos > routePosX * (int) Screen.SQUARE){
		    this.xPos = (routePosX * (int) Screen.SQUARE);
		}
	    }
	    if (yGridPos > routePosY) {
		this.yPos = yPos - enemy.speed / SPEED_DIVIDER;
		if(yPos < routePosY * (int) Screen.SQUARE){
		    this.yPos = (routePosY * (int) Screen.SQUARE);
		}
	    }
	    if (yGridPos < routePosY) {
		this.yPos = yPos + enemy.speed / SPEED_DIVIDER;
		if(yPos > routePosY * (int) Screen.SQUARE){
		    this.yPos = (routePosY * (int) Screen.SQUARE);
		}
	    }


	}


    }

    public void cantFindRoute(){

	System.out.println("Can't find the enemyRoute!");
    }

    public boolean isAttack() {
	return attack;
    }
}
