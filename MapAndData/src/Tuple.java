/**
 * @author Yangxiao, Cheng Song
 */

public class Tuple
{
	private int key;
	private float value;
	
	public Tuple(int keyP, float valueP)
	{
		key = keyP;
		value = valueP;
	}
	
	public int getKey()
	{
		return key;
	}
	
	public float getValue()
	{
		return value;
	}
	
	public boolean equals(Tuple t)
	{
		return (key == t.getKey() && value == t.getValue());
	}
}
