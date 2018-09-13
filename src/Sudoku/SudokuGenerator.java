package Sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SudokuGenerator {
   //private ArrayList<SudokuTile> sudoku = new ArrayList<>();
   private static String test = "742859316839617425165234987958361742613742598427598631571483269386925174294176853";
   private String test1 = "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
  /* public SudokuGenerator() {
      int[] givenTiles = stringToSudoku(test);
      //twoNums(givenTiles);
      for (int row = 0; row < 9; row++) {
         for (int column = 0; column < 9; column++) {
            //Make it possible to represent a empty tile with any character
            sudoku.add(new SudokuTile(row, column, givenTiles[row * 9 + column]));
         }
      }
      this.digHoles(20, 30);
      //System.out.println(SudokuTile.checkUniqueness(sudoku));
   }*/

   public static int[] generateSudoku(int minHoles, int maxHoles) {
      ArrayList<SudokuTile> tiles = new ArrayList<>();
      int[] givenTiles = stringToSudoku(test);
      Random rnd = new Random();
      for(int i = 0; i < 25; ++i) {
         int shuffle = rnd.nextInt(4);
         switch (shuffle){
            case 0:
               vertical(givenTiles);
               break;
            case 1:
               horizontal(givenTiles);
               break;
            case 2:
               twoNums(givenTiles, rnd);
               break;
            case 3:
               rowBoxDown(givenTiles, rnd);
               break;
         }
      }
      for (int row = 0; row < 9; row++) {
         for (int column = 0; column < 9; column++) {
            //Make it possible to represent a empty tile with any character
            tiles.add(new SudokuTile(row, column, givenTiles[row * 9 + column]));
         }
      }

      digHoles(tiles, minHoles, maxHoles, rnd);
      return sudokuToArr(tiles);
   }

   public static int[] sudokuToArr(ArrayList<SudokuTile> sudoku) {
      int[] arr = new int[81];
      for(int i = 0; i < sudoku.size(); ++i){
         arr[i] = sudoku.get(i).getValue();
      }
      return arr;
   }

   private static int[] stringToSudoku(String s){
      if(s.length() != 81) throw new IllegalArgumentException();
      int[] sudokuValues = new int[81];
      for(int i = 0; i < s.length(); ++i){
         char temp = s.charAt(i);
         switch (temp) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
               sudokuValues[i] = temp - '0';
               break;
            default:
               sudokuValues[i] = 0;
               break;
         }
      }
      return sudokuValues;
   }

   //Swap rows 0 <-> 8, 1 <-> 7, 2 <-> 6, 3 <-> 5
   private static void vertical(int[] tiles){
      for(int i = 0; i < 9/2; ++i) {
         int[] row = new int[9];
         System.arraycopy(tiles, i*9, row, 0, 9);
         System.arraycopy(tiles, (81-9) - i*9, tiles, i*9, 9);
         System.arraycopy(row, 0, tiles, (81-9) - i*9, 9);
      }
   }

   //Swap columns 0 <-> 8, 1 <-> 7, 2 <-> 6, 3 <-> 5
   private static void horizontal(int[] tiles) {
      for(int i = 0; i < 9/2; ++i) {
         for(int j = 0; j < 9; ++j) {
            int temp = tiles[i + j*9];
            tiles[i + j*9] = tiles[(9-1-i) + j*9];
            tiles[(9-1-i) + j*9] = temp;
          }
      }
   }

   //swap two numbers with each other on each row
   private static void twoNums(int[] tiles, Random rnd) {
      int x = rnd.nextInt(9) + 1;
      int y = rnd.nextInt(9) + 1;
      while (y == x){
         y = rnd.nextInt(9) + 1;
      }
      for(int i = 0; i < 9; ++i) {
         for(int j = i*9; j < i*9 + 9; ++j){
            if(tiles[j] == x){
               tiles[j] = y;
            } else if(tiles[j] == y) {
               tiles[j] = x;
            }
         }
      }
   }

   //Rolls a random rows of three (ex). row 0 -> row 2, row 1 -> row 0, row 2 -> row 1
   private static void rowBoxDown(int[] tiles, Random rnd) {
      int box = rnd.nextInt(3) * 3;
      int[] row1 = new int[9];
      int[] row2 = new int[9];
      int[] row3 = new int[9];
      System.arraycopy(tiles, box*9, row1, 0, 9);
      System.arraycopy(tiles, (box + 1)*9, row2, 0, 9);
      System.arraycopy(tiles, (box + 2)*9, row3, 0, 9);

      System.arraycopy(row1, 0, tiles, (box + 1)*9, 9);
      System.arraycopy(row2, 0, tiles, (box + 2)*9, 9);
      System.arraycopy(row3, 0, tiles, (box)*9, 9);
   }

   public static void digHoles(ArrayList<SudokuTile> sudoku, int minHoles, int maxHoles, Random rnd) {
      int[] holes = new int[81];
      for(int i = 0; i < 81; ++i) {
         holes[i] = i;
      }
      int holesToDig = rnd.nextInt(maxHoles - minHoles) + minHoles;
      //System.out.println(holesToDig);
      shuffleArray(holes, rnd);
      int amountOfHoles = 0;
      for(int i = 0; i < 81; ++i) {
         amountOfHoles++;
         int indexToRemove = holes[i];
         SudokuTile tile = sudoku.get(indexToRemove);
         int tempValue = tile.getValue();
         sudoku.get(indexToRemove).clearTile();
         if(!SudokuTile.checkUniqueness(sudoku)){
            sudoku.get(indexToRemove).setValue(tempValue);
            sudoku.get(indexToRemove).lockInTile();
            amountOfHoles--;
         }
         for(SudokuTile temp : sudoku){
            temp.resetTile();
         }
         if(amountOfHoles >= holesToDig){
            break;
         }
      }
   }

   private static void shuffleArray(int[] arr, Random rnd) {
      for(int i = 0; i < arr.length; ++i) {
         swap(arr, i, rnd.nextInt(i + 1));
      }
   }
   private static void swap(int[] arr, int a, int b) {
      int temp = arr[a];
      arr[a] = arr[b];
      arr[b] = temp;
   }
}
