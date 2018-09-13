package Sudoku;

import javax.swing.*;
import java.awt.*;

/**
 * Main window
 */
public class MyDisplay extends JFrame {

   private static final int WIDTH = 800;
   private static final int HEIGHT = 600;
   private static final String TITLE = "Sudoku Solver";
   public MyDisplay() {
      //Initialize the window
      super(TITLE);
      setSize(WIDTH, HEIGHT);
      setResizable(false);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      //Place the window in the middle of the screen
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation(dim.width/2 - WIDTH/2, dim.height/2 - HEIGHT/2);

      //Place the buttons on the left side of the window
      this.add(new ButtonPanel(), BorderLayout.WEST);

      //Place the sudoku on the right side of the window
      this.add(new GamePanel(new SudokuPanel("700050000039000020005204987000301002010742500000090630570003260386920100200176850"))
              , BorderLayout.EAST);

      this.setVisible(true);
   }

   public static int getDisplayWidth() {
      return WIDTH;
   }

   public static int getDisplayHeight() {
      return HEIGHT;
   }

   public static String getDisplayTitle() {
      return TITLE;
   }
}
