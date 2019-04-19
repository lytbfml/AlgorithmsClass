import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Yangxiao, Cheng Song
 */
public class HashTable
{
	private HashFunction hashF;
	ArrayList<Tuple>[] table;
	
	public static void main(String[] args)
	{
		System.out.println((double) 22 / 10);
	}
	
	public HashTable(int size)
	{
		int p = primePick(size);
		table = new ArrayList[p];
		hashF = new HashFunction(p);
		
		for (int i = 0; i < p; i++)
			table[i] = null;
	}
	
	public int maxLoad()
	{
		int max = 0;
		for (int i = 0; i < table.length; i++)
		{
			if (table[i] != null)
			{
				int size = table[i].size();
				if (max < size)
					max = size;
			}
		}
		
		return max;
	}
	
	public int averageLoad()
	{
		int nonNull = 0;
		int count = 0;
		
		for (int i = 0; i < table.length; i++)
		{
			if (table[i] != null)
			{
				count += table[i].size();
				nonNull += 1;
			}
		}
		
		return count / nonNull;
	}
	
	public int size()
	{
		return table.length;
	}
	
	public int numElements()
	{
		int nonNull = 0;
		for (int i = 0; i < table.length; i++)
		{
			if (table[i] != null)
			{
				nonNull += table[i].size();
			}
		}
		return nonNull;
	}
	
	public int loadFactor()
	{
		return numElements() / size();
	}
	
	public void add(Tuple t)
	{
		int index = hashF.hash(t.getKey());
		if (table[index] == null)
		{
			table[index] = new ArrayList<Tuple>();
		}
		table[index].add(t);
		
		if (loadFactor() > 0.7)
		{
			reHash();
		}
	}
	
	private void reHash()
	{
		int len = primePick(table.length * 2);
		hashF = new HashFunction(len);
		ArrayList<Tuple>[] newT = new ArrayList[len];
		
		for (int i = 0; i < table.length; i++)
		{
			if (table[i] != null)
			{
				for (Tuple temp : table[i])
				{
					int indexT = hashF.hash(temp.getKey());
					if(newT[indexT] == null)
					{
						newT[indexT] = new ArrayList<Tuple>();
					}
					newT[indexT].add(temp);
				}
			}
		}
		table = newT;
	}
	
	public ArrayList<Tuple> search(int k)
	{
		ArrayList<Tuple> found = new ArrayList<>();
		int index = hashF.hash(k);
		if (table[index] == null)
		{
			return null;
		}
		for (Tuple temp : table[index])
		{
			if (temp.getKey() == k)
			{
				found.add(temp);
			}
		}
		return found;
	}
	
	public void remove(Tuple t)
	{
		int index = hashF.hash(t.getKey());
		if (table[index] != null)
		{
			if (table[index].contains(t))
				table[index].remove(t);
		}
	}
	
	private int primePick(int range)
	{
		int f = range;
		
		if (f % 2 == 0)
		{
			f += 1;
		}
		
		while (!isPrime(f))
		{
			f += 2;
		}
		return f;
	}
	
	private boolean isPrime(int n)
	{
		if (n % 2 == 0)
		{
			return false;
		}
		for (int i = 3; i * i <= n; i += 2)
		{
			if (n % i == 0)
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (ArrayList<Tuple> tp : table)
		{
			if (tp == null)
				continue;
			for (Tuple t : tp)
			{
				sb.append("Key: " + t.getKey() + " - " + t.getValue() + ", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
