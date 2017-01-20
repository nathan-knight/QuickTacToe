package codes.knight.quicktactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class QuickTacToe extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public static QuickTacToe quickTacToe;
	
	private int[][] board;
	private JButton[] buttons;
	
	private boolean turnX = true;
	
	public static void main(String args[]) {
		quickTacToe = new QuickTacToe();
	}
	
	public QuickTacToe() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setTitle("QuickTacToe by Nathan Knight");
		
		board = new int[3][3];
		
		GridLayout layout = new GridLayout();
		layout.setColumns(3);
		layout.setRows(3);
		this.setLayout(layout);
		
		buttons = new JButton[9];
		for(int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			final int buttonNumber = i;
			buttons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(board[buttonNumber % 3][buttonNumber / 3] != 0) { //Check if the space has been taken
						return;
					} else {
						board[buttonNumber % 3][buttonNumber / 3] = turnX ? 1 : 2; //Set the value in the array for easy win checking
						buttons[buttonNumber].setText(turnX ? "X" : "O"); //Set the button text for user
						turnX = !turnX;
						checkWin();
					}
				}
			});
			this.add(buttons[i]);
		}
		
		this.setVisible(true);
	}
	
	public void checkWin() {
		int columnWin = checkColumns();
		int rowWin = checkRows();
		int diagWin = checkLine(0, 0, 1, 1, board[0][0]) ? board[0][0] : -1;
		//Easier to do a special case for other diagonal that would require a negative slope
		int otherDiagWin = (board[1][1] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) ? board[0][2] : -1;
		
		if(columnWin != -1) {
			win(board[columnWin][0]);
			return;
		}
		if(rowWin != -1) {
			win(board[0][rowWin]);
			return;
		}
		if(diagWin != -1) {
			win(board[1][1]);
			return;
		}
		if(otherDiagWin != -1) {
			win(otherDiagWin);
			return;
		}
		
		//Check for tie
		int taken = 0;
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				if(board[x][y] != 0) taken++;
			}
		}
		if(taken == 9) {
			JOptionPane.showMessageDialog(this, "The game ended in a tie!", "Tie!", JOptionPane.WARNING_MESSAGE);
			newGame();
		}
	}
	
	public int checkColumns() {
		for(int i = 0; i < 3; i++) { //Check for vertical victory conditions
			if(checkLine(i, 0, 0, 1, board[i][0])) {
				return i;
			}
		}
		return -1;
	}
	
	public int checkRows() {
		for(int i = 0; i < 3; i++) { //Check for horizontal victory conditions
			if(checkLine(0, i, 1, 0, board[0][i])) {
				return i;
			}
		}
		return -1;
	}
	
	//Checks if every space in a given line is the same (if there is a win)
	public boolean checkLine(int x, int y, int dX, int dY, int checkValue) {
		if(checkValue == 0) return false; //0 cannot win
		for(int xx = x; xx < 3; xx += dX) {
			for(int yy = y; yy < 3; yy += dY) {
				if(board[xx][yy] != checkValue) return false;
				if(dY == 0) break;
			}
			if(dX == 0) break;
		}
		return true;
	}
	
	public void win(int winner) {
		JOptionPane.showMessageDialog(this, (winner == 1 ? "X" : "O") + " has won", "Winner!", JOptionPane.INFORMATION_MESSAGE);
		newGame();
	}
	
	public void newGame() {
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 3; y++) {
				board[x][y] = 0;
			}
		}
		for(int i = 0; i < 9; i++) {
			buttons[i].setText("");
		}
		turnX = true;
	}

}
