import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Cheng Song, Yangxiao Wang
 */
public class DynamicProgramming {
	
	
	/**
	 * Calculate each cell's shortest from the first row of matrix M to the bottom.
	 * Then compare them to get the shortest path of the matrix.
	 * The path should follow the min( [i+1,j] [i,j] [i-1,j]) rule.
	 *
	 * @param M input matrix M
	 * @return a min-cost vertical cut of Matrix M. if M has n rows, the the returned array list has
	 * exactly 2n integers.
	 */
	public static ArrayList<Integer> minCostVC(int[][] M) {
		int lenOfRow = M.length;
		int lenOfCol = M[0].length;
		
		if (lenOfRow == 1) {
			int y = 0;
			for (int i = 0; i < lenOfCol; i++) {
				if (M[0][i] < M[0][y]) {
					y = i;
				}
			}
			return new ArrayList<Integer>(Arrays.asList(0, M[0][y]));
		}
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (lenOfCol == 1) {
			for (int i = 0; i < M.length; i++) {
				result.add(i);
				result.add(0);
			}
			return result;
		}
		
		//Clone Matrix M
		int[][] tmp = new int[lenOfRow][];
		for (int i = 0; i < lenOfRow; i++) {
			tmp[i] = M[i].clone();
		}
		
		//Calculate the minimum path for M(x_i, y_i) by add smallest possible M(x_i+1, y_i+1)
		//Start from the second last row
		for (int i = lenOfRow - 2; i >= 0; i--) {
			for (int j = 0; j < lenOfCol; j++) {
				//left boundary
				if (j == 0) {
					tmp[i][j] = tmp[i][j] + Math.min(tmp[i + 1][j], tmp[i + 1][j + 1]);
				}
				//right boundary
				else if (j == lenOfCol - 1) {
					tmp[i][j] = tmp[i][j] + Math.min(tmp[i + 1][j - 1], tmp[i + 1][j]);
				}
				//normal condition
				else {
					tmp[i][j] = tmp[i][j] +
							Math.min(Math.min(tmp[i + 1][j - 1], tmp[i + 1][j]), tmp[i + 1][j + 1]);
				}
			}
		}
		
		//for debug
		//		for(int i = 0; i < lenOfRow; i++) {
		//			for(int j = 0; j < lenOfCol; j++) {
		//				System.out.print((tmp[i][j] < 10 ? (tmp[i][j]) + " " : tmp[i][j]) + "  ");
		//			}
		//			System.out.println();
		//		}
		
		//Find y0 of minVC
		int y = 0;
		for (int i = 0; i < lenOfCol; i++) {
			if (tmp[0][i] < tmp[0][y]) {
				y = i;
			}
		}
		
		result.add(0);
		result.add(y);
		for (int i = 1; i < lenOfRow; i++) {
			//left boundary
			if (y == 0) {
				if (tmp[i][y] < tmp[i][y + 1]) {
					result.add(i);
					result.add(y);
				} else {
					result.add(i);
					result.add(++y);
				}
			}
			//right boundary
			else if (y == (lenOfCol - 1)) {
				if (tmp[i][y] < tmp[i][y - 1]) {
					result.add(i);
					result.add(y);
				} else {
					result.add(i);
					result.add(--y);
				}
			} else {
				result.add(i);
				int sCol = y - 1;
				if (tmp[i][y] < tmp[i][sCol]) {
					sCol = y;
				}
				if (tmp[i][y + 1] < tmp[i][sCol]) {
					sCol = y + 1;
				}
				y = sCol;
				result.add(sCol);
			}
		}
		return result;
	}
	
	/**
	 * Assume that x is a string of length n and y is a
	 * string of length m such that n >= m. This method returns a string z (obtained by inserting $
	 * at
	 * n - m indices in y) such that AlignCost(x, z) <= AlignCost(x, z0) over all possible z0
	 * (obtained by
	 * inserting n - m many $'s in y). You may assume that length of x is at least the length of y
	 * and
	 * neither of x or y has the character $. Note that the length of the returned string z must
	 * equal the
	 * length of x.
	 *
	 * @param x the longer string
	 * @param y the shorter string
	 * @return the aligned string z
	 */
	public static String stringAlignment(String x, String y) {
		//If the length of two strings are equal, there's no place to add '$', then z = y
		if (x.length() == y.length()) {
			return y;
		}
		//Gap penalty = 4, match reward = 0, mismatch penalty = 2
		int gap = -4;
		int mis = -2;
		
		int n = x.length();
		int m = y.length();
		Cell[][] score = new Cell[m + 1][n + 1];
		score[0][0] = new Cell(0, 0, null, 0);
		
		for (int j = 1; j <= n; j++) {
			score[0][j] = new Cell(0, j, score[0][j - 1], gap * j);
		}
		for (int i = 1; i <= m; i++) {
			score[i][0] = new Cell(0, i, score[i - 1][0], gap * i);
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				Cell c1 = score[i][j - 1];
				Cell c2 = score[i - 1][j];
				Cell c3 = score[i - 1][j - 1];
				int v1 = c1.s + gap;
				int v2 = c2.s + gap;
				int v3 = c3.s;
				if (x.charAt(j - 1) == y.charAt(i - 1)) {
					v3 += 0;
				} else {
					v3 += mis;
				}
				int max = v1;
				Cell parent = c1;
				//				if(max < v2) {
				//					max = v2;
				//					parent = c2;
				//				}
				if (max < v3) {
					max = v3;
					parent = c3;
				}
				score[i][j] = new Cell(i, j, parent, max);
			}
		}
		
		ArrayList<Cell> re = new ArrayList<>();
		Cell temp = score[m][n];
		while (temp.p != null) {
			re.add(temp);
			temp = temp.p;
		}
		re.add(temp);
		Collections.reverse(re);
		for (Cell c : re) {
			System.out.print("[" + c.i + ", " + c.j + "], ");
		}
		
		ArrayList<String> rr = new ArrayList<>();
		String fin = "";
		for (int i = 1; i < re.size(); i++) {
			int l1 = re.get(i).i;
			int l2 = re.get(i).j;
			if (re.get(i - 1).i == l1) {
				fin += "$";
			} else {
				fin += ((y.charAt(l1 - 1)));
			}
		}
		
		//printScoreMatrix(score, y);
		
		return fin;
	}
	
	/**
	 * Debug helper method print score data for martix
	 *
	 * @param score
	 * @param y
	 */
	private static void printScoreMatrix(Cell[][] score, String y) {
		for (Cell[] aScore : score) {
			System.out.println(Arrays.toString(aScore));
		}
	}
	
	/**
	 * Class to store cell's data: position in matrix, parents and score
	 */
	private static class Cell {
		int i, j, s;
		Cell p;
		
		Cell(int l1, int l2, Cell parent, int score) {
			i = l1;
			j = l2;
			p = parent;
			s = score;
		}
		
		@Override
		public String toString() {
			return s + "";
		}
	}
	
	/**
	 * Helper method that calculate penalty
	 *
	 * @param a
	 * @param b
	 * @return the penalty
	 */
	private static int penalty(char a, char b) {
		if (a == b) {
			return 0;
		} else if (a == '$' || b == '$') {
			return 4;
		} else {
			return 2;
		}
	}
	
	//for debug
	public static void main(String args[]) {
		
		int[][] test = {{9, 3, 6, 2, 2}, {8, 4, 3, 6, 1}, {11, 4, 8, 9, 3}};
		
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList = minCostVC(test);
		System.out.println(testList.toString());
		System.out.println();
		System.out.println(stringAlignment("GCCCTAGCG", "GCGCAATG"));
		System.out.println(stringAlignment("Hello", "ell"));
	}
}