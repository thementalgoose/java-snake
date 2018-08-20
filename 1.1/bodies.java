//@Author	: Jordan Fisher
//@Version	: 1.1

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class bodies {
	int x, y, zI, aI;
	Image body;
	ImageIcon bodyii;
	
	int movement = 5;
	int lr;
	public bodies(int x, int y, int z, int a) {
		zI = z;
		aI = a;
		if (zI == 3) {
			body = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/head.png"));
		}
		if (zI == 2) {
			body = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/body.png"));
		}
		bodyii = new ImageIcon(body);
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Image getImage() {
		return body;
	}
	public ImageIcon getImageIcon() {
		return bodyii;
	}
	public int getDirection() {
		return lr;
	}
	public void move(int leftright) {
		lr = leftright;
		if (lr == 1) {
			x += movement;
		}
		if (lr == 2) {
			y += movement;
		}
		if (lr == 3) {
			x -= movement;
		}
		if (lr == 4) {
			y -= movement;
		}
	}
}