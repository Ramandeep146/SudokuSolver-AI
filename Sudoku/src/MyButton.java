import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class MyButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4093122890422314038L;

	MyButton(String text, int x1, int y1, int w, int h){;
		this.setText(text);
		//this.setPreferredSize(new Dimension(80, 25));
		this.setBounds(x1, y1, w, h);
		
		//this.setSize(50,50);
		this.setFont(new Font("Times Roman", Font.BOLD, 10));
		this.setBackground(new Color(59, 91, 204));
		
		this.setFocusable(false);
	}
	
	public void setName(String text) {
		this.setText(text);
	}
	
}
