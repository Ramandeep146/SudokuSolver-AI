import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ControlPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1114244843521493300L;
	private static int screenW = 120;
	private static int screenH = 450;
	
	ControlPanel(){
		
		this.setBackground(new Color(48, 48, 48));
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		this.setLayout(null);
		this.setFocusable(true);
		
	}	
	
	

}
