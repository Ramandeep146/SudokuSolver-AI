package superFunSudoku;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1114244843521493300L;
	private static int screenW = 240;
	private static int screenH = 540;
	
	private BufferedImage check;
	private BufferedImage cross;
	private BufferedImage s;
	private boolean solved;
	Thread t;
	
	String string = "tick";
	
	boolean bool;

	
	ControlPanel(){
		this.setBackground(new Color(48, 48, 48));
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		this.setLayout(null);
		this.setFocusable(false);
		
		try {
			check = ImageIO.read(ControlPanel.class.getResource("/imgs/check.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cross = ImageIO.read(ControlPanel.class.getResource("/imgs/close.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s = ImageIO.read(ControlPanel.class.getResource("/imgs/s.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bool = false;
	}	
	
	public void reset() {
		bool = false;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(bool) {
			if(string=="check") {
				g.drawImage(check, 80, 410, 80, 80, this);
			}else {
				g.drawImage(cross, 80, 410, 80, 80, this);
			}
		}else {
			g.drawImage(s, 60, 400, 120, 110, this);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke strokeThick = new BasicStroke(4);
		g2.setColor(new Color(77, 136, 219));
		g2.setStroke(strokeThick);
		
		g2.drawLine(0, 92, screenW, 92);

	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		bool = true;
		if(solved) {
			displayTick();
		}else {
			displayCross();
		}
		try {
			t.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reset();
	}

	public void displayTick() {
		string = "check";
		repaint();
		
	}
	
	public void displayCross() {
		string = "cross";
		repaint();
	}
	
	public void displayAction(boolean ac) {
		this.solved = ac;
		
		t = new Thread(this);
		
		if(t.isAlive()) {
			return;
		}
		
		t.start();
	}
	
}
