package hanoi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The AI player for a Hanoi game. Used for both Versus and Demo game modes.
 */
public class AIPlayer{

    //==================== Demo specific =====================

    LinkedList<int[]> moves; //The moves the AI must do to win as fast as possible.

    //=================== Versus specific ====================

    ArrayList<ArrayList<GameState>> allStates; //Holds a list of 100 possible games.
    ArrayList<GameState> randMoves; //Picked randomly from allStates, represents an entire set of moves that can complete a game.
    LinkedList<int[]> readableRandMoves; //randMoves, converted into a format readable by HanoiMain.
    AITree<GameState> moveCreator; //A tree that holds the possible moves

    //==================================================

    /**
     * Constructor for AIPlayer
     */
    public AIPlayer() {
        moves = new LinkedList<>();
        allStates = new ArrayList<>();
        createMoves(3,0,2,1);
        createAllMoves();
    }

    /**
     * Creates the 'moves' variable. Finds best possible list of moves to complete a Hanoi puzzle for the demo.
     * @param n the number of disks to be moved.
     * @param frStack the stack you're taking a disk from.
     * @param toStack the stack you want the disk to be placed on.
     * @param spStack the third stack, used for 'storage'.
     */
    private void createMoves(int n, int frStack, int toStack, int spStack){
        int[] fromTo = new int[2];
        fromTo[0] = frStack;
        fromTo[1] = toStack;

        if(n ==1){
            moves.addLast(fromTo);
        }
        else{
            createMoves(n-1,frStack,spStack,toStack);
            moves.addLast(fromTo);
            createMoves(n-1,spStack,toStack,frStack);
        }
    }

    /**
     * Gets the next move for a demo to do. Removes the move from the list of remaining moves.
     * @return the move for the demo to do.
     */
    public int[] getMove(){
        int[] move = moves.getFirst();
        moves.removeFirst();
        return move;
    }

    /**
     * Gets the next move for a versus AI to do. Removes the move from the list of remaining moves.
     * @return the move for the versus AI to do.
     */
    public int[] getRandMove(){
        int[] move = readableRandMoves.getFirst();
        readableRandMoves.removeFirst();
        return move;
    }

    /**
     * @return a boolean that tells the computer whether there are any moves left (true or false)
     */
    public boolean movesLeft(){
        if(moves.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * Assigns the allStates variable. Finds up to 100 possible ways to complete a hanoi game in under 20 moves.
     */
    private void createAllMoves(){

        int[][] rootData = new int[3][3];
        rootData[0][0] = 1; //The size of the top piece of the first stack (0 = none, 1 = small, 2 = med, 3 = big)
        rootData[0][1] = 2; //The size of the second-to-top piece of first stack
        rootData[0][2] = 3; //The size of the third-to-top piece of first stack

        for(int i = 1; i <= 2; i++){
            for(int j = 0; j <= 2; j++){
                rootData[i][j] = 0; //In starting position, the other two stacks have no disks.
            }
        }

        GameState rootState = new GameState(rootData);

        this.moveCreator = new AITree<>(rootState);
        ArrayList<GameState> currMoves = new ArrayList<>();
        currMoves.add(rootState);

        createAllMovesRecurse(0, currMoves, moveCreator.getRoot()); //Calls the recursive method.

        pickRandMoves(); //Picks randomly from the available ways to complete the game.

    }

    /**
     * Recursive method, fills allStates variable and moveCreator variable with possible moves and ways to
     * complete a hanoi puzzle.
     * @param n the amount of moves taken so far.
     * @param lastMoves A list of all the last moves done.
     * @param currParent The parent that the next move will be a child of in the tree of moves.
     */
    private void createAllMovesRecurse(int n, ArrayList<GameState> lastMoves, AITree.Node<GameState> currParent){

        int lastIndex = lastMoves.size() - 1;
        GameState lastMove = lastMoves.get(lastIndex);
        GameState currMove;

        if(n >= 20 || allStates.size() > 100){ //Tells the recursive function to stop if a current game method has exceeded 20 moves or if 100 solutions have already been found.
            return;
        }

        if(lastMove.isWin()){ //Checks to see if a solution was found.
            allStates.add(lastMoves);
            return;
        }

        for(int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if(j!=i) {
                    if (lastMove.canMove(i, j)) {

                        int[][] currMoveData = clone(lastMove.getGameState());
                        currMove = new GameState(currMoveData);
                        currMove.moveDisk(i, j);

                        if(currMove.isNotRedundant(lastMoves)) { //Does not allow AI to make redundant moves (e.g. looping endlessly by moving a peg back and forth)
                            ArrayList<GameState> currLastMoves = new ArrayList<>(lastMoves);
                            currLastMoves.add(currMove);
                            AITree.Node<GameState> newParent = currParent.addChild(currMove);
                            createAllMovesRecurse(n + 1, currLastMoves, newParent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Clones a 2D array so it may be altered without changing the original.
     * @param toClone the 2D array that is going to be duplicated.
     * @return the duplicated 2D array.
     */
    private int[][] clone(int[][] toClone){
        int[][] newArray = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                newArray[i][j] = toClone[i][j];
            }
        }
        return newArray;
    }

    /**
     * Chooses a random list of moves from the global allStates variable, assigns it to the global randMoves.
     */
    private void pickRandMoves(){
        int max = allStates.size();
        int index = ThreadLocalRandom.current().nextInt(0, max);
        randMoves = allStates.get(index);
        makeRandMovesReadable();
    }

    /**
     * Converts the randMoves variable into one readable by HanoiMain.
     * (HanoiMain reads a move as an int array with a length of 2, the
     * first int being the 'from' stack and the second being the 'to' stack.)
     */
    private void makeRandMovesReadable(){
        readableRandMoves = new LinkedList<>();
        int from = 0;
        int to = 0;
        for(int i = 0; i < randMoves.size() - 1 ; i++){
            int j = i + 1;
            int[][] before = randMoves.get(i).getGameState(); //Before the move took place
            int[][] after = randMoves.get(j).getGameState(); //After the move took place

            for(int k = 0; k < 3; k++){
                if (before[k][0] > after[k][0] && after[k][0]!=0) { //If after now has a smaller disk than before
                    to = k;
                }
                else if (before[k][0] < after[k][0] && before[k][0]!=0) { //If after lost a smaller disk.
                    from = k;
                }
                if (before[k][0] == 0 && after[k][0] != 0){ //If after has a disk when before had none.
                    to = k;
                }
                else if (before[k][0] != 0 && after[k][0] == 0){ //If after does not have any disks when before did.
                    from = k;
                }
            }

            int[] readableMove = new int[2];
            readableMove[0] = from;
            readableMove[1] = to;
            readableRandMoves.add(readableMove);
        }
    }


}
