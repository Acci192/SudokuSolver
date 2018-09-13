package Sudoku;

/**
 * Button that solves the sudoku when pressed
 */
public class SolveSudokuButton extends MenuButton {
   public SolveSudokuButton() {
      super("Solve");
      this.addActionListener(e -> {
         SudokuPanel.getTiles().get(0).solveCell(SudokuPanel.getTiles());
         for (SudokuTile tile : SudokuPanel.getTiles()) {
            tile.updateTile();
         }
      });
   }
}
