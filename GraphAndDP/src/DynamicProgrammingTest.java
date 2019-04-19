
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DynamicProgrammingTest {
	/*
	 * I use a cost method to determine the equality of both minCostVC and stringAlignment. This
	 */
	@Test
	public void testMinCostVC() {
		int[][] tMatrix0 = {{1, 2, 4}, {7, 12, 3}, {0, 1, 4}, {3, 4, 3}};
		int expected0 = 2 + 3 + 1 + 3;
		int actual0 = minCostAsInteger(DynamicProgramming.minCostVC(tMatrix0), tMatrix0);
		assertEquals("Error on test 1", expected0, actual0);
		int[][] tMatrix1 = {{3}, {2}, {1}};
		int expected1 = 6;
		int actual1 = minCostAsInteger(DynamicProgramming.minCostVC(tMatrix1), tMatrix1);
		assertEquals("Error on test 2", expected1, actual1);
		int[][] tMatrix2 = {{0, 0, 0}, {0, 0, 0}, {5, 6, 7}};
		int expected2 = 5;
		int actual2 = minCostAsInteger(DynamicProgramming.minCostVC(tMatrix2), tMatrix2);
		assertEquals("Error on test 3", expected2, actual2);
		int[][] tMatrix3 = {{5, 3, 2}, {2, 4, 3}, {5, 6, 4}};
		int expected3 = 9;
		int actual3 = minCostAsInteger(DynamicProgramming.minCostVC(tMatrix3), tMatrix3);
		assertEquals("Error on test 4", expected3, actual3);
		int[][] tMatrix4 = {{8, 8, 8}, {8, 8, 8}, {8, 8, 8}};
		int expected4 = 24;
		int actual4 = minCostAsInteger(DynamicProgramming.minCostVC(tMatrix4), tMatrix4);
		assertEquals("Error on test 5", expected4, actual4);
		int[][] tMatrix5 = {{3, 8, 9}, {9, 8, 3}, {3, 5, 3}};
		int expected5 = 14;
		int actual5 = minCostAsInteger(DynamicProgramming.minCostVC(tMatrix5), tMatrix5);
		assertEquals("Error on test 6", expected5, actual5);
	}
	
	@Test
	public void testStringAlignment() {
		//String One
		String t0_full = "Hello";
		String t0_short = "ell";
		String t0_expected = "$ell$";
		String t0_actual = DynamicProgramming.stringAlignment(t0_full, t0_short);
		System.out.println(t0_actual);
		assertEquals("t0 Error", cost(t0_full, t0_expected), cost(t0_full, t0_actual));
		//String 2
		String t1_full = "Hello World!";
		String t1_short = "Ho!";
		String t1_expected = "H$$$o$$$$$$!";
		String t1_actual = DynamicProgramming.stringAlignment(t1_full, t1_short);
		assertEquals("t1 Error", cost(t1_full, t1_expected), cost(t1_full, t1_actual));
		//String 3
		String t2_full = "My Name is Michael Aras Sila";
		String t2_short = "y ame is ichael Aras Sila";
		String t2_expected = "$y $ame is $ichael Aras Sila";
		String t2_actual = DynamicProgramming.stringAlignment(t2_full, t2_short);
		assertEquals("t2 Error", cost(t2_full, t2_expected), cost(t2_full, t2_actual));
		//String 4
		String t3_full = "These Are My Test Codes";
		String t3_short = "ese re M Tst Code";
		String t3_expected = "$$ese $re M$ T$st Code$";
		String t3_actual = DynamicProgramming.stringAlignment(t3_full, t3_short);
		assertEquals("t3 Error", cost(t3_full, t3_expected), cost(t3_full, t3_actual));
		//String 5
		String t4_full = "I'm not good at writing test codes, so Buyer Beware";
		String t4_short = "I'm not good at writing test codes, so buyer beware";
		String t4_expected = "I'm not good at writing test codes, so buyer beware";
		String t4_actual = DynamicProgramming.stringAlignment(t4_full, t4_short);
		assertEquals("t4 Error", cost(t4_full, t4_expected), cost(t4_full, t4_actual));
		//String 6
		String t5_full = "This is a Special Test Case";
		String t5_short = "z";
		String t5_expected = "z$$$$$$$$$$$$$$$$$$$$$$$$$$";
		String t5_expected_alt = "$$$$$$$$$$$$$$$$$$$$$$$$$$z";
		String t5_actual = DynamicProgramming.stringAlignment(t5_full, t5_short);
		assertEquals("t5 Error: Cost", cost(t5_full, t5_expected), cost(t5_full, t5_actual));
		boolean equalString = t5_actual.equals(t5_expected) || t5_actual.equals(t5_expected_alt);
		assertTrue("t5 Error: Content", equalString);
		
		
	}
	
	private static int penalty(char x, char y) {
		if (x == y) { return 0;} else if (y == '$') { return 4;} else { return 2;}
	}
	
	public static int cost(String x, String y) {
		if (x.length() != y.length()) {
			return -1;
		}
		int cost = 0;
		for (int i = 0; i < x.length(); i++) {
			cost += penalty(x.charAt(i), y.charAt(i));
		}
		return cost;
	}
	
	public int minCostAsInteger(ArrayList<Integer> points, int[][] M) {
		if (points.size() % 2 != 0) {
			return -1;
		}
		int cost = 0;
		for (int i = 0; i < points.size() / 2; i++) {
			cost += M[points.get(2 * i)][points.get(2 * i + 1)];
		}
		return cost;
	}
	
}
