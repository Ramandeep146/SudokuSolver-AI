

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
	
	MyButton start;
	MyButton pause;
	MyButton incSpeed;
	MyButton decSpeed;
	MyButton reset;
	MyButton generateNew;
	

	MyFrame(){
		
		// Big pannel which will contain other panels
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.X_AXIS));
		//bigPanel.setBackground(new Color(127, 163, 250));
		
		
		// Buttons added in the control panel
		start = new MyButton("Auto Solve", 10, (buttonH + gap) * 0 + gap, buttonW, buttonH);
		start.addActionListener(e -> autoSolve());
		controlPanel.add(start);
		
		pause = new MyButton("Pause", 10, (buttonH + gap) * 1 + gap, buttonW, buttonH);
		pause.addActionListener(e -> {
			try {
				pause();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		controlPanel.add(pause);
		
		reset = new MyButton("Reset", 10, (buttonH + gap) * 2 + gap, buttonW, buttonH);
		reset.addActionListener(e -> reset());
		controlPanel.add(reset);
		
		incSpeed = new MyButton("Speed Up", 10, (buttonH + gap) * 3 + gap, buttonW, buttonH);
		incSpeed.addActionListener(e -> incSpeed());
		controlPanel.add(incSpeed);
		
		decSpeed = new MyButton("Speed Down", 10, (buttonH + gap) * 4 + gap, buttonW, buttonH);
		decSpeed.addActionListener(e -> decSpeed());
		controlPanel.add(decSpeed);
		
		generateNew = new MyButton("New Level", 10, (buttonH + gap) * 5 + gap, buttonW, buttonH);
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

	private void pause() throws InterruptedException {
		
		if(!myPanel.getPauseStatus() && myPanel.getSolvingStatus()) {
			myPanel.pauseBut();
			pause.setText("Re-Start");
		}else if(myPanel.getSolvingStatus()) {
			myPanel.restartBut();
			pause.setText("Pause");
		}
		
	}

	private void autoSolve() {
		if(!myPanel.getSolvingStatus()) {
			myPanel.autoSolveBut();
		}
	}

	private void reset() {
		if(myPanel.getSolvingStatus()) {
			myPanel.resetBut();
			pause.setText("Pause");
		}
	}
	
}
