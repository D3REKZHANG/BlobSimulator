/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 30 minutes
 */

/**
 * Change Log
 *
 * May 15, 2018 - Added to represent the portal to finish the level
 * June 1, 2018 - Revamped the sprite
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Class representing the waypoint at the end of every level
 * @author Derek Zhang William Xu
 * @version 2
 */
public class Waypoint extends GameObject{

	/** Stores the portal image */
	private BufferedImage image;
	/** Stores the width and height */
	private int w,h; 

	/**
	 *	Constructor to setup and load images
	 * 
	 * @param x x position
	 * @param y y position
	 * @param id ObjectId enum
	 */
	public Waypoint(int x, int y, ObjectId id){
		super(x,y,id);
		try{
            image = ImageIO.read(new File("images/portal.png"));
        }catch(IOException e){
            System.err.println("Could not find portal image!");
        }
        w = image.getWidth();
        h = image.getHeight();
	}

	/**
	 * Basic update method for processing before drawing
	 * @param objects Object list of Handler
	 */
	public void update(ArrayList<GameObject> objects){

	}
	/**
	 * Basic draw method for rendering graphics
	 * @param g Graphics reference
	 */
	public void draw(Graphics g){
		g.drawImage(image,x,y,null);
	}

	/**
	 * Used to get hit box for collision
	 * @return Rectangle instance
	 */
	public Rectangle getBounds(){
		return new Rectangle(x,y,w,h);
	}
}