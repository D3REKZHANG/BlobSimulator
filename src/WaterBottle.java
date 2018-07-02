/**
 * Name: Derek 
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 25 minutes
 */

/**
 * Change Log
 *
 * May 26, 2018 - Added to represent the water bottles throughout the levels
 */

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * Class that represents water bottles. It extends GameObject
 * 
 * @author Derek Zhang
 * @version 1
 */
public class WaterBottle extends GameObject{
    
    /** Stores width and height*/
    private int w,h;
    /** Waterbottle image */
    private BufferedImage image;    
    /**
     * Constructor
     * @param  x  start pos x of water
     * @param  y  start pos y of water
     * @param  id ObjectId
     */
    public WaterBottle(int x, int y, ObjectId id){
        super(x,y,id);
        try{
            image = ImageIO.read(new File("images/bottle.png"));
        }catch(IOException e){
            System.err.println("Could not find water image!");
        }
        w = image.getWidth();
        h = image.getHeight();
    }
    
    /**
     * Update method
     * @param objects Object list passed from handler
     */
    public void update(ArrayList<GameObject> objects){
       
    }

    /**
     * Draws the food
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        g.drawImage(image,x,y,null);
    }
    
    /**
     * Method to generate the hitbox for the player
     * @return Rectangle instance with x,y,w,h
     */
    public Rectangle getBounds(){
     return new Rectangle(x,y,w,h);
    }
}