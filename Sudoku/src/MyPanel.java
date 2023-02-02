import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int screenW = 450;
	private static int screenH = 450;
	
	private static int colN = 9;
	private static int rowN = 9;
	
	
	
	private static int unitS = screenW/rowN;
	
	private Thread myThread;
	private boolean solving = false;
	private boolean paused = false;
	
	//private int[][] values = new int[9][9];
	private int[][] values = 
		{	{8, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 3, 8, 6, 7, 5, 4, 9},
			{4, 7, 0, 5, 0, 3, 2, 6, 0},
			{0, 0, 0, 0, 5, 0, 9, 8, 1},
			{0, 6, 8, 9, 0, 0, 0, 0, 0},
			{7, 0, 1, 3, 4, 0, 0, 2, 0},
			{6, 0, 0, 0, 7, 0, 0, 0, 4},
			{0, 0, 7, 0, 0, 9, 0, 0, 0},
			{0, 3, 0, 0, 8, 0, 0, 1, 2}};
	
	private boolean[][] fixedValues = new boolean[9][9];
	
	private static final int speedPause = 50;
	
	
	MyPanel(){
		this.setPreferredSize(new Dimension(screenW,screenH));
		this.setFocusable(true);
		this.setBackground(new Color(118, 184, 222));
		
		initialize();
	}
	
	private void initialize() {
		
		int[][] val = 
			{	{8, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 3, 8, 6, 7, 5, 4, 9},
				{4, 7, 0, 5, 0, 3, 2, 6, 0},
				{0, 0, 0, 0, 5, 0, 9, 8, 1},
				{0, 6, 8, 9, 0, 0, 0, 0, 0},
				{7, 0, 1, 3, 4, 0, 0, 2, 0},
				{6, 0, 0, 0, 7, 0, 0, 0, 4},
				{0, 0, 7, 0, 0, 9, 0, 0, 0},
				{0, 3, 0, 0, 8, 0, 0, 1, 2}};
		values = val;
		
		for(int i=0; i<rowN; i++) {
			for(int j=0; j<colN; j++) {
				if(values[i][j] == 0) {
					fixedValues[i][j] = false;
				}else {
					fixedValues[i][j] = true;
				}
			}
		}
		
		solving = false;
		paused = false;
		
	}

	public void autoSolveBut() {

		initialize();
		myThread = new Thread(this);
		myThread.start();
		
		solving = true;
	}
	
	public void pauseBut() throws InterruptedException {
		myThread.suspend();
		paused = true;
	}
	
	@SuppressWarnings("removal")
	public void restartBut() {
		myThread.resume();
		paused = false;
	}

	public void resetBut() {
		solving = false;
		paused = false;
		//myThread.interrupt();
		
		System.out.println(myThread.getState());
		myThread.stop();
		initialize();
		repaint();
	}
	
	public boolean getSolvingStatus() {
		return solving;
	}
	
	public boolean getPauseStatus() {
		return paused;
	}
	
	// Backtracking Algorithm ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@SuppressWarnings("static-access")
	private boolean solveSudoku(int i, int j) {
		
		if(j==9) {
			i++;
			j=0;
		}
		
		if(i==9) {
			return true;
		}
		
		while(fixedValues[i][j] == true) {
			j++;
			if(j==9) {
				i++;
				j=0;
			}
			if(i==9) {
				return true;
			}
		}
		
		for(int v=1; v<=9; v++) {
			if(checkValue(i, j, v)) {
				values[i][j] = v;
				try {
					myThread.sleep(speedPause);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
				int temp = j;
				if(solveSudoku(i, ++temp)) {
					return true;
				}else {
					values[i][j] = 0;
				}
			}
			
		}
		
		return false;

	}


	private boolean checkValue(int i, int j, int val) {
		
		for(int k=0; k<9; k++) {
			if(values[i][k] == val) {
				return false;
			}
			if(values[k][j] == val) {
				return false;
			}
		}
		
		
		int i1 = i/3;
		int j1 = j/3;
		
		for(int k=0; k<3; k++) {
			for(int k2=0; k2<3; k2++){
				if(values[3*i1 + k][3*j1+k2]==val) {
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	// Draw Board+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		drawValues(g);
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

	private void drawValues(Graphics g) {
		int fontSize = 20;
		g.setFont(new Font("Times Roman", Font.BOLD, fontSize));
		FontMetrics metrics = getFontMetrics(g.getFont()); 

		// Show only revealed cells
		for(int i=0; i<rowN; i++) {
			for(int j=0; j<colN; j++) {
				if(values[i][j] == 0) {
					continue;
				}
				if(fixedValues[i][j] == true) {
					g.setColor(new Color(59, 91, 204));
				}else {
					g.setColor(new Color(250, 250, 252));
				}
				g.drawString("" + values[i][j], 
							 j*unitS + unitS/2 - metrics.stringWidth("" + values[i][j])/2, 
							 i*unitS + unitS/2 + fontSize/2);
			}
		}
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//Running the Thread
	@Override
	public void run() {

		if(solveSudoku(0,0)) {
			System.out.println("Solved");
		}else {
			System.out.println("No Solution Found");
		}
		
		solving = false;
		myThread.stop();
		
	}
	
}
