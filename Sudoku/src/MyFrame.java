

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MyPanel myPanel = new MyPanel(); 
	private ControlPanel controlPanel = new ControlPanel();
	
	private int buttonH = 40;
	private int buttonW = 100;
	private int gap = 30;

	MyFrame(){
		
		// Big pannel which will contain other panels
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.X_AXIS));
		//bigPanel.setBackground(new Color(127, 163, 250));
		
		
		// Buttons added in the control panel
		MyButton start = new MyButton("Start", 10, (buttonH + gap) * 0 + gap, buttonW, buttonH);
		start.addActionListener(e -> start());
		controlPanel.add(start);
		
		MyButton stop = new MyButton("Stop", 10, (buttonH + gap) * 1 + gap, buttonW, buttonH);
		stop.addActionListener(e -> stop());
		controlPanel.add(stop);
		
		MyButton incSpeed = new MyButton("Speed Up", 10, (buttonH + gap) * 2 + gap, buttonW, buttonH);
		incSpeed.addActionListener(e -> incSpeed());
		controlPanel.add(incSpeed);
		
		MyButton decSpeed = new MyButton("Speed Down", 10, (buttonH + gap) * 3 + gap, buttonW, buttonH);
		decSpeed.addActionListener(e -> decSpeed());
		controlPanel.add(decSpeed);
		
		MyButton reset = new MyButton("Reset", 10, (buttonH + gap) * 4 + gap, buttonW, buttonH);
		reset.addActionListener(e -> reset());
		controlPanel.add(reset);
		
		MyButton generateNew = new MyButton("New Level", 10, (buttonH + gap) * 5 + gap, buttonW, buttonH);
		generateNew.addActionListener(e -> generateNew());
		controlPanel.add(generateNew);
		
		// Adding other panels in the big panel
		bigPanel.add(myPanel);
		bigPanel.add(controlPanel);
		
		// Adding the big panel in myFrame and setting it up
		this.add(bigPanel);
		this.setTitle("Sudoku Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

	private void generateNew() {
	}

	private void decSpeed() {
	}

	private void incSpeed() {
	}

	private void stop() {
	}

	private void start() {
	}

	private void reset() {
		System.out.println("reset");
	}
	
}
