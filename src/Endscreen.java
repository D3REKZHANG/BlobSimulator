/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 15 minutes
 */

/**
 * Change Log
 *
 * May 28, 2018 - Added to represent the endscreen
 * May 29, 2018 - Improved graphics, better key input
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
public class Endscreen extends KeyAdapter{
    /** Stores background image for endscreen */
    private BufferedImage image;
    /** Game reference */
    private Game game;
    /** Stores the name being typed */
    private ArrayList<Character> nickname = new ArrayList<Character>();

    /**
     * Constructor to load images and assign Game reference
     * @param  game Game reference
     */
    public Endscreen(Game game){
        try{
            image = ImageIO.read(new File("images/endscreen.png"));
        }catch(IOException e){
            System.err.println("Could not find titlescreen image!");
        }
        this.game = game;
    }

    /**
     * Update that is called each frame
     */
    public void update(){
        
    }

    /**
     * Draws all graphics per frame
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        g.drawImage(image, 0, 0, null);
        g.setFont(new Font("Lucida Sans Typewriter", Font.ITALIC, 50));
        g.setColor(new Color(160,255,0));
        g.drawString(""+game.score,175,205);
        for(int x=0;x<nickname.size();x++){
            g.drawString(Character.toString(nickname.get(x)),195+x*26,370);
        }
    }
    
    public void insert ()
    {      
        String name = "";
        for(Character c : nickname){
            name += c;
        }
        
        int insertPosition = 0;
        
        for (Integer x : game.highscores [game.level - 1])
        {
            if (x < game.score)
                break;
            
            insertPosition++;
        }
        
        game.highscores [game.level - 1].add (insertPosition, game.score);
        game.highscores [game.level - 1].remove (game.highscores [game.level - 1].size () - 1);
        game.usernames [game.level - 1].add (insertPosition, name);
        game.usernames [game.level - 1].remove (game.usernames [game.level - 1].size () - 1);
 
        try{
            PrintWriter writer = new PrintWriter(new FileWriter("..\\highscores\\level_" + game.level + ".txt"));
            for (int x=0;x<game.highscores[game.level-1].size()-1;x++) {
                writer.println(game.highscores[game.level-1].get(x) + " " + game.usernames[game.level-1].get(x));
            }
            writer.print(game.highscores[game.level-1].get(game.highscores[game.level-1].size()-1) + " " + game.usernames[game.level-1].get(game.highscores[game.level-1].size()-1));
            writer.close();
        }catch(IOException e){
            System.err.println("Insert failed to write!");
        }
    }

    public void keyPressed(KeyEvent e){
        if(Game.gameState == "endscreen"){
            char ch = e.getKeyChar();
            if(ch == '\n'){
                insert();
                nickname.clear();
                Game.gameState = "menu";
            }else if(ch == '\b' && nickname.size() > 0){
                nickname.remove(nickname.size()-1);
            }else if(nickname.size()<=10 
                && e.getKeyCode() != KeyEvent.VK_SHIFT 
                && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
                && e.getKeyCode() != KeyEvent.VK_LEFT
                && e.getKeyCode() != KeyEvent.VK_RIGHT){
                nickname.add(ch);
            }
        }
    }
}