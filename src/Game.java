/* Arnav Jaiswal
 * Mar 14
 * the entire game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Game extends JPanel implements ActionListener, KeyListener{
	// detect whether these keys are pressed
	private boolean kleft,kright, lright, kup, kdown, kw, ka, ks, kd;
	private double pl1X, pl1XVel, pl1Y, pl1YVel, pl2X, pl2XVel, pl2Y, pl2YVel;
	private double gameX, gameY, gameWidth, gameHeight;
	private int floor;
	private Timer gameTM;

	public Game() {
		gameX = gameY = pl1XVel = pl1YVel = pl2XVel = pl2YVel = 0;
		
		gameWidth = 800;
		gameHeight = 600;
		
		floor = 500;
		

		pl1X = 125;
		pl2X = 675;
		pl1Y = pl2Y = floor;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLUE);
		g.fillRect((int) pl1X - 25, (int) pl1Y-100, 50, 100);
		g.setColor(Color.RED);
		g.fillRect((int) pl2X - 25, (int) pl2Y-100, 50, 100);
		
	}	

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {		
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			kleft = true;
		} else if (key == KeyEvent.VK_RIGHT) {
			kright = true;
		} else if (key == KeyEvent.VK_UP) {
			kup = true;
		} else if (key == KeyEvent.VK_DOWN) {
			kdown = true;
		} else if (key == KeyEvent.VK_W) {
			kw = true;
		} else if (key == KeyEvent.VK_A) {
			ka = true;
		} else if (key == KeyEvent.VK_S) {
			ks = true;
		} else if (key == KeyEvent.VK_D) {
			kd = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			kleft = false;
		} else if (key == KeyEvent.VK_RIGHT) {
			kright = false;
		} else if (key == KeyEvent.VK_UP) {
			kup = false;
		} else if (key == KeyEvent.VK_DOWN) {
			kdown = false;
		} else if (key == KeyEvent.VK_W) {
			kw = false;
		} else if (key == KeyEvent.VK_A) {
			ka = false;
		} else if (key == KeyEvent.VK_S) {
			ks = false;
		} else if (key == KeyEvent.VK_D) {
			kd = false;
		}

	}

	public void actionPerformed(ActionEvent e) {

		requestFocusInWindow();
		
		int jumpHeight = -20;

		// All of player one's code(WASD)
		if (kw && pl1Y >= floor) {
			pl1YVel = jumpHeight;
		} if (ka) {
			pl1XVel -= 1;
		} if (kd) {
			pl1XVel += 1;
		}
		
		if (pl1Y < floor) {
			pl1YVel += 1;
		} else {
			pl1Y = floor;
		}
		pl1XVel *= 0.9;
		
		pl1X += pl1XVel;
		pl1Y += pl1YVel;

		// All of player two's code(↑←→↓)
		if (kup && pl2Y >= floor) {
			pl2YVel = jumpHeight;
		} if (kleft) {
			pl2XVel -= 1;
		} if (kright) {
			pl2XVel += 1;
		}
		
		if (pl2Y < floor) {
			pl2YVel += 1;
		} else {
			pl2Y = floor;
		}
		pl2XVel *= 0.9;
		
		pl2X += pl2XVel;
		pl2Y += pl2YVel;

		repaint();
	}

	public void setupWindow() {
		JFrame f = new JFrame("Game");

		gameTM = new Timer(5, this);
		gameTM.start();
		
		addKeyListener(this);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		f.setLocationRelativeTo(null);
		f.add(this);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setupWindow();
	}
}
