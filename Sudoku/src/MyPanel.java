import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int screenW = 450;
	private static int screenH = 450;
	
	private static int colN = 9;
	private static int rowN = 9;
	
	private static int unitS = screenW/rowN;
	
	
	MyPanel(){
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setFocusable(true);
		this.setBackground(new Color(118, 184, 222));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		draw(g);
	}

	private void drawGrid(Graphics g) {
		// Draw grid lines
		Graphics2D g2 = (Graphics2D) g;
				
		BasicStroke strokeThin = new BasicStroke(2);
		BasicStroke strokeThick = new BasicStroke(4);
		g2.setColor(new Color(77, 136, 219));
				
		for(int i=0; i<=rowN; i++) {
			if(i%3==0) {
				g2.setStroke(strokeThick);
			}else {
				g2.setStroke(strokeThin);
			}
			g2.drawLine(unitS*i, 0, unitS*i, screenH);
		}
				
		for(int i=0; i<=colN; i++) {
			if(i%3==0) {
				g2.setStroke(strokeThick);
			}else {
				g2.setStroke(strokeThin);
			}
			g2.drawLine(0, unitS*i, screenW, unitS*i);
		}
		
	}

	private void draw(Graphics g) {
		
	}

	
	
}
