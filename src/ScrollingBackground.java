import java.awt.*; // Import the AWT package for GUI components
import java.awt.event.*; // Import the AWT event package for event handling
import java.awt.image.ImageObserver; // Import ImageObserver for image handling

import javax.swing.*; // Import Swing package for GUI components

// The ScrollingBackground class extends JPanel and implements ActionListener and KeyListener interfaces
public class ScrollingBackground extends JPanel implements ActionListener, KeyListener {
    protected Image bg; // Background image
    private Image player, plWalk, plIdle, plJump; // Player images for different states

    private Timer animationTM; // Timer for animation
    private int walkFrame, idleFrame; // Variables to keep track of animation frames

    private Timer gameTM, gravityTM; // Timers for game loop and gravity
    private double plyvel, plxvel, ply, plx, bgX; // Variables for player position and velocity, and background position

    private boolean kleft, kright, kup; // Booleans to track key presses for movement
    private boolean doubleJump; // Boolean to track double jump

    // Constants
    private double gravity; // Gravity constant
    private int floor; // Floor position

    // Method to get background size for external use
    public Dimension bgSize() {
        return new Dimension(bg.getWidth(null), bg.getHeight(null));
    }

    // Constructor to initialize the game
    public ScrollingBackground() {
        addKeyListener(this); // Add key listener to the panel

        // Load background image
        ImageIcon bgi = new ImageIcon("src/background.jpg");
        bg = bgi.getImage();

        // Load player images for different states
        ImageIcon pli = new ImageIcon("src/player.png");
        player = pli.getImage();

        ImageIcon shWalk = new ImageIcon("src/animation/shaman/Walk.png");
        plWalk = shWalk.getImage();
        walkFrame = 0;

        ImageIcon shIdle = new ImageIcon("src/animation/shaman/idle.png");
        plIdle = shIdle.getImage();
        idleFrame = 0;

        ImageIcon shJump = new ImageIcon("src/animation/shaman/jump.png");
        plJump = shJump.getImage();

        // Initialize variables
        bgX = 0;
        kup = kleft = kright = false;
        gravity = 0.5;
        plx = 200;
        floor = 594;
        ply = floor - player.getHeight(null);
        plyvel = 0;
        plxvel = 0;
        doubleJump = true;

        // Initialize and start game, gravity, and animation timers
        gameTM = new Timer(5, this);
        gameTM.start();
        gravityTM = new Timer(10, this);
        gravityTM.start();

        animationTM = new Timer(100, this);
        animationTM.start();

        requestFocusInWindow(); // Request focus for key inputs
    }

    // Method to draw an image with specified coordinates
    public void drawImage(Graphics2D g2d, Image img,
                          int dx1, int dy1, int dx2, int dy2,
                          int sx1, int sy1, int sx2, int sy2) {
        g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }

    // Method to paint the component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the background image
        g2d.drawImage(bg, (int) bgX, 0, null);
        g2d.drawImage(bg, (int) bgX - bg.getWidth(null), 0, null);
        if (bgX < 0) {
            bgX = bg.getWidth(null);
        } else if (bgX >= bg.getWidth(null)) {
            bgX = 0;
        }

        // Decide whether to walk or idle based on player state
        if (ply + player.getHeight(null) < floor) {
            drawImage(g2d, plJump,
                    (int) plx - 32, (int) ply, (int) plx + 96, (int) ply + 160,
                    (plxvel < 0) ? 288 : 192, 0, (plxvel < 0) ? 192 : 288, 96);
        } else {
            if (plxvel != 0) {
                if (plxvel > 1) {
                    drawImage(g2d, plWalk,
                            (int) plx, (int) ply, (int) plx + 64, (int) ply + 128,
                            (walkFrame * 96) + 32, 32, (walkFrame * 96) + 64, 96);
                } else if (plxvel < 1) {
                    drawImage(g2d, plWalk,
                            (int) plx + 64, (int) ply, (int) plx, (int) ply + 128,
                            (walkFrame * 96) + 32, 32, (walkFrame * 96) + 64, 96);
                }
            } else {
                drawImage(g2d, plIdle,
                        (int) plx, (int) ply, (int) plx + 76, (int) ply + 128,
                        (idleFrame * 96) + 32, 32, (idleFrame * 96) + 76, 96);
            }
        }
    }

    // Method called when an action event occurs
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTM) {
            // Update player movement based on key presses
            if (kleft) {
                if (ply + player.getHeight(null) == floor) {
                    plxvel -= 0.3;
                } else {
                    plxvel -= 0.4;
                }
            }
            if (kright) {
                if (ply + player.getHeight(null) == floor) {
                    plxvel += 0.3;
                } else {
                    plxvel += 0.4;
                }
            }

            // Handle jump and double jump
            if (kup) {
            	System.out.println("1:" + doubleJump);
                if (ply + player.getHeight(null) >= floor) { 
                    plyvel = -20;  // Regular jump
                    doubleJump = true;
                } else if (doubleJump) {
                    plyvel = -20;  // Double jump
                    doubleJump = false;
                }
            	System.out.println("2:" + doubleJump);
            }

            // Update player position
            plx += plxvel;
            plxvel *= 0.95;

            // Handle background scrolling
            int bound = 200;
            double distanceFromNearestBorder = Math.min((double) plx, (double) getWidth() - (plx + player.getWidth(null)));

            if (!(plx > ((double) getWidth() / 2) - 50 && plx < ((double) getWidth() / 2) + 50)) {
                if (plx < (double) getWidth() / 2) {
                    bgX += Math.pow(bound / distanceFromNearestBorder, 2);
                    plx += Math.pow(bound / distanceFromNearestBorder, 2);
                } else if (plx > (double) getWidth() / 2) {
                    bgX -= Math.pow(bound / distanceFromNearestBorder, 2);
                    plx -= Math.pow(bound / distanceFromNearestBorder, 2);
                }
            }

            repaint(); // Repaint the component
        } else if (e.getSource() == gravityTM) {
            // Update player gravity
            ply += plyvel;

            // Check if player is on the floor
            if (ply + player.getHeight(null) >= floor) {
                ply = floor - player.getHeight(null);
                plyvel = 0;
                doubleJump = true;
            }

            plyvel += gravity; // Apply gravity to player
        } else if (e.getSource() == animationTM) {
            // Update animation frames
            if (++walkFrame > 6) {
                walkFrame = 0;
            }
            if (++idleFrame > 4) {
                idleFrame = 0;
            }
        }
    }

    // Empty method for keyTyped event
    public void keyTyped(KeyEvent e) {

    }

    // Method called when a key is pressed
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            kleft = true; // Set left key press to true
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            kright = true; // Set right key press to true
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            kup = true; // Set up key press to true
        }
    }

    // Method called when a key is released
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            kleft = false; // Set left key press to false
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            kright = false; // Set right key press to false
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            kup = false; // Set up key press to false
        }
    }

    // Method to set up the game window
    public void setupWindow() {
        JFrame f = new JFrame("Game"); // Create a new JFrame
        JPanel p = new ScrollingBackground(); // Create a new instance of ScrollingBackground

        f.add(p); // Add the panel to the frame
        f.setSize(bg.getWidth(null), bg.getHeight(null)); // Set the size of the frame based on background size
        f.setLocationRelativeTo(null); // Center the frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        f.setVisible(true); // Make the frame visible
        p.requestFocusInWindow(); // Request focus for key inputs
    }

    // Main method to start the game
    public static void main(String[] args) {
        ScrollingBackground g = new ScrollingBackground(); // Create a new instance of ScrollingBackground
        g.setupWindow(); // Set up the game window
    }
}
