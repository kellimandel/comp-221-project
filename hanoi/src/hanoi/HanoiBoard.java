package hanoi;

import acm.graphics.GCompound;
import acm.program.GraphicsProgram;

import java.awt.*;

/**
 * The game board in a game of Hanoi.
 */
public class HanoiBoard extends GCompound {

    protected GraphicsProgram canvas; //The GraphicsProgram the board will be drawn on.

    private int START_X = 100; //The starting X coordinate for the pegs.
    private int START_Y; //The Y coordinate for the pegs.
    private int DX = 230; //The distance between pegs.

    private Peg peg1; //First peg
    private Peg peg2; //Second peg
    private Peg peg3; //Third peg

    private DiskStack stack1; //First stack
    private DiskStack stack2; //Second stack
    private DiskStack stack3; //Third stack

    /**
     * Constructor for HanoiBoard
     * @param canvas the GraphicsProgram to draw the board on top of.
     * @param y the y coordinate of the pegs.
     */
    public HanoiBoard(GraphicsProgram canvas, int y) {
        this.canvas = canvas;
        START_Y = y;
        DrawPegs();
    }

    /**
     * Draws the pegs.
     */
    private void DrawPegs(){
        int x = START_X;

        this.peg1 = new Peg(canvas,x,START_Y);
        this.stack1 = new DiskStack(canvas,x,START_Y, Color.pink,true,true,true);
        x = x + DX;

        this.peg2 = new Peg(canvas,x,START_Y);
        this.stack2 = new DiskStack(canvas,x,START_Y, Color.pink,false,false,false);
        x = x + DX;

        this.peg3 = new Peg(canvas,x,START_Y);
        this.stack3 = new DiskStack(canvas,x,START_Y, Color.pink,false,false,false);
        x = x + DX;
    }

    /**
     * Getter for stack1
     * @return stack1
     */
    public DiskStack getStack1() {
        return stack1;
    }

    /**
     * Getter for stack2
     * @return stack2
     */
    public DiskStack getStack2() {
        return stack2;
    }

    /**
     * Getter for stack3
     * @return stack3
     */
    public DiskStack getStack3() {
        return stack3;
    }

    /**
     * Checks if the board is in a winning state (all disks on last peg)
     * @return true if it's a win, false if it's not
     */
    public boolean checkWin(){
        if(stack1.empty() && stack2.empty()){
            return true;
        }
        return false;
    }
}
