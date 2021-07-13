import java.util.*;

/**
 * This [roject aims to be a helper in generating and solving
 * Sudoku puzzles
 *
 * A Sudoku puzzle has 9 boxes arranged as a 3x3 matrix.
 * Each box is itself a 3x3 matrix of 9 cells.
 *
 * We number the boxes and cells thus:
 * Numbers inside puzzle refer to each box
 * and those on the perimeter of the puzzle refer to
 * cells.
 *
 * Ex.: the 1st Box at the top-left corner is
 * Box #0, the box on its right is Box #1 and so on.
 *
 * Similarly the cell in the top-left corner is
 * written as [0,0] the one to its immediate right
 * is [0,1] and so on.
 *
 *      0   1   2    3   4   5    6   7   8
 *   ||===|===|===||===|===|===||===|===|===||
 * 0 ||   |   |   ||   |   |   ||   |   |   ||
 *   ||---|---|---||---|---|---||---|---|---||
 * 1 ||   | 0 |   ||   | 1 |   ||   | 2 |   ||
 *   ||---|---|---||---|---|---||---|---|---||
 * 2 ||   |   |   ||   |   |   ||   |   |   ||
 *   ||===|===|===||===|===|===||===|===|===||
 * 3 ||   |   |   ||   |   |   ||   |   |   ||
 *   ||---|---|---||---|---|---||---|---|---||
 * 4 ||   | 3 |   ||   | 4 |   ||   | 5 |   ||
 *   ||---|---|---||---|---|---||---|---|---||
 * 5 ||   |   |   ||   |   |   ||   |   |   ||
 *   ||===|===|===||===|===|===||===|===|===||
 * 6 ||   |   |   ||   |   |   ||   |   |   ||
 *   ||---|---|---||---|---|---||---|---|---||
 * 7 ||   | 6 |   ||   | 7 |   ||   | 8 |   ||
 *   ||---|---|---||---|---|---||---|---|---||
 * 8 ||   |   |   ||   |   |   ||   |   |   ||
 *   ||===|===|===||===|===|===||===|===|===||
 *
 * Author: Alok Vaidya
 * Date: 08/07/21
 */
public class Sudoku {
    private Cell [][] puzzle;

    enum difficulty {EASY,MED,HARD}

    Sudoku(){
        puzzle = new Cell [9][9];

        //for (int i=0;i<9;i++)
    }

    public static void main(String [] args){
        Sudoku sudoku = new Sudoku();
        HashMap <Integer,Cell> numbersToAssign;
        /*
        Ask the user for Sudoku values
         */
        //displayMenu();

        // Static puzzle for testing 19th June 2021 "The Hindu"
        /*int [] row1 = {0,0,4,0,0,5,0,0,0};
        int [] row2 = {0,1,0,0,0,4,0,5,0};
        int [] row3 = {6,5,0,0,0,3,0,0,7};
        int [] row4 = {0,0,0,7,0,0,5,4,0};
        int [] row5 = {0,0,7,5,0,1,2,0,0};
        int [] row6 = {0,3,6,0,0,9,0,0,0};
        int [] row7 = {8,0,0,3,0,0,0,7,6};
        int [] row8 = {0,4,0,6,0,0,0,9,0};
        int [] row9 = {0,0,0,9,0,0,3,0,0};
        */

        // Static puzzle for testing 20th June 2021 "The Hindu"
        int [] row1 = {1,0,0,0,0,0,0,0,0};
        int [] row2 = {0,0,0,0,0,1,6,2,0};
        int [] row3 = {9,0,4,0,2,5,0,0,1};
        int [] row4 = {0,7,0,2,0,0,0,6,0};
        int [] row5 = {2,0,0,7,0,8,0,0,4};
        int [] row6 = {0,4,0,0,0,9,0,7,0};
        int [] row7 = {7,0,0,4,1,0,9,0,8};
        int [] row8 = {0,2,1,6,0,0,0,0,0};
        int [] row9 = {0,0,0,0,0,0,0,0,6};
        

        sudoku.puzzleRow(0,row1);
        sudoku.puzzleRow(1,row2);
        sudoku.puzzleRow(2,row3);
        sudoku.puzzleRow(3,row4);
        sudoku.puzzleRow(4,row5);
        sudoku.puzzleRow(5,row6);
        sudoku.puzzleRow(6,row7);
        sudoku.puzzleRow(7,row8);
        sudoku.puzzleRow(8,row9);


        /*
        start solving the puzzle pass by pass
         */
        int pass = 0;
        int cells_filled_in_pass;
        do {
            cells_filled_in_pass = 0;
            System.out.println("Pass: "+ ++pass);
            System.out.println("No. of Empty Cells: "+ sudoku.getNoOfEmptyCells());
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
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (sudoku.puzzle[i][j].getCandidates()!=null) {
                        if (sudoku.puzzle[i][j].getCandidates().size() == 1){
                            sudoku.puzzle[i][j].setValue(
                                    sudoku.puzzle[i][j].getCandidates().remove());
                            sudoku.puzzle[i][j].getCandidates().clear();
                            cells_filled_in_pass++;
                        }
                    }
                }
            }
            
            sudoku.updateCandidates();

            for (int i=0;i<9;i++){
                numbersToAssign = sudoku.getBoxNumbersToAssign(i);
                for (Integer number : numbersToAssign.keySet()) {
                    Cell cell = numbersToAssign.get(number);
                    //sudoku.puzzle[cell.getRow()][cell.getCol()].setValue(number);
                    cell.setValue(number);
                    cell.getCandidates().clear();
                    cells_filled_in_pass++;
                }
            }
            /*

             */
            System.out.println("Cells Filled: "+ cells_filled_in_pass);
        }  while (sudoku.getNoOfEmptyCells()!=0 && cells_filled_in_pass>=1);

        System.out.println("============= Final Solution ============");
        sudoku.displayPuzzle();
    }

    /*
    We fill an entire row at a time

    rowNo - indicates the row number
    row [] - contains the values either (1-9) or zero (0)
    if blank for all columns within that row.
     */
    public void puzzleRow(int rowNo, int [] row){
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

    public void updateCandidates(){
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                /*
                Update the possible candidates for cell
                  - make a list of all candidates to be removed
                  - update the cell by removing such candidates
                 */
                if (puzzle[i][j].getValue()==0)
                    puzzle[i][j].removeCandidates
                            (computeCellCandidates(i,j));

            }
        }
    }

    public ArrayDeque<Integer> computeCellCandidates(int row, int col){
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
                if (! (i==row && j==col)) { // explicitly avoiding current cell
                    if (puzzle[i][j].getValue() != 0) // 0 means cell is blank
                        removeList.add(puzzle[i][j].getValue());
                }
            }
        }

        return removeList;
    }

    /*
       Given a Cell, returns the box number
       it falls in.

       Please refer class Sudoku for Box and Cell numbering
     */
    public int getBoxNo(Cell cell){
        return (((cell.getRow()/3)*3) + cell.getCol()/3);
    }
    
    /*
      Given a box number this method returns the number candidates
      and the corresponding cells to assign the numbers to as a map
     */
    public HashMap<Integer,Cell> getBoxNumbersToAssign(int box){
        int start_row=(box/3)*3;
        int start_col=(box%3)*3;
        return getUniqueCellCandidateInBox(start_row,start_col);
    }

    /**
     * This method checks whether for any number(1-9) there exists
     * only one possible cell as a candidate.
     *
     * The cell chosen may still have multiple candidates, but since
     * some number has only that cell as an option, it should be
     * assigned to that cell.
     * @param start_row - starting row position for the box
     * @param start_col - starting col position for the box
     * @return a map of numbers with the corresponding Cell to which
     * they must be assigned
     *
     * null - if there is an error
     */
    public HashMap<Integer,Cell> getUniqueCellCandidateInBox(int start_row, int start_col){
        HashMap<Integer,Cell> numbersToAssign = new HashMap<Integer,Cell>();
        HashSet<Integer> filterList = new HashSet<>();
        ArrayDeque<Integer> candidates;
        if (start_row%3!=0 || start_col%3!=0)
            /*
            positions represented by start_row and start_col
            are not the start positions for a box.
             */
            return null;
        else{
            for (int i=start_row;i<start_row+3;i++) {
                for (int j = start_col; j < start_col + 3; j++) {
                    candidates = puzzle[i][j].getCandidates();
                    if (candidates!=null) {
                        for (Integer candidate : candidates) {
                            if (numbersToAssign.containsKey(candidate)) {
                            /*
                            candidate number is already present in the
                            numbersToAssign map, which means it has multiple
                            cells as possible spots. Put this number in a filter
                            list that we will use to finally filter the original
                            list.

                            If we do not take this measure than a number appearing
                            as candidate in 3 or 5 or 7 cells will end up in the
                            original list as it will be added on the first encounter
                            removed on the second and again added on the third.
                            Ending up in the orignal list, even though it shouldn't
                            as it has multiple cell spots as options.
                             */
                                if (!filterList.contains(candidate))
                                    filterList.add(candidate);
                            } else {
                            /*
                            This number is not present in the ToAssign map
                            we will insert it into the map assuming this
                            is the only cell it can be assigned to.
                            */
                                numbersToAssign.put(candidate, puzzle[i][j]);
                            }
                        }
                    }
                }
            }

            /*
            filter original list with the filterList
             */
            Iterator<Integer> iter = filterList.iterator();
            while (iter.hasNext())
                numbersToAssign.remove(iter.next());
        }
        /*
        the map shall now only contain numbers with only a single cell
        as the option.
         */
        return numbersToAssign;
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
        this.candidates.clear();
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
        candidates = new ArrayDeque<>();
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
        if (value==0){
           /*
            Since we are initializing this cell as blank, it should have
            all digits 1-9 as possible candidates
           */
           candidates = new ArrayDeque<Integer>();
           for (int i=1;i<=9;i++)
                candidates.add(i);
        }
    }

    /*
    Removes all candidates for cell using the remove list.
     */
    void removeCandidates(ArrayDeque <Integer> removeList){
        this.candidates.removeAll(removeList);
    }
}
