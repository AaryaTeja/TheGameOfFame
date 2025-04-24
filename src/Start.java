// Start.java
// by Aarya and Arnav
// Splash screen for our mining game - press any key to continue

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start {
	private JFrame window;
	private JPanel mainPanel;
	private boolean showInstructions = false;

	public Start() {
		setupWindow();
		createPanels();
		addListeners();
	}

	private void setupWindow() {
		window = new JFrame("Mining Game - Start Screen");
		window.setSize(1440, 900);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null); // center window

		mainPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawScreen(g);
			}
		};

		window.add(mainPanel);
	}

	private void createPanels() {
		// Just using one panel that we redraw
	}

	private void addListeners() {
		mainPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				advanceScreen();
			}
		});
		mainPanel.setFocusable(true);
	}

	private void drawScreen(Graphics g) {
		// Draw background
		g.setColor(new Color(50, 50, 70));
		g.fillRect(0, 0, window.getWidth(), window.getHeight());

		if (!showInstructions) {
			// Draw splash screen
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial", Font.BOLD, 72));
			String title = "The Game Of Fame";
			int titleWidth = g.getFontMetrics().stringWidth(title);
			g.drawString(title, (window.getWidth()-titleWidth)/2, 200);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 24));
			String pressKey = "Press any key to continue";
			int pressWidth = g.getFontMetrics().stringWidth(pressKey);
			g.drawString(pressKey, (window.getWidth()-pressWidth)/2, 400);

			// Our names
			g.setFont(new Font("Arial", Font.ITALIC, 18));
			g.drawString("By: [Your Name] & [Partner's Name]", 20, window.getHeight()-30);
		} else {
			// Draw instructions
			g.setColor(new Color(240, 240, 240));
			g.fillRect(100, 50, window.getWidth()-200, window.getHeight()-100);

			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString("HOW TO PLAY", 150, 100);

			g.setFont(new Font("Arial", Font.PLAIN, 20));
			String[] instructions = {
					"Player 1 (Left Side):",
					"W = Jump   A = Left   D = Right   F = Place Block",
					"",
					"Player 2 (Right Side):",
					"↑ = Jump   ← = Left   → = Right   ↓ = Place Block",
					"",
					"Game Rules:",
					"- Collect coins for points",
					"- Place blocks to climb",
					"- Don't fall in lava!",
					"- Buy more blocks at shops",
					"",
					"Press any key to begin!"
			};

			int y = 150;
			for (String line : instructions) {
				g.drawString(line, 150, y);
				y += 30;
			}
		}
	}

	private void advanceScreen() {
		if (!showInstructions) {
			showInstructions = true;
		} else {
			startGame();
		}
		mainPanel.repaint();
	}

	private void startGame() {
		window.dispose();
		Game game = new Game();
		game.setupWindow();
		game.startGame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Start startScreen = new Start();
			startScreen.window.setVisible(true);
		});
	}
}