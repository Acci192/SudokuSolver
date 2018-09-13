package Sudoku;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that contain a sudokuPanel
 * Creates a border around the panel and placing the sudoku in the middle
 */
public class GamePanel extends JPanel {
   public GamePanel(JPanel game) {
      super();
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
      this.setPreferredSize(new Dimension(MyDisplay.getDisplayWidth() / 4 * 3, MyDisplay.getDisplayHeight()));
      this.setBackground(new Color(0xA3A3A3));

      this.add(game, BorderLayout.CENTER);
   }
}
