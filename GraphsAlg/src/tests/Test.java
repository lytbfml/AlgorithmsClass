package tests;

import OSMMap;
import graphalgorithms.IWeight;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yangxiao on 12/5/2016.
 */
public class Test
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
	{
		String routefile = "src/cs311/hw8/routes3.txt";
		String mapfile = "src/cs311/hw8/AmesMap.txt";
		String ss[] = {mapfile, routefile};
		OSMMap m = new OSMMap();
		m.main3(ss);
	}
	
//	@Before
//	public void setUp() throws ParserConfigurationException, SAXException, IOException
//	{
//	}
//
//	@org.junit.Test
//	public void routeTest() throws ParserConfigurationException, SAXException, IOException
//	{
//		m.main3(ss);
//	}
//
}


class Weight2 implements IWeight
{
	private double w;
	
	Weight2()
	{
		this(0.0);
	}
	
	Weight2(Double W)
	{
		w = W;
	}
	
	@Override
	public double getWeight()
	{
		return w;
	}
}

class Weight implements IWeight
{
	
	public double weight;
	
	public Weight(double weight)
	{
		this.weight = weight;
	}
	
	@Override
	public double getWeight()
	{
		return weight;
	}
	
	@Override
	public String toString()
	{
		return weight + "";
	}
}