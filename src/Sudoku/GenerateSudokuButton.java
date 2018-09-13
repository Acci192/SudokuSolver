package Sudoku;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GenerateSudokuButton extends MenuButton {
   private JPopupMenu popUp;
   public GenerateSudokuButton() {
      super("Generate Sudoku");
      popUp = new JPopupMenu("Generate");
      popUp.add(new difficultyMenu("Very Easy", e -> {
         SudokuPanel.changeSudoku(SudokuGenerator.generateSudoku(25, 31));
      }));
      popUp.add(new difficultyMenu("Easy", e -> {
         SudokuPanel.changeSudoku(SudokuGenerator.generateSudoku(32, 45));
      }));
      popUp.add(new difficultyMenu("Medium", e -> {
         SudokuPanel.changeSudoku(SudokuGenerator.generateSudoku(46, 49));
      }));
      this.addActionListener(e -> {
         popUp.show(this, this.getX(), this.getHeight());
      });
   }

   private class difficultyMenu extends JMenuItem {
      public difficultyMenu(String text, ActionListener al) {
         super(text);
         this.addActionListener(al);
      }
   }
}
