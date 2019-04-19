import java.util.Random;

/**
 * @author Yangxiao, Cheng Song
 */
public class HashFunction
{
	private int p;
	private int a;
	private int b;
	
	public HashFunction(int range)
	{
		Random rand = new Random();
		p = primePick(range);
		a = rand.nextInt(p);
		b = rand.nextInt(p);
	}
	
	public int hash(int x)
	{
		long re = (long) a * x + (long) b;
		re = re % p;
		return (int) re;
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
	
	public int getP()
	{
		return p;
	}
	
	public int getA()
	{
		return a;
	}
	
	public int getB()
	{
		return b;
	}
	
	public void setP(int x)
	{
		p = primePick(x);
	}
	
	public void setA(int x)
	{
		a = x % p;
	}
	
	public void setB(int y)
	{
		b = y % p;
	}
}
