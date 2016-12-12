package hanoi;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * Created by Kelly on 11/18/16.
 */
public class Peg extends GCompound {

    protected GraphicsProgram canvas;

    public static int PEGBASE_WIDTH = 150;
    public static int PEGBASE_HEIGHT = 20;

    public static int PEGPOLE_WIDTH = 20;
    public static int PEGPOLE_HEIGHT = 150;


    public Peg(GraphicsProgram canvas, int x,int y) {

        this.canvas = canvas;

        DrawPeg(x,y);

    }

    private void DrawPeg(int x, int y){

        int PEGPOLE_X = x + PEGBASE_WIDTH/2 - PEGPOLE_WIDTH/2;
        int PEGPOLE_Y = y + PEGBASE_HEIGHT - PEGPOLE_HEIGHT;

        Color color = Color.decode("#00000");

        GRect pegPole = new GRect(PEGPOLE_X,PEGPOLE_Y, PEGPOLE_WIDTH, PEGPOLE_HEIGHT);
        pegPole.setFillColor(color);
        pegPole.setFilled(true);

        GRect base = new GRect(x,y,PEGBASE_WIDTH,PEGBASE_HEIGHT);
        base.setFillColor(color);
        base.setFilled(true);


        canvas.add(pegPole);
        canvas.add(base);
    }
}
