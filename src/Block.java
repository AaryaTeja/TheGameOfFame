/* Arnav Jaiswal
 * Mar 28
 * Class that stores the block properties
 */

import java.awt.Image;
import java.awt.event.*;
import javax.swing.Timer;

public class Block implements ActionListener{
	int row, column, crack;
	String image;
	Timer life;


	public Block(int row, int column) {
		this.row = row;
		this.column = column;
		crack = 0;
		image = "air";
		life = new Timer(1000, this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == life) {
			if (crack > 0) {
				crack--;
				System.out.println(crack);
			} else {
				image = "air";
				life.stop();
			}
		}
	}
	
	public int getCrack() {
		return crack;
	}
	
	public Image getImage() {
		return (new Picture()).getImage(image);
	}
	
	public void setImage(String image) {
		this.image = image;
		if (!image.equals("air")) {
			crack = 20;
			life.start();
			
		}
	}
	
	public void floor() {
		image = "grass";
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
