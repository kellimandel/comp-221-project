package hanoi;

import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;

/**
 * The class for Buttons to choose the game mode of Hanoi
 */
public class Button {

    private double width; //Width of the button.
    private double height; //Height of the button.
    private int X; //X coordinate of the button.
    private int Y; //Y coordinate of the button.

    protected GraphicsProgram canvas; //The GraphicsProgram for the button to be drawn on top of

    private GRect rect; //The visual representation of the button
    private GLabel name; //The label on the button

    /**
     * Constructor for button
     * @param canvas The GraphicsProgram for the button to be drawn on top of
     * @param x X coordinate of the button.
     * @param y Y coordinate of the button.
     * @param text The text on the button.
     * @param hex The hex code for the color of the button.
     */
    public Button(GraphicsProgram canvas, int x, int y,  String text, String hex) {
        this.canvas = canvas;
        this.X = x;
        this.Y = y;

        Color color = Color.decode(hex);

        name = new GLabel(text, x, y);
        name.setFont("Helvetica-18");
        width = name.getWidth() + 10;
        height = name.getHeight() + 10;

        rect = new GRect(x - 5,y - height + 7, width, height);
        rect.setFillColor(color);
        rect.setFilled(true);

        add();
    }

    /**
     * Checks if the button was clicked on.
     * @param x the x coordinate of where the user clicked.
     * @param y the y coordinate of where the user clicked.
     * @return true if it was clicked, false if it was not.
     */
    public boolean isClicked(int x, int y){
        boolean inXRange = x >= X && x <= X + width;
        boolean inYRange = y <= Y && y >= Y - height;
        if(inXRange && inYRange){
            return true;
        }
        return false;
    }

    /**
     * Removes button from canvas
     */
    public void remove(){
        canvas.remove(rect);
        canvas.remove(name);
    }

    /**
     * Adds button to canvas.
     */
    public void add(){
        canvas.add(rect);
        canvas.add(name);
    }
}
