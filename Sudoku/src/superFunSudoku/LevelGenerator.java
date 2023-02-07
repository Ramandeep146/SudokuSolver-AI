package superFunSudoku;
import java.util.ArrayList;
import java.util.Random;
public class LevelGenerator {
	
	private int[][] array;
	
	private ArrayList<Integer> cellListInUse;
	
	private ArrayList<ArrayList<Integer>> eachCellValues;
	
	private int[] shuffledNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	
	private int totalCells;
	private Random random;
	private int level;
	
	LevelGenerator(int gameLevel){
		
		level = gameLevel;
		totalCells = 81;
		cellListInUse = new ArrayList<Integer>(81);
		eachCellValues = new ArrayList<ArrayList<Integer>>(81);
		array = new int[9][9];
		
		for(int i=0; i<totalCells; i++) {
			cellListInUse.add(i);
			ArrayList<Integer> helper = new ArrayList<Integer>(10);
			shuffle(shuffledNumbers);
			for(int j=0; j<9; j++) {
				helper.add(shuffledNumbers[j]);
			}
			eachCellValues.add(helper);
		}
		
		shuffle(shuffledNumbers);
		if(createFullBoard(0)) {
			for(int i=0; i<9; i++) {
				System.out.print("///// ");
				for(int j=0; j<9; j++) {
					System.out.print(array[i][j]);
				}
				System.out.println();
			}
			releaseValues(0);
		}
		System.out.println();
	}
	
	
	
	private void shuffle(int[] shuffledNumbers) {
		random = new Random();
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
		
		if(i==totalCells) {
			return true;
		}
		ArrayList<Integer> helper = eachCellValues.get(i);
		for(int j=0; j<9; j++) {
			int val = helper.get(j);
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
		
		
		array[a][b] = 0;
		
		if(solveNewBoard(0,array, indexValue, a, b)) {
			// multiple solutions
			array[a][b] = indexValue;
		}
		
		i++;
		if(releaseValues(i)) {
			return true;
		}else {
			return false;
		}
		
	}

	private boolean solveNewBoard(int i, int[][] arr, int indexValue, int p1, int p2) {
		
		if(i==totalCells) {
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
		for(int val=1; val<=9; val++) {
			if(a==p1 && b==p2 && val == indexValue) {
				continue;
			}
			if(checkValidity(a, b, val, arr)) {
				arr[a][b] = val;
				if(solveNewBoard(i+1, arr, indexValue, p1, p2)) {
					arr[a][b]=0;
					return true;
				}else {
					arr[a][b] = 0;
				}
			}
		}
		return false;
	}
}
