/* 
 * Aaryateja
 * 3/25/25
 * All the code
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    // detect whether these keys are pressed
    private boolean kleft, kright, kup, kdown, kw, ka, ks, kd;
    private double pl1X, pl1XVel, pl1Y, pl1YVel, pl2X, pl2XVel, pl2Y, pl2YVel;
    private double gameX, gameY, gameWidth, gameHeight;
    private int floor;
    private Timer gameTM, gameStartTM;
    private Image player1Image, player2Image, backgroundImage;

    private int player1Width;
    private int player1Height;
    private int player1Size;

    private int player2Width;
    private int player2Height;
    private int player2Size;

    public Game() {
        ImageIcon player1Image = new ImageIcon("src/player1.png");
        this.player1Image = player1Image.getImage();

        ImageIcon player2Image = new ImageIcon("src/player2.png");
        this.player2Image = player2Image.getImage();

        ImageIcon backgroundIcon = new ImageIcon("src/Background.png");
        this.backgroundImage = backgroundIcon.getImage();

        gameX = gameY = pl1XVel = pl1YVel = pl2XVel = pl2YVel = 0;

        gameWidth = 800;
        gameHeight = 600;

        floor = 500;

        pl1X = 125;
        pl2X = 675;
        pl1Y = pl2Y = floor;

        player1Width = this.player1Image.getWidth(null);
        player1Height = this.player1Image.getHeight(null);
        player1Size = 2;

        player2Width = this.player2Image.getWidth(null);
        player2Height = this.player2Image.getHeight(null);
        player2Size = 2;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

        g.drawImage(player1Image, (int) pl1X - (player1Width / player1Size / 2),
                (int) pl1Y - (player1Height / player1Size), player1Image.getWidth(null) / player1Size,
                player1Image.getHeight(null) / player1Size, null);

        g.drawImage(player2Image, (int) pl2X - (player2Width / player2Size / 2),
                (int) pl2Y - (player2Height / player2Size), player2Image.getWidth(null) / player2Size,
                player2Image.getHeight(null) / player2Size, null);
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
        if (e.getSource() == gameTM) {
            floor = 9 * getHeight() / 10;

            requestFocusInWindow();

            double jumpHeight = -20;
            double speed = 2;

            // All of player one's code(WASD)
            if (kw && pl1Y >= floor) {
                pl1YVel = jumpHeight;
            }
            if (ka) {
                pl1XVel -= speed;
            }
            if (kd) {
                pl1XVel += speed;
            }

            pl1XVel *= 0.9;

            pl1X += pl1XVel;
            pl1Y += pl1YVel;

            if (pl1Y < floor) {
                pl1YVel += 1;
            } else {
                pl1YVel = 0;
                pl1Y = floor;
            }

            if (pl1X + player1Width / 2 / player1Size >= getWidth()) {
                pl1X = getWidth() - player1Width / 2 / player1Size;
            } else if (pl1X - player1Width / 2 / player1Size <= 0) {
                pl1X = player1Width / 2 / player1Size;
            }

            // All of player two's code(↑←→↓)
            if (kup && pl2Y >= floor) {
                pl2YVel = jumpHeight;
            }
            if (kleft) {
                pl2XVel -= speed;
            }
            if (kright) {
                pl2XVel += speed;
            }

            pl2XVel *= 0.9;

            pl2X += pl2XVel;
            pl2Y += pl2YVel;

            System.out.println(pl2X);

            if (pl2Y < floor) {
                pl2YVel += 1;
            } else {
                pl2YVel = 0;
                pl2Y = floor;
            }

            if (pl2X + player2Width / 2 / player2Size >= getWidth()) {
                pl2X = getWidth() - player2Width / 2 / player2Size;
            } else if (pl2X - player2Width / 2 / player2Size <= 0) {
                pl2X = player2Width / 2 / player2Size;
            }

            repaint();
        } else if (e.getSource() == gameStartTM) {
            gameTM.start();
            gameStartTM.stop();
        }
    }

    public void setupWindow() {
        JFrame f = new JFrame("Game");

        gameTM = new Timer(10, this);
        gameStartTM = new Timer(500, this);

        gameStartTM.start();

        addKeyListener(this);
        f.add(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set up full-screen mode
        GraphicsEnvironment gfxEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gfxDev = gfxEnv.getDefaultScreenDevice();
        
        f.setUndecorated(true);
        gfxDev.setFullScreenWindow(f);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setupWindow();
    }
}
