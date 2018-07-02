/**
 * Name: Unsight Labs 
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 3 hours
 */

/**
 * Change Log
 *
 * May 22, 2018 - Created to represent the Highscores page
 * May 28, 2018 - Added all highscore functionalities
 * May 29, 2018 - Improved graphics
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
 * @author Derek Zhang William Xu
 * @version 1
 */
public class Highscore{
    /** different background images for each highlighted option */
    private BufferedImage image,i2,i3;
    /** whether each option is highlighted or not */
    private boolean h2 = false, h3 = false;
    /** Game reference */
    private Game game;
    
    /**
     * Constructor to load images and assign Game reference
     * @param  game Game reference
     */
    public Highscore(Game game){
        try{
            image = ImageIO.read(new File("images/highscores.png"));
            i2 = ImageIO.read(new File("images/highscoresH2.png"));
            i3 = ImageIO.read(new File("images/highscoresH3.png"));
        }catch(IOException e){
            System.err.println("Could not find highscores image!");
        }
        this.game = game;
    }

    /**
     * Update that is called each frame
     */
    public void update(){
        int mx = MouseInput.getX(game);
        int my = MouseInput.getY(game);
        if(mx >= 140 && mx <= 350 && my >= 555 && my <= 580){
            h2 = true;
        }else if(mx >= 522 && mx <= 595 && my >= 556 && my <= 578){
            h3 = true;
        }else{
            h2 = false;
            h3 = false;
        }
    }
    
    public static void reset () {
      
      try {
        
        for (int i = 0; i < 3; i++)
        {
          PrintWriter writer = new PrintWriter (new FileWriter ("highscores/level_" + (i+1) + ".txt"));
          
          for (int x = 0; x < 9; x++)
          {
            writer.println("-1 NA");
          }
          writer.print("-1 NA");

          writer.close ();
        }
      }
      catch (Exception e)
      {
        
      }
    }
    
    /**
     * Draws all graphics per frame
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        if(h2) g.drawImage(i2,0,0,null);
        else if(h3) g.drawImage(i3,0,0,null);
        else g.drawImage(image,0,0,null);
        
        g.setColor (Color.white);
        g.setFont(new Font("Lato",Font.PLAIN, 15));
        
        for (int i = 0; i < 3; i++)
        {
          g.drawString ("Level " + (i + 1), i * 200 + 145, 125);
          for (int x = 0; x < 10; x++)
          {
            if (game.highscores [i].get (x) == -1)
            {
              g.drawString ("  -", i * 200 + 160, x * 30 + 175);
            }
            else
            {
              g.drawString (Integer.toString (game.highscores [i].get (x)),i * 199 + 110, x * 30 + 175);
              g.drawString ("-   " + game.usernames [i].get (x), i * 199 + 170, x * 30 + 175);
            }
          }
        }
    }
}