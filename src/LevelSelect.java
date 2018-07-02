/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 15 minutes
 */

/**
 * Change Log
 *
 * May 21, 2018 - Created to represent the level select page
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
/**
 * LevelSelect Class
 * 
 * @author Derek Zhang
 * @version 1
 */
public class LevelSelect{

    private BufferedImage image,i1,i2,i3,i4;
    private boolean h1 = false,h2 = false,h3 = false,h4 = false;
    private Game game;

    /**
     * Constructor to load images and assign Game reference
     * @param  game Game reference
     */
    public LevelSelect(Game game){
        try{
            image = ImageIO.read(new File("images/levelSelect.png"));
            i1 = ImageIO.read(new File("images/levelSelectH1.png"));
            i2 = ImageIO.read(new File("images/levelSelectH2.png"));
            i3 = ImageIO.read(new File("images/levelSelectH3.png"));
            i4 = ImageIO.read(new File("images/levelSelectH4.png"));
        }catch(IOException e){
            System.err.println("Could not find levelSelect image!");
        }
        this.game = game;
    }

    /**
     * Update that is called each frame
     */
    public void update(){
        int mx = MouseInput.getX(game);
        int my = MouseInput.getY(game);
        if(mx > 50 && mx < 505 && my > 182 && my < 234){
            h1 = true;
        }else if(mx > 50 && mx < 335 && my > 269 && my < 332){
            h2 = true;
        }else if(mx > 50 && mx < 356 && my > 358 && my < 416){
            h3 = true;
        }else if(mx > 600 && mx < 760 && my > 530 && my < 560){
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

