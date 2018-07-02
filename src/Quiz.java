/**
 * Name: Unsight Labs 
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 1 hour
 */

/**
 * Change Log
 * June 4, 2018 - Created to handle the quiz at the end of each level
 * June 5, 2018 - Added highlighting menu options
 * June 6, 2018 - Completed quiz mechanic 
 */ 

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * Quiz Class
 * 
 * @author Derek Zhang William Xu 
 * @version 2
 */
public class Quiz extends MouseAdapter{

    /** Stores the background images */
    private BufferedImage image,i1,i2,i3,i4;
    /** Stores which option is highlighted */
    private boolean h1 = false,h2 = false,h3 = false,h4 = false;
    /** Game reference */
    private Game game;
    /** Stores all questions [level][question][question+answer] */
    private String questions[][][] = {
        {
            {"What is the recommended amount of daily physical exercise for teens?","1 hour","30 min","2 hours","15 minutes"},
            {"What percentage of adolescents exercise for at least 1 hour a day?","20%","15%","80%","95%"},
            {"Which of these are not consequences from inadequate exercise?","brain damage","obesity","depression","heart disease"}
        },
        {
            {"What percentage of your daily calories should be from carbs?","45-65%","80-90%","10-15%","30-40%"},
            {"Shedding excess weight does not decrease your risk of:","pneumonia","type-2 diabetes","thyroid dysfunction","atheroschlerosis"},
            {"Which of these foods is the most unhealthy?","macadamia nuts","starfruit","eggplant","celery"}
        },
        {
            {"What is the recommended hours of sleep for adolescents?","8-10 hours","7+ hours","6-8 hours","none of the above"},
            {"What percentage of adolescents sleep more than 8.5 hours?","15%","35%","80%","95%"},
            {"Not sleeping enough can lead to which problem?","concentration","cancer","hyperactivity","heart failure"}
        },
    };
    
    /** Text for the questions and answers */
    private Text q,a1,a2,a3,a4;
    /** Stores the correct answer */
    private int aC;
    /** Stores the userChoice */
    private int userChoice = -1;
    /** Stores whether the player was correct */
    private boolean correct = false;
    /** Stores whether the player was incorrect */
    private boolean incorrect = false;

    /**
     * Constructor to load images and assign Game reference
     * @param  game Game reference
     */
    public Quiz(Game game){
        try{
            image = ImageIO.read(new File("images/quizscreen.png"));
            i1 = ImageIO.read(new File("images/quizscreenH1.png"));
            i2 = ImageIO.read(new File("images/quizscreenH2.png"));
            i3 = ImageIO.read(new File("images/quizscreenH3.png"));
            i4 = ImageIO.read(new File("images/quizscreenH4.png"));
        }catch(IOException e){
            System.err.println("Could not find quiz images!");
        }
        this.game = game;
        this.loadQuestions();
    }
    
    /**
     * Loads Questions for the Quiz
     */
    public void loadQuestions(){
        int x = (int)(Math.random()*3);
        int a = (int)(Math.random()*2);
        this.q = new Text(55,260,questions[Game.level-1][x][0],ObjectId.Text);
        this.a1 = new Text(55,410,questions[Game.level-1][x][2+a],ObjectId.Text);
        this.a2 = new Text(430,410,questions[Game.level-1][x][1+a],ObjectId.Text);
        this.a3 = new Text(55,530,questions[Game.level-1][x][4-3*a],ObjectId.Text);
        this.a4 = new Text(430,530,questions[Game.level-1][x][3+a],ObjectId.Text);
        this.aC = ((a == 0)?2:3);
    }
    
    /**
     * Update that is called each frame
     */
    public void update(){
        int mx = MouseInput.getX(game);
        int my = MouseInput.getY(game);
        if(mx >= 45 && mx <= 375 && my >= 360 && my <= 448){
            h1 = true;
        }else if(mx >= 431 && mx <= 756 && my >= 362 && my <= 448){
            h2 = true;
        }else if(mx >= 45 && mx <= 375 && my >= 479 && my <= 566){
            h3 = true;
        }else if(mx >= 431 && mx <= 756 && my >= 479 && my <= 566){
            h4 = true;
        }else{
            h1 = false;
            h2 = false;
            h3 = false;
            h4 = false;
        }
        if(userChoice != -1){
            if(userChoice == aC) correct = true;
            else incorrect = true;
        }
    }

    /**
     * Draws all graphics per frame
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        g.drawImage(image,0,0,null);
        if(h1) g.drawImage(i1,0,0,null);
        else if(h2) g.drawImage(i2,0,0,null);
        else if(h3) g.drawImage(i3,0,0,null);
        else if(h4) g.drawImage(i4,0,0,null);
        
        q.draw(g);
        a1.draw(g);
        a2.draw(g);
        a3.draw(g);
        a4.draw(g);

        if(correct || incorrect){
            g.setColor(Color.black);
            g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
            g.setFont(new Font("Lucida Sans Typewriter", Font.ITALIC, 50));
            if(correct) {
                g.setColor(Color.green);
                g.drawString("C O R R E C T",200,320);
            }else if(incorrect){
                g.setColor(Color.red);
                g.drawString("I N C O R R E C T",140,320);
            }
            g.setColor(Color.white);
            g.setFont(new Font("Lucida Sans Typewriter", Font.PLAIN, 20));
            g.drawString("click anywhere to continue",245,530);
        }
        g.dispose();
    }
    
    /**
     * Handles mouse clicks
     * @param e MouseEvent instance
     */ 
    public void mouseReleased(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(Game.gameState.equals("quiz")){
            if(mx >= 45 && mx <= 375 && my >= 360 && my <= 448){
                userChoice = 1;
            }else if(mx >= 431 && mx <= 756 && my >= 362 && my <= 448){
                userChoice = 2;
                if(userChoice == aC) Game.score += 5000;
            }else if(mx >= 45 && mx <= 375 && my >= 479 && my <= 566){
                userChoice = 3;
                if(userChoice == aC) Game.score += 5000;
            }else if(mx >= 431 && mx <= 756 && my >= 479 && my <= 566){
                userChoice = 4;
            }
        }
    }

    /**
     * Handles mouse clicks
     * @param e MouseEvent instance
     */
    public void mousePressed(MouseEvent e){
        if(Game.gameState.equals("quiz")){
            if(userChoice != -1){
                Game.gameState = "endscreen";
                game.fade();
                userChoice = -1;
                correct = false;
                incorrect = false;
            }
        }
    }
}

