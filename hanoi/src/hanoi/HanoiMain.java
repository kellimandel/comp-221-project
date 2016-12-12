package hanoi;

import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * A 3-disk 3-peg towers of Hanoi game. The user can choose between three different game modes:
 * Single Player: A regular Hanoi game.
 * Versus AI: The user competes against an AI to complete the puzzle in less moves.
 * Demonstration: The user gets to watch a demonstration on the best possible hanoi solution.
 * Created by Kelly on 11/18/16.
 */
public class HanoiMain extends GraphicsProgram {


    //==================General==================

    private static final int WIDTH = 800; //Width of the canvas.
    private static final int HEIGHT = 300; //Recommended height per HanoiBoard.
    private int moves = 0; //Number of moves the user took.
    private Disk pickedDisk = null; //Disk picked up
    private HanoiBoard userBoard; //The board the user uses

    //================Booleans that tell the program what mode the user chose================

    private boolean isSinglePlayer = false;
    private boolean isVersus = false;
    private boolean isMenu = true;
    private boolean isDemo = false;

    //================Versus and Demo mode specific================

    private HanoiBoard AiBoard; //The board the AI uses
    private boolean isPlayerTurn = true;
    private Timer timer; //Used to animate the Demonstration mode

    private GLabel versusTurn; //The label that tells the user whose move it is in versus mode
    private AIPlayer AI; //The AI player.

    //---------- Strings for Versus Labels -----------
    private String yourTurn = "It's your turn.";
    private String AiTurn = "It's the AI's turn.";
    private String Loss = "You lose!";
    //---------------------------------------------

    //==================Buttons==================
    private Button single; //Button that, if clicked, makes the mode single player
    private Button versus; //Button that, if clicked, makes the mode versus
    private Button demo; //Button that, if clicked, makes the mode a demonstration

    //===================Methods========================

    /**
     * Initializes the canvas size, etc.
     */
    public void init() {
        this.resize(200, 200);
        addMouseListeners();
        timer = new Timer(0,this);
        timer.setInitialDelay(1000);
        setupJavaTimer();
    }


    /**
     * Creates the buttons for the user to choose a mode.
     */
    public void run(){
        single = new Button(this, 50, 50, "Single Player", "#fffcef");
        versus = new Button(this, 50, 100, "Versus AI", "#ffefef");
        demo = new Button(this, 50, 150, "Demonstration", "#eff7ff");
    }

    /**
     * Interprets the user's mouse clicks.
     * @param event the click.
     */
    @Override
    public void mousePressed(MouseEvent event){
        if(isMenu){
            chooseMode(event);
        }
        else if(isSinglePlayer && isPlayerTurn) {
            playerTurn(event);
        }
        else if(isVersus && isPlayerTurn){
                playerTurn(event);
        }
    }

    /**
     * ChooseMode checks what game mode (single player, versus, demo) the player chose.
     * (Checks to see which button was pressed).
     */

    private void chooseMode(MouseEvent event){
        int x = event.getX();
        int y = event.getY();

        if(single.isClicked(x,y)){
            isSinglePlayer = true;
            isMenu = false;
            removeButtons();
            createSingleBoard();
        }

        if(versus.isClicked(x,y)){
            isVersus = true;
            isMenu = false;
            removeButtons();
            createVersusBoard();
        }


        if(demo.isClicked(x,y)){
            isDemo = true;
            isMenu=false;
            isPlayerTurn = false;
            removeButtons();
            createDemoBoard();
            timer.start();
        }
    }

    /**
     * Selects the stack that the player chose and allows the player to either pick up or place a disk.
     */

    private void playerTurn(MouseEvent event){
        DiskStack stack = clickedStack(event.getX(), event.getY());
        if(stack!=null) {
            if (pickedDisk == null) {
                grabDisk(stack);
            } else {
                putDisk(stack);
            }
        }

    }

    /**
     * The AI selects a move given by AIPlayer (depending on if demo or versus) and performs its move.
     */

    private void AiTurn(){

        int[] move;

        if(isDemo) {
            move = AI.getMove();
        }
        else{
            move = AI.getRandMove();
        }

        DiskStack fromStack = intToStack(move[0]);
        DiskStack toStack = intToStack(move[1]);


        Disk AIPickedDisk = fromStack.pop();

        toStack.add(AIPickedDisk);

        switchTurn();

    }

    /**
     * Makes the program pause between moves in demo (so that the demo isn't instantly solved
     * without visualization).
     */

    private void setupJavaTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                int[] move = AI.getMove();

                DiskStack fromStack = intToStack(move[0]);
                DiskStack toStack = intToStack(move[1]);


                Disk AIPickedDisk = fromStack.pop();

                toStack.add(AIPickedDisk);

                timerCheck();
            }
        });
    }

    /**
     * Stops the demo from continuing to run after it has already been solved.
     */

    private void timerCheck(){
        if(!AI.movesLeft()){
            timer.stop();
        }
    }

    /**
     * Picks up the top disk from a selected stack of disks.
     * @param stack is the stack selected.
     */

    private void grabDisk(DiskStack stack){
        if(stack != null && !stack.empty()){
            pickedDisk = stack.pop();
        }
    }

    /**
     * Places the selected disk on top of the selected stack.
     * @param stack is the selected stack.
     */

    private void putDisk(DiskStack stack){
        if(stack.empty() || pickedDisk.canBePlacedOn(stack.peek())){
            stack.add(pickedDisk);
            pickedDisk = null;
            moves++;
            if(isVersus){
                switchTurn();
            }
        }
        checkWin();
    }

    /**
     * Tells the program what stack the user clicked on (if any).
     * @param x the X coordinate of where the player clicked.
     * @param y the Y coordinate of where the player clicked.
     * @return the stack that was clicked on (null if none were clicked).
     */

    private DiskStack clickedStack(int x, int y){
        DiskStack stack1 = userBoard.getStack1();
        DiskStack stack2 = userBoard.getStack2();
        DiskStack stack3 = userBoard.getStack3();

        if(stack1.isClicked(x,y)){
            return stack1;
        }

        else if(stack2.isClicked(x,y)){
            return stack2;
        }

        else if(stack3.isClicked(x,y)){
            return stack3;
        }
        return null;
    }

    /**
     * Resizes the canvas for a single HanoiBoard and creates said HanoiBoard.
     * Used for single player.
     */
    private void createSingleBoard(){
        this.resize(WIDTH, HEIGHT + 20);
        userBoard = new HanoiBoard(this, 200);
    }

    /**
     * Resizes the canvas for a single HanoiBoard, creates it, and then
     * creates the AI to control it.
     * Used for demonstration mode.
     */
    private void createDemoBoard(){
        this.resize(WIDTH, HEIGHT + 20);
        AiBoard = new HanoiBoard(this, 200);
        AI = new AIPlayer();
    }

    /**
     * Resizes the canvas to hold two HanoiBoards (stacked vertically) and creates
     * appropriate versus mode labels (e.g. telling the user whose turn it is) as
     * well as the AI.
     * Used for versus mode.
     */
    private void createVersusBoard(){
        this.resize(WIDTH, HEIGHT*2 + 20);
        versusTurn = new GLabel(yourTurn, WIDTH/2-60, HEIGHT);
        versusTurn.setFont("Helvetica-18");
        add(versusTurn);

        AI = new AIPlayer();

        AiBoard = new HanoiBoard(this, 200);
        userBoard = new HanoiBoard(this, 500);
    }

    /**
     * Removes the buttons that ask the user what mode they want. Used after
     * a game mode has already been selected.
     */
    private void removeButtons(){
        versus.remove();
        demo.remove();
        single.remove();
    }

    /**
     * Checks to see if either the user or the AI won and updates the visuals accordingly
     * with labels that tell the player who won.
     */
    private void checkWin(){
        if(userBoard.checkWin()){
            String stringMoves = Integer.toString(moves);
            GLabel win = new GLabel("You Won in " + stringMoves + " moves!");

            double labelWidth = win.getWidth();

            win.setLocation(WIDTH/2-labelWidth/2, 40);
            add(win);
            if(isVersus){
                remove(versusTurn);
            }
            isPlayerTurn = false;

        }
        else if(isVersus && AiBoard.checkWin()){
            versusTurn.setLabel(Loss);
            isPlayerTurn = false;
        }

    }

    /**
     * Used in versus mode. Switches between the AI's turn and the user's turn.
     */
    private void switchTurn(){
        if(isPlayerTurn) {
            isPlayerTurn = false;
            versusTurn.setLabel(AiTurn);
            AiTurn();
        }
        else{
            isPlayerTurn = true;
            versusTurn.setLabel(yourTurn);
        }
    }


    /**
     * Converts an integer to the appropriate relevant stack.
     */
    private DiskStack intToStack(int num){
        if(num == 0){
            return AiBoard.getStack1();
        }
        if(num == 1){
            return AiBoard.getStack2();
        }
        if(num == 2){
            return AiBoard.getStack3();
        }
        return null;
    }

}
