import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * Created by alokvaidya on 11/07/21.
 */
public class SudokuTest {
    /*@Test
    public void evaluatesGetUniqueCellCandidateInBox(){
        Sudoku sudoku = new Sudoku();

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

        HashMap<Integer,Cell> numbersToAssign;
        Cell candidate;
        numbersToAssign = sudoku.getUniqueCellCandidateInBox(3,0);
        for (Integer i: numbersToAssign.keySet()){
            System.out.println(numbersToAssign.get(i).getRow());
            System.out.println(numbersToAssign.get(i).getCol());
        }
        candidate=numbersToAssign.get(5);
        if (candidate!=null) {
            System.out.println(candidate.getRow());
            System.out.println(candidate.getCol());
        }
        assertEquals((candidate.getRow()==5 && candidate.getCol()==0),true);
    }   */

    @Test
    public void evaluatesComputeCellCandidates(){
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

        ArrayDeque<Integer> removeList = sudoku.computeCellCandidates(0,4);
        System.out.println("Cell Candidates:");
        for (Integer i: removeList)
            System.out.println(i);
        assertEquals((removeList.contains(3)&&removeList.contains(4)&&removeList.contains(5)),true);
    }

    @Test
    public void evaluatesGetBoxNumbersToAssign(){
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

        sudoku.updateCandidates();

        HashMap<Integer,Cell> numbersToAssign = sudoku.getBoxNumbersToAssign(6);
        for (Integer i: numbersToAssign.keySet()){
            System.out.println(i+" in cell["+numbersToAssign.get(i).getRow()+
                    "]["+numbersToAssign.get(i).getCol()+"]");
        }
        assertEquals((numbersToAssign.containsKey(6)),true);
    }
}
