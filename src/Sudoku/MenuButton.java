package Sudoku;

import javax.swing.*;
import java.awt.*;

/**
 * Base class for buttons in the application
 * Following a specific design
 */
public class MenuButton extends JButton {
   public MenuButton(String buttonText) {
      super(buttonText);
      this.setAlignmentX(Component.CENTER_ALIGNMENT);
      this.setMaximumSize(new Dimension(150, 40));
      this.setBackground(new Color(0x035184));
      this.setForeground(Color.WHITE);

      this.setBorder(BorderFactory.createLineBorder(new Color(0x004077), 2));
   }
}
