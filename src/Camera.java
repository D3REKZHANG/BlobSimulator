/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: June 7, 2018
 * Time Spent: 10 minutes
 */

/**
  *  Change Log
  *  
  *  May 10, 2018 - Created camera class that follows player and allows scrolling
 */

/**
 * Basic class that hold information on the camera
 * 
 * @author Derek Zhang
 * @version 1
 */ 
public class Camera{

    /**Stores position on screen*/
    private int x,y;
    /**Stores velocity */
    private int velX = 0,velY = 0;

    /**
     * Basic Constructor
     * @param  x starting x pos
     * @param  y starting y pos
     */
    public Camera(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Update method
     * @param player Player instance
     */
    public void update(Player player){
        x = -player.getX() + Game.WIDTH/2;
        y = -player.getY() + 100 + Game.HEIGHT/2;
    }

    /**
     * Accessor for x pos
     * @return x
     */
    public int getX(){
        return this.x;
    }

    /**
     * Accessor for y pos
     * @return y
     */
    public int getY(){
        return this.y;
    }

    /**
     * Mutator for x pos
     * @param x new x pos
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Mutator for y pos
     * @param y new y pos
     */
    public void setY(int y){
        this.y = y;
    }
}