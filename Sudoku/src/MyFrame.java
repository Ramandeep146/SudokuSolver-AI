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
	
	MyButton start;
	MyButton pause;
	MyButton check;
	MyButton reset;
	MyButton generateNew;
	MyComboBox myComboBox;
	
	MyFrame(){
		
		JPanel godPanel = new JPanel();
		godPanel.setLayout(new BoxLayout(godPanel, BoxLayout.Y_AXIS));
		godPanel.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));
		
		
		// Big pannel which will contain other panels
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.X_AXIS));
		
		
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

		
		check = new MyButton("Check", 10, (buttonH + gap) * 3 + gap, buttonW, buttonH);
		check.addActionListener(e -> check());
		controlPanel.add(check);
		
		generateNew = new MyButton("New Level", 10, (buttonH + gap) * 4 + gap+gap/2, buttonW, buttonH);
		generateNew.addActionListener(e -> generateNew());
		controlPanel.add(generateNew);
		
		myComboBox = new MyComboBox(10, (buttonH + gap) * 5 + gap+gap/2, buttonW, buttonH);
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
			myPanel.autoSolveBut();
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
