package towerdefense.logic;

import towerdefense.graphics.Level;

import java.awt.*;

/**
 * This class has functions which calculates the route to the goal of the map. It also has variables of the directions and positions.
 * Each enemy gets a unique route.
 */
public class EnemyRoute {


    private static final int OUT_OF_BOUNCE_Y = 13;
    private static final int OUT_OF_BOUNCE_X = 21;
    private final Level level;

    private final int[][] route = new int[22][14];


    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int UPP = 4;

    private int lastPos = -1;

    private boolean foundBasePoint = false;

    private int xPos;
    private int yPos;

    public EnemyRoute(Level level) {
	this.level = level;
	this.xPos = this.level.getSpawnPoint().x;
	this.yPos = this.level.getSpawnPoint().y;
	calculateRoute();

    }

    private void calculateRoute() {

	do {
	    calculateNextPos();//find the next position if you haven't found the base yet
	} while (!foundBasePoint);
    }

    private void calculateNextPos() {

	for (int i = 1; i < 5; i++) {
	    if (i != lastPos) {

		// Bounds check
		if (yPos  > 0 && yPos < OUT_OF_BOUNCE_Y && xPos < OUT_OF_BOUNCE_X && xPos > 0){

		    int baseBlock = 3;
		    if (i == UPP) {
			if (level.getMap()[xPos][yPos - 1] == 1) {
			    lastPos = DOWN;
			    route[xPos][yPos] = UPP;
			    yPos--;
			    System.out.println("UPP");
			    break;
			}

		    } else if (level.getMap()[xPos][yPos - 1] == baseBlock) {
			this.level.setBasePoint(new Point(xPos, yPos));
			foundBasePoint=true;

		    }

		    ///////////////////////////////////////////////////////

		    if (i == RIGHT) {
			if (level.getMap()[xPos + 1][yPos] == 1) {
			    lastPos = LEFT;
			    route[xPos][yPos] = RIGHT;
			    xPos++;
			    System.out.println("RIGHT");
			    break;
			}

		    } else if (level.getMap()[xPos + 1][yPos] == baseBlock) {
			this.level.setBasePoint(new Point(xPos, yPos));
			foundBasePoint=true;

		    }

		    ///////////////////////////////////////////////////////

		    if (i == LEFT) {
			if (level.getMap()[xPos - 1][yPos] == 1) {
			    lastPos = RIGHT;
			    route[xPos][yPos] = LEFT;
			    xPos--;
			    System.out.println("LEFT");
			    break;
			}

		    } else if (level.getMap()[xPos - 1][yPos] == baseBlock) {
			this.level.setBasePoint(new Point(xPos, yPos));
			foundBasePoint=true;
		    }

		    ///////////////////////////////////////////////////////

		    if (i == DOWN) {
			if (level.getMap()[xPos][yPos + 1] == 1) {
			    lastPos = UPP;
			    route[xPos][yPos] = DOWN;
			    yPos++;
			    System.out.println("DOWN");
			    break;
			}

		    } else if (level.getMap()[xPos][yPos + 1] == baseBlock) {
			this.level.setBasePoint(new Point(xPos, yPos));
			foundBasePoint=true;

		    }
		}
	    }
	}
    }

    public int[][] getRoute() {
	return route;
    }

}
