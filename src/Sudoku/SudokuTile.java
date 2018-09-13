package Sudoku;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Tile object
 * Each tile gets a row, column at creation.
 * The box is calculated using the row and column
 */
public class SudokuTile extends JTextField {
   private static int tileID = 0;
   private Font defaultFont = new Font("Times New Roman", Font.PLAIN, 40);
   private Font lockedInFont = new Font("Times New Roman", Font.BOLD, 40);
   private final int row;
   private final int column;
   private final int box;
   private final int tileNumber;
   private int value;
   private boolean safeValue;
   private static int count = 0;

   public SudokuTile(int row, int column) {
      super();
      if(tileID > 80) {
         tileID = 0;
      }
      this.row = row;
      this.column = column;
      this.box = (row / 3) * 3 + (column % 9) / 3;
      this.tileNumber = tileID++;
      this.value = 0;
      this.safeValue = false;

      //Initialize the layout of the JTextField
      this.setMaximumSize(new Dimension(25, 25));
      this.setFont(defaultFont);
      this.setHorizontalAlignment(JTextField.CENTER);
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      //Every second box will be light grey
      if (box % 2 == 0) {
         this.setBackground(new Color(0xE5E5E5));
      }

      //Add a document filter and Document listener to prevent user to type in invalid input
      //Also prevent user to copy paste invalid input into each tile
      ((PlainDocument) this.getDocument()).setDocumentFilter(new SudokuFilter());
      this.getDocument().addDocumentListener(new CheckForInput(this));
   }

   public SudokuTile(int row, int column, int value) {
      super();
      if(tileID > 80) {
         tileID = 0;
      }
      this.row = row;
      this.column = column;
      this.box = (row / 3) * 3 + (column % 9) / 3;
      this.tileNumber = tileID++;
      this.value = value;
      this.safeValue = false;
      //Initialize the layout of the JTextField
      this.setMaximumSize(new Dimension(25, 25));
      this.setFont(defaultFont);
      this.setHorizontalAlignment(JTextField.CENTER);
      this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
      //Every second box will be light grey
      if (box % 2 == 0) {
         this.setBackground(new Color(0xE5E5E5));
      }

      //Add a document filter and Document listener to prevent user to type in invalid input
      //Also prevent user to copy paste invalid input into each tile
      ((PlainDocument) this.getDocument()).setDocumentFilter(new SudokuFilter());
      this.getDocument().addDocumentListener(new CheckForInput(this));
      if(value != 0) {
         lockInTile();
      }
      this.updateTile();
   }

   public void setValue(int value) {
      this.value = value;
   }

   public void setLockedValue(int value) {
      this.value = value;
      lockInTile();
      updateTile();
   }
   //Function to lock in the tile, this will prevent the value to be changed, also makes the text bold
   public void lockInTile() {
      if (value != 0) {
         safeValue = true;
         this.setFont(lockedInFont);
         this.setFocusable(false);
      }
   }

   //Clear the tile if it's not locked
   public void resetTile() {
      if (!safeValue) {
         value = 0;
         this.setText("");
      }
   }

   //Clear the tile will unlock any locked tile and clear them aswell
   public void clearTile() {
      value = 0;
      safeValue = false;
      this.setFont(defaultFont);
      this.setFocusable(true);
      this.setText("");
   }

   //Update the textField to show the value of the tile
   public void updateTile() {
      this.setText(Integer.toString(value));
   }

   //Recursive function that will return true if reached the end successfully;
   public boolean solveCell(ArrayList<SudokuTile> sudoku) {
      HashSet<Integer> possibilities = getPossibilities(sudoku);
      //Check if tile is locked
      if (safeValue) {
         //If last tile it returns true else return the result from the next cells solveFunction
         return tileNumber == 80 || sudoku.get(tileNumber + 1).solveCell(sudoku);
      } else if (tileNumber == 80) {
         //if it's the last tile insert the value from possiblities and return true;
         for (Integer i : possibilities) {
            value = i;
         }
         return true;
      } else {
         //for each possibility it check if the sudoku can be solved
         for (Integer i : possibilities) {
            value = i;
            //If reached the end this will be true
            if (sudoku.get(tileNumber + 1).solveCell(sudoku)) {
               return true;
            }
         }
         value = 0;
      }
      //Otherwise return false;
      return false;
   }
   /*TODO:
   Change so that a sudokupanel is not static
   Also change so solve and solveniquenss uses an arraylist instead of static version of sudoku.
    */
   public static boolean checkUniqueness(ArrayList<SudokuTile> sudoku) {
      count = 0;
      sudoku.get(0).solveUnique(sudoku);
      //System.out.println(count);
      return count == 1;
   }

   private boolean solveUnique(ArrayList<SudokuTile> sudoku) {
      HashSet<Integer> possibilities = getPossibilities(sudoku);
      //System.out.println(tileNumber);
      //System.out.println(safeValue);
      //Check if tile is locked
      if (safeValue) {
         //If last tile it returns true else return the result from the next cells solveFunction
         if(count < 2 && tileNumber == 80){
            count++;
            return false;
         } else if(count >= 2 && tileNumber == 80) {
            //System.out.println("Test");
            return true;
         } else{
            return sudoku.get(tileNumber + 1).solveUnique(sudoku);
         }
      } else if (tileNumber == 80) {
         //if it's the last tile insert the value from possiblities and return true;
         for (Integer i : possibilities) {
            value = i;
         }
         if(count < 2) {
            count++;
            return false;
         } else {
            return true;
         }
      } else {
         //for each possibility it check if the sudoku can be solved
         for (Integer i : possibilities) {
            value = i;
            //If reached the end this will be true
            if (sudoku.get(tileNumber + 1).solveUnique(sudoku)) {
               return true;
            }
         }
         value = 0;
      }
      //Otherwise return false;
      return false;
   }

   //Function that returns every possibility for a tile as a HashSet
   public HashSet<Integer> getPossibilities(Collection<SudokuTile> tiles) {
      HashSet<Integer> possibilities = new HashSet<>();
      for (int i = 1; i <= 9; i++) {
         possibilities.add(i);
      }
      //Loop through each tile in the sudoku, if the this is on same row/column/box as the tile,
      //remove the value of tile from possibilities
      for (SudokuTile tile : tiles) {
         if (tile.row == this.row || tile.column == this.column || tile.box == this.box) {
            possibilities.remove(tile.value);
         }
      }
      return possibilities;
   }

   public int getRow() {
      return row;
   }

   public int getColumn() {
      return column;
   }

   public int getBox() {
      return box;
   }

   public int getValue() {
      return value;
   }

   public boolean isLocked() {
      return safeValue;
   }
}
