package Sudoku;

/**
 * Button that reset the sudoku
 * Each tile that is'nt currently locked in will be cleared
 */
public class ResetSudokuButton extends MenuButton {
   public ResetSudokuButton() {
      super("Reset");
      this.addActionListener(e -> {
         for (SudokuTile tile : SudokuPanel.getTiles()) {
            tile.resetTile();
         }
      });
   }
}
