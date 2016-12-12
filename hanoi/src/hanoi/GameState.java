package hanoi;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class representing a state of the board in a Hanoi game.
 */
public class GameState {

    private int[][] gameState; //A 2D array representing the state of the game. (3x3 matrix)
    /*
    Example of a gameState:
    [1,3,0],[0,0,0],[2,0,0]
    1 = small disk
    2 = med disk
    3 = large disk
    This represents a hanoi board that looks like this:
        |         |         |
    _▅▅███▅▅_  ___|___  __▅▅▅▅▅__

     e.g. the first peg has a large and small disk, and the third has a medium sized disk.
     */
    private int[] firstStack; //The first array of the gameState
    private int[] secondStack; //The second array of the gameState
    private int[] thirdStack; //The third array of the gameState


    /**
     * Constructor
     * @param gameState a 2D array representing a gameState
     */
    public GameState(int[][] gameState) {
        this.gameState = gameState;
        firstStack = gameState[0];
        secondStack = gameState[1];
        thirdStack = gameState[2];
    }

    /**
     * Represents a move in a disk from one stack to another. Updates all global variables.
     * @param from the stack you're moving the disk from.
     * @param to the stack you're placing the disk on.
     */
    public void moveDisk(int from, int to){

        int[] fromStack = intToStack(from);
        int[] toStack = intToStack(to);

        int disk = fromStack[0];

        if((toStack[0] == 0 || disk < toStack[0]) && disk != 0 && toStack[2] == 0){
            toStack[2] = toStack[1];
            toStack[1] = toStack[0];
            toStack[0] = disk;
            fromStack[0] = 0;
        }

        updateGameState();
        gameState = cleanState();

    }

    /**
     * Tells the program whether or not a move is legal.
     * @param from The stack that the disk is coming from.
     * @param to The stack that the disk is going to.
     * @return A boolean ("true" is a legal move, "false" if illegal)
     */

    public boolean canMove(int from, int to){
        int[] fromStack = intToStack(from);
        int[] toStack = intToStack(to);
        int disk = fromStack[0];

        if(toStack[0] == 0 && disk != 0){
            return true;
        }

        return (disk < toStack[0] && disk != 0 && toStack[2] == 0);
    }

    /**
     * Updates firstStack, secondStack, and thirdStack to keep up with gameState variable.
     */
    private void updateGameState(){
        gameState[0] = firstStack;
        gameState[1] = secondStack;
        gameState[2] = thirdStack;
    }

    /**
     * Makes sure that the gameState variable is in order.
     * e.g. The 'top' disk should always be the first in each array, the second-to-top disk
     * should always be the second in the array, and the third index in the array should only
     * be filled if all 3 disks are on the peg.
     * @return the cleaned version of gameState.
     */
    private int[][] cleanState(){

        int[][] move = gameState;

        for(int i = 0; i <= 2 ; i++){

            if (move[i][0] == 0 && move[i][1] > 0){
                move[i][0] = move[i][1];
                move[i][1] = move[i][2];
                move[i][2] = 0;
            }
            else if (move[i][0] == 0 && move[i][2] > 0){
                move[i][0] = move[i][2];
                move[i][1] = 0;
                move[i][2] = 0;
            }

            else if (move[i][1] == 0 && move[i][2] > 0){
                move[i][1] = move[i][2];
                move[i][2] = 0;
            }
        }
        return move;
    }

    /**
     * Converts an integer (1, 2, or 3) to the corresponding stack.
     * @param stackNum the number being converted into a stack.
     * @return the stack that stackNum represents.
     */
    private int[] intToStack(int stackNum){

        if(stackNum == 1){
            return firstStack;
        }
        if(stackNum == 2){
            return secondStack;
        }
        if(stackNum == 3){
            return thirdStack;
        }

        return null;
    }

    /**
     * Checks if gameState variable represents a win. (i.e. all disks are on last peg).
     * @return the boolean -- true if a win, false if not a win.
     */
    public boolean isWin(){
        int[] winState = new int[3];
        winState[0] = 1;
        winState[1] = 2;
        winState[2] = 3;
        boolean win = true;

        for(int i = 0; i <= 2; i++){
            if(thirdStack[i] != winState[i]){
                win = false;
            }
        }

        return win;
    }

    /**
     * Getter for global gameState variable.
     * @return gameState
     */
    public int[][] getGameState() {
        return gameState;
    }

    /**
     * Checks whether the current gameState already exists in a list of previous gameStates.
     * @param alreadyMoved the list of gameStates already reached.
     * @return true if the gameState does not appear in the list, false if it does
     */
    public boolean isNotRedundant(ArrayList<GameState> alreadyMoved){
        return !alreadyMoved.contains(this);
    }


    /**
     * Equals method for GameState class.
     * @param object the object being compared to this GameState.
     * @return true if they have the same gameState variable, false if they don't.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof GameState){

            int[][] objGS = ((GameState) object).getGameState();
            int[][] thisGS = this.gameState;

            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(objGS[i][j] != thisGS[i][j]){
                        return false;
                    }
                }
            }
        }
        else{
            return false;
        }
        return true;
    }
}
