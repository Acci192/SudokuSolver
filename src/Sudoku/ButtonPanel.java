package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * JPanel that contains all the buttons
 */
public class ButtonPanel extends JPanel {
   private JLabel title = new JLabel(MyDisplay.getDisplayTitle());

   public ButtonPanel() {
      //Initialize the panel
      super();
      this.setPreferredSize(new Dimension(MyDisplay.getDisplayWidth() / 4, MyDisplay.getDisplayHeight()));
      this.setBackground(new Color(0x007AA7));
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      ArrayList<MenuButton> buttons = new ArrayList<>();
      //Adding all buttons to the Arraylist
      buttons.add(new GenerateSudokuButton());
      buttons.add(new ResetSudokuButton());
      buttons.add(new ClearSudokuButton());
      buttons.add(new LockInSudokuButton());
      buttons.add(new CheckSolutionButton());
      buttons.add(new SolveSudokuButton());

      //Center the title within the panel
      title.setAlignmentX(Component.CENTER_ALIGNMENT);

      //Offset from top to title
      this.add(Box.createRigidArea(new Dimension(0, 50)));
      this.add(title);
      //Offset from title to buttons
      this.add(Box.createRigidArea(new Dimension(0, 40)));
      //Adding each button with offset 20 from each other
      for (MenuButton button : buttons) {
         this.add(button);
         this.add(Box.createRigidArea(new Dimension(0, 20)));
      }
   }
}
