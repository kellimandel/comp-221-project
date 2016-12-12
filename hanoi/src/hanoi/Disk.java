package hanoi;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;

/**
 * The disk in a game of Hanoi.
 */
public class Disk {

    protected GraphicsProgram canvas;

    private int size; //Size of disk. 0 is small, 1 is med, 2 is large.
    private GRect disk; //Visual representation of the disk.

    public static int height = 30; //Height of all disks.
    public static int smallwidth = 50; //Width of a small disk.
    public static int medwidth = 100; //Width of a medium sized disk.
    public static int bigwidth = 150; //Width of a big disk.

    /**
     * Constructor
     * @param canvas the GraphicsProgram that will visualize the disk.
     * @param x X coordinate of disk on canvas
     * @param y Y coordinate of disk on canvas
     * @param size The size (0 for small, 1 for med, 2 for large)
     * @param color The color of the disk.
     */
    public Disk(GraphicsProgram canvas, int x, int y, int size, Color color) {
        this.canvas = canvas;
        this.size = size;
        CreateDisk(x, y, size, color);
    }

    /**
     * Creates the disk and adds it to the canvas.
     * @param x the x coordinate of the disk.
     * @param y the y coordinate of the disk.
     * @param size the size of the disk (0 for small, 1 for med, 2 for big)
     * @param color the color of the disk.
     */
    private void CreateDisk(int x, int y, int size, Color color){

        if (size < 1){
            disk = new GRect(x,y,smallwidth,height);
        }
        if (size == 1){
            disk = new GRect(x,y,medwidth,height);
        }
        if (size > 1){
            disk = new GRect(x,y,bigwidth,height);
        }

        disk.setFillColor(color);
        disk.setFilled(true);

        canvas.add(disk);
    }


    /**
     * Makes sure that the disk is on the top layer.
     */
    public void sendToFront(){
        disk.sendToFront();
    }

    /**
     * Moves disk.
     * @param dx Change in x coordinate
     * @param dy Change in y coordinate
     */
    public void move(int dx, int dy){
        disk.move(dx,dy);
    }

    /**
     * Getter for the size of the disk.
     * @return the size of the disk (0 is small, 1 is med, 2 is large)
     */
    public int getSize() {
        return size;
    }

    /**
     * Moves disk to specified location.
     * @param x the x coordinate of the wanted location.
     * @param y the y coordinate of the wanted location.
     */
    public void setLocation(int x, int y){
        disk.setLocation(x,y);
    }

    /**
     * Tells the program whether this disk can be placed on the given disk.
     * @param disk the disk to be placed on top of.
     * @return true if it's legal, false if it's illegal
     */
    public boolean canBePlacedOn(Disk disk){
        if(disk == null){
            return true;
        }
        else if(this.getSize() <= disk.getSize()){
            return true;
        }
        return false;
    }

    /**
     * Checks if disk is small.
     * @return true is it's small, false if it's not
     */
    public boolean isSmall(){
        if (size < 1){
            return true;
        }
        return false;
    }

    /**
     * Checks if disk is medium sized.
     * @return true is it's medium sized, false if it's not
     */
    public boolean isMed(){
        if (size == 1){
            return true;
        }
        return false;
    }

    /**
     * Checks if disk is big.
     * @return true is it's big, false if it's not
     */
    public boolean isBig(){
        if (size > 1){
            return true;
        }
        return false;
    }
}
