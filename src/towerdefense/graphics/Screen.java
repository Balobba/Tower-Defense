package towerdefense.graphics;

import towerdefense.components.enemies.*;
import towerdefense.components.towers.Tower;
import towerdefense.logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * This is the head class that has variables stored, has the paint method, mouse/keyboard handlers-methods, select/place-method for towers.
 * This class has load/start/run/game over-method for the game. It also has an update-method for the game.
 */
public class Screen extends JPanel implements Runnable {


    private static final int INFOBOX_ENEMIES_X_COORD = 835;
    private static final int INFOBOX_ENEMIES_Y_COORD = 50;
    /**
     * INFOBOX_ENEMIES_OFFSET_MULTIPLIER
     */
    public static final int INFOBOX_ENEMIES_OFFSET_MULTI_X = 25;
    /**
     * INFOBOX_ENEMIES_OFFSET_MULTIPLIER
     */
    public static final int INFOBOX_ENEMIES_OFFSET_MULTI_Y = 14;
    /**
     * DESCRIPTION_TEXT_RANGE
     */
    public static final int DESCRIPTION_TEXT_RANGE_X = 840;
    /**
     * DESCRIPTION_TEXT_DAMAGE
     */
    public static final int DESCRIPTION_TEXT_DAMAGE_X = 840;
    /**
     * DESCRIPTION_TEXT_DAMAGE
     */
    public static final int DESCRIPTION_TEXT_DAMAGE_Y = 205;
    /**
     * DESCRIPTION_TEXT_RANGE
     */
    public static final int DESCRIPTION_TEXT_RANGE_Y = 230;
    /**
     * OFFSET FOR DRAWING ENEMY IMAGE
     */
    public static final double DRAW_ENEMY_IMAGE_OFFSET = 1.5;
    /**
     * INFOBOX VARIABLE
     */
    public static final int INFOBOX_X_COORD = 50;
    /**
     * INFOBOX VARIABLE
     */
    public static final int INFOBOX_Y_COORD = 550;
    /**
     * INFOBOX VARIABLE
     */
    public static final int INFOBOX_HEIGHT_FIRST = 66;
    /**
     * INFOBOX VARIABLE
     */
    public static final int INFOBOX_HEIGHT_SECOND = 33;
    /**
     * INFOBOX VARIABLE
     */
    public static final int INFOBOX_TEXT_OFFSET_HEALTH = 25;
    /**
     * INFOTEXT VARIABLE
     */
    public static final int INFOBOX_TEXT_OFFSET_MONEY = 610;
    /**
     * INFOTEXT VARIABLE
     */
    public static final int INFOBOX_TEXT_OFFSET_WAVE = 645;
    /**
     * INFOTEXT VARIABLE
     */
    public static final int INFOTEXT_ENTER_X_COORD = 500;
    /**
     * INFOTEXT VARIABLE
     */
    public static final int INFOTEXT_ENTER_Y_COORD = 610;
    /**
     * TOWERLIST OFFSET
     */
    public static final int TOWERLIST_OFFSET_X = 200;
    /**
     * TOWERLIST OFFSET
     */
    public static final int TOWERLIST_OFFSET_Y = 575;
    /**
     * MULTIPLIER FOR CROPING IMAGES
     */
    public static final int IMAGE_TERRAIN_CROP_MULTIPLIER = 25;
    /**
     * OFFSET VARIABLE
     */
    public static final int TOWER_X_COORD_OFFSET = 52;
    /**
     * OFFSET VARIABLE
     */
    public static final int TOWER_Y_COORD_OFFSET = 75;


    /**
     * COLOR CODE RED
     */
    //Color code red
    public static final int CCR1 = 131;
    /**
     * COLOR CODE RED
     */
    public static final int CCR2 = 33;
    /**
     * COLOR CODE RED
     */
    public static final int CCR3 = 219;

    /**
     * COLOR CODE GREY
     */
    //Color code grey
    public static final int CCG1 = 64;
    private final Thread thread = new Thread(this);

    private final Frame frame;
    private Player player = null;
    private Level level = null;
    private LevelFile levelFile = null;
    private Wave wave = null;


    private final ImageIcon gameover;
    private final ImageIcon splashscreen;

    private boolean running = false;
    private boolean startedGame = false;

    public Level getLevel() {
	return level;
    }

    private int scene;

    /**
     * //The size of the squares in game. This variable is the base for all the frames in game.
     */
    public static final double SQUARE = 35;
    private static final int OFFSET_GRID = 15; //For the grid
    private int hand = 0;
    private int handXpos = 0;
    private int handYpos = 0;
    private static final int MAP_XPOS_START = 50; //The x-position for the corner of the map-screen.
    private static final int MAP_YPOS_START = 50; //The y-position for the corner of the map-screen.

    private Tower selectedTower = null;


    private final Image[] terrain = new Image[100];


    public Screen(Frame frame) {

	this.gameover = new ImageIcon("res/gameover.png");
	this.splashscreen = new ImageIcon("res/splashfinal.png");
	this.frame = frame;
	thread.start();
	this.frame.addKeyListener(new KeyHandler(this));
	this.frame.addMouseListener(new MouseHandler(this));
	this.frame.addMouseMotionListener(new MouseHandler(this));
    }

    @Override
    public void paintComponent(Graphics g){

	g.clearRect(0,0,this.frame.getWidth(), this.frame.getHeight());

	if(scene == 0){ //Splashscreen
	    g.drawImage(splashscreen.getImage(), 0, 0, getWidth(), getHeight(), null);
	}
	else if(scene == 1){
	    //background
	    g.setColor(Color.BLACK);
	    g.fillRect(0,0,this.frame.getWidth(), this.frame.getHeight());

	    //the grid
	    g.setColor(Color.GRAY);
	    for(int x = 0; x<Level.MAP_SIZE_X ; x++){
		for(int y = 0;y<Level.MAP_SIZE_Y; y++ ){
		    g.drawImage(terrain[level.getMap()[x][y]], (int) SQUARE + (x * (int) SQUARE) + OFFSET_GRID, (int) SQUARE + (y * (int) SQUARE) +
														OFFSET_GRID, (int) SQUARE, (int) SQUARE, null);
		    g.drawRect(MAP_XPOS_START + (x * (int) SQUARE), MAP_YPOS_START + (y * (int) SQUARE), (int) SQUARE, (int) SQUARE);
		}
	    }


	    //Info box

	    g.setColor(Color.GRAY);
	    g.drawRect(INFOBOX_ENEMIES_X_COORD, INFOBOX_ENEMIES_Y_COORD, frame.getWidth() - (int) SQUARE *
											    INFOBOX_ENEMIES_OFFSET_MULTI_X, (int) SQUARE *
															    INFOBOX_ENEMIES_OFFSET_MULTI_Y);
	    g.drawRect(INFOBOX_ENEMIES_X_COORD + (int) SQUARE / 3 - 1, INFOBOX_ENEMIES_Y_COORD - 1 + (int) SQUARE / 2, (int) SQUARE * 3 + 2, (int) SQUARE * 3 + 2);
	    if (selectedTower != null) {
		g.drawImage(selectedTower.getTexture(), INFOBOX_ENEMIES_X_COORD + (int) SQUARE / 3 , INFOBOX_ENEMIES_Y_COORD + (int) SQUARE / 2, (int) SQUARE * 3, (int) SQUARE * 3, null);
		g.drawString(selectedTower.getDescriptionDamage(), DESCRIPTION_TEXT_DAMAGE_X, DESCRIPTION_TEXT_DAMAGE_Y);
		g.drawString(selectedTower.getDescriptionRange(), DESCRIPTION_TEXT_RANGE_X, DESCRIPTION_TEXT_RANGE_Y);
	    }


	    //enemies

	    for (int i = 0; i < level.getEnemies().size(); ++i){

		if (level.getEnemies().get(i) != null) {

		    g.drawImage(level.getEnemies().get(i).getTexture(),
				(int) level.getEnemies().get(i).getEnemyAI().getxPos() + (int) (SQUARE * DRAW_ENEMY_IMAGE_OFFSET),
				(int) level.getEnemies().get(i).getEnemyAI().getyPos() + (int) (SQUARE * DRAW_ENEMY_IMAGE_OFFSET), null);
		}
	    }

	    //Display health, money and wave number
	    g.drawRect(INFOBOX_X_COORD, INFOBOX_Y_COORD, 100, 100);
	    g.drawRect(INFOBOX_X_COORD, INFOBOX_Y_COORD, 100, INFOBOX_HEIGHT_FIRST);
	    g.drawRect(INFOBOX_X_COORD, INFOBOX_Y_COORD, 100, INFOBOX_HEIGHT_SECOND);

	    g.drawString("Health: " + player.getHealth(), INFOBOX_X_COORD + 5, INFOBOX_Y_COORD + INFOBOX_TEXT_OFFSET_HEALTH);
	    g.drawString("Money: " + player.getMoney(), INFOBOX_X_COORD + 5, INFOBOX_TEXT_OFFSET_MONEY);
	    g.drawString("Wave: " + wave.getWaveNumber(), INFOBOX_X_COORD + 5, INFOBOX_TEXT_OFFSET_WAVE);
	    g.drawString("Press 'ENTER' to start next wave", INFOTEXT_ENTER_X_COORD, INFOTEXT_ENTER_Y_COORD);

	    //towers scroll list

	    for(int x = 0; x<Tower.NUMBER_OF_TOWERS; x++){
		for (int y = 0; y < 1; y++) {

		    if(Tower.TOWER_LIST[x + y * Tower.NUMBER_OF_TOWERS] != null){

			g.drawImage(Tower.TOWER_LIST[x + y * Tower.NUMBER_OF_TOWERS].getTexture(), (int) (TOWERLIST_OFFSET_X + (x *
																SQUARE)), (int) (TOWERLIST_OFFSET_Y + (y *
																				       SQUARE)), (int ) SQUARE, (int) SQUARE, null );
			if(Tower.TOWER_LIST[x + y * Tower.NUMBER_OF_TOWERS].getCost() > this.player.getMoney()){
			    //if tower cannot be bought
			    g.setColor(new Color(CCR1, CCR2, CCR2, CCR3));//IGNORE MAGIC NUMBER
			    g.fillRect((int) (TOWERLIST_OFFSET_X + (x * SQUARE)), (int) (TOWERLIST_OFFSET_Y + (y * SQUARE)), (int) SQUARE, (int) SQUARE);

			}

		    }

		    g.setColor(Color.GRAY);
		    g.drawRect((int) (TOWERLIST_OFFSET_X + (x * SQUARE)), (int) (TOWERLIST_OFFSET_Y + (y * SQUARE)), (int) SQUARE, (int) SQUARE);
		}
	    }

	    //towers on grid

	    for (int x = 0; x < Level.MAP_SIZE_X; x++) {
		for (int y = 0; y < Level.MAP_SIZE_Y; y++) {
		    Tower[][] towerMap = level.getTowerMap();
		    if(towerMap[x][y] != null){
			if(Objects.equals(selectedTower, towerMap[x][y])) {


			    g.setColor(Color.GRAY);
			    g.drawOval(MAP_XPOS_START + (x * (int) SQUARE) - (towerMap[x][y].getRange() * 2 * (int) SQUARE / 2) + (int) SQUARE / 4, MAP_YPOS_START + (y * (int) SQUARE) - (towerMap[x][y].getRange() * 2 * (int) SQUARE / 2) + (int) SQUARE / 4, (towerMap[x][y]
																																	  .getRange() * 2 * (int) SQUARE) + (int) SQUARE / 2, (
					       towerMap[x][y].getRange() * 2 * (int) SQUARE + (int) SQUARE / 2));
			    g.setColor(new Color(CCG1,CCG1 ,CCG1 ,CCG1 )); //IGNORE MAGIC NUMBER
			    g.fillOval(
				    MAP_XPOS_START + (x * (int) SQUARE) - (towerMap[x][y].getRange() * 2 * (int) SQUARE / 2) + (int) SQUARE / 4, MAP_YPOS_START + (y * (int) SQUARE) - (towerMap[x][y].getRange() * 2 * (int) SQUARE / 2) + (int) SQUARE / 4, (towerMap[x][y]
																																       .getRange() * 2 * (int) SQUARE) + (int) SQUARE / 2, (towerMap[x][y]
																																								    .getRange() * 2 * (int) SQUARE) + (int) SQUARE / 2);
			}
			g.drawImage(Tower.TOWER_LIST[towerMap[x][y].getId()].getTexture(), MAP_XPOS_START + (x * (int) SQUARE), MAP_YPOS_START + (y * (int) SQUARE), (int) SQUARE, (int) SQUARE, null);

		    }
		}
	    }
	    //HAND
	    if(hand != 0 && Tower.TOWER_LIST[hand - 1] != null){

		g.drawImage(Tower.TOWER_LIST[hand - 1].getTexture(), this.handXpos - (int) SQUARE / 2, this.handYpos - (int) SQUARE, (int) SQUARE, (int) SQUARE, null);
	    }

	}
	else if(scene == 2) { //game over screen

	    g.fillRect(0,0,this.frame.getWidth(), this.frame.getHeight());
	    g.drawImage(gameover.getImage(), 0, 0,getWidth(), getHeight() , null);


	}else{
	    g.setColor(Color.WHITE);
	    g.fillRect(0,0,this.frame.getWidth(), this.frame.getHeight());
	}

    }

    private void loadGame(){
	player = new Player();
	this.scene = 0;
	levelFile = new LevelFile();

	for (int y = 0; y < 10; y++) {
	    for (int x = 0; x < 10; x++) {
		terrain[x+(y*10)] = new ImageIcon("res/" + "/terrain.png").getImage();
		terrain[x+(y*10)] = createImage( new FilteredImageSource(terrain[x+(y*10)].getSource(), new CropImageFilter(x *IMAGE_TERRAIN_CROP_MULTIPLIER, y * IMAGE_TERRAIN_CROP_MULTIPLIER, IMAGE_TERRAIN_CROP_MULTIPLIER, IMAGE_TERRAIN_CROP_MULTIPLIER)));
	    }
	}

	running = true;
    }

    private void startGame(String level){

	this.scene = 1;
	this.level = levelFile.getLevel(level);
	this.level.createPlayer(player);
	this.level.findSpawnAndBase();


	////// Enemmies added to ENEMY_LIST
	Enemy.ENEMY_LIST[0] = new EnemySlime(this.level);
	Enemy.ENEMY_LIST[1] = new EnemyBlue(this.level);
	Enemy.ENEMY_LIST[2] = new EnemyTomato(this.level);
	Enemy.ENEMY_LIST[3] = new EnemySun(this.level);

	//////
	wave = new Wave(this);

	this.wave.setWaveNumber(0);

	startedGame = true;

    }

    @Override
    public void run() {

	loadGame();
	if (running) {
	    do {

		repaint();
		try {
		    sleep(2);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}


		if(startedGame){
		    update();
		}

	    } while (running);
	}

	System.exit(0);
    }

    private void update(){

	level.updateLevel();
	if(wave.isWaveSpawning()) {
	    wave.spawnEnemies();
	}
	gameOver();
    }

    private void gameOver(){

	if(player != null && player.getHealth() <= 0){

	    this.scene = 2; //game Over Screen
	}
    }


    private void placeTower(int x, int y) {

	int xPos = (x - TOWER_X_COORD_OFFSET) / (int) SQUARE;
	int yPos = (y - TOWER_Y_COORD_OFFSET) / (int) SQUARE;

	if (xPos >= 0 && xPos <= Level.MAP_SIZE_X && yPos <= Level.MAP_SIZE_Y && yPos >= 0) {
	    if (level.getTowerMap()[xPos][yPos] == null && level.getMap()[xPos][yPos] == 0) {


		player.setMoney(player.getMoney() - Tower.TOWER_LIST[hand - 1].getCost());

		//Clone is to prevent one tower from changing other towers of the same kind
		level.getTowerMap()[xPos][yPos] = (Tower) Tower.TOWER_LIST[hand - 1].clone();

		selectedTower = level.getTowerMap()[xPos][yPos];

	    }
	}
    }

    private void selectTower(int x, int y) {

	int xPos = (x - TOWER_X_COORD_OFFSET)/(int) SQUARE;
	int yPos = (y - TOWER_Y_COORD_OFFSET)/(int) SQUARE;

	if (xPos >= 0 && xPos <= Level.MAP_SIZE_X && yPos <= Level.MAP_SIZE_Y && yPos >= 0) {

	    selectedTower = level.getTowerMap()[xPos][yPos];

	}else{
	    selectedTower = null;

	}

    }

    public void spawnEnemy(Enemy enemy) {
	level.addEnemy(enemy);
    }

    public int getScene() {
	return scene;
    }

    public class MouseHeld{

	private static final int MOUSE_X_COORD_OFFSET = 201;
	private static final int MOUSE_Y_COORD_OFFSET = 598;
	private static final int SHOP_X_COORD = 204;
	private static final int SHOP_Y_COORD = 601;
	private static final int SHOP_Y_COORD_END = 636;

	private boolean mouseDown = false;

	public void mouseDown(MouseEvent e) {

	    this.mouseDown = true;

	    if(startedGame){
		if(hand!=0){

		    placeTower(e.getX(), e.getY());

		    hand = 0;

		}else{

		    selectTower(e.getX(),e.getY());
		}

		updateMouse(e);
	    }


	}

	public void mouseMoved(MouseEvent e) {

	    handXpos = e.getX();
	    handYpos = e.getY();
	}

	public void updateMouse(MouseEvent e) {

	    int shopX = (e.getX() - MOUSE_X_COORD_OFFSET) / (int) SQUARE;
	    int shopY = (e.getY() - MOUSE_Y_COORD_OFFSET) / (int) SQUARE;
	    int indexInShop = (shopX + shopY * Tower.NUMBER_OF_TOWERS);

	    if (getScene() == 1 && mouseDown && hand == 0) {
		if (0 <= e.getX() && e.getX() <= frame.getWidth()) {
		    if (0 <= e.getY() && e.getY() <= frame.getHeight()) {
			if (SHOP_X_COORD <= e.getX() && e.getX() <= (SHOP_X_COORD + SQUARE * Tower.NUMBER_OF_TOWERS) && SHOP_Y_COORD <= e.getY() && e.getY() <= SHOP_Y_COORD_END) { //Inside shop
			    if ((indexInShop) < Tower.TOWER_LIST.length && (indexInShop) >= 0) { //Checks if the array is inside the towerlist
				if (player.getMoney() >= Tower.TOWER_LIST[indexInShop].getCost()) { //Checks in the towers exist
				    System.out.println("Bought tower @: " + shopX + ", " + shopY);
				    hand = (indexInShop) + 1;
				    System.out.println(Tower.TOWER_LIST[hand - 1]);
				}
			    }
			}
		    }
		    ////   ********FOR TESTING REASONS********
		    //System.out.println(e.getX() + "," + e.getY());
		    //System.out.println("TileX: " + (e.getX() - 204)/35);//för att hitta första raden i shoppen
		    //System.out.println("TileX: " + (e.getX() - 52)/(int)SQUARE);//för att hitta xcord i griden
		    //System.out.println("TileY: " + (e.getY() - 75)/(int)SQUARE);//för att hitta ycord i griden
		    //System.out.println("ShopX: " + (e.getX() - 201)/(int)SQUARE);//för att hitta Shopens x
		    //System.out.println("ShopY: " + (e.getY() - 598)/(int)SQUARE);//för att hitta Shopens y
		    //System.out.println("index: " + (((e.getX() - 201)/(int)SQUARE) + ((e.getY() - 598)/(int)SQUARE)*20));

		}
	    }
	}
    }



    public class KeyTyped{

	public void keyENTER(){
	    if(startedGame && !level.isWaveInProgress() && level.getEnemies().isEmpty()){
		wave.nextWave();
	    }



	}

	public void keyESC(){

	    running = false;
	}
	public void keySPACE(){


	    if(!startedGame && getScene() != 1){
		startGame("level1");
	    }

	}

    }
}