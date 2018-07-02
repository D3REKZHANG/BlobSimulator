/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: May 28, 2018
 * Time Spent: 4 hour
 */

/**
    Change Log
    May 13, 2018 - Basic Player framework completed
    May 14, 2018 - Added basic movement correlating with the KeyInput class
    May 18, 2018 - Added collision
    May 19, 2018 - Added shrinking/growing mechanic
    May 22, 2018 - Greatly improved shrinking/growing mechanics 
    May 26, 2018 - Added Food collision mechanics
    May 29, 2018 - Added sleeping mechanics
    May 30, 2018 - Added realistic movement (velocity and acceleration)
    June 2, 2018 - Added WaterBottle compatibility
    June 5, 2018 - Polishing and Extra Commenting 
 */

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

/**
 * Class that represents the Player. It extends GameObject
 * 
 * @author Derek Zhang and William Xu
 * @version 5
 */
public class Player extends GameObject{
    
    /** Stores width and height of player */
    public int w, h;
    /** Stores the last direction the player was facing */
    private String lastDir = "L"; 
    /** Stores the gravity constant */
    private double gravity = 0.5;
    /** Stores whether or not the player is sleeping */
    private boolean sleeping = false;
    /** Stores player's energy levels */
    private double energy = 100;
    /** Stores total sleep time */
    private double sleeptime = 0;
    /** Stores the number of bottles consumed */
    private int bottleCount = 0;
    /** Stores the currents speed of the player */
    public int speed = 1;
    /** Stores the maximum speed constant */
    private final double MAX_VEL = 10;
    /** Stores the left and right image of the blob */
    private BufferedImage imageL,imageR;
    
    /** Stores whether or not hacks have been turned on */
    public boolean boosted = false;

    /** Handler reference */
    Handler handler;
    /** Game reference */
    Game game;

    /**
     * Constructor
     * @param  x  start pos x of player
     * @param  y  start pos y of player
     * @param  handler Handler reference
     * @param  game Game reference
     * @param  id ObjectId
     */
    public Player(int x, int y, Handler handler, Game game, ObjectId id){
        super(x,y,id);
        this.handler = handler;
        this.game = game;
        try{
            imageL = ImageIO.read(new File("images/blobL.png"));
            imageR = ImageIO.read(new File("images/blobR.png"));
        }catch(IOException e){
            System.err.println("Could not find Blob images!");
        }
        w = imageL.getWidth();
        h = imageL.getHeight();
    }

    /**
     * Update method
     * @param objects Object list passed from handler
     */
    public void update(ArrayList<GameObject> objects){
        if(!sleeping){
            x += velX;
            w = imageL.getWidth();
            h = imageL.getHeight();

            if(velX != 0){
                energy = Math.max(energy-0.05,0);
            }
        }else{
            energy = Math.min(energy+0.1,100);
            if(energy <  100) sleeptime = Math.min(sleeptime+0.1,200);
        }

        y += velY;
        
        falling = true;
        
        if(falling || jumping){
            velY += gravity;
            
            if(velY > MAX_VEL){
                velY = MAX_VEL;
            }
        }
        
        // Falling too low
        if(y > 1450){
            this.reset();
            handler.setLevel(Game.level);
        }

        // Last facing direction
        if(velX < 0) lastDir = "L";
        else if(velX > 0) lastDir = "R";    

        // Speed
        if(h >= 115 || (energy <= 0 && Game.level == 3)) speed = 1;
        else if(h >= 105) speed = 2;
        else if(h >= 90) speed = 3;
        else if(h >= 80) speed = 4;
        else if(h >= 70) speed = 5;
        else if(h >= 60) speed = 6;
        else if(h >= 50) speed = 7;

        // Tutorial modifications
        if(Game.level == 4){
            speed = 3;
            // Resize blob to be a bit smaller for the tutorial
            try{
                imageL = ImageIO.read(new File("images/blobL75.png"));
                imageR = ImageIO.read(new File("images/blobR75.png"));
            }catch(IOException e){} 
        }

        checkCollision();
    }

    /**
     * Method to shrink/grow blob
     */
    public void tick(){
        if(!sleeping){
            // Scale image
            if((velX != 0) && h > 50){
                w = (int)(Math.round((double)w*0.99));
                h = (int)(Math.round((double)h*0.99));
            }else if((velX == 0 && velY >= 0) && h <= 135){
                w = (int)(Math.round((double)w*1.01));
                h = (int)(Math.round((double)h*1.01));
            }
            String height = "xd";
            if(h == 120){
                height = "120";
            }if(h == 105){
                height = "105";
            }if(h == 90){
                height = "90";
            }if(h == 75){
                height = "75";
            }if(h == 60){
                height = "60";
            }if(h == 50){
                height = "50";
            }
            // Set images
            try{
                imageL = ImageIO.read(new File("images/blobL" + height + ".png"));
                imageR = ImageIO.read(new File("images/blobR" + height + ".png"));
            }catch(IOException e){
                imageL = Game.scale(imageL,w,h);
                imageR = Game.scale(imageR,w,h);
            } 
        }        
    }
    
    /**
     * Resets the player size and position for a new level
     */
    public void reset(){
        y = 400;
        x = 100;
        energy = 100;
        sleeping = false;
        sleeptime = 0;
        bottleCount = 0;
        try{
            imageL = ImageIO.read(new File("images/blobL.png"));
            imageR = ImageIO.read(new File("images/blobR.png"));
        }catch(IOException e){
            System.err.println("Could not find Blob images!");
        }
    }

    /**
     * Checks collision with any of the platform objects
     */
    public void checkCollision(){
        for(GameObject obj : handler.objects){
            if(obj.getId().equals(ObjectId.Platform)){
                if(getBoundsB().intersects(obj.getBounds())){
                    velY = 0;
                    falling = false;
                    jumping = false;
                    this.y = obj.getY() - this.h/2;
                }
                if(getBoundsT().intersects(obj.getBounds())){
                    velY = 0;
                    falling = true;
                    this.y = obj.getY() + 40 + this.h/2;
                }
                if(getBoundsL().intersects(obj.getBounds())){
                    velX = 0;
                    this.x = obj.getX() + 40 + this.w/2;
                }
                if(getBoundsR().intersects(obj.getBounds())){
                    velX = 0;
                    this.x = obj.getX() - this.w/2;
                }
            }else if(obj.getId().equals(ObjectId.Waypoint)){
                if(getBounds().intersects(obj.getBounds())){
                    Game.gameState = ((Game.level == 4)?"menu":"quiz");
                    if(Game.level != 4) game.quiz.loadQuestions();
                    this.reset();
                    break;
                }
            }else if(obj.getId().equals(ObjectId.Food)){
                if(getBounds().intersects(obj.getBounds())){
                    handler.remove(obj);
                    if(!(((Food)obj).isHealthy())){
                        w = (int)(Math.round((double)w*1.2));
                        h = (int)(Math.round((double)h*1.2));
                        imageL = Game.scale(imageL,w,h);
                        imageR = Game.scale(imageR,w,h);
                        Game.score -= 2500;
                    }
                    Game.score += 5000;
                    break;
                }
            }else if(obj.getId().equals(ObjectId.Water)){
                if(getBounds().intersects(obj.getBounds())){
                    handler.remove(obj);
                    bottleCount++;
                    Game.score += 5000;
                    break;
                }
            }
        }
    }

    /**
     * Draws all player graphics
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        if (lastDir.equals("L"))
            g.drawImage(imageL,x-w/2,y-h/2,null);
        else 
            g.drawImage(imageR,x-w/2,y-h/2,null);
    }
    
    /**
     * Accessor for energy
     * @return energy
     */
    public double getEnergy(){
        return this.energy;
    }

    /**
     * Accessor for sleeptime
     * @return sleeptime
     */
    public double getSleep(){
        return this.sleeptime;
    }
    
    /**
     * Accessor for sleeping
     * @return sleeping
     */ 
    public boolean isSleeping(){
        return this.sleeping;
    }

    /**
     * Toggles sleeping variable
     */
    public void toggleSleep(){
        sleeping = !sleeping;
    }

    /**
     * Accessor for bottleCount
     * @return bottleCount
     */
    public int getBottleCount(){
        return this.bottleCount;
    }

    /**
     * Methods to generate the hitbox for the player
     * @return Rectangle instance with x,y,w,h
     */
    public Rectangle getBounds(){
     return new Rectangle(x-w/2,y-h/2,w,h);
    }
    public Rectangle getBoundsL(){
        if(speed <= 3){
            return new Rectangle(x-w/2,y-h/2+h/20,w/20,h-h/10); 
        }
        return new Rectangle(x-w/2,y-h/2+h/20,w/5,h-h/10);
    }
    public Rectangle getBoundsR(){
        if(speed <= 3){
            return new Rectangle(x+w/2-w/20,y-h/2+h/20,w/20,h-h/10); 
        }
        return new Rectangle(x+w/2-w/5,y-h/2+h/20,w/5,h-h/10);
    }
    public Rectangle getBoundsT(){
        if(speed <= 3){
            return new Rectangle(x-w/2+w/20,y-h/2,w-(w/2+w/20)/2+w/6,h/5);
        }
        return new Rectangle(x-w/2+w/5+w/40,y-h/2,w-w/2+w/20,h/5);
    }
    public Rectangle getBoundsB(){
        if(speed <= 3){
            return new Rectangle(x-w/2+w/20,y-h/2+(h-h/5),w-(w/2+w/20)/2+w/6,h/5);
        }
        return new Rectangle(x-w/2+w/5+w/40,y-h/2+(h-h/5),w-w/2+w/20,h/5);
    }
}