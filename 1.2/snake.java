//@Author	: Jordan Fisher
//@Version	: 1.2

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class snake extends JFrame {
	int frameX = 500;
	int frameY = 500;
	int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
	int border = 20;
	int timer = 140;
	
	int headX = frameX / 2;
	int headY = frameY / 2;
	
	int lr = 1;
	int templr = 1;
	int inGame = 1;
	
	int posX = 0, posY = 0;
	int appleX, appleY;
	int movement = 5;
	public int positionInArray;
	int startingBodies = 6;
	int pause = 0;
	int score = 0;
	String sp = "";
	int aCollected = 0;
	
	ArrayList body = new ArrayList();
	ArrayList<String> vals = new ArrayList<String>();
	Image head, apple;
	ImageIcon headii, appleii;
	updateThread t;
	public snake() {
		head = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/head.png"));
		headii = new ImageIcon(head);
		apple = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/apple.png"));
		appleii = new ImageIcon(apple);
		startBodies();
		moveApple();
		setTitle("Snake!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation((screenX / 2) - (frameX / 2), (screenY / 2) - (frameY / 2));
		setSize(frameX, frameY);
		setResizable(false);
		setBackground(Color.BLACK);
		setVisible(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (pause == 0) {
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (lr != 3) {	
							lr = 1;
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						if (lr != 4) {	
							lr = 2;
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						if (lr != 1) {	
							lr = 3;
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						if (lr != 2) {	
							lr = 4;
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_S) {
						if (timer == 140) {
							timer = 70;
							sp = "(S)";
						} else {
							timer = 140;
							sp = "";
						}
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_P) {
					if (pause == 0) {
						t.suspend();
						pause = 1;
						repaint();
					} else {
						t.resume();
						pause = 0;
						repaint();
					}
				}
			}
		});
		t = new updateThread();
		t.start();
	}
	public void startBodies() {
		int p = movement;
		int tempX = frameX / 2, tempY = frameY / 2;
		body.add(new bodies(tempX, tempY, 3, 0));
		vals.add("1");
		int con = tempX;
		int temp;
		for (int i = 0; i < startingBodies; i++) {
			con -= p;
			temp = i + 1;
			body.add(new bodies(con, tempY, 2, temp));
			vals.add("1");
		}
	}
	public void addBody() {
		int a = body.size() - 1;
		bodies b = (bodies) body.get(a);
		a += 1;
		body.add(new bodies(b.getX(), b.getY(), 2, a));
		vals.add(Integer.toString(lr));
	}
	public static void main(String[] args) {
		snake s = new snake();
	}
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		if (inGame == 1) {
			g.clearRect(0, 0, frameX, frameY);
			g.drawRect(border, border + 30, (frameX - (2 * border)), (frameY - (2 * border) - 30));
			for (int i = 0; i < body.size(); i++) {
				bodies b = (bodies) body.get(i);
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
			g.drawImage(apple, appleX, appleY, this);
			bodies h = (bodies) body.get(0);
			Rectangle rect2 = new Rectangle(h.getX(), h.getY(), 5, 5);
			for (int i = 1; i < body.size(); i++) {
				bodies b = (bodies) body.get(i);
				Rectangle rect1 = new Rectangle(b.getX(), b.getY(), 5, 5);
				if (rect1.intersects(rect2)) {
					inGame = 0;
				}
			}
			g.drawString("Score: " + score, frameX - 100, 42);
			if (pause == 1) {
				g.drawString("Game paused. Press 'P' to resume", border, 42);
			} else {
				g.drawString("Jordan Fisher " + sp, border, 42);	
			}
		}
		if (inGame == 0) {
			g.clearRect(0, 0, frameX, frameY);
			Image go = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/gameover.png"));
			ImageIcon goii = new ImageIcon(go);
			g.drawImage(go, (frameX / 2) - (goii.getIconWidth() / 2), (frameY / 2) - (goii.getIconHeight() / 2) - 60, this);
			g.drawString("Score: " + score, frameX / 2 - 50, frameY / 2 + 5);
			g.drawString("Apples collected: " + aCollected, frameX / 2 - 50, frameY / 2 + 20);
			g.drawString("Body length: " + body.size(), frameX / 2 - 50, frameY / 2 + 35);
		}	
	}
	public void moveApple() {
		Random r = new Random();
		int widthOfPlayingWindow = frameX - (2 * border);
		int heightOfPlayingWindow = frameY - (2 * border) - 30;
		int factorX = widthOfPlayingWindow / 5;
		int factorY = heightOfPlayingWindow / 5;
		int rX = r.nextInt(factorX) + 1;
		rX = (rX * 5) + border;
		int rY = r.nextInt(factorY) + 1;
		rY = (rY * 5) + (border + 30);
		for (int i = 0; i < body.size(); i++) {
			bodies b = (bodies) body.get(i);
			if (rX == b.getX() && rY == b.getY()) {
				moveApple();
			}
		}
		appleX = rX;
		appleY = rY;
	}
	public class updateThread extends Thread implements Runnable {
		public void run() {
			for (int i = 1; i > 0; i++) {
				try {
					Thread.sleep(timer);
				} catch (Exception e) {
					System.out.println(e);
				}
				bodies h = (bodies) body.get(0);
				int tX = h.getX();
				int tY = h.getY();
				h.move(lr);								
				int[] tempX = new int[body.size()];
				for (int a = 1; a < body.size(); a++) {
					bodies b = (bodies) body.get(a);
					tempX[a] = b.getX();
				}
				int[] tempY = new int[body.size()];
				for (int a = 1; a < body.size(); a++) {
					bodies b = (bodies) body.get(a);
					tempY[a] = b.getY();
				}
				tempX[0] = tX;
				tempY[0] = tY;
				for (int o = 1; o < body.size(); o++) {
					bodies z = (bodies) body.get(o);
					z.setCoords(tempX[o - 1], tempY[o - 1]);
				}
				if (h.getX() < border || h.getY() < border + 30 || h.getX() > frameX - border || h.getY() > frameX - border) {
					inGame = 0;
				}
				if (h.getX() == appleX && h.getY() == appleY) {
					addBody();
					moveApple();
					score += 20;
					aCollected++;
				}
				repaint();
			}
		}
	}
}