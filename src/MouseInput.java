/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: May 30, 2018
 * Time Spent: 20 minutes
 */

/*
    Change Log
    May 25, 2018 - Created to handle mouse input
    May 26, 2018 - Added hover highlighting over options
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * MouseInput class to get mouse input
 *
 * @author Derek Zhang
 * @version 2
 */
public class MouseInput extends MouseAdapter{

    /** Handler reference */
    private Handler handler;
    /** Game reference */
    private Game game;
        
    /**
     * Constructor to pass Handler reference
     * @param  handler Handler reference
     * @param  game Game reference 
     */
    public MouseInput(Handler handler, Game game){
        this.handler = handler;
        this.game = game;
    }

    /**
     * Override method of the MouseListener class
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(Game.gameState.equals("menu")){
            if(mx > 430 && mx <605 && my > 375 && my < 420){
                Game.gameState = "level select";
                game.fade();
            }else if(mx >= 440 && mx <= 627 && my >= 430 && my <= 467){
                handler.setLevel(4);
                Game.gameState = "game";
                game.fade();
            }else if(mx >= 440 && mx <= 505 && my >= 482 && my <= 517){
                System.exit(1);
            }else if(mx >= 670 && mx <= 775 && my >= 500 && my <= 583){
                Game.gameState = "highscores";
                game.fade();
            }
        }else if(Game.gameState.equals("level select")){
            if(mx > 50 && mx < 505 && my > 182 && my < 234){
                handler.setLevel(1);
                Game.gameState = "game";
                game.fade();
            }else if(mx > 50 && mx < 335 && my > 269 && my < 332){
                handler.setLevel(2);
                Game.gameState = "game";
                game.fade();
            }else if(mx > 50 && mx < 356 && my > 358 && my < 416){
                handler.setLevel(3);
                Game.gameState = "game";
                game.fade();
            }else if(mx > 600 && mx < 760 && my > 530 && my < 560){
                Game.gameState = "menu";
                game.fade();
            }
            Game.score = 60000;
        }else if(Game.gameState.equals("highscores")){
            if(mx >= 140 && mx <= 350 && my >= 555 && my <= 580){
              Game.gameState = "menu";
              game.fade();
            }else if(mx >= 522 && mx <= 595 && my >= 556 && my <= 578){
              Highscore.reset();
              Game.read();
              game.fade();
            }
        }
    }

    /**
     * Returns the current x coord of the mouse relative to the window
     * @param  game Game reference
     * @return Mouse X Coord
     */
    public static int getX(Game game){
        return (int)MouseInfo.getPointerInfo().getLocation().getX()-(int)game.getLocationOnScreen().getX();
    }

    /**
     * Returns the current y coord of the mouse relative to the window
     * @param  game Game reference
     * @return Mouse Y Coord
     */
    public static int getY(Game game){
        return (int)MouseInfo.getPointerInfo().getLocation().getY()-(int)game.getLocationOnScreen().getY();
    }
    
}