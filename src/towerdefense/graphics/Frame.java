package towerdefense.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * This class has all the information of the window/Frame that opens. The size has fixed dimensions and is not resizable.
 */
public class Frame extends JFrame {


    public Frame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("The game of Tower Defence");
        final int width = 1000;
        final int height = 700;
        setPreferredSize( new Dimension(width, height));
        //setExtendedState(MAXIMIZED_BOTH); //fullscreen
        //setUndecorated(true); //for fullscreen
        setResizable(false);
        //setLayout(new GridLayout(1, 1, 0, 0));

        Screen gameScreen = new Screen(this);

        add(gameScreen); //adds board to the Frame
        pack();

        setLocationRelativeTo(null);
        setVisible(true);

    }

}

