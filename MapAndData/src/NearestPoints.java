import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.*;

/**
 * @author Yangxiao, Cheng Song
 */

public class NearestPoints
{
	ArrayList<Float> list;
	HashTable ht;
	
	/**
	 *  nearestPoints(String dataFile) The variable dataFile holds the absolute path of the file that contains the set of points S.
	 */
	public NearestPoints(String dataFile) throws FileNotFoundException
	{
		list = new ArrayList<Float>();
		
		Scanner scan = new Scanner(new File(dataFile));
		
		while(scan.hasNextFloat())
		{
			list.add(scan.nextFloat());
		}
		scan.close();
	}
	
	/**
	 * nearestPoints(ArrayList<float> pointSet) The array list pointSet contains the set of points S.
	 *
	 * @param pointSet
	 */
	public NearestPoints(ArrayList<Float> pointSet)
	{
		list = new ArrayList<>(pointSet);
	}
	
	/**
	 * naiveNearestPoints(float p) Returns an array list of points (from the set S) that are close to p.
	 * This method must implement the naive approach. Note that the type of this method must be ArrayList<float>
	 * @param p
	 * @return
	 */
	public ArrayList<Float> naiveNearestPoints(float p)
	{
		ArrayList<Float> ret = new ArrayList<>();
		for(Float q : list)
		{
			float m = Math.abs(p - q);
			if(m <= 1)
			{
				ret.add(q);
			}
		}
		
		return ret;
	}
	
	/**
	 * buildDataStructure() Builds the data structure that enables to quickly answer nearest point
	 * queries. Your data structure must use the notion of neighbor preserving hashing and along with
	 * the class HashTable. Otherwise, you will receive zero credit.
	 *
	 */
	public void buildDataStructure()
	{
		ht = new HashTable(list.size());
		for(Float f : list)
		{
			ht.add(new Tuple((int) Math.floor(f), f));
		}
	}
	
	/**
	 * npHashNearestPoints(float p) Returns an array list of points (from the S) that are close to
	 * p. This method must use the data structure that was built. The expected run time of this method
	 * must be O(N(p)); otherwise you will receive zero credit.
	 *
	 * @param p
	 * @return
	 */
	public ArrayList<Float> npHashNearestPoints(float p)
	{
		ArrayList<Float> re = new ArrayList<>();
		
		for(int i = (-1); i <= 1; i++)
		{
			ArrayList<Tuple> tp = ht.search(i + (int) Math.floor(p));
			if(tp != null)
			{
				for(Tuple t : tp)
				{
					if(Math.abs(t.getValue() - p) <= 1)
					{
						re.add(t.getValue());
					}
				}
			}
		}
		
		return re;
	}
	
	/**
	 * allNearestPointsNaive() For every point p 2 S, compute the list of all points from S that
	 * are close to p by calling the method NaiveNearestPoints(p). Write the results to a file named NaiveSolution.txt
	 */
	@SuppressWarnings("unchecked")
	public void allNearestPointsNaive() throws FileNotFoundException
	{
		PrintWriter wr = new PrintWriter(new File("NaiveSolution.txt"));
		for(Float p : list)
		{
			ArrayList<Float> re = naiveNearestPoints(p);
			int len = re.size();
			for(int i = 0; i < len; i++)
			{
				wr.print(re.get(i));
				if(i != len - 1)
				{
					
					wr.print(" ");
				}
			}
			wr.println();
		}
		wr.close();
	}
	
	/**
	 * allNearestPointsHash() For every point p 2 S, compute the list of all points from S that
	 * are close to p by calling the method NPHashNearestPoints(p). Write the results to a file named
	 * HashSolution.txt. The expected time of this method must be O(); otherwise you will receive zero credit.
	 */
	public void allNearestPointsHash() throws FileNotFoundException
	{
		PrintWriter wr = new PrintWriter(new File("HashSolution.txt"));
		for(Float p : list)
		{
			ArrayList<Float> re = npHashNearestPoints(p);
			int len = re.size();
			for(int i = 0; i < len; i++)
			{
				wr.print(re.get(i));
				if(i != len - 1)
				{
					wr.print(" ");
					
				}
			}
			wr.println();
		}
		wr.close();
	}
	
	
	public static void main(String[] args) throws FileNotFoundException
	{
		NearestPoints np = new NearestPoints("points.txt");
		long time1 =  System.currentTimeMillis();
		np.buildDataStructure();
		time1 = System.currentTimeMillis() - time1;
		System.out.println("buildDataStructure: " + (double)time1/1000);
		
		time1 =  System.currentTimeMillis();
		np.allNearestPointsNaive();
		time1 = System.currentTimeMillis() - time1;
		System.out.println("allNearestPointsNaive: " + (double)time1/1000);
		
		time1 =  System.currentTimeMillis();
		np.allNearestPointsHash();
		time1 = System.currentTimeMillis() - time1;
		System.out.println("allNearestPointsHash: " + (double)time1/1000);
	}
}













