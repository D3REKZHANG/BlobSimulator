/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 3 hours
 */

/**
 * Change Log
 *
 * May 4, 2018 - Created Class to host game and act as driver
 * May 5, 2018 - Added functionality to support graphical drawing
 * May 6, 2018 - Added more framework for better game loop
 * May 8, 2018 - Added scale method for images
 * May 11-13, 2018 - Added multiple screen and game state handling
 * May 18, 2018 - Added more menus
 * May 21, 2018 - Improved OOP
 * May 22, 2018 - Improved Camera mechanics
 * May 25, 2018 - Restructuring of code for even better OOP
 * May 30, 2018 - Started adding in basic framework for highscores
 * May 31, 2018 - Added insert method
 * June 1, 2018 - Completed highscores 
 * June 3, 2018 - Fade method for fade ins and outs
 * June 5, 2018 - Connected Quiz mechanic to game loop
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
 * @version 4
 */
public class Game extends Canvas implements Runnable{
    
    /**Dimensions*/
    public static int WIDTH, HEIGHT;
    
    /**Game thread*/
    private Thread thread;
    /** Stores whether or not the game is running */
    private boolean running = false;
    
    /**Stores the score of the entries for highscores*/
    public static ArrayList <Integer> [] highscores;
    /**Stores the names of the entries for highscores*/
    public static ArrayList <String> [] usernames;
    
    /**Keeps track of which game state is active*/
    public static String gameState = "loading screen";
    /** Menu class*/
    private Menu menu = new Menu(this);
    /** LoadingScreen class*/
    private LoadingScreen loadScreen = new LoadingScreen(this);
    /** LevelSelect class*/
    private LevelSelect lvlSelect = new LevelSelect(this);
    /** Highscore class*/
    private Highscore highscore = new Highscore(this);
    /** Endscreen class */
    private Endscreen endscreen = new Endscreen(this);
    /** Quiz class*/
    public Quiz quiz = new Quiz(this);
    /** Stores which level is active*/
    public static int level = 1;
    
    /**Handler class*/
    Handler handler;
    /**Camera class*/
    public static Camera cam;
    /**Player class*/
    public static Player player;
    
    /** Stores the score*/
    public static int score = 60000;
    /** Stores whether motionBlur*/
    public static boolean motionBlur = false;
    
    /** Stores fade alpha value*/
    private int fadeAlpha = 0;
    /** Stores whether or not the screen is fading*/
    private boolean fade = false;

    /**
     * Scales the given image to the new width and height
     * @param imageToScale BufferedImage image to scale
     * @param dWidth new width
     * @param dHeight new height
     * @return scaled image
     */
    public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
        BufferedImage scaledImage = null;
        if (imageToScale != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        return scaledImage;
    }
    
    /**
     * Fades the screen in from black
     */
    public void fade(){
        fade = true;
        fadeAlpha = 255;
    }
    
    /**
     * Initializes variables and the game
     */
    private void init(){
        WIDTH = getWidth();
        HEIGHT = getHeight();
        
        cam = new Camera(0,0);
        handler = new Handler(cam);
        player = new Player(200,100,handler,this,ObjectId.Player);

        handler.add(player);
        
        read();
        
        this.addKeyListener(new KeyInput(handler));
        this.addKeyListener(endscreen);
        this.addMouseListener(new MouseInput(handler,this));
        this.addMouseListener(quiz);
    }
    
    /**
     * Starts the thread
     */
    public synchronized void start(){
        if(!running){
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * Reads highscores from file
     */
    @SuppressWarnings("unchecked")
    public static void read() {
        
        // Reset highscores
        highscores = (ArrayList<Integer>[]) new ArrayList[3];
        usernames = (ArrayList<String>[]) new ArrayList[3];
        
        // Initialize ArrayLists
        for (int i = 0; i < 3; i++)
        {
            highscores [i] = new ArrayList <Integer> ();
            usernames [i] = new ArrayList <String> ();
        }
        
        // Read and parse data
        try
        {
            for (int i = 0; i < 3; i++){
                
                BufferedReader reader = new BufferedReader (new FileReader ("highscores/level_" + (i+1) + ".txt"));
                
                for (int x=0;x<10;x++)
                {
                    String line = reader.readLine ();
                    if (line == null) break;
                    highscores [i].add (Integer.parseInt (line.split (" ")[0]));
                    usernames [i].add (line.substring (line.indexOf (" ")));
                }
                reader.close ();
            }
        }
        catch (Exception e)
        {
            System.err.println (e);
        }
    }
    
    /**
     * Run method since Game is a runnable
     */
    public void run(){
        
        // Initialize needed variables
        init();
        
        // Time variables
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        
        // Game loop
        while(running){
            // Process the keys being pressed
            try{
                KeyInput.process();
            }catch(ConcurrentModificationException err){
                System.out.println();
            }            
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            for(;delta >= 1;delta--){
                this.update();
                updates++;
            }
            this.draw();
            frames++;
            // Score and player tick
            if(Game.gameState.equals("game") && Game.level != 4){
                if(frames%2==0) score = Math.max(score-1,0);
                if(frames%100==0) player.tick();
            }
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
                updates = 0;
            }
        }
    }
    
    /**
     * Update that is called each frame
     */
    private void update(){
        if(Game.gameState.equals("menu")){
            menu.update();
        }else if(Game.gameState.equals("loading screen")){
            loadScreen.update();
        }else if(Game.gameState.equals("level select")){
            lvlSelect.update();
        }else if(Game.gameState.equals("game")){
            handler.update();
            cam.update(player);
        }else if(Game.gameState.equals("highscores")){
            highscore.update();
        }else if(Game.gameState.equals("endscreen")){
            endscreen.update();
        }else if(Game.gameState.equals("quiz")){
            quiz.update();
        }
        // Compute Fade
        if(fade){
            fadeAlpha-=3;
        }
        if(fadeAlpha <= 0){
            fadeAlpha = 0;
            fade = false;            
        }
    }

    /**
     * Draws all graphics per frame
     */
    private void draw(){
        // Triple Buffering
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        Graphics g2d = (Graphics2D) g;

        if(Game.gameState.equals("menu")){
            menu.draw(g);
        }else if(Game.gameState.equals("loading screen")){
            loadScreen.draw(g);
        }else if(Game.gameState.equals("level select")){
            lvlSelect.draw(g);
        }else if(Game.gameState.equals("highscores")){
            highscore.draw(g);
        }else if(Game.gameState.equals("endscreen")){
            endscreen.draw(g);
        }else if(Game.gameState.equals("quiz")){
            quiz.draw(g);
        } else if(Game.gameState.equals("game")){
            int motionAlpha = ((motionBlur) ? 100 : 255);

            // Background
            g.setColor(new Color(0,0,0,motionAlpha));
            g.fillRect(0,0,WIDTH,HEIGHT);
        
            // Camera Area of Effect
            if(player.getX() > 400)
                g2d.translate(cam.getX(),0);
            g2d.translate(0,cam.getY());
            
            handler.draw(g);

            if(player.getX() > 400)
                g2d.translate(-cam.getX(),0);
            g2d.translate(0,-cam.getY());
            // End of Camera AOE
            
            // Player Sleeping Screen Darken
            if(player.isSleeping()){
                g.setColor(new Color(0,0,0,180));
                g.fillRect(0,0,WIDTH,HEIGHT);
            }else if(player.getEnergy() <= 0 && Game.level == 3){
                // Draw fatigue
                g.setColor(new Color(0,0,0,100));
                g.fillRect(0,0,WIDTH,HEIGHT);
            }
            
            if(Game.level == 3){
                // Player Energy Bar
                g.setColor(Color.white);
                g.drawRect(10,10,200,50);
                g.fillRect(10,10,(int)(player.getEnergy()*2),50);
                // Sleep time bar
                g.setColor(new Color(0,200,200));
                g.drawRect(10,65,200,10);
                g.fillRect(10,65,(int)(player.getSleep()),10);
            
                g.setColor(Color.white);
                g.setFont(new Font("Arial",Font.PLAIN,20));
                if(player.isSleeping()){
                    g.drawString("SLEEPING...",10,95);
                    g.drawString("Press 's' to wake up",10,135);
                }else{
                    g.drawString("Press 's' to sleep",10,95);
                    if(player.getEnergy() <= 0){
                        g.drawString("YOU NEED TO SLEEP!",10,120);
                    }
                }
            }

            // Displaying score
            g.setColor(Color.white);
            // Score Padding 0s
            String sscore = Integer.toString(score);
            int len = sscore.length();
            for(int x=0;x<5-len;x++){
                sscore = "0" + sscore;
            }

            g.setFont(new Font("arial", Font.ITALIC, 20));
            g.drawString("SCORE: " + sscore,650,25);
            
            // Water Bottle Count
            if(Game.level != 4){
                g.setFont(new Font("Lato", Font.ITALIC, 40));
                g.drawString(player.getBottleCount() + "/8",10,575);
                try{
                    BufferedImage image = ImageIO.read(new File("images/bottleMini.png"));
                    g.drawImage(image, 75, 540, null);
                }catch(IOException e){
                    System.err.println("Could not find water image!");
                }
            }
            
            /*
            g.setColor(Color.green);
            g.drawString("isAsleep: " + player.isSleeping(),10,400);
            g.drawString("x: " + player.getX(),10,415);
            g.drawString("y: " + player.getY(),10,430); 
            g.drawString("velX: " + player.getVelX(),10,445);
            g.drawString("velY: " + player.getVelY(),10,460);
            g.drawString("w,h: " + player.w + "," + player.h,10,475);
            g.drawString("lastDir: " + player.lastDir,10,490);
            g.drawString("speed: " + player.speed,10,505);
            */
        }
        
        // Fade
        g.setColor(new Color(0,0,0,fadeAlpha));
        g.fillRect(0,0,WIDTH,HEIGHT);
        
        g.dispose();
        bs.show();
    }
    
    /**
     * Main method
     */
    public static void main(String[] args){
        new Window(790,590, "ICS4U0 Final", new Game());
    }
}

