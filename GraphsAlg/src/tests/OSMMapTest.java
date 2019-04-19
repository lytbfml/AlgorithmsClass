package tests;

import OSMMap;
import graph.Graph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Keane on 12/3/2016.
 */
public class OSMMapTest {
	// Change these paths to match your setup. For example, my text files are in the outer package directory
	private static final String routefile = "src/cs311/hw8/routes2.txt";
	private static final String routefile2 = "src/cs311/hw8/routes3.txt";
	private static final String mapfile = "src/cs311/hw8/AmesMap.txt";
	private static OSMMap m;
	
	@BeforeClass
	public static void setup() {
		m = new OSMMap();
		long starttime = System.nanoTime();
		m.LoadMap(mapfile);
		long time = System.nanoTime() - starttime;
		System.out.println("Load took " + time / 1000000 + " ms");
	}
	
	@Test
	public void testLoad() {
		// You don't actually need to implement the OSMMap.getGraph() method.
		// I just did it for the sake of the test and will not include it in my submission.
		Graph g = m.getGraph();
		assertTrue(g.isDirectedGraph());
		assertEquals(121648, g.getVertices().size());
		assertEquals(34785, g.getEdges().size());
	}
	
	@Test
	public void testClosestRoad() {
		String v = m.ClosestRoad(new OSMMap.Location(42.0492620, -93.7442400));
		assertEquals("159018339", v);
	}
	
	@Test
	public void testAlmostClosestRoad() {
		String v = m.ClosestRoad(new OSMMap.Location(42.0492621, -93.7442499));
		assertEquals("159018339", v);
	}
	
	@Test
	public void testNotSoClosestRoad() {
		String v = m.ClosestRoad(new OSMMap.Location(42.0492600, -93.7442450));
		assertEquals("159018339", v);
	}
	
	@Test
	public void testmain3() {
        /*
        Here's a hack that makes sure the method prints the right thing.
        http://stackoverflow.com/a/8708357
        */
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		String args[] = {mapfile, routefile};
		OSMMap.main3(args);
		// On Windows, use \r\n, on *nix, change to \n. Sorry for not writing portable code...
		assertEquals("Marshall Avenue\r\n" +
				"Story Street\r\n" +
				"Crane Avenue\r\n" +
				"West Street\r\n" +
				"Union Drive\r\n" +
				"Bissell Road\r\n" +
				"Osborn Drive\r\n", baos.toString());
	}
	
	@Test
	public void testmain3again() {
        /*
        Here's a hack that makes sure the method prints the right thing.
        http://stackoverflow.com/a/8708357
        */
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		String args[] = {mapfile, routefile2};
		OSMMap.main3(args);
		// On Windows, use \r\n, on *nix, change to \n. Sorry for not writing portable code...
		assertEquals("Union Drive\r\n" +
				"Lynn Avenue\r\n" +
				"Lincoln Way\r\n" +
				"Gray Avenue\r\n" +
				"Gable Lane\r\n" +
				"Gray Avenue\r\n" +
				"Greeley Street\r\n" +
				"Beach Avenue\r\n" +
				"South 4th Street\r\n" +
				"University Boulevard\r\n" +
				"South 16th Street\r\n" +
				"South Duff Avenue\r\n" +
				"South 5th Street\r\n" +
				"South Walnut Avenue\r\n" +
				"South 4th Street\r\n" +
				"South Oak Avenue\r\n" +
				"South 2nd Street\r\n" +
				"South Riverside Drive\r\n" +
				"Lincoln Way\r\n" +
				"Lynn Avenue\r\n" +
				"Union Drive\r\n", baos.toString());
	}
	
	@Test
	public void testmain2() {
        /*
        Here's a hack that makes sure the method prints the right thing.
        http://stackoverflow.com/a/8708357
        */
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		OSMMap.main2(null);
		assertEquals(763.012, Double.valueOf(baos.toString()), 2); // delta might be too strict
	}
	
	@Test
	public void testgetDistance() {
		assertEquals(0.0, DistanceCalculator.distance(42.990967, -71.463767, 42.990967, -71.463767, "M"));
		// Increase delta if needed
		assertEquals(5.52, DistanceCalculator.distance(42.910970, -71.463767, 42.990967, -71.463767, "M"), 0.1);
	}
}