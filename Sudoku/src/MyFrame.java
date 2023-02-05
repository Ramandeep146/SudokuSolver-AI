import java.awt.Color;

import javax.swing.BorderFactory;
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
	private TitlePanel titlePanel = new TitlePanel();
	
	private int buttonH = 40;
	private int buttonW = 100;
	private int gap = 30;
	
	private String gameLevel;
	private String speed;
	
	MyButton start;
	MyButton pause;
	MyButton check;
	MyButton reset;
	MyButton generateNew;
	MyComboBox myComboBox;
	MyComboBox speedBox;
	
	MyFrame(){
		
		JPanel godPanel = new JPanel();
		godPanel.setLayout(new BoxLayout(godPanel, BoxLayout.Y_AXIS));
		godPanel.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		
		
		// Big pannel which will contain other panels
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.X_AXIS));
		
		String[] allSpeeds = {"Instant", "Slow", "Fast"};
		speedBox = new MyComboBox(allSpeeds, 15, (buttonH + gap) * 1 + gap+gap/2, buttonW, buttonH);
		speed = (String) speedBox.getSelectedItem();
		speedBox.addActionListener(e -> {
			
			if(speedBox.getSelectedItem() == null) {
				return;
			}
			
			if(e.getSource()==speedBox) {
				speed = (String) speedBox.getSelectedItem();
			}
		});
		controlPanel.add(speedBox);
		
		// Buttons added in the control panel
		start = new MyButton("Auto Solve", 15 + buttonW + 10, (buttonH + gap) * 1 + gap+gap/2, buttonW, buttonH);
		start.addActionListener(e -> autoSolve());
		controlPanel.add(start);
		
		pause = new MyButton("Pause", 15 + buttonW/2 + 5, (buttonH + gap) * 2 + gap+gap/2, buttonW, buttonH);
		pause.addActionListener(e -> {
			try {
				pause();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		controlPanel.add(pause);
		
		reset = new MyButton("Reset", 15+ buttonW/2 + 5, (buttonH + gap) * 3 + gap+gap/2, buttonW, buttonH);
		reset.addActionListener(e -> reset());
		controlPanel.add(reset);

		check = new MyButton("Check", 15+ buttonW/2 + 5, (buttonH + gap) * 4 + gap+gap/2, buttonW, buttonH);
		check.addActionListener(e -> check());
		controlPanel.add(check);
		
		generateNew = new MyButton("New Level", 15 + buttonW + 10, (buttonH + gap) * 0 + gap, buttonW, buttonH);
		generateNew.addActionListener(e -> generateNew());
		controlPanel.add(generateNew);
		
		String[] text = {"Easy", "Normal", "Difficult"};
		myComboBox = new MyComboBox(text, 15, (buttonH + gap) * 0 + gap, buttonW, buttonH);
		gameLevel = (String) myComboBox.getSelectedItem();
		myComboBox.addActionListener(e -> {
			
			if(myComboBox.getSelectedItem() == null) {
				return;
			}
			
			if(e.getSource()==myComboBox) {
				gameLevel = (String) myComboBox.getSelectedItem();
			}
		});
		controlPanel.add(myComboBox);
		
		// Adding other panels in the big panel
		bigPanel.add(myPanel);
		bigPanel.add(controlPanel);
		
		godPanel.add(titlePanel);
		godPanel.add(bigPanel);
		
		// Adding the big panel in myFrame and setting it up
		this.add(godPanel);
		this.setTitle("Sudoku Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

	private void generateNew() {
		
		myPanel.generateNewLevel(gameLevel);
		
	}


	private void pause() throws InterruptedException {
		
		if(!myPanel.getPauseStatus() && myPanel.getSolvingStatus()) {
			myPanel.pauseBut();
			pause.setText("Resume");
		}else if(myPanel.getSolvingStatus()) {
			myPanel.restartBut();
			pause.setText("Pause");
		}
		
	}

	private void autoSolve() {
		controlPanel.reset();
		if(!myPanel.getSolvingStatus()) {
			myPanel.autoSolveBut(speed);
		}
	}

	private void reset() {
		controlPanel.reset();
		myPanel.resetBut();
		pause.setText("Pause");
	}
	
	private void check() {
		//if(!myPanel.getSolvingStatus()) {
			if(myPanel.check()) {
				controlPanel.displayAction(true);
			}else {
				System.out.println("here");
				controlPanel.displayAction(false);
			}
		//}
	}
	
}
