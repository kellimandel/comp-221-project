package hanoi;

import acm.program.GraphicsProgram;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The class for a stack of Disks in a Hanoi game.
 */
public class DiskStack{

    private Stack<Disk> diskStack; //The stack of disks.
    private ArrayList<Disk> diskList; //A list of the disks in the stack.
    protected GraphicsProgram canvas; //The GraphicsProgram for the stack to be drawn on top of

    private int X; //X coordinate for the stack
    private int Y; //Y coordinate for the stack


    private Disk pickedDisk; //The disk currently picked up, waiting to be placed

    private int width = Disk.bigwidth; //Width of the stack (as wide as the largest disk)
    private int height = Disk.height*3; //Height of the stack (as tall as all three disks)

    private int DY = Disk.height; //Change in height for each disk

    /**
     * Constructor for DiskStack
     * @param canvas The GraphicsProgram for the stack to be drawn on top of
     * @param x X coordinate for the stack
     * @param y Y coordinate for the stack
     * @param color The color of the Disks in the stack
     * @param small true if the stack contains a small disk, otherwise false
     * @param med true if the stack contains a med disk, otherwise false
     * @param large true if the stack contains a big disk, otherwise false
     */
    public DiskStack(GraphicsProgram canvas, int x, int y, Color color, boolean small, boolean med, boolean large) {
        this.canvas = canvas;
        this.diskList = new ArrayList();
        this.diskStack = new Stack();
        this.X = x;
        this.Y = y;
        CreateStack(color, x, y, small, med, large);

    }

    /**
     * Creates the stack.
     * @param color The color of the Disks in the stack
     * @param x X coordinate for the stack
     * @param y Y coordinate for the stack
     * @param small true if the stack contains a small disk, otherwise false
     * @param med true if the stack contains a med disk, otherwise false
     * @param large true if the stack contains a big disk, otherwise false
     */
    private void CreateStack(Color color, int x, int y, boolean small, boolean med, boolean large) {

        int currY = y - Disk.height;
        int smallX = x + Disk.bigwidth/2 - Disk.smallwidth/2;
        int medX = x + Disk.bigwidth/2 - Disk.medwidth/2;

        if(large){
            Disk bigDisk = new Disk(canvas, x, currY, 2, color);
            diskStack.add(bigDisk);
            diskList.add(bigDisk);
            currY = currY - DY;

        }
        if(med){
            Disk medDisk = new Disk(canvas, medX, currY, 1, color);
            diskStack.add(medDisk);
            diskList.add(medDisk);
            currY = currY - DY;
        }
        if(small){
            Disk smallDisk = new Disk(canvas, smallX, currY, 0, color);
            diskStack.add(smallDisk);
            diskList.add(smallDisk);
        }
    }

    /**
     * Removes the top Disk from the stack.
     * @return the Disk that was picked up.
     */
    public Disk pop(){
        this.pickedDisk = diskStack.pop();
        pickedDisk.move(0,-30);
        diskList.remove(diskList.size() - 1);
        return pickedDisk;
    }

    /**
     * Adds a Disk to the stack.
     * @param disk the Disk to be added
     * @return true if add was successful, false if unsuccessful
     */
    public boolean add(Disk disk){
        int y = Y - Disk.height*(diskList.size()+1);
        int smallX = X + Disk.bigwidth/2 - Disk.smallwidth/2;
        int medX = X + Disk.bigwidth/2 - Disk.medwidth/2;


        if(disk.isBig()) {
            disk.setLocation(X, y);
        }

        if(disk.isMed()) {
            disk.setLocation(medX, y);
        }

        if(disk.isSmall()) {
            disk.setLocation(smallX, y);
        }

        disk.sendToFront();
        diskList.add(disk);
        return diskStack.add(disk);
    }

    /**
     * @return the top Disk in the stack
     */
    public Disk peek(){
        return diskStack.peek();
    }

    /**
     * @return true if stack is empty, false if stack is not.
     */
    public boolean empty(){
        return diskStack.empty();
    }

    /**
     * Checks to see if user clicked on stack.
     * @param x x coordinate of where the user clicked.
     * @param y y coordinate of where the user clicked.
     * @return true if the stack was clicked, false if not.
     */
    public boolean isClicked(int x, int y){
        boolean inXRange = x >= X && x <= X + width;
        boolean inYRange = y <= Y && y >= Y - height;
        if(inXRange && inYRange){
            return true;
        }
        return false;
    }


}
