import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TitlePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int screenW = 570;
	private static int screenH = 80;
	
	TitlePanel(){
		this.setBackground(new Color(48, 48, 48));
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		this.setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int fontSize = 50;
		String message = "Super Sudoku";
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, fontSize));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.setColor(new Color(77, 136, 219));
		g.drawString(message, screenW/2 - metrics.stringWidth(message)/2, screenH/2 + fontSize/2 - 8);
	}
	
	

}
