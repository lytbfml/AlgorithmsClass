import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Yangxiao, Cheng Song
 */
public class RecSys
{
	ArrayList<Float> userMap;
	ArrayList<user> userList;
	NearestPoints np;
	
	/**
	 * RecSys(String mrMatrix) The string mrMatrix is contains the absolute path name of the file that
	 * contains the mapped ratings matrix.
	 *
	 * @ aram mrMatrix
	 */
	public RecSys(String mrMatrix) throws FileNotFoundException
	{
		userMap = new ArrayList<>();
		userList = new ArrayList<>();
		Scanner scan = new Scanner(new File(mrMatrix));
		int numU = scan.nextInt();
		int numM = scan.nextInt();
		for(int i = 0; i < numU; i++)
		{
			float map = scan.nextFloat();
			userMap.add(map);
			int[] rates = new int[numM];
			for(int j = 0; j < numM; j++)
			{
				rates[j] = scan.nextInt();
			}
			userList.add(new user(map, rates));
		}
		scan.close();
		np = new NearestPoints(userMap);
		np.buildDataStructure();
	}
	
	/**
	 * ratingOf(int u, int m) If the user u has rated movie m, then it returns that rating;
	 * otherwise it will predict the rating based on the approach described above, and returns the predicted rating.
	 * The type of this method must be float.
	 *
	 * @param u
	 * @param m
	 */
	public float ratingOf(int u, int m){
		user us = userList.get(u-1);
		if(us.getRates(m) != 0)
		{
			return us.getRates(m);
		}
		
		ArrayList<Float> nearUs = np.npHashNearestPoints(us.getMap());
		float re = 0.0f;
		int count = 0;
		for(user temp : userList)
		{
			if(nearUs.contains(temp.getMap()))
			{
				float ra = temp.getRates(m);
				re += ra;
				if(ra != 0) count++;
			}
		}
		float rating = (float) re / count;
		
		return rating;
	}
	
	/**
	 * A user class that contains every user's info
	 */
	class user{
		float map;
		int[] rates;
 		
		public user(float map, int[] rates)
		{
			this.rates = Arrays.copyOf(rates, rates.length);
			this.map = map;
		}
		
		public float getMap()
		{
			return map;
		}
		
		
		public int getRates(int movie)
		{
			return rates[movie - 1];
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		//test
//		RecSys rc = new RecSys("testM.txt");
//		System.out.println(rc.ratingOf(3, 4));
	}
}
