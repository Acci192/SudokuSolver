package Sudoku;

/**
 * Button that clear every tile in the sudoku
 */
public class ClearSudokuButton extends MenuButton {
   public ClearSudokuButton() {
      super("Clear");
      this.addActionListener(e -> {
         for (SudokuTile tile : SudokuPanel.getTiles()) {
            tile.clearTile();
         }
      });
   }
}
