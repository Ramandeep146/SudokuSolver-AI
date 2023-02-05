import java.util.ArrayList;
import java.util.Random;
public class LevelGenerator {
	
	private int[][] array;
	
	private ArrayList<Integer> cellListInUse;
	
	private ArrayList<ArrayList<Integer>> eachCellValues;
	
	private int shuffledNumbers[] = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	
	private int totalCells = 81;
	private Random random;
	//hard level
	private int level;
	
	LevelGenerator(int gameLevel){
		
		level = gameLevel;
		
		cellListInUse = new ArrayList<Integer>(81);
		eachCellValues = new ArrayList<ArrayList<Integer>>(81);
		array = new int[9][9];
		
		for(int i=0; i<totalCells; i++) {
			cellListInUse.add(i);
			ArrayList<Integer> helper = new ArrayList<Integer>();
			for(int j=0; j<9; j++) {
				helper.add(j+1);
			}
			eachCellValues.add(helper);
		}
		
		shuffle(shuffledNumbers);
		if(createFullBoard(0)) {
			for(int i=0; i<9; i++) {
				System.out.print("/////");
				for(int j=0; j<9; j++) {
					System.out.print(array[i][j]);
				}
				System.out.println();
			}
			releaseValues(0);
		}
		System.out.println();
		//System.out.println(cellListInUse.size());
	}
	
	
	
	private void shuffle(int[] shuffledNumbers) {
		Random random = new Random();
		int temp;
		int upperBound = 8;
		for(int i=0; i<upperBound; i++) {
			temp = (random.nextInt(upperBound-i) + i) + 1;
			swap(shuffledNumbers, temp, i);
		}
	}

	private void swap(int[] shuffledNumbers, int temp, int i) {
		int a = shuffledNumbers[temp];
		shuffledNumbers[temp] = shuffledNumbers[i];
		shuffledNumbers[i] = a;
	}

	private boolean createFullBoard(int i) {
		
		if(i==81) {
			return true;
		}
		
		for(int val: shuffledNumbers) {
			int a = i/9;
			int b = i%9;
			if(checkValidity(a, b, val, array)) {
				array[a][b] = val;
				if(createFullBoard(i+1)) {
					return true;
				}else {
					array[a][b] = 0;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkValidity(int i, int j, int val, int[][] arr) {
		for(int k=0; k<9; k++) {
			
			if(j!=k && arr[i][k] == val) {
				return false;
			}
			if(i!=k && arr[k][j] == val) {
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
				if(arr[3*i1 + k][3*j1+k2]==val) {
					return false;
				}
			}
		}
		
		return true;
	}

	public int[][] getArray() {
		return array;
	}
	
	private boolean releaseValues(int i) {
		
		if(i>level || cellListInUse.size()==0) {
			return true;
		}
		
		
		random = new Random();
		
		int size = cellListInUse.size();
		int randomI = random.nextInt(size);
		int indexToBeRem = cellListInUse.get(randomI);
		cellListInUse.remove(randomI);
		
		int a = indexToBeRem/9;
		int b = indexToBeRem%9;
		int indexValue = array[a][b];
		
		eachCellValues.get(indexToBeRem).remove(indexValue-1);
		
		array[a][b] = 0;
		
		int[][] arr = new int[9][9];
		
		for(int k1=0; k1<9; k1++) {
			for(int k2=0; k2<9; k2++) {
				arr[k1][k2] = array[k1][k2];
			}
		}
		
		if(solveNewBoard(0,arr)) {
			// multiple solutions
			array[a][b] = indexValue;
		}else {
			// only one solution
			array[a][b] = 0;
		}
		eachCellValues.get(indexToBeRem).add(indexValue);
		
		i++;
		if(releaseValues(i)) {
			return true;
		}else {
			return false;
		}
		
	}

	private boolean solveNewBoard(int i, int[][] arr) {
		
		if(i==81) {
			return true;
		}
	
		int a = i/9;
		int b = i%9;
		while(arr[a][b]!=0) {
			i++;
			if(i==81) {
				return true;
			}
			a = i/9;
			b = i%9;
		}
		for(int val: eachCellValues.get(i)) {
			if(checkValidity(a, b, val, arr)) {
				arr[a][b] = val;
				if(solveNewBoard(i+1, arr)) {
					return true;
				}else {
					arr[a][b] = 0;
				}
			}
		}
		return false;
	}
}
