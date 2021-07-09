import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Set;

/**
 * This [roject aims to be a helper in generating and solving
 * Sudoku puzzles
 *
 * Author: Alok Vaidya
 * Date: 08/07/21
 */
public class Sudoku {
    private Cell [][] puzzle;

    enum difficulty {EASY,MED,HARD}

    private Sudoku(){
        puzzle = new Cell [9][9];

        //for (int i=0;i<9;i++)
    }

    public static void main(String [] args){
        Sudoku sudoku = new Sudoku();

        /*
        Ask the user for Sudoku values
         */
        //displayMenu();

        // Static puzzle for testing
        int [] row1 = {0,0,4,0,0,5,0,0,0};
        int [] row2 = {0,1,0,0,0,4,0,5,0};
        int [] row3 = {6,5,0,0,0,3,0,0,7};
        int [] row4 = {0,0,0,7,0,0,5,4,0};
        int [] row5 = {0,0,7,5,0,1,2,0,0};
        int [] row6 = {0,3,6,0,0,9,0,0,0};
        int [] row7 = {8,0,0,3,0,0,0,7,6};
        int [] row8 = {0,4,0,6,0,0,0,9,0};
        int [] row9 = {0,0,0,9,0,0,3,0,0};

        sudoku.puzzleRow(0,row1);
        sudoku.puzzleRow(1,row2);
        sudoku.puzzleRow(2,row3);
        sudoku.puzzleRow(3,row4);
        sudoku.puzzleRow(4,row5);
        sudoku.puzzleRow(5,row6);
        sudoku.puzzleRow(6,row7);
        sudoku.puzzleRow(7,row8);
        sudoku.puzzleRow(8,row9);

        sudoku.displayPuzzle();

        /*
        we update the candidate values for each cell
        as per the preset puzzle entered
         */
        sudoku.updateCandidates();

        /*
        Check if any cells have only one candidate value.
        All cells having only a single candidate value are
        assigned that value.
         */
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if (sudoku.puzzle[i][j].getCandidates().size()==1)
                    sudoku.puzzle[i][j].setValue(
                            sudoku.puzzle[i][j].getCandidates().getFirst());
            }

        }


    }

    /*
    We fill an entire row at a time

    rowNo - indicates the row number
    row [] - contains the values either (1-9) or zero (0)
    if blank for all columns within that row.
     */
    private void puzzleRow(int rowNo, int [] row){
        for (int i=0;i<9;i++){
            Cell cell = new Cell(rowNo, i, row[i]);
            puzzle [rowNo][i] = cell; // assign the newly created cell to the puzzle
        }
    }

    /*
    we display the puzzle in its current state
     */
    private void displayPuzzle(){
        for (int i=0;i<9;i++){
            if (i%3==0)
                System.out.println("\n||===|===|===||===|===|===||===|===|===||");
            else
                System.out.println("\n||---|---|---||---|---|---||---|---|---||");
            for (int j=0;j<9;j++) {
                if (j == 0)
                    System.out.print("|| ");
                else if (j%3 == 0)
                    System.out.print(" || ");
                else
                    System.out.print(" | ");

                if (puzzle[i][j].getValue() == 0)
                    System.out.print(" ");
                else
                    System.out.print(puzzle[i][j].getValue());
            }
            System.out.print(" ||");
        }
        System.out.println("\n||===|===|===||===|===|===||===|===|===||");
    }

    private void updateCandidates(){
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                /*
                Update the possible candidates for cell
                  - make a list of all candidates to be removed
                  - update the cell by removing such candidates
                 */
                puzzle[i][j].removeCandidates(computeCellCandidates(i,j));

            }
        }
    }

    private ArrayDeque<Integer> computeCellCandidates(int row, int col){
        ArrayDeque<Integer> removeList = new ArrayDeque<>();
        for (int i=0;i<9;i++) {
            if (i!=col) {
                if (puzzle[row][i].getValue() != 0)
                    removeList.add(puzzle[row][i].getValue());
            }
        }

        /*
        add any values occurring in the corresponding column to remove list
         */
        for (int i=0;i<9;i++){
            if (i!=row){
                if(puzzle[i][col].getValue()!=0)
                    removeList.add(puzzle[i][col].getValue());
            }
        }

        /*
        Also remove any values occuring in the 3x3 box the cell falls in
         */
        /*
        for example in the box below, 4 & 6 cannot appear in any of the
        vacant cells. So we remove 4 & 6 from the set of possible candidates
        for each of vacant cells.
        ||===|===|===||
        || 4 |   |   ||
        ||---|---|---||
        ||   |   | 6 ||
        ||---|---|---||
        ||   |   |   ||
        ||===|===|===||
         */

        /* first we need to find the correct box for the cell */
        /* we divide the row and col numbers by 3 and shift mod
        cells back (in case of rows) or up (in case of cols)
        this provides us with the correct starting pos of row
        & col for the box the cell is in.
         */
        int start_row = row - row%3;
        int start_col = col - col%3;

        for (int i=start_row;i<(start_row+3);i++) {
            for (int j = start_col; j < (start_col + 3); j++) {
                if (i != row && j != col) { // explicitly avoiding current cell
                    if (puzzle[i][j].getValue() != 0) // 0 means cell is blank
                        removeList.add(puzzle[i][j].getValue());
                }
            }
        }

        return removeList;
    }

    /*
    Returns the no of empty cells in the puzzle
    If there are no empty cells it means the puzzle
    is done.
     */
    public int getNoOfEmptyCells(){
        int empty_cells = 0;
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                if (puzzle[i][j].getValue()==0)
                    empty_cells++;
            }
        }
        return empty_cells;
    }

    public void displayMenu(){
        System.out.println("1.Enter Puzzle");
        System.out.println("2.Solve Puzzle");
    }
}

/*
represents a cell from the puzzle
 */
class Cell{
    private int row;
    private int col;
    /*
    These are the possible candidates for this cell
    still in contention.
    We shall in every pass update the list of poss.
    candidates for every cell
     */
    private ArrayDeque<Integer> candidates;

    /*
    The final value for the cell. When only one possible candidate remains
    for a cell that becomes its value.
     */
    private int value;

    public int getRow(){
        return this.row;
    }

    public void setRow(int row){
        this.row= row;
    }

    public int getCol(){
        return this.col;
    }

    public void setCol(int col){
        this.col = col;
    }

    int getValue(){
        return this.value;
    }

    void setValue(int val){
        this.value = val;
    }

    ArrayDeque<Integer> getCandidates(){
        return this.candidates;
    }

    void setCandidates(ArrayDeque <Integer> cand){
        this.candidates = cand;
    }

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.value = 0;

        /*
        Since we are initializing this cell as blank, it should have
        all digits 1-9 as possible candidates
         */
        for (int i=1;i<=9;i++)
            candidates.add(i);
    }

    /*
    Constructor to be used when populating a puzzle
    Populating a puzzle requires entering a preset
    configuration of values in the puzzle. Some cells
    will have given values most will be blank.
    A blank cell will be indicated with a value of 0

    When a cell is non-empty, having a given value (1-9)
    we set it's candidate array to null to indicate that
    the cell has been finalized.
     */
    Cell(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
        if (value!=0){
            candidates = null;
        }
    }

    /*
    Removes all candidates for cell using the remove list.
     */
    void removeCandidates(ArrayDeque <Integer> removeList){
        this.candidates.removeAll(removeList);
    }
}
