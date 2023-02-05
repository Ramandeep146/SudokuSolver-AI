import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;

public class MyComboBox extends JComboBox<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyComboBox(String[] text, int x1, int y1, int w, int h){
		this.setBounds(x1, y1, w, h);
		this.setFont(new Font("Times Roman", Font.BOLD + Font.ITALIC, 12));
		this.setBackground(new Color(77, 136, 219));
		this.setFocusable(false);
		
		for(String s: text) {
			this.addItem(s);
		}
		
		this.setSelectedIndex(0);
	}
	
}
