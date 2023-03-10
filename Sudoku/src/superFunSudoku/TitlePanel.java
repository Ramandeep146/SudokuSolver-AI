package superFunSudoku;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TitlePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int screenW = 780;
	private static int screenH = 100;
	
	private BufferedImage image;
	
	TitlePanel(){
		this.setBackground(new Color(48, 48, 48));
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		this.setLayout(null);
		
		try {
			image = ImageIO.read(TitlePanel.class.getResource("/imgs/title.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int fontSize = 50;
		String message = "Super Fun Sudoku";
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, fontSize));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.setColor(new Color(77, 136, 219));
		g.drawString(message, screenW/2 - metrics.stringWidth(message)/2, screenH/2 + fontSize/2 - 8);
		
		g.drawImage(image, 0, 0, screenW, screenH, null);
	}
}