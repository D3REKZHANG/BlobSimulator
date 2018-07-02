/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 15 minutes
 */

/**
 * Change Log
 *
 * May 25, 2018 - Added loading screen for artistic flare
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * GamePanel class to contain all graphics
 * 
 * @author Derek Zhang
 * @version 1
 */
public class LoadingScreen extends KeyAdapter{

    /** background image */
    private BufferedImage image;
    /** Game reference */
    private Game game;

    /** Loading bar with */
    private int barWidth = 0;
    /** Fade alpha value */
    private int fadeAlpha = 0;
    /** Stores whether or not to fade */
    private boolean fade = false;

    /**
     * Constructor to load images and assign Game reference
     * @param  game Game reference
     */
    public LoadingScreen(Game game){
        try{
            image = ImageIO.read(new File("images/loadingScreen.png"));
        }catch(IOException e){
            System.err.println("Could not find image!");
        }
        this.game = game;
    }

    /**
     * Update that is called each frame
     */
    public void update(){
        if(barWidth >= 315){
            fade = true;   
        }else{
            barWidth++;
        }
        if(fade){
            fadeAlpha+=3;
        }
        if(fadeAlpha == 255){
            Game.gameState = "menu";  
            game.fade();
        }
    }

    /**
     * Draws all graphics per frame
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        g.drawImage(image,0,0,null);
        g.setColor(Color.white);
        // left side
        g.drawLine(340,550,340-barWidth,550);
        // right side
        g.drawLine(460,550,460+barWidth,550);

        // Fade out
        if(fade){
            g.setColor(new Color(0,0,0,fadeAlpha));
            g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
        }
        
        g.dispose();

        
    }
}