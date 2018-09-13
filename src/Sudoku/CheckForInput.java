package Sudoku;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CheckForInput implements DocumentListener {
	private SudokuTile tile;
	public CheckForInput(SudokuTile tile) {
		this.tile = tile;
	}

	public void insertUpdate(DocumentEvent e) {
		tile.setValue(Integer.parseInt(tile.getText()));
	}

	public void removeUpdate(DocumentEvent e) {
		try{
			tile.setValue(Integer.parseInt(tile.getText()));
		} catch (NumberFormatException nfe) {
			tile.setValue(0);
		}
	}

	public void changedUpdate(DocumentEvent e) {
		tile.setValue(Integer.parseInt(tile.getText()));
	}

}
