/**
 * Name: Unsight Labs 
 * Teacher: Ms. Krasteva
 * Date: May 20, 2018
 * Time Spent: 45 minutes
 */

/**
 * Change Log
 * May 20, 2018 - Created to hold the menu
 * May 21, 2018 - Added highlighted options
 * June 1, 2018 - Small Changes to structure
 */ 

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Menu Class
 * 
 * @author Derek Zhang William Xu
 * @version 4
 */
public class Menu{

    /** Background images for each option highlight */
    private BufferedImage image,i1,i2,i3,i4;
    /** Stores which option is being hovered*/
    private boolean h1 = false,h2 = false,h3 = false,h4 = false;
    /** Game reference */
    private Game game;

    /**
     * Constructor to load images and assign Game reference
     * @param  game Game reference
     */
    public Menu(Game game){
        try{
            image = ImageIO.read(new File("images/titlescreen.png"));
            i1 = ImageIO.read(new File("images/titlescreenH1.png"));
            i2 = ImageIO.read(new File("images/titlescreenH2.png"));
            i3 = ImageIO.read(new File("images/titlescreenH3.png"));
            i4 = ImageIO.read(new File("images/titlescreenH4.png"));
        }catch(IOException e){
            System.err.println("Could not find titlescreen image!");
        }
        this.game = game;
    }

    /**
     * Update that is called each frame
     */
    public void update(){
        int mx = MouseInput.getX(game);
        int my = MouseInput.getY(game);
        if(mx > 440 && mx <605 && my > 375 && my < 420){
            h1 = true;
        }else if(mx > 440 && mx <627 && my > 430 && my < 467){
            h2 = true;
        }else if(mx > 440 && mx <505 && my > 482 && my < 517){
            h3 = true;
        }else if(mx >= 670 && mx <= 775 && my >= 500 && my <= 583){
            h4 = true;
        }else{
            h1 = false;
            h2 = false;
            h3 = false;
            h4 = false;
        }
    }

    /**
     * Draws all graphics per frame
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        g.drawImage(image,0,0,null);
        if(h1) g.drawImage(i1,0,0,null);
        else if(h2) g.drawImage(i2,0,0,null);
        else if(h3) g.drawImage(i3,0,0,null);
        else if(h4) g.drawImage(i4,0,0,null);
    }
}

