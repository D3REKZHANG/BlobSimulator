/**
 * Name: Unsight Labs
 * Teacher: Ms. Krasteva
 * Date: May 28, 2018
 * Time Spent: 15 min
 */

/**
 *  Change Log
 *  May 28, 2018 - Created to hold text and useful operations for the game
 */

import java.awt.*;
import java.util.*;
/**
 * Class that represents the text to be displayed on the screen
 * 
 * @author Derek Zhang
 * @version 1
 */
public class Text extends GameObject{

    /** String of each line of text*/
    private String text1,text2,text3;

    /**
     * Constructor 1
     * @param  x     x position
     * @param  y     y position
     * @param  text1 first line of text
     * @param  id    ObjectId
     */
    public Text(int x, int y, String text1, ObjectId id){
        super(x,y,id);
        this.text1 = text1;
        this.text2 = "";
        this.text3 = "";
    }

    /**
     * Constructor 2
     * @param  x     x position
     * @param  y     y position
     * @param  text1 first line of text
     * @param  text2 second line of text
     * @param  id    ObjectId
     */
    public Text(int x, int y, String text1, String text2, ObjectId id){
        super(x,y,id);
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = "";
    }

    /**
     * Constructor 3
     * @param  x     x position
     * @param  y     y position
     * @param  text1 first line of text
     * @param  text2 second line of text
     * @param  text3 third line of text
     * @param  id    ObjectId
     */
    public Text(int x, int y, String text1, String text2, String text3, ObjectId id){
        super(x,y,id);
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
    }

    /**
    * Update method
    * @param objects Object list passed from handler
    */
    public void update(ArrayList<GameObject> objects){
        // Empty
    }

    /**
     * Draw method
     * @param g Graphics reference
     */
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 20));
        g.drawString(text1,x,y);
        g.drawString(text2,x,y+30);
        g.drawString(text3,x,y+60);
    }   

    /**
     * Gets hitbox (not needed in this case)
     * @return Rectangle hitbox
     */
    public Rectangle getBounds(){
        return null;
    }
}