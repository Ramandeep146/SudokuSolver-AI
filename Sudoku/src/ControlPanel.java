import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ControlPanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1114244843521493300L;
	private static int screenW = 120;
	private static int screenH = 540;
	
	private BufferedImage action;
	private boolean solved;
	Thread t;

	
	ControlPanel(){
		this.setBackground(new Color(48, 48, 48));
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		this.setLayout(null);
		this.setFocusable(false);
	}	
	
	public void reset() {
		action = null;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(action!=null) {
			g.drawImage(action, 35, 465, 50, 50, null);
		}
		
		Graphics2D g2 = (Graphics2D) g;
		BasicStroke strokeThick = new BasicStroke(4);
		g2.setColor(new Color(77, 136, 219));
		g2.setStroke(strokeThick);
		
		g2.drawLine(0, 300, screenW, 300);

	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		System.out.println("running");
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
		try {
			action = ImageIO.read(new File("check.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
		
	}
	
	public void displayCross() {
		try {
			action = ImageIO.read(new File("close.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
