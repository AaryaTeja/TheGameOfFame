import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class ScrollingBackground extends JPanel implements ActionListener, KeyListener{
	protected Image bg;
	private Image player, plWalk, plIdle, plJump;

	private Timer animationTM;
	private int walkFrame, idleFrame;


	private Timer gameTM, gravityTM;
	private double plyvel, plxvel, ply, plx, bgX;

	private boolean kleft, kright, kup;
	private boolean doubleJump;

	// constants
	private double gravity;
	private int floor;
	
	// for external uses
	public Dimension bgSize() {
		return new Dimension(bg.getWidth(null), bg.getHeight(null));
	}

	public ScrollingBackground(){
		addKeyListener(this);

		ImageIcon bgi = new ImageIcon("src/background.jpg");
		bg = bgi.getImage();

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

		// initializing
		bgX = 0;
		kup = kleft = kright = false;
		gravity = 0.5;
		plx = 200;
		floor = 594;
		ply = floor - player.getHeight(null);
		plyvel = 0;
		plxvel = 0;
		doubleJump = true;

		gameTM = new Timer(5, this);
		gameTM.start();
		gravityTM = new Timer(10, this);
		gravityTM.start();

		animationTM = new Timer(100, this);
		animationTM.start();
		
		requestFocusInWindow();
	}
	
	public void drawImage(Graphics2D g2d, Image img,
            int dx1, int dy1, int dx2, int dy2,
            int sx1, int sy1, int sx2, int sy2) {
		g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(bg, (int) bgX, 0, null);
		g2d.drawImage(bg, (int) bgX -bg.getWidth(null), 0, null);
		if (bgX < 0){
			bgX = bg.getWidth(null);
		} else if (bgX >= bg.getWidth(null)){
			bgX = 0;
		}
		
		// decide wether to walk or idle
		if (ply + player.getHeight(null) < floor){
			drawImage(g2d, plJump,
					(int) plx - 32, (int) ply, (int) plx + 96, (int) ply + 160,
					kleft?288:192, 0, kleft?192:288, 96);
		}
		else {
			if (kright || kleft) {
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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gameTM){
			if (kleft){
				if (ply + player.getHeight(null) == floor) {
					plxvel -= 0.3;
				} else {
					plxvel -= 0.4;
				}
			} if (kright){
				if (ply + player.getHeight(null) == floor) {
					plxvel += 0.3;
				} else {
					plxvel += 0.4;
				}
			} if (kup && ply + player.getHeight(null) == floor){
				plyvel = -100;
			}

			if (kup && ply >= floor - player.getHeight(null)) {
				plyvel = -20;
			}

			plx += plxvel;
			plxvel *= 0.95;


			int bound = 200;
			double distanceFromNearestBorder = Math.min((double) plx, (double) getWidth() - (plx + player.getWidth(null)));

			if (!(plx > ((double) getWidth()/2) - 50 && plx < ((double) getWidth()/2) + 50)) {
				if (plx < (double)getWidth()/2){
					bgX += Math.pow(bound / distanceFromNearestBorder, 2);
					plx += Math.pow(bound / distanceFromNearestBorder, 2);
				} else if (plx > (double)getWidth()/2){
					bgX -= Math.pow(bound / distanceFromNearestBorder, 2);
					plx -= Math.pow(bound / distanceFromNearestBorder, 2);
				}
			}


			repaint();
		} else if (e.getSource() == gravityTM){
			ply += plyvel;

			if (ply + player.getHeight(null) >= floor) {
				ply = floor - player.getHeight(null);
				plyvel = 0;
			}

			plyvel += gravity;
		} else if (e.getSource() == animationTM) {
			if (++walkFrame > 6){
				walkFrame = 0;
			}
			if (++idleFrame > 4){
				idleFrame = 0;
			}
		}


	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			kleft = true;
		} if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			kright = true;
		} if (e.getKeyCode() == KeyEvent.VK_UP){
			kup = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			kleft = false;
		} if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			kright = false;
		} if (e.getKeyCode() == KeyEvent.VK_UP){
			kup = false;
		}

	}

	public void setupWindow() {
		JFrame f = new JFrame("Game");
		JPanel p = new ScrollingBackground();

		f.add(p);
		f.setSize(bg.getWidth(null), bg.getHeight(null));
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		p.requestFocusInWindow();
	}

	public static void main(String[] args) {
		ScrollingBackground g = new ScrollingBackground();
		g.setupWindow();
	}
}
