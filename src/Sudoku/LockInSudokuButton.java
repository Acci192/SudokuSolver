package Sudoku;

/**
 * Button that lock in all the tiles that are currently filled
 * Locked in squares doesn't change unless the sudoku is cleared
 */
public class LockInSudokuButton extends MenuButton {
   public LockInSudokuButton() {
      super("Lock in");
      this.addActionListener(e -> {
         for (SudokuTile tile : SudokuPanel.getTiles()) {
            tile.lockInTile();
         }
      });
   }
}
