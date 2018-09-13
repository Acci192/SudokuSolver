package Sudoku;

import javax.swing.*;

/**
 * Button that check if there is a possible solution for the current sudoku
 * Will create a popup window depending on the situation
 *    Less than 17 locked in tiles - Warning
 *    Wrong input - Error
 *    Correct input - Good job window
 */
public class CheckSolutionButton extends MenuButton {
   public CheckSolutionButton() {
      super("Check");
      this.addActionListener(e -> {
         if(SudokuPanel.isSolved()){
            JOptionPane.showMessageDialog(getRootPane(), "GREAT JOB! You solved the puzzle!",
                    "GREAT JOB!", JOptionPane.PLAIN_MESSAGE);
         }else if(SudokuPanel.lockedInTiles() < 17) {
            JOptionPane.showMessageDialog(getRootPane(), "Need at least 17 locked in tiles!",
                    "Invalid Input!", JOptionPane.WARNING_MESSAGE);
         } else if(SudokuPanel.checkSolution()){
            JOptionPane.showMessageDialog(getRootPane(), "So far so good. Keep up the good work!",
                    "Good Job!", JOptionPane.PLAIN_MESSAGE);
         } else {
            JOptionPane.showMessageDialog(getRootPane(), "Something is wrong, try something else!",
                    "Wrong Input!", JOptionPane.ERROR_MESSAGE);
         }
      });
   }
}
