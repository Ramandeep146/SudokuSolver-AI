import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int WIDTH = 540;
	private final static int HEIGHT = 540;
	
	private final static int COLS = 9;
	private final static int ROWS = 9;
	
	private final static int UNITS = WIDTH/ROWS;
	
	private Thread myThread;
	private boolean solving = false;
	private boolean paused = false;
	private int delay = 50;
	
	private Point selectedPoint;
	
	private int[][] values;
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
	private boolean[][] fixedValues;
	private boolean[][] wrongValues;
	
	MyPanel(){
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setFocusable(true);
		this.setBackground(new Color(48, 48, 48));
		this.setBorder((BorderFactory.createLineBorder(new Color(77, 136, 219), 2)));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(solving) {
					return;
				}
				int i = e.getX()/UNITS;
				int j = e.getY()/UNITS;
				
				if(!fixedValues[j][i]) {
					selectedPoint.setX(i*UNITS);
					selectedPoint.setY(j*UNITS);
				}
				repaint();
				
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(solving) {
					return;
				}
				
				int i = selectedPoint.getY()/UNITS;
				int j = selectedPoint.getX()/UNITS;
				int val = e.getKeyChar()-48;
				
				if(values[i][j] == val) {
					values[i][j] = 0;
					repaint();
					return;
				}
				
				if(val >=0 && val <= 9 && i >=0 && j >=0) {
					values[i][j] = val;
					if(val!=0 && !checkValue(i, j, val)) {
						wrongValues[i][j] = true;
					}else {
						wrongValues[i][j] = false;
					}
					repaint();
				}	
			}
		});
		initialize();
	}
	
	private void initialize() {
		
		delay = 40;
		solving = false;
		paused = false;
		
		fixedValues = new boolean[COLS][ROWS];
		wrongValues = new boolean[ROWS][COLS];
		values = new int[9][9];
		
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				values[i][j] = val[i][j];
				if(values[i][j] == 0) {
					fixedValues[i][j] = false;
				}else {
					fixedValues[i][j] = true;
				}
				wrongValues[i][j] = false;
			}
		}

		selectedPoint = new Point(-100, -100);
		
	}

	public void autoSolveBut() {
		initialize();
		myThread = new Thread(this);
		myThread.start();
		solving = true;
	}
	
	@SuppressWarnings("removal")
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
		initialize();
		repaint();
	}
	
	public boolean check() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(values[i][j]==0 || wrongValues[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void generateNewLevel(String gameLevel) {
		if(solving) {
			return;
		}
		
		int level;
		
		switch(gameLevel) {
			case "Easy": 
				level = 25;
				break;
			case "Normal":
				level = 42;
				break;
			case "Difficult":	
				level = 60;
				break;
			default:
				level = 60;
		}
		
		//long value=0;
		
		//for(int i=1; i<=1000; i++) {
			long startTime = System.nanoTime();
			LevelGenerator newLevel = new LevelGenerator(level);
			val = newLevel.getArray();
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			//value+=duration;
		//}
		
		//value = value/1000;
		
		System.out.println(((double)duration)/1000000 + " averae milliseconds for auto level generation");
		
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
		
		if(solving == false) {
			return false;
		}
		
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
				if(solving == true) {
					try {
						myThread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
				
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
			
			if(j!=k && values[i][k] == val) {
				return false;
			}
			if(i!=k && values[k][j] == val) {
				return false;
			}
		}
		
		
		int i1 = i/3;
		int j1 = j/3;
		
		for(int k=0; k<3; k++) {
			for(int k2=0; k2<3; k2++){
				if((3*i1 + k) == i && (3*j1 + k2) == j) {
					continue;
				}
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
				
		for(int i=0; i<=ROWS; i++) {
			if(i%3==0) {
				g2.setStroke(strokeThick);
			}else {
				g2.setStroke(strokeThin);
			}
			g2.drawLine(UNITS*i, 0, UNITS*i, HEIGHT);
		}
				
		for(int i=0; i<=COLS; i++) {
			if(i%3==0) {
				g2.setStroke(strokeThick);
			}else {
				g2.setStroke(strokeThin);
			}
			g2.drawLine(0, UNITS*i, WIDTH, UNITS*i);
		}
		
		g2.setColor(new Color(20, 23, 64));
		g2.setStroke(strokeThick);
		g2.fillRect(selectedPoint.getX(), selectedPoint.getY(), UNITS, UNITS);
		g2.setColor(new Color(77, 136, 219));
		g2.drawRect(selectedPoint.getX(), selectedPoint.getY(), UNITS, UNITS);
		
	}

	private void drawValues(Graphics g) {
		int fontSize = 20;
		g.setFont(new Font("Times Roman", Font.BOLD, fontSize));
		FontMetrics metrics = getFontMetrics(g.getFont()); 

		// Show only revealed cells
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++) {
				if(values[i][j] == 0) {
					continue;
				}
				if(fixedValues[i][j]) {
					g.setColor(new Color(77, 136, 219));
				}else if(wrongValues[i][j]){
					g.setColor(new Color(204, 18, 28));
				}else {
					g.setColor(new Color(250, 250, 252));
				}
				g.drawString("" + values[i][j], 
							 j*UNITS + UNITS/2 - metrics.stringWidth("" + values[i][j])/2, 
							 i*UNITS + UNITS/2 + fontSize/2);
			}
		}
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//Running the Thread
	@Override
	public void run() {
		long startTime = System.nanoTime();
		if(solveSudoku(0,0)) {
			System.out.println("Solved");
		}else {
			System.out.println("No Solution Found");
		}
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		System.out.println(((double)duration)/1000000 + " milliseconds for auto solving");
		solving = false;
	}
	
}
