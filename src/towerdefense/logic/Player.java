package towerdefense.logic;

/**
 * This player class has variables of the starting health and money that the player got.
 *
 */
public class Player {

    static final int STARTING_HEALTH = 100;
    static final int STARTING_MONEY = 300;

    private int health;
    private int money;

    public Player(){

        this.money = STARTING_MONEY;
        this.health = STARTING_HEALTH;

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
