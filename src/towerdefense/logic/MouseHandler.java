package towerdefense.logic;


import towerdefense.graphics.Screen;
import towerdefense.graphics.Screen.MouseHeld;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * This class has all the mouse methods.
 */
public class MouseHandler extends MouseAdapter
{

    private final MouseHeld mouseHeld;

    public MouseHandler(Screen screen){


        this.mouseHeld = screen.new MouseHeld();

    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.mouseHeld.mouseDown(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        this.mouseHeld.mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        this.mouseHeld.mouseMoved(e);
    }
}
