/* Arnav Jaiswal & Aaryateja Addala
 * all the code
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
    private boolean kleft, kright, kup, kslash, kw, ka, kf, kd;
    private double pl1X, pl1XVel, pl1Y, pl1YVel, pl2X, pl2XVel, pl2Y, pl2YVel;
    private boolean pl1Block, pl2Block;

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
    private int player1Coins;
    private int player1Blocks;
    private int player2Coins;
    private int player2Blocks;

    private final double PL1_SPAWN_X = 125;
    private final double PL1_SPAWN_Y = 750;
    private final double PL2_SPAWN_X = 1315;
    private final double PL2_SPAWN_Y = 750;

    private Picture picture;

    private Random random;
    private final int MAX_COINS;

    private int shop1x1, shop1y1, shop1x2, shop1y2;
    private int shop2x1, shop2y1, shop2x2, shop2y2;

    private boolean pl1InQuiz, pl2InQuiz;

    private int q1n1, q1n2, q1ans, q1alt1, q1alt2, q1correctChoice;
    private int q2n1, q2n2, q2ans, q2alt1, q2alt2, q2correctChoice;

    public Game() {
        picture = new Picture();

        blocks = new Block[60][40];
        coins = new ArrayList<>();

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block(i, j);
            }
        }

        int[][] floor = {
                {59, 0}, {58, 0}, {57, 32}, {56, 32}, {55, 32}, {54, 32}, {53, 32}, {52, 31}, {51, 31}, {50, 31}, {49, 32}, {48, 32}, {47, 32}, {46, 32}, {45, 32}, {44, 32}, {43, 31}, {42, 31}, {41, 31}
        };

        for (int[] coords : floor) {
            int x = coords[0];
            int y = coords[1];

            for (int i = y; i < blocks[x].length; i++) {
                blocks[x][i].floor();
                blocks[blocks.length - 1 - x][i].floor();
            }
        }

        random = new Random();
        coins = new ArrayList<>();
        MAX_COINS = 5;

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

        pl1X = PL1_SPAWN_X;
        pl2X = PL2_SPAWN_X;
        pl1Y = PL1_SPAWN_Y;
        pl2Y = PL2_SPAWN_Y;
        pl1Block = pl2Block = true;

        player1Width = this.player1Image.getWidth(null);
        player1Height = this.player1Image.getHeight(null);
        player1Size = 3;
        player1Coins = 0;
        player1Blocks = 10;

        player2Width = this.player2Image.getWidth(null);
        player2Height = this.player2Image.getHeight(null);
        player2Size = 3;
        player2Coins = 0;
        player2Blocks = 10;

        shop1x1 = 11;
        shop1y1 = 29;
        shop1x2 = 13;
        shop1y2 = 31;

        shop2x1 = 46;
        shop2y1 = 29;
        shop2x2 = 48;
        shop2y2 = 31;

        pl1InQuiz = pl2InQuiz = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(null), backgroundImage.getHeight(null), null);

        for (Coin coin : coins) {
            if (coin.isNotCollected()) {
                g.drawImage(coinImage, coin.getX(), coin.getY(),
                        coinImage.getWidth(null) / 2,
                        coinImage.getHeight(null) / 2, null);
            }
        }

        for (Block[] block : blocks) {
            for (Block value : block) {
                Image image = value.getImage();
                if (image != null) {
                    int row = value.getRow();
                    int column = value.getColumn();

                    g.drawImage(image, row * 24, column * 24, (row * 24) + 24, (column * 24) + 24, 0, 0, 8, 8, null);

                    int crack = value.getCrack();
                    switch (crack) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            g.drawImage(picture.getImage("crack4"), row * 24, column * 24, (row * 24) + 24, (column * 24) + 24, 0, 0, 8, 8, null);
                            break;
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            g.drawImage(picture.getImage("crack3"), row * 24, column * 24, (row * 24) + 24, (column * 24) + 24, 0, 0, 8, 8, null);
                            break;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                            g.drawImage(picture.getImage("crack2"), row * 24, column * 24, (row * 24) + 24, (column * 24) + 24, 0, 0, 8, 8, null);
                            break;
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                            g.drawImage(picture.getImage("crack1"), row * 24, column * 24, (row * 24) + 24, (column * 24) + 24, 0, 0, 8, 8, null);
                            break;
                    }
                }
            }
        }

        g.drawImage(player1Image,
                (int) pl1X - (player1Image.getWidth(null) / (2 * player1Size)),
                (int) pl1Y - (player1Image.getHeight(null) / player1Size),
                player1Image.getWidth(null) / player1Size,
                player1Image.getHeight(null) / player1Size, null);

        g.drawImage(player2Image,
                (int) pl2X - (player2Image.getWidth(null) / (2 * player2Size)),
                (int) pl2Y - (player2Image.getHeight(null) / player2Size),
                player2Image.getWidth(null) / player2Size,
                player2Image.getHeight(null) / player2Size, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Coins: " + player1Coins, 50, 50);
        g.drawString("Blocks: " + player1Blocks, 50, 100);
        g.drawString("Coins: " + player2Coins, getWidth() - 200, 50);
        g.drawString("Blocks: " + player2Blocks, getWidth() - 200, 100);

        if (pl1InQuiz && player1Coins > 0) {
            generateQuestion(g, 1, q1n1, q1n2, q1ans, q1alt1, q1alt2, q1correctChoice);
        }
        if (pl2InQuiz && player2Coins > 0) {
            generateQuestion(g, 2, q2n1, q2n2, q2ans, q2alt1, q2alt2, q2correctChoice);
        }


    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) kleft = true;
        else if (key == KeyEvent.VK_RIGHT) kright = true;
        else if (key == KeyEvent.VK_UP) kup = true;
        else if (key == KeyEvent.VK_SLASH) kslash = true;
        else if (key == KeyEvent.VK_W) kw = true;
        else if (key == KeyEvent.VK_A) ka = true;
        else if (key == KeyEvent.VK_F) kf = true;
        else if (key == KeyEvent.VK_D) kd = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) kleft = false;
        else if (key == KeyEvent.VK_RIGHT) kright = false;
        else if (key == KeyEvent.VK_UP) kup = false;
        else if (key == KeyEvent.VK_SLASH) {
            kslash = false;
            pl2Block = true;
        } else if (key == KeyEvent.VK_W) kw = false;
        else if (key == KeyEvent.VK_A) ka = false;
        else if (key == KeyEvent.VK_F) {
            kf = false;
            pl1Block = true;
        } else if (key == KeyEvent.VK_D) kd = false;
    }

    private boolean isBlockAt(double x, double y) {
        int col = (int) Math.floor(x / 24);
        int row = (int) Math.floor(y / 24);
        if (col < 0 || col >= blocks.length || row < 0 || row >= blocks[0].length) return false;
        return blocks[col][row].isBlock();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTM) {
            requestFocusInWindow();

            double jumpHeight = -15;
            double speed = 0.5;
            double friction = 0.93;

            // Player 1 (WADF)

            if (!pl1InQuiz) {
                pl1InQuiz = isPlayerInShop(1);

                if (pl1InQuiz) {
                    randomizeQuestion(1);
                }

                boolean isPl1Floor = isBlockAt(pl1X, pl1Y) ||
                        isBlockAt(pl1X - ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y) ||
                        isBlockAt(pl1X + ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y);

                if (isPl1Floor) pl1YVel = 0;

                if (kw && (pl1Y >= lava || isPl1Floor)) pl1YVel = jumpHeight;
                if (ka) pl1XVel -= speed;
                if (kd) pl1XVel += speed;

                pl1XVel *= friction;

                for (int i = 0; i < (int) Math.abs(pl1XVel); i++) {
                    boolean isPl1Left = isBlockAt(pl1X - ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - 1) ||
                            isBlockAt(pl1X - ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - ((double) player1Image.getHeight(null) / player1Size)) ||
                            isBlockAt(pl1X - ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - ((double) player1Image.getHeight(null) / player1Size / 2));
                    boolean isPl1Right = isBlockAt(pl1X + ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - 1) ||
                            isBlockAt(pl1X + ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - ((double) player1Image.getHeight(null) / player1Size)) ||
                            isBlockAt(pl1X + ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - ((double) player1Image.getHeight(null) / player1Size / 2));

                    if (!isPl1Left && pl1XVel < 0) pl1X--;
                    else if (!isPl1Right && pl1XVel > 0) pl1X++;
                }

                for (int i = 0; i < Math.abs(pl1YVel); i++) {
                    boolean isPl1Ceil = isBlockAt(pl1X, pl1Y - ((double) player1Image.getHeight(null) / player1Size)) ||
                            isBlockAt(pl1X - ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - ((double) player1Image.getHeight(null) / player1Size)) ||
                            isBlockAt(pl1X + ((double) player1Image.getWidth(null) / (2 * player1Size)), pl1Y - ((double) player1Image.getHeight(null) / player1Size));
                    if (!isPl1Ceil) pl1Y += pl1YVel / Math.abs(pl1YVel);
                    else {
                        pl1Y += 1;
                        pl1YVel = 0;
                    }
                }

                if (pl1Y < lava)
                    pl1YVel += 1;

                if (pl1X + (double) player1Image.getWidth(null) / (2 * player1Size) >= getWidth() - 48) {
                    pl1X = (getWidth() - 48) - (double) player1Image.getWidth(null) / (2 * player1Size);
                } else if (pl1X - (double) player1Image.getWidth(null) / (2 * player1Size) <= 48) {
                    pl1X = (double) player1Image.getWidth(null) / (2 * player1Size) + 48;
                }

                if (kf && pl1Y <= lava && pl1Block && player1Blocks > 0) {
                    int row = (int) Math.floor(pl1X / 24);
                    int column = (int) Math.ceil(pl1Y / 24);
                    if (row >= 0 && row < blocks.length && column >= 0 && column < blocks[0].length && !blocks[row][column].isBlock()) {
                        blocks[row][column].setImage("stone");
                        pl1Block = false;
                        player1Blocks--;
                    }
                }

                if (isPl1Floor) {
                    if (!kw) pl1YVel = 0;
                    pl1Y = Math.floorDiv((int) pl1Y, 24) * 24;
                }
            } else { // pl1 not in quiz

                if (player1Coins == 0) {
                    pl1InQuiz = false;
                } else {
                    if (!kf) { // doesn't want to exit

                        if (ka && q1correctChoice == 0) {
                            player1Blocks += (player1Coins >= 5 ? 15 : 3 * player1Coins);
                            player1Coins -= Math.min(player1Coins, 5);
                            randomizeQuestion(1);
                        } else if (kw && q1correctChoice == 1) {
                            player1Blocks += (player1Coins >= 5 ? 15 : 3 * player1Coins);
                            player1Coins -= Math.min(player1Coins, 5);
                            randomizeQuestion(1);
                        } else if (kd && q1correctChoice == 2) {
                            player1Blocks += (player1Coins >= 5 ? 15 : 3 * player1Coins);
                            player1Coins -= Math.min(player1Coins, 5);
                            randomizeQuestion(1);
                        }

                    } else {
                        pl1X = PL1_SPAWN_X;
                        pl1Y = PL1_SPAWN_Y;
                        pl1XVel = pl1YVel = 0;
                        pl1InQuiz = false;
                    }
                }
            }

            // Player 2 (↑←→/)

            if (!pl2InQuiz) {
                pl2InQuiz = isPlayerInShop(2);

                if (pl2InQuiz) {
                    q2n1 = random.nextInt(11);
                    q2n2 = random.nextInt(11);
                    q2ans = q2n1 * q2n2;
                    q2alt1 = random.nextInt(101);
                    q2alt2 = random.nextInt(101);

                    q1correctChoice = random.nextInt(3);
                }

                boolean isPl2Floor = isBlockAt(pl2X, pl2Y) ||
                        isBlockAt(pl2X - ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y) ||
                        isBlockAt(pl2X + ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y);

                if (isPl2Floor) pl2YVel = 0;

                if (kup && (pl2Y >= lava || isPl2Floor)) pl2YVel = jumpHeight;
                if (kleft) pl2XVel -= speed;
                if (kright) pl2XVel += speed;

                pl2XVel *= friction;

                for (int i = 0; i < (int) Math.abs(pl2XVel); i++) {
                    boolean isPl2Left = isBlockAt(pl2X - ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - 1) ||
                            isBlockAt(pl2X - ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - ((double) player2Image.getHeight(null) / player2Size)) ||
                            isBlockAt(pl2X - ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - ((double) player2Image.getHeight(null) / player2Size / 2));
                    boolean isPl2Right = isBlockAt(pl2X + ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - 1) ||
                            isBlockAt(pl2X + ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - ((double) player2Image.getHeight(null) / player2Size)) ||
                            isBlockAt(pl2X + ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - ((double) player2Image.getHeight(null) / player2Size / 2));

                    if (!isPl2Left && pl2XVel < 0) pl2X--;
                    else if (!isPl2Right && pl2XVel > 0) pl2X++;
                }

                for (int i = 0; i < Math.abs(pl2YVel); i++) {
                    boolean isPl2Ceil = isBlockAt(pl2X, pl2Y - ((double) player2Image.getHeight(null) / player2Size)) ||
                            isBlockAt(pl2X - ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - ((double) player2Image.getHeight(null) / player2Size)) ||
                            isBlockAt(pl2X + ((double) player2Image.getWidth(null) / (2 * player2Size)), pl2Y - ((double) player2Image.getHeight(null) / player2Size));
                    if (!isPl2Ceil) pl2Y += pl2YVel / Math.abs(pl2YVel);
                    else {
                        pl2Y += 1;
                        pl2YVel = 0;
                    }
                }

                if (pl2Y < lava)
                    pl2YVel += 1;

                if (pl2X + (double) player2Image.getWidth(null) / (2 * player2Size) >= getWidth() - 48) {
                    pl2X = (getWidth() - 48) - (double) player2Image.getWidth(null) / (2 * player2Size);
                } else if (pl2X - (double) player2Image.getWidth(null) / (2 * player2Size) <= 48) {
                    pl2X = (double) player2Image.getWidth(null) / (2 * player2Size) + 48;
                }

                if (kslash && pl2Y <= lava && pl2Block && player2Blocks > 0) {
                    int row = (int) Math.floor(pl2X / 24);
                    int column = (int) Math.ceil(pl2Y / 24);
                    if (row >= 0 && row < blocks.length && column >= 0 && column < blocks[0].length && !blocks[row][column].isBlock()) {
                        blocks[row][column].setImage("stone");
                        pl2Block = false;
                        player2Blocks--;
                    }
                }

                if (isPl2Floor) {
                    if (!kup) pl2YVel = 0;
                    pl2Y = Math.floorDiv((int) pl2Y, 24) * 24;
                }
            } else {
                if (!kf) { // doesn't want to exit

                    if (ka && q1correctChoice == 0) {
                        player1Blocks += (player1Coins >= 5 ? 15 : 3 * player1Coins);
                        player1Coins -= Math.min(player1Coins, 5);
                        randomizeQuestion(1);
                    } else if (kw && q1correctChoice == 1) {
                        player1Blocks += (player1Coins >= 5 ? 15 : 3 * player1Coins);
                        player1Coins -= Math.min(player1Coins, 5);
                        randomizeQuestion(1);
                    } else if (kd && q1correctChoice == 2) {
                        player1Blocks += (player1Coins >= 5 ? 15 : 3 * player1Coins);
                        player1Coins -= Math.min(player1Coins, 5);
                        randomizeQuestion(1);
                    }

                } else {
                    pl1X = PL1_SPAWN_X;
                    pl1Y = PL1_SPAWN_Y;
                    pl1XVel = pl1YVel = 0;
                    pl1InQuiz = false;
                }
            }

            if (pl1Y >= lava) respawnPlayer1();
            if (pl2Y >= lava) respawnPlayer2();

            checkCoinCollection();
        } else if (e.getSource() == gameStartTM) {
            gameTM.start();
            gameStartTM.stop();
        }

        repaint();
    }

    private void respawnPlayer1() {
        pl1X = PL1_SPAWN_X;
        pl1Y = PL1_SPAWN_Y;
        pl1XVel = 0;
        pl1YVel = 0;
        player1Coins = 0;
        player1Blocks = 10;
    }

    private void respawnPlayer2() {
        pl2X = PL2_SPAWN_X;
        pl2Y = PL2_SPAWN_Y;
        pl2XVel = 0;
        pl2YVel = 0;
        player2Coins = 0;
        player2Blocks = 10;
    }

    private void spawnCoins() {
        coins.clear();
        while (coins.size() < MAX_COINS) {
            int x = random.nextInt(getWidth() - 100) + 50;
            int y = random.nextInt(lava - 300) + 100;

            int blockX = Math.min(Math.max(x / 24, 0), blocks.length - 1);
            int blockY = Math.min(Math.max(y / 24, 0), blocks[0].length - 1);

            if (!blocks[blockX][blockY].isBlock()) {
                coins.add(new Coin(x, y));
            }
        }
    }

    private void checkCoinCollection() {
        Rectangle player1Rect = new Rectangle((int) pl1X - (player1Width / player1Size / 2),
                (int) pl1Y - (player1Height / player1Size),
                player1Width / player1Size,
                player1Height / player1Size);

        Rectangle player2Rect = new Rectangle((int) pl2X - (player2Width / player2Size / 2),
                (int) pl2Y - (player2Height / player2Size),
                player2Width / player2Size,
                player2Height / player2Size);

        for (int i = coins.size() - 1; i >= 0; i--) {
            Coin coin = coins.get(i);
            if (coin.isNotCollected()) {
                Rectangle coinRect = new Rectangle(coin.getX(), coin.getY(),
                        coinImage.getWidth(null) / 2,
                        coinImage.getHeight(null) / 2);

                if (player1Rect.intersects(coinRect)) {
                    coin.collect();
                    player1Coins += 1;
                    coins.remove(i);
                    spawnCoins();
                } else if (player2Rect.intersects(coinRect)) {
                    coin.collect();
                    player2Coins += 1;
                    coins.remove(i);
                    spawnCoins();
                }
            }
        }
    }

    private boolean isPlayerInShop(int player) {
        boolean pl1 = player == 1;

        double x = pl1 ? pl1X : pl2X;
        double y = pl1 ? pl1Y : pl2Y;

        int width = pl1 ? player1Width / player1Size : player2Width / player2Size;
        int height = pl1 ? player1Height / player1Size : player2Height / player2Size;

        Rectangle playerRect = new Rectangle((int) (x - ((double) width / 2)), (int) (y - height), width, height);

        int sx = pl1 ? shop1x1 : shop2x1;
        int sy = pl1 ? shop1y1 : shop2y1;

        sx *= 24;
        sy *= 24;

        int swidth = pl1 ? shop1x2 - shop1x1 : shop2x2 - shop2x1;
        int sheight = pl1 ? shop1y2 - shop1y1 : shop2y2 - shop2y1;

        swidth++;
        sheight++;

        swidth *= 24;
        sheight *= 24;

        Rectangle shopRect = new Rectangle(sx, sy, swidth, sheight);

        return playerRect.intersects(shopRect);
    }

    private void generateQuestion(Graphics g, int player, int n1, int n2, int ans, int alt1, int alt2, int correctChoice) {
        boolean pl1 = player == 1;

        int width = 250;
        int height = 160;

        int x = pl1 ? 100 : getWidth() - 100 - width;
        int y = 500;

        int border = 10;

        g.setColor(Color.darkGray);
        g.fillRect(x - border, y - border, width + (2 * border), height + (2 * border));
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);

        String question = Integer.toString(n1) + " × " + Integer.toString(n2) + " = ?";

        g.setColor(Color.black);
        g.drawString(question, x + 10, y + 30);

        String ans1 = (pl1 ? "A: " : "←: ") + (correctChoice == 0 ? ans : alt1);
        String ans2 = (pl1 ? "W: " : "↑: ") + (correctChoice == 1 ? ans : (correctChoice == 0 ? alt1 : alt2));
        String ans3 = (pl1 ? "D: " : "→: ") + (correctChoice == 2 ? ans : alt2);

        g.drawString(ans1, x + 10, y + 80);
        g.drawString(ans2, x + 10, y + 110);
        g.drawString(ans3, x + 10, y + 140);
    }

    private void randomizeQuestion(int player) {
        if (player == 1) {
            q1n1 = random.nextInt(11);
            q1n2 = random.nextInt(11);
            q1ans = q1n1 * q1n2;
            q1alt1 = random.nextInt(101);
            q1alt2 = random.nextInt(101);

            q1correctChoice = random.nextInt(3);
        } else {
            q2n1 = random.nextInt(11);
            q2n2 = random.nextInt(11);
            q2ans = q2n1 * q2n2;
            q2alt1 = random.nextInt(101);
            q2alt2 = random.nextInt(101);

            q2correctChoice = random.nextInt(3);
        }
    }

    public void setupWindow() {
        JFrame f = new JFrame("Game");

        gameTM = new Timer(10, this);
        gameStartTM = new Timer(500, this);

        addKeyListener(this);
        f.add(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphicsEnvironment gfxEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gfxDev = gfxEnv.getDefaultScreenDevice();

        f.setUndecorated(true);
        gfxDev.setFullScreenWindow(f);
        f.setVisible(true);
        f.setSize(1440, 900);

        spawnCoins();
    }

    public void startGame() {
        gameStartTM.start();
    }

}
