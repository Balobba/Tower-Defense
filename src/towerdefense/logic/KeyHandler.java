package towerdefense.logic;

import towerdefense.graphics.Screen;
import towerdefense.graphics.Screen.KeyTyped;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * This class has all the keyboard methods.
 */
public class KeyHandler extends KeyAdapter
{

    /**
     *  The int that represents the "ENTER"-button
     */
    public static final int ENTER_KEY_CODE = 10;
    private static final int ESC_KEY_CODE = 27;
    private static final int SPACE_KEY_CODE = 32;

    private final KeyTyped keyTyped;


    public KeyHandler(Screen screen){
        this.keyTyped = screen.new KeyTyped();
    }


    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == ESC_KEY_CODE){
            this.keyTyped.keyESC();
        }
        if(keyCode == SPACE_KEY_CODE){
            this.keyTyped.keySPACE();
        }

        if(keyCode == ENTER_KEY_CODE){
            this.keyTyped.keyENTER();

        }
    }

}

