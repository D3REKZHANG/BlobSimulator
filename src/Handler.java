/**
* Name: Unsight Labs
* Teacher: Ms. Krasteva
* Date: June 7, 2018
* Time Spent: 1.5 hour
*/

/*
    Change Log
    May 10, 2018 - Created to accommodate Game driver class
    May 11, 2018 - Added more functionality
    May 15, 2018 - Added image loading algorithm
    May 20, 2018 - Optimized drawing
    May 29, 2018 - Added text and text loading algorithm
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
* Handler class for managing the game
* 
* @author Derek Zhang and William Xu
* @version 4
*/
public class Handler{
    
    /** Stores all the objects being updated and drawn on the screen */
    public ArrayList<GameObject> objects = new ArrayList<GameObject>();
    /** Camera reference */
    private Camera cam;
    /** Images of each level */
    private BufferedImage level1,level2,level3,tutlvl;
    
    /** 3D array to store all the text for all three levels */
    private String[][][] text = 
    {    
        {
            {"Slow and almost immovable eh? That's just", "like how hard it is to start being active again", "Start moving around to loosen up!"},
            {"As you keep moving, you will become","more fit, smaller, and faster",""},
            {"In order to progress through the level", "you'll have to stay in shape",""},
            {"Collect water to stay hydrated. There","are 8 water bottles throughout the level.",""},
            {"Being inactive will gradually increase", "your size and ruin your progress",""},
            {"Children and adolescents aged 6 to 17 ", "should be getting at least 60 minutes ", "of physical activity EACH day."},
            {"Only 20% of adolescents follow", "this guideline. Do you?", ""},
            {"Physical inactivity can lead to many health ", "problems, including higher chance of", "obesity and heart disease"},
            {"Students who are physically active tend to ","have better grades, school attendance, ", "cognitive performance"}
        },
        {
            {"Collect food to boost your score", "", ""},
            {"Healthy foods will give more points","than unhealthy foods",""},
            {"Unhealthy foods will also ruin your", "healthy progress by increasing your blob size",""},
            {"Eating a proper nutritious diet offers", "numerous health benefits that keep you", "mentally and physically well."},
            {"45-65% of your daily calories should be", "from carbohydrates, between 10-35% from", "protein and 20-35% from fats."},
            {"Exercise releases endorphins, your happy hormone", "and helps reduce the risk of depression", "and other mental illnesses"},
            {"Shedding excess weight reduces your risk", "of obesity-related conditions such as", "type-2 diabetes and thyroid dysfunction."}
        },
        {
            {"Moving around now takes energy!", "The white bar at the top left of", "your screen is your energy bar"},
            {"To restore energy, press 's' to","sleep. This will prevent you from moving", "but will fill your energy bar"},
            {"Sleeping fills your sleep bank (blue bar)","Fill this by the end of the level to","demonstrate adequate sleep"},
            {"The Sleep Foundation recommends ","8-10 hours for teens.","Are you getting enough?"},
            {"Not sleeping enough can limit your", "ability to learn, listen, concentrate", "and solve problems."},
            {"Only 15% of adolescents are sleeping more", "than 8.5 hours a night. That means 85% ","of teens aren?t getting enough. Where do you fall?"}
        },
        {
            {"Use the Arrow Keys to move.", "", "Press space to jump"},
            {"Welcome to Blob Simulator!", "You will be playing as Blob and learning", "all about healthy living"},
            {"Remember to stop to read the important", "text in the background. They will", "teach you about healthy living"},
            {"Each level will introduce a new game mechanic","Make sure to read the embedded text", "for descriptions."},
            {"At the end of every level there will be ","a quiz to test your learning. Answering ","correct will boost your points"},
            {"This blue ring is the portal to finish", "the level. The faster you are to finish, ","the higher your points"}
        }
    }; 


    /**
     * Constructor
     * @param  cam Camera reference
     */
    public Handler(Camera cam){
        this.cam = cam;

        try{
            level1 = ImageIO.read(new File("images/LLevel 1.png"));
            level2 = ImageIO.read(new File("images/LLevel 2.png"));
            level3 = ImageIO.read(new File("images/LLevel 3.png"));
            tutlvl = ImageIO.read(new File("images/TutorialLevel.png"));
        }catch(IOException e){
            System.err.println("Could not find level images!");
        }
    }

    /**
    * Loops through objects and calls their update methods, so each object doesn't have 
    * to be updated manually.
    */
    public void update(){
        for(int x=0;x<objects.size();x++){
            objects.get(x).update(objects);
        }
    }

    /**
    * Loops through objects and calls their draw methods.
    * @param g [description]
    */
    public void draw(Graphics g){
        int n = 0;
        for(int x=0;x<objects.size();x++){
            GameObject obj = objects.get(x);
            // Optimization: Draw only on screen
            if(obj.getId().equals(ObjectId.Platform)){
                if((Math.abs(Game.player.getX()-obj.getX())>=440 || Math.abs(Game.player.getY()-obj.getY())>=540) && obj.getX() >= 800){
                    continue;
                }
            }else if(obj.getId().equals(ObjectId.Text)){
                if(Math.abs(Game.player.getX()-obj.getX())>=700 || Math.abs(Game.player.getY()-obj.getY())>=540){
                    continue;
                }
            }
            obj.draw(g);
            n++;
        }
    }

    /**
    * Add GameObject to objects ArrayList
    * @param obj GameObject to be added
    */
    public void add(GameObject obj){
        this.objects.add(obj);
    }

    /**
    * Remove GameObject from objects ArrayList
    * @param obj GameObject to remove
    */
    public void remove(GameObject obj){
        this.objects.remove(obj);
    }

    /**
     * Loads level by image
     * @param image BufferedImage
     */
    public void loadImageLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        int txtcount = 0;

        for(int x=0;x<w;x++){
            for(int y=0;y<h;y++){
                int pixel = image.getRGB(x,y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 0 && green == 0 && blue == 0){
                    this.add(new Platform(x*40,y*40,40,40,ObjectId.Platform));
                }else if(red == 0 && green == 255 && blue == 0){
                    this.add(new Waypoint(x*40,y*40,ObjectId.Waypoint));
                }else if(red == 255 && green == 0 && blue == 0){
                    this.add(new Food(x*40,y*40,ObjectId.Food,true));
                }else if(red == 0 && green == 0 && blue == 255){
                    this.add(new Food(x*40,y*40,ObjectId.Food,false));
                }else if(red == 0 && green == 200 && blue == 200){
                    this.add(new WaterBottle(x*40,y*40,ObjectId.Water));
                }else if(red == 0 && green == 255 && blue == 255){
                    this.add(new Text(x*40,y*40, text[Game.level-1][txtcount][0],text[Game.level-1][txtcount][1],text[Game.level-1][txtcount][2],ObjectId.Text));
                    txtcount++;
                }
            }
        }
        Game.score = 60000;
    }

    /**
     * Sets level to specified level
     * @param level level number
     */
    public void setLevel(int level){
        this.clearLevel();
        Game.level = level;
        switch(level){
            case 1:
              this.loadImageLevel(level1);
              break;
            case 2:
              this.loadImageLevel(level2);
              break;
            case 3:
              this.loadImageLevel(level3);
              break;
            case 4:
              this.loadImageLevel(tutlvl);
              break;
        }
        this.add(Game.player);
        Game.player.setX(100);
        
    }

    /**
     * Clears level (object list)
     */
    private void clearLevel(){
        this.objects.clear();
    }

}