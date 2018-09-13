package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * JPanel that displays the sudoku
 * The class uses a static Arraylist that contain each tile of the sudoku
 */
public class SudokuPanel extends JPanel {
   private static ArrayList<SudokuTile> tiles = new ArrayList<>();

   public SudokuPanel() {
      super();
      //Create each tile in the sudoku and place it in the container
      for (int row = 0; row < 9; row++) {
         for (int column = 0; column < 9; column++) {
            tiles.add(new SudokuTile(row, column));
         }
      }

      //Specify what layout to use
      this.setLayout(new GridLayout(9, 9));

      //Add each tile to the panel
      for (SudokuTile tile : tiles) {
         this.add(tile);
      }
   }

   //Create a sudoku with a String as input
   public SudokuPanel(String input) {
      super();
      char[] givenTiles = input.toCharArray();
      for (int row = 0; row < 9; row++) {
         for (int column = 0; column < 9; column++) {
            //Make it possible to represent a empty tile with any character
            char temp = givenTiles[row * 9 + column];
            switch (temp){
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
                  tiles.add(new SudokuTile(row, column, temp - '0'));
                  break;
               default:
                  tiles.add(new SudokuTile(row, column, 0));
                  break;
            }
         }
      }
      //Specify what layout to use
      this.setLayout(new GridLayout(9, 9));

      //Add each tile to the panel
      for (SudokuTile tile : tiles) {
         this.add(tile);
      }
   }

   public static void changeSudoku(int[] arr) {
      for(int i = 0; i < arr.length; ++i) {
         tiles.get(i).clearTile();
         tiles.get(i).setLockedValue(arr[i]);
      }
   }

   public static ArrayList<SudokuTile> getTiles() {
      return tiles;
   }

   //Check so the current sudoku can be solved
   public static boolean checkSolution() {
      ArrayList<Set<Integer>> rows = new ArrayList<>();
      ArrayList<Set<Integer>> columns = new ArrayList<>();
      ArrayList<Set<Integer>> boxes = new ArrayList<>();

      //Create an HashSet for each row/col/box
      for (int i = 0; i < 9; i++) {
         rows.add(new HashSet<>());
         columns.add(new HashSet<>());
         boxes.add(new HashSet<>());
      }
      //Check each tile
      for (SudokuTile tile : tiles) {
         boolean solvable = tile.getPossibilities(tiles).size() != 0;
         if (!solvable) {
            return false;

         }

         //Add each tile into it's row/col/box HashSet if the HashSet already contains the value, return false
         if(tile.getValue() != 0){
            if (!rows.get(tile.getRow()).add(tile.getValue()) ||
                    !columns.get(tile.getColumn()).add(tile.getValue()) ||
                    !boxes.get(tile.getBox()).add(tile.getValue())) {
               return false;
            }
         }
      }
      return true;
   }

   //Return the amount of tiles that are locked in
   public static int lockedInTiles() {
      int counter = 0;
      for (SudokuTile tile : tiles) {
         if (tile.isLocked()) {
            counter++;
         }
      }
      return counter;
   }

   public static SudokuTile getTile(int index) {
      if (index < tiles.size()) {
         return tiles.get(index);
      } else {
         return tiles.get(tiles.size() - 1);
      }
   }

   //Check if the sudoku is solved, Checks so there is no 0s and no duplicates in each row/col/box
   public static boolean isSolved() {
      ArrayList<Set<Integer>> rows = new ArrayList<>();
      ArrayList<Set<Integer>> columns = new ArrayList<>();
      ArrayList<Set<Integer>> boxes = new ArrayList<>();

      for (int i = 0; i < 9; i++) {
         rows.add(new HashSet<>());
         columns.add(new HashSet<>());
         boxes.add(new HashSet<>());
      }
      for (SudokuTile tile : tiles) {
         if (tile.getValue() == 0) {
            return false;
         } else {
            rows.get(tile.getRow()).add(tile.getValue());
            columns.get(tile.getColumn()).add(tile.getValue());
            boxes.get(tile.getBox()).add(tile.getValue());
         }
      }
      for (int i = 0; i < 9; i++) {
         if (rows.get(i).size() != 9) {
            return false;
         }
         if (columns.get(i).size() != 9) {
            return false;
         }
         if (boxes.get(i).size() != 9) {
            return false;
         }
      }
      return true;
   }
}
