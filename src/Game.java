/* 
 * Arnav Jaiswal & Aaryateja
 * 3/25/25
 * All the code
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
	private boolean kleft, kright, kup, kdown, kw, ka, kf, kd;
	private double pl1X, pl1XVel, pl1Y, pl1YVel, pl2X, pl2XVel, pl2Y, pl2YVel;
	private boolean pl1Block, pl2Block;

	private double gameWidth, gameHeight;
	private int lava;
	private Timer gameTM, gameStartTM;
	private Image player1Image, player2Image, backgroundImage, coinImage;

	private int player1Width;
	private int player1Height;
	private int player1Size;

	private int player2Width;
	private int player2Height;
	private int player2Size;

	private Block[][] blocks;
	private ArrayList<Coin> coins;
	private int scorePlayer1 = 0;
	private int scorePlayer2 = 0;

	private final double PL1_SPAWN_X = 125;
	private final double PL1_SPAWN_Y = 750;
	private final double PL2_SPAWN_X = 675;
	private final double PL2_SPAWN_Y = 750;

	private Picture picture;

	private Random random;
	private final int MAX_COINS = 5;

	public Game() {
		picture = new Picture();

		blocks = new Block[60][40];
		coins = new ArrayList<>();

		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				blocks[i][j] = new Block(i, j);
			}
		}

		blocks[57][32].floor();
		blocks[56][32].floor();
		blocks[55][32].floor();
		blocks[54][32].floor();
		blocks[53][32].floor();
		blocks[52][31].floor();
		blocks[51][31].floor();
		blocks[50][31].floor();
		blocks[49][32].floor();
		blocks[48][32].floor();
		blocks[47][32].floor();
		blocks[46][32].floor();
		blocks[45][32].floor();
		blocks[44][32].floor();
		blocks[43][31].floor();
		blocks[42][31].floor();
		blocks[41][31].floor();

		blocks[2][32].floor();
		blocks[3][32].floor();
		blocks[4][32].floor();
		blocks[5][32].floor();
		blocks[6][32].floor();
		blocks[7][31].floor();
		blocks[8][31].floor();
		blocks[9][31].floor();
		blocks[10][32].floor();
		blocks[11][32].floor();
		blocks[12][32].floor();
		blocks[13][32].floor();
		blocks[14][32].floor();
		blocks[15][32].floor();
		blocks[16][31].floor();
		blocks[17][31].floor();
		blocks[18][31].floor();

		random = new Random();
		coins = new ArrayList<>();

		ImageIcon player1Image = new ImageIcon("src/player1.png");
		this.player1Image = player1Image.getImage();

		ImageIcon player2Image = new ImageIcon("src/player2.png");
		this.player2Image = player2Image.getImage();

		ImageIcon backgroundIcon = new ImageIcon("src/GameBackground.png");
		this.backgroundImage = backgroundIcon.getImage();

		ImageIcon coinIcon = new ImageIcon("src/coin.png");
		this.coinImage = coinIcon.getImage();

		pl1XVel = pl1YVel = pl2XVel = pl2YVel = 0;

		lava = 840;

		pl1X = 200;
		pl2X = 1236;
		pl1Y = pl2Y = 740;
		pl1Block = pl2Block = true;

		player1Width = this.player1Image.getWidth(null);
		player1Height = this.player1Image.getHeight(null);
		player1Size = 3;

		player2Width = this.player2Image.getWidth(null);
		player2Height = this.player2Image.getHeight(null);
		player2Size = 3;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null), null);

		for (Coin coin : coins) {
			if (!coin.isCollected()) {
				g.drawImage(coinImage, coin.getX(), coin.getY(), 
						coinImage.getWidth(null)/2, 
						coinImage.getHeight(null)/2, null);
			}
		}

		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				Image image = blocks[i][j].getImage();
				if (image != null) {
					int row = blocks[i][j].getRow();
					int column = blocks[i][j].getColumn();

					g.drawImage(image, row*24, column*24, (row*24)+24, (column*24)+24, 0, 0, 8, 8, null);

					int crack = blocks[i][j].getCrack();
					switch (crack) {
					case 1:
					case 2:
					case 3:
					case 4:
						g.drawImage(picture.getImage("crack4"), row*24, column*24, (row*24)+24, (column*24)+24, 0, 0, 8, 8, null);
						break;
					case 5:
					case 6:
					case 7:
					case 8:
						g.drawImage(picture.getImage("crack3"), row*24, column*24, (row*24)+24, (column*24)+24, 0, 0, 8, 8, null);
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						g.drawImage(picture.getImage("crack2"), row*24, column*24, (row*24)+24, (column*24)+24, 0, 0, 8, 8, null);
						break;
					case 13:
					case 14:
					case 15:
					case 16:
						g.drawImage(picture.getImage("crack1"), row*24, column*24, (row*24)+24, (column*24)+24, 0, 0, 8, 8, null);
						break;
					}
				}
			}
		}

		g.drawImage(player1Image, (int) pl1X - (player1Width / player1Size / 2),
				(int) pl1Y - (player1Height / player1Size), player1Image.getWidth(null) / player1Size,
				player1Image.getHeight(null) / player1Size, null);

		g.drawImage(player2Image, (int) pl2X - (player2Width / player2Size / 2),
				(int) pl2Y - (player2Height / player2Size), player2Image.getWidth(null) / player2Size,
				player2Image.getHeight(null) / player2Size, null);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("Player 1: " + scorePlayer1, 50, 50);
		g.drawString("Player 2: " + scorePlayer2, getWidth() - 200, 50);
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
		} else if (key == KeyEvent.VK_F) {
			kf = true;
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
			pl2Block = true;
		} else if (key == KeyEvent.VK_W) {
			kw = false;
		} else if (key == KeyEvent.VK_A) {
			ka = false;
		} else if (key == KeyEvent.VK_F) {
			kf = false;
			pl1Block = true;
		} else if (key == KeyEvent.VK_D) {
			kd = false;
		}
	}

	private boolean isBlockAt(double x, double y) {
		int col = (int) Math.floor(x / 24);
		int row = (int) Math.floor(y / 24);
		return blocks[col][row].isBlock();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gameTM) {
			requestFocusInWindow();

			double jumpHeight = -15;
			double speed = 0.7;
			double friction = 0.9;

			boolean isPl1Floor = isBlockAt(pl1X, pl1Y) || isBlockAt(pl1X - (player1Width / player1Size / 2), pl1Y) || isBlockAt(pl1X + (player1Width / player1Size / 2), pl1Y);

			if (isPl1Floor) {
				pl1YVel = 0;
			}

			if (kw && (pl1Y >= lava || isPl1Floor)) {
				pl1YVel = jumpHeight;
			}
			if (ka) {
				pl1XVel -= speed;
			}
			if (kd) {
				pl1XVel += speed;
			}

			pl1XVel *= friction;

			for (int i = 0; i < (int) Math.abs(pl1XVel); i++) {

				boolean isPl1Left = isBlockAt(pl1X - (player1Width / player1Size / 2), pl1Y - 1) || isBlockAt(pl1X - (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size)) || isBlockAt(pl1X - (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size / 2));
				boolean isPl1Right = isBlockAt(pl1X + (player1Width / player1Size / 2), pl1Y - 1) || isBlockAt(pl1X + (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size)) || isBlockAt(pl1X + (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size / 2));

				if (!isPl1Left && pl1XVel < 0) {
					pl1X--;
				} else if (!isPl1Right && pl1XVel > 0) {
					pl1X++;
				}
			}

			for (int i = 0; i < Math.abs(pl1YVel); i++) {
				boolean isPl1Ceil = isBlockAt(pl1X, pl1Y - (player1Height / player1Size)) || isBlockAt(pl1X - (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size)) || isBlockAt(pl1X + (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size));
				if (!isPl1Ceil) {
					pl1Y += pl1YVel / Math.abs(pl1YVel);
				} else {
					pl1Y += 1;
					pl1YVel = 0;
				}
			}

			if (pl1Y < lava) {
				pl1YVel += 1;
			} else {
				pl1YVel = 0;
				pl1Y = lava;
			}

			if (pl1X + player1Width / 2 / player1Size >= getWidth() - 48) {
				pl1X = (getWidth() - 48) - player1Width / 2 / player1Size;
			} else if (pl1X - player1Width / 2 / player1Size <= 48) {
				pl1X = player1Width / 2 / player1Size + 48;
			}

			if (kf && pl1Y <= lava && pl1Block) {
				int row = (int) Math.floor(pl1X / 24);
				int column = (int) Math.ceil(pl1Y / 24);
				blocks[row][column].setImage("stone");
				pl1Block = false;
			}

			if (isPl1Floor) {
				if (!kw) {
					pl1YVel = 0;
				}
				pl1Y = Math.floorDiv((int)pl1Y, 24) * 24;
			}
			
			// 2
			boolean isPl2Floor = isBlockAt(pl2X, pl2Y) || isBlockAt(pl2X - (player2Width / player2Size / 2), pl2Y) || isBlockAt(pl2X + (player2Width / player2Size / 2), pl2Y);

			if (isPl2Floor) {
				pl2YVel = 0;
			}

			if (kw && (pl2Y >= lava || isPl2Floor)) {
				pl2YVel = jumpHeight;
			}
			if (ka) {
				pl2XVel -= speed;
			}
			if (kd) {
				pl2XVel += speed;
			}

			pl2XVel *= friction;

			for (int i = 0; i < (int) Math.abs(pl2XVel); i++) {

				boolean isPl2Left = isBlockAt(pl2X - (player2Width / player2Size / 2), pl2Y - 1) || isBlockAt(pl2X - (player2Width / player2Size / 2), pl2Y - (player2Height / player2Size)) || isBlockAt(pl2X - (player2Width / player2Size / 2), pl2Y - (player2Height / player2Size / 2));
				boolean isPl2Right = isBlockAt(pl2X + (player2Width / player2Size / 2), pl2Y - 1) || isBlockAt(pl2X + (player2Width / player2Size / 2), pl2Y - (player2Height / player2Size)) || isBlockAt(pl2X + (player2Width / player2Size / 2), pl2Y - (player2Height / player2Size / 2));
// finish rpelacing with 2
				if (!isPl1Left && pl1XVel < 0) {
					pl1X--;
				} else if (!isPl1Right && pl1XVel > 0) {
					pl1X++;
				}
			}

			for (int i = 0; i < Math.abs(pl1YVel); i++) {
				boolean isPl1Ceil = isBlockAt(pl1X, pl1Y - (player1Height / player1Size)) || isBlockAt(pl1X - (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size)) || isBlockAt(pl1X + (player1Width / player1Size / 2), pl1Y - (player1Height / player1Size));
				if (!isPl1Ceil) {
					pl1Y += pl1YVel / Math.abs(pl1YVel);
				} else {
					pl1Y += 1;
					pl1YVel = 0;
				}
			}

			if (pl1Y < lava) {
				pl1YVel += 1;
			} else {
				pl1YVel = 0;
				pl1Y = lava;
			}

			if (pl1X + player1Width / 2 / player1Size >= getWidth() - 48) {
				pl1X = (getWidth() - 48) - player1Width / 2 / player1Size;
			} else if (pl1X - player1Width / 2 / player1Size <= 48) {
				pl1X = player1Width / 2 / player1Size + 48;
			}

			if (kf && pl1Y <= lava && pl1Block) {
				int row = (int) Math.floor(pl1X / 24);
				int column = (int) Math.ceil(pl1Y / 24);
				blocks[row][column].setImage("stone");
				pl1Block = false;
			}

			if (isPl1Floor) {
				if (!kw) {
					pl1YVel = 0;
				}
				pl1Y = Math.floorDiv((int)pl1Y, 24) * 24;
			}


			if (pl1Y >= lava) {
				respawnPlayer1();
			}
			if (pl2Y >= lava) {
				respawnPlayer2();
			}

			checkCoinCollection();
		} else if (e.getSource() == gameStartTM) {
			gameTM.start();
			gameStartTM.stop();
		}

		repaint();
	}

	public void setupWindow() {
		JFrame f = new JFrame("Game");

		gameTM = new Timer(10, this);
		gameStartTM = new Timer(500, this);

		gameStartTM.start();

		addKeyListener(this);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GraphicsEnvironment gfxEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gfxDev = gfxEnv.getDefaultScreenDevice();

		f.setUndecorated(true);
		gfxDev.setFullScreenWindow(f);
		f.setVisible(true);

		gameWidth = getWidth();
		gameHeight = getHeight();
		spawnCoins();
	}

	private void spawnCoins() {
		coins.clear();
		while (coins.size() < MAX_COINS) {
			int x = random.nextInt(getWidth() - 100) + 50;
			int y = random.nextInt(lava - 300) + 100;

			int blockX = x / 24;
			int blockY = y / 24;
			if (!blocks[blockX][blockY].isBlock()) {
				coins.add(new Coin(x, y));
			}
		}
	}

	private void checkCoinCollection() {
		Rectangle player1Rect = new Rectangle((int)pl1X - (player1Width/player1Size/2), 
				(int)pl1Y - (player1Height/player1Size),
				player1Width/player1Size, 
				player1Height/player1Size);

		Rectangle player2Rect = new Rectangle((int)pl2X - (player2Width/player2Size/2), 
				(int)pl2Y - (player2Height/player2Size),
				player2Width/player2Size, 
				player2Height/player2Size);

		for (int i = coins.size() - 1; i >= 0; i--) {
			Coin coin = coins.get(i);
			if (!coin.isCollected()) {
				Rectangle coinRect = new Rectangle(coin.getX(), coin.getY(),
						coinImage.getWidth(null)/2,
						coinImage.getHeight(null)/2);

				if (player1Rect.intersects(coinRect)) {
					coin.collect();
					scorePlayer1++;
					coins.remove(i);
					spawnCoins();
				} else if (player2Rect.intersects(coinRect)) {
					coin.collect();
					scorePlayer2++;
					coins.remove(i);
					spawnCoins();
				}
			}
		}
	}

	private void respawnPlayer1() {
		pl1X = PL1_SPAWN_X;
		pl1Y = PL1_SPAWN_Y;
		pl1XVel = 0;
		pl1YVel = 0;
		scorePlayer1 = 0;
		coins.clear();
		spawnCoins();
	}

	private void respawnPlayer2() {
		pl2X = PL2_SPAWN_X;
		pl2Y = PL2_SPAWN_Y;
		pl2XVel = 0;
		pl2YVel = 0;
		scorePlayer2 = 0;
		coins.clear();
		spawnCoins();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setupWindow();
	}
}
