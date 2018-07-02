/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 30 min
 */

/*
    Change Log
    May 11, 2018 - Created for handling key input
    May 15, 2018 - Added better movement
    May 26, 2018 - Added new way to process keys
 */

import java.awt.event.*;
import java.util.*;

/**
 * KeyInput class to get keyboard input
 *
 * @author Derek Zhang
 * @version 3
 */
public class KeyInput extends KeyAdapter{
    
    /**Handler reference*/
    Handler handler;
    
    /**Left right pressed boolean to improve movement*/
    private static boolean leftPressed = false, rightPressed = false;

    /** Set of all keys being pressed down */
    private static Set<Integer> keysDown = new HashSet<Integer>();

    /**
     * Constructor
     * @param  handler Handler instance
     */
    public KeyInput(Handler handler){
        this.handler = handler;
    }
    
    /**
     * Method to handle events when a key is pressed
     * @param e KeyEvent reference
     */
    public void keyPressed(KeyEvent e){
        keysDown.add(e.getKeyCode());
    }

    /**
     * Processes all the keys that are currently being held down
     */
    public static void process(){
        for(int key : keysDown){
            if(Game.gameState.equals("game")){
                Player p = Game.player;
                if(key == KeyEvent.VK_LEFT) {
                    if(p.speed == 0) p.speed++;
                    p.setVelX(-p.speed);
                    leftPressed = true;
                }else if(key == KeyEvent.VK_RIGHT){
                    if(p.speed == 0) p.speed++;
                    p.setVelX(p.speed);
                    rightPressed = true;
                }else if((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) && (!p.isJumping() || p.boosted) && !p.isSleeping()) {
                    p.setVelY(Math.max(-(p.speed+5),-15));
                    p.setJumping(true);
                }
                if(key == KeyEvent.VK_ESCAPE){
                    System.exit(1);
                }
                if(key == KeyEvent.VK_L){
                    p.speed = 20;
                    p.boosted = true;
                }
            }
        }
    }
    
    /**
     * Method to handle events when a key is released
     * @param e KeyEvent reference
     */
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        Player p = Game.player;
        if(key == KeyEvent.VK_S && Game.level == 3 && Game.gameState.equals("game")){
            p.toggleSleep();
        }

        //Camera Debugging
        if(key == KeyEvent.VK_LEFT) {
            if(!rightPressed) p.setVelX(0);
            leftPressed = false;
            keysDown.remove(KeyEvent.VK_LEFT);
        }else if(key == KeyEvent.VK_RIGHT){
            if(!leftPressed) p.setVelX(0);
            rightPressed = false;
            keysDown.remove(KeyEvent.VK_RIGHT);
        }else if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP) {
            keysDown.remove(KeyEvent.VK_SPACE);
            keysDown.remove(KeyEvent.VK_UP);
        }
        if(key == KeyEvent.VK_O){
            Game.gameState = "endscreen";
            Game.player.reset();
        }
        if(key == KeyEvent.VK_L){
           keysDown.remove(KeyEvent.VK_L);
        }
        if(key == KeyEvent.VK_M){
            Game.motionBlur = !Game.motionBlur;
        }
    }
}