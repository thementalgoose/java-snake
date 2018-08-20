//@Author	: Jordan Fisher
//@Version	: 1.1

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class snake extends JFrame {
	int frameX = 500;
	int frameY = 500;
	int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
	int border = 20;
	int timer = 150;
	
	int headX = frameX / 2;
	int headY = frameY / 2;
	
	int lr = 1;
	int templr = 1;
	int inGame = 1;
	
	int posX = 0, posY = 0;
	
	int movement = 5;
	public int positionInArray;
	int startingBodies = 3;
	
	ArrayList body = new ArrayList();
	ArrayList<String> vals = new ArrayList<String>();
	Image head;
	ImageIcon headii;
	public snake() {
		head = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/head.png"));
		headii = new ImageIcon(head);
		startBodies();
		
		setTitle("Snake!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation((screenX / 2) - (frameX / 2), (screenY / 2) - (frameY / 2));
		setSize(frameX, frameY);
		setResizable(false);
		setBackground(Color.BLACK);
		setVisible(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					templr = lr;
					lr = 1;
					bodies l = (bodies) body.get(0);
					posX = l.getX();
					posY = l.getY();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					templr = lr;
					lr = 2;
					bodies l = (bodies) body.get(0);
					posX = l.getX();
					posY = l.getY();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					templr = lr;
					lr = 3;
					bodies l = (bodies) body.get(0);
					posX = l.getX();
					posY = l.getY();
				}
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					templr = lr;
					lr = 4;
					bodies l = (bodies) body.get(0);
					posX = l.getX();
					posY = l.getY();
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					addBody();
				}
			}
		});
		updateThread t = new updateThread();
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
	public int getPosition() {
		return positionInArray;
	}
	public void addBody() {
		int a = body.size() - 1;
		int tempX = 0, tempY = 0;
		bodies b = (bodies) body.get(a);
		if (lr == 1) {
			tempX = b.getX() - movement;
			tempY = b.getY();
		}
		if (lr == 2) {
			tempY = b.getY() - movement;
			tempX = b.getX();
		}
		if (lr == 3) {
			tempX = b.getX() + movement;
			tempY = b.getY();
		}
		if (lr == 4) {
			tempY = b.getY() + movement;
			tempX = b.getX();
		}
		a += 1;
		body.add(new bodies(tempX, tempY, 2, a));
		vals.add(Integer.toString(lr));
	}
	public static void main(String[] args) {
		snake s = new snake();
	}
	public void paint(Graphics g) {
		if (inGame == 1) {
			g.clearRect(0, 0, frameX, frameY);
			g.setColor(Color.GREEN);
			g.drawRect(border, border + 30, (frameX - (2 * border)), (frameY - (2 * border) - 30));
			g.drawString("Jordan Fisher", border, 42);
			for (int i = 0; i < body.size(); i++) {
				bodies b = (bodies) body.get(i);
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
		if (inGame == 0) {
			g.clearRect(0, 0, frameX, frameY);
		}	
	}
	public class updateThread extends Thread implements Runnable {
		public void run() {
			for (int i = 1; i > 0; i++) {
				try {
					Thread.sleep(timer);
				} catch (Exception e) {
					System.out.println(e);
				}
				for (int a = 0; a < body.size(); a++) {
					bodies b = (bodies) body.get(a);
					String u = vals.get(a);
					if (b.getX() == posX && b.getY() == posY) {
						b.move(lr);
						vals.set(a, Integer.toString(lr));
					} else if (u.equals(Integer.toString(lr))) {
						b.move(lr);
 					} else {
						b.move(templr);
					}
					if (b.getX() > (frameX - border - b.getImageIcon().getIconWidth()) || b.getX() < border || b.getY() > (frameY - border - b.getImageIcon().getIconWidth()) || b.getY() < border) {
						inGame = 0;
					}
					repaint();
				}
			}
		}
	}
}