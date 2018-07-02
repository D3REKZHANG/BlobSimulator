/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 15 minutes
 */

/**
 * Change Log
 *
 * May 4, 2018 - Created to represent all object in the game
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * GameObject abstract class for all objects/entities in the game
 * 
 * @author Derek Zhang William Xu
 * @version 1
 */
public abstract class GameObject{

    /**Stores x and y positions */
    protected int x, y;
    /** ObjectId enum */
    protected ObjectId id;
    /** Velocity X and Y */
    protected double velX = 0, velY = 0;
    /** Whether or not object is falling */
    protected boolean falling = true;
    /** Whether or not object is jumping */
    protected boolean jumping = false;

    /**
    * Contructor
    * @param  x  Starting x pos
    * @param  y  Starting y pos
    * @param  id ObjectId of object
    */
    public GameObject(int x, int y, ObjectId id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
    * Basic update method for processing before drawing
    * @param objects Object list of Handler
    */
    public abstract void update(ArrayList<GameObject> objects);
    /**
    * Basic draw method for rendering graphics
    * @param g Graphics reference
    */
    public abstract void draw(Graphics g);
    /**
    * Used to get the bounding box
    * @return Rectangle instance with bounding box of this object
    */
    public abstract Rectangle getBounds();

    /**
    * Getter for X
    * @return x position
    */
    public int getX(){
        return this.x;
    }

    /**
    * Getter for y
    * @return y position
    */
    public int getY(){
        return this.y;
    }

    /**
    * Setter for x
    * @param x new value of x
    */
    public void setX(int x){
        this.x = x;
    }

    /**
    * Setter for x
    * @param y new value of y
    */
    public void setY(int y){
        this.y = y;
    }

    /**
    * Getter for velX
    * @return x velocity
    */
    public double getVelX(){
        return this.velX;
    }

    /**
    * Getter for velY
    * @return y velocity
    */
    public double getVelY(){
        return this.velY;
    }

    /**
    * Setter for velX
    * @param velX new value for velX
    */
    public void setVelX(double velX){
        this.velX = velX;
    }

    /**
    * Setter for velY
    * @param velY new value for velY
    */
    public void setVelY(double velY){
        this.velY = velY;
    }

    /**
    * Getter for falling
    * @return if falling return true, otherwise false
    */
    public boolean isFalling(){
        return this.falling;
    }

    /**
    * Setter for falling
    * @param b new value for falling
    */
    public void setFalling(boolean b){
        this.falling = b;
    }

    /**
    * Getter for jumping
    * @return if jumping return true, otherwise false
    */
    public boolean isJumping(){
        return this.jumping;
    }

    /**
    * Setter for jumping
    * @param b new value for jumping
    */
    public void setJumping(boolean b){
        this.jumping = b;
    }

    /**
    * Getter for ObjectId of this GameObject
    * @return id the ObjectId of the GameObject
    */
    public ObjectId getId(){
        return this.id;
    }

}