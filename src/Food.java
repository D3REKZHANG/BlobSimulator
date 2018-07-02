/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 25 minutes
 */

/**
 * Change Log
 *
 * May 22, 2018 - Added to represent the food in levels 2+
 */

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * Class that represents food. It extends GameObject
 * 
 * @author Derek Zhang William Xu
 * @version 1
 */
public class Food extends GameObject{
    
    /** Stores width and height */
    private int w = 80, h = 80;
    /** Stores the food image */
    private BufferedImage image;
    /** Stores whether this food is healthy of not */
    private boolean healthy;
    
    /**
     * Constructor
     * @param x  start pos x of food
     * @param y  start pos y of food
     * @param id ObjectId
     * @param healthy whether or not the food is healthy
     */
    public Food(int x, int y, ObjectId id, boolean healthy){
        super(x,y,id);
        this.healthy = healthy;
        int r = (int)(Math.random()*3+1);
        try{
            if(healthy){
                image = ImageIO.read(new File("images/H" + r + ".png"));
            }else{
                image = ImageIO.read(new File("images/UH" + r + ".png"));
            }
        }catch(IOException e){
            System.err.println("Could not find food images!");
        }
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
    
    /**
     * Getter for the healthy boolean
     * @return whether the food is healthy or not
     */ 
    public boolean isHealthy(){
        return healthy;
    }
}