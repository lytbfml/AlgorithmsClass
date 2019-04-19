package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import graph.IGraph.Edge;
import graph.IGraph.Vertex;
import graphalgorithms.GraphAlgorithms;
import graphalgorithms.IWeight;

public class AllTests
{
	@Before public void setUp() throws Exception
	{
		// Student init stuff
		g = new Graph<String,String>();
		g.setDirectedGraph();
		g.addVertex("d1");
		g.addVertex("d2");
		
		kg = new Graph();
		kg.setUndirectedGraph();
		
		for(char c = 'a';c < 'k';c++)
			kg.addVertex(String.valueOf(c));
		
		kg.addEdge("a","b",new Weighted(10));
		kg.addEdge("a","d",new Weighted(87));
		kg.addEdge("b","d",new Weighted(79));
		kg.addEdge("b","e",new Weighted(60));
		kg.addEdge("b","f",new Weighted(16));
		kg.addEdge("b","c",new Weighted(71));
		kg.addEdge("c","f",new Weighted(76));
		kg.addEdge("c","g",new Weighted(85));
		kg.addEdge("d","e",new Weighted(33));
		kg.addEdge("d","h",new Weighted(66));
		kg.addEdge("e","h",new Weighted(11));
		kg.addEdge("e","f",new Weighted(16));
		kg.addEdge("e","i",new Weighted(75));
		kg.addEdge("f","i",new Weighted(7));
		kg.addEdge("f","g",new Weighted(36));
		kg.addEdge("g","j",new Weighted(34));
		kg.addEdge("i","j",new Weighted(66));
		
		graph2 = new Graph<String,String>();
		
		graph3 = new Graph<String,String>();
		graph3.addVertex("v");
		
		graph4 = new Graph<String,String>();
		graph4.setDirectedGraph();
		
		graph4.addVertex("V1");
		graph4.addVertex("V2");
		graph4.addVertex("V3");
		graph4.addVertex("V4");
		
		graph4.addEdge("V1","V2");
		graph4.addEdge("V2","V3");
		graph4.addEdge("V3","V4");
		
		graph4.setVertexData("V1","VD1");
		graph4.setVertexData("V2","VD2");
		graph4.setVertexData("V3","VD3");
		graph4.setVertexData("V4","VD4");
		
		graph4.setEdgeData("V1","V2","ED1");
		graph4.setEdgeData("V2","V3","ED2");
		graph4.setEdgeData("V3","V4","ED3");
		
		graph5 = new Graph<String,String>();
		graph5.setUndirectedGraph();
		
		graph5.addVertex("V1","VD1");
		graph5.addVertex("V2","VD2");
		graph5.addVertex("V3","VD3");
		graph5.addVertex("V4","VD4");
		graph5.addVertex("V5","VD5");
		graph5.addVertex("V6","VD6");
		
		graph5.addEdge("V1","V4","ED1");
		graph5.addEdge("V1","V2","ED2");
		graph5.addEdge("V1","V3","ED3");
		graph5.addEdge("V3","V6","ED4");
		graph5.addEdge("V3","V5","ED5");
		
		graph6 = new Graph<String,String>();
		graph6.setDirectedGraph();
		
		for(int i = 0;i < 1000;i++)
			graph6.addVertex("V" + i);
		
		kg2 = new Graph<String,Weighted>();
		kg2.setUndirectedGraph();
		
		kg2.addVertex("A");
		kg2.addVertex("B");
		kg2.addVertex("C");
		kg2.addVertex("D");
		
		kg2.addEdge("A","B",new Weighted(1.0));
		kg2.addEdge("A","C",new Weighted(2.0));
		kg2.addEdge("C","D",new Weighted(3.0));
		kg2.addEdge("B","D",new Weighted(4.0));
		
		kg3 = new Graph<String,Weighted>();
		kg3.setUndirectedGraph();
		
		kg3.addVertex("A");
		kg3.addVertex("B");
		kg3.addVertex("C");
		kg3.addVertex("D");
		kg3.addVertex("E");
		
		kg3.addEdge("A","B",new Weighted(1.0));
		kg3.addEdge("A","D",new Weighted(1.0));
		kg3.addEdge("C","B",new Weighted(1.0));
		kg3.addEdge("A","C",new Weighted(2.0));
		kg3.addEdge("C","D",new Weighted(2.0));
		kg3.addEdge("B","E",new Weighted(2.0));
		kg3.addEdge("C","E",new Weighted(3.0));
		kg3.addEdge("D","E",new Weighted(4.0));
		
		tp0 = new Graph<List,Number>();
		tp0.setDirectedGraph();
		
		tp1 = new Graph<List,Number>();
		tp1.setDirectedGraph();
		
		tp1.addVertex("A");
		tp1.addVertex("B");
		
		tp1.addEdge("A","B");
		
		tp2 = new Graph<List,Number>();
		tp2.setDirectedGraph();
		
		tp2.addVertex("A");
		tp2.addVertex("B");
		tp2.addVertex("C");
		
		tp2.addEdge("A","C");
		
		tp3 = new Graph<List,Number>();
		tp3.setDirectedGraph();
		
		tp3.addVertex("A");
		tp3.addVertex("B");
		tp3.addVertex("C");
		tp3.addVertex("D");
		
		tp3.addEdge("A","B");
		tp3.addEdge("B","C");
		tp3.addEdge("C","D");
		
		tp4 = new Graph<List,Number>();
		tp4.setDirectedGraph();
		
		tp4.addVertex("A");
		tp4.addVertex("B");
		tp4.addVertex("C");
		tp4.addVertex("D");
		tp4.addVertex("E");
		
		tp4.addEdge("A","E");
		tp4.addEdge("B","E");
		tp4.addEdge("C","E");
		tp4.addEdge("D","E");
		
		tp5 = new Graph<List,Number>();
		tp5.setDirectedGraph();
		
		tp5.addVertex("A");
		tp5.addVertex("B");
		tp5.addVertex("C");
		tp5.addVertex("D");
		tp5.addVertex("E");
		tp5.addVertex("F");
		
		tp5.addEdge("A","B");
		tp5.addEdge("B","C");
		tp5.addEdge("B","D");
		tp5.addEdge("C","E");
		tp5.addEdge("D","E");
		tp5.addEdge("E","F");
		
		tp6 = new Graph<List,Number>();
		tp6.setDirectedGraph();
		
		tp6.addVertex("A");
		tp6.addVertex("B");
		tp6.addVertex("C");
		tp6.addVertex("D");
		tp6.addVertex("E");
		tp6.addVertex("F");
		tp6.addVertex("G");
		tp6.addVertex("H");
		tp6.addVertex("I");
		tp6.addVertex("J");
		
		tp6.addEdge("A","B");
		tp6.addEdge("B","C");
		tp6.addEdge("B","D");
		tp6.addEdge("B","E");
		tp6.addEdge("C","F");
		tp6.addEdge("D","G");
		tp6.addEdge("F","I");
		tp6.addEdge("G","I");
		tp6.addEdge("E","H");
		tp6.addEdge("H","J");
		
		tp7 = new Graph<List,Number>();
		tp7.setDirectedGraph();
		
		tp7.addVertex("A");
		tp7.addVertex("B");
		tp7.addVertex("C");
		tp7.addVertex("D");
		tp7.addVertex("E");
		
		tp7.addEdge("A","C");
		tp7.addEdge("B","C");
		tp7.addEdge("C","D");
		tp7.addEdge("C","E");
		
		atp1 = new Graph<List,Number>();
		atp1.setDirectedGraph();
		
		atp1.addVertex("A");
		atp1.addVertex("B");
		atp1.addVertex("C");
		atp1.addVertex("D");
		
		atp1.addEdge("A","B");
		atp1.addEdge("A","C");
		atp1.addEdge("B","D");
		atp1.addEdge("C","D");
		
		atp2 = new Graph<List,Number>();
		atp2.setDirectedGraph();
		
		atp2.addVertex("A");
		atp2.addVertex("B");
		atp2.addVertex("C");
		
		atp2.addEdge("A","C");
		atp2.addEdge("B","C");
		
		atp3 = new Graph<List,Number>();
		atp3.setDirectedGraph();
		
		atp3.addVertex("A");
		atp3.addVertex("B");
		atp3.addVertex("C");
		atp3.addVertex("D");
		atp3.addVertex("E");
		atp3.addVertex("F");
		
		atp3.addEdge("A","C");
		atp3.addEdge("B","C");
		atp3.addEdge("C","D");
		atp3.addEdge("C","E");
		atp3.addEdge("D","F");
		atp3.addEdge("E","F");
		
		atp4 = new Graph<List,Number>();
		atp4.setDirectedGraph();
		
		atp4.addVertex("A");
		atp4.addVertex("B");
		atp4.addVertex("C");
		atp4.addVertex("D");
		atp4.addVertex("E");
		atp4.addVertex("F");
		
		atp4.addEdge("A","B");
		atp4.addEdge("B","C");
		atp4.addEdge("D","E");
		atp4.addEdge("E","F");
		
		return;
	}
	
	// Regular tests
	@Test public void SetDirectedGraph()
	{
		graph2.setDirectedGraph();
		assertTrue(graph2.isDirectedGraph());
	}
	
	@Test public void SetUndirectedGraph()
	{
		graph2.setDirectedGraph();
		assertTrue(graph2.isDirectedGraph());
	}
	
	@Test public void AddVertex1()
	{
		assertEquals("v",graph3.getVertex("v").getVertexName());
	}
	
	@Test public void AddVertex2()
	{
		List<Vertex<String>> l = graph3.getVertices();
		
		assertEquals(1,l.size());
		assertEquals("v",l.get(0).getVertexName());
	}
	
	@Test public void AddVertex3()
	{
		assertEquals(0,graph3.getEdges().size());
	}
	
	@Test public void AddVertex4()
	{
		assertEquals(0,graph3.getNeighbors("v").size());
	}
	
	@Test public void LinkedList1() throws Exception
	{
		assertEquals("VD1",graph4.getVertexData("V1"));
		assertEquals("VD2",graph4.getVertexData("V2"));
		assertEquals("VD3",graph4.getVertexData("V3"));
		assertEquals("VD4",graph4.getVertexData("V4"));
	}
	
	@Test public void LinkedList2() throws Exception
	{
		assertEquals("ED1",graph4.getEdgeData("V1","V2"));
		assertEquals("ED2",graph4.getEdgeData("V2","V3"));
		assertEquals("ED3",graph4.getEdgeData("V3","V4"));
	}
	
	@Test public void LinkedList3()
	{
		assertEquals("V1",graph4.getVertex("V1").getVertexName());
		assertEquals("VD1",graph4.getVertex("V1").getVertexData());
	}
	
	@Test public void LinkedList4()
	{
		assertEquals("V2",graph4.getEdge("V2","V3").getVertexName1());
		assertEquals("V3",graph4.getEdge("V2","V3").getVertexName2());
		assertEquals("ED2",graph4.getEdge("V2","V3").getEdgeData());
	}
	
	@Test public void LinkedList5()
	{
		List<Vertex<String>> le = new ArrayList<Vertex<String>>(4);
		List<Vertex<String>> la = graph4.getVertices();
		
		le.add(new Vertex<String>("V1","VD1"));
		le.add(new Vertex<String>("V2","VD2"));
		le.add(new Vertex<String>("V3","VD3"));
		le.add(new Vertex<String>("V4","VD4"));
		
		for(Vertex<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getVertexData(),la.get(index).getVertexData());
		}
	}
	
	@Test public void LinkedList6()
	{
		List<Edge<String>> le = new ArrayList<Edge<String>>(3);
		List<Edge<String>> la = graph4.getEdges();
		
		le.add(new Edge<String>("V1","V2","ED1"));
		le.add(new Edge<String>("V2","V3","ED2"));
		le.add(new Edge<String>("V3","V4","ED3"));
		
		for(Edge<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getEdgeData(),la.get(index).getEdgeData());
		}
	}
	
	@Test public void LinkedList7()
	{
		List<Vertex<String>> l = graph4.getNeighbors("V2");
		
		assertEquals(1,l.size());
		assertEquals("V3",l.get(0).getVertexName());
	}
	
	@Test public void LinkedList8() throws Exception
	{
		graph4.setUndirectedGraph();
		
		graph4.getEdgeData("V1","V2");
		graph4.getEdgeData("V2","V1");
	}
	
	@Test public void HeadlessMan1() throws Exception
	{
		List<Vertex<String>> le = new ArrayList<Vertex<String>>(6);
		List<Vertex<String>> la = graph5.getVertices();
		
		le.add(new Vertex<String>("V1","VD1"));
		le.add(new Vertex<String>("V2","VD2"));
		le.add(new Vertex<String>("V3","VD3"));
		le.add(new Vertex<String>("V4","VD4"));
		le.add(new Vertex<String>("V5","VD5"));
		le.add(new Vertex<String>("V6","VD6"));
		
		for(Vertex<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getVertexData(),la.get(index).getVertexData());
		}
	}
	
	@Test public void HeadlessMan2() throws Exception
	{
		List<Edge<String>> le = new ArrayList<Edge<String>>(5);
		List<Edge<String>> la = graph5.getEdges();
		
		le.add(new Edge<String>("V1","V4","ED1"));
		le.add(new Edge<String>("V1","V2","ED2"));
		le.add(new Edge<String>("V1","V3","ED3"));
		le.add(new Edge<String>("V3","V6","ED4"));
		le.add(new Edge<String>("V3","V5","ED5"));
		
		for(Edge<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getEdgeData(),la.get(index).getEdgeData());
		}
	}
	
	@Test public void HeadlessMan3() throws Exception
	{
		Edge<String> e = graph5.getEdge("V1","V4");
		
		assertTrue(("V1".equals(e.getVertexName1()) && "V4".equals(e.getVertexName2())) || ("V4".equals(e.getVertexName1()) && "V1".equals(e.getVertexName2())));
		assertEquals("ED1",e.getEdgeData());
	}
	
	@Test public void HeadlessMan4() throws Exception
	{
		Edge<String> e = graph5.getEdge("V4","V1");
		
		assertTrue(("V1".equals(e.getVertexName1()) && "V4".equals(e.getVertexName2())) || ("V4".equals(e.getVertexName1()) && "V1".equals(e.getVertexName2())));
		assertEquals("ED1",e.getEdgeData());
	}
	
	@Test public void HeadlessMan5() throws Exception
	{
		List<Vertex<String>> le = new ArrayList<Vertex<String>>(3);
		List<Vertex<String>> la = graph5.getNeighbors("V1");
		
		le.add(new Vertex<String>("V2","VD2"));
		le.add(new Vertex<String>("V3","VD3"));
		le.add(new Vertex<String>("V4","VD4"));
		
		for(Vertex<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getVertexData(),la.get(index).getVertexData());
		}
	}
	
	@Test public void HeadlessMan6() throws Exception
	{
		List<Vertex<String>> le = new ArrayList<Vertex<String>>(3);
		List<Vertex<String>> la = graph5.getNeighbors("V3");
		
		le.add(new Vertex<String>("V1","VD1"));
		le.add(new Vertex<String>("V5","VD5"));
		le.add(new Vertex<String>("V6","VD6"));
		
		for(Vertex<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getVertexData(),la.get(index).getVertexData());
		}
	}
	
	@Test public void HeadlessMan7() throws Exception
	{
		graph5.setDirectedGraph(); // Crash test
	}
	
	@Test public void Explosion1() throws Exception
	{
		for(int i = 0;i < 1000;i++)
			assertEquals("V" + i,graph6.getVertex("V" + i).getVertexName());
	}
	
	@Test public void Explosion2() throws Exception
	{
		assertEquals(null,graph6.getVertex("V42").getVertexData());
	}
	
	@Test public void Explosion3() throws Exception
	{
		List<Edge<String>> le = new ArrayList<Edge<String>>(200);
		
		Random rand = new Random();
		
		for(int i = 0;i < 200;i++)
		{
			Edge<String> e = new Edge<String>("V" + rand.nextInt(1000),"V" + rand.nextInt(1000),"ED" + i);
			
			if(le.contains(e))
			{
				i--; // We failed and must restore our honor by committing
				// seppuku
				continue;
			}
			
			graph6.addEdge(e.getVertexName1(),e.getVertexName2(),"ED" + i);
			le.add(e);
		}
		
		List<Edge<String>> la = graph6.getEdges();
		
		for(Edge<String> v : le)
		{
			int index = la.indexOf(v);
			
			assertTrue(index > -1);
			assertEquals(v.getEdgeData(),la.get(index).getEdgeData());
		}
	}
	
	@Test public void Explosion4() throws Exception
	{
		List<List<Vertex<String>>> lle = new ArrayList<List<Vertex<String>>>(1000);
		
		for(int i = 0;i < 1000;i++)
			lle.add(new ArrayList<Vertex<String>>());
		
		Random rand = new Random();
		
		for(int i = 0;i < 200;i++)
		{
			int v1 = rand.nextInt(1000);
			int v2 = rand.nextInt(1000);
			
			Edge<String> e = new Edge<String>("V" + v1,"V" + v2,"ED" + i);
			
			if(lle.contains(e))
			{
				i--; // We failed and must restore our honor by committing
				// seppuku
				continue;
			}
			
			graph6.addEdge(e.getVertexName1(),e.getVertexName2(),"ED" + i);
			lle.get(v1).add(new Vertex<String>("V" + v2,null));
		}
		
		for(int i = 0;i < 1000;i++)
		{
			List<Vertex<String>> le = lle.get(i);
			List<Vertex<String>> la = graph6.getNeighbors("V" + i);
			
			for(Vertex<String> v : le)
				assertTrue(la.contains(v));
		}
	}
	
	// Student Graph tests follow (from Keane?)
	@Test public void setDirectedGraph() throws Exception
	{
		g.setDirectedGraph();
		assertTrue(g.isDirectedGraph());
	}
	
	@Test public void setUndirectedGraph() throws Exception
	{
		g.setUndirectedGraph();
		assertFalse(g.isDirectedGraph());
	}
	
	@Test public void isDirectedGraph() throws Exception
	{
		g.setDirectedGraph();
		assertTrue/* assertFalse */(g.isDirectedGraph());
	}
	
	@Test public void addVertex() throws Exception
	{
		IGraph.Vertex v = new IGraph.Vertex("testVertex",null);
		g.addVertex("testVertex");
		assertEquals(v,g.getVertex("testVertex"));
	}
	
	@Test(expected = IGraph.DuplicateVertexException.class) public void addVertexDup() throws Exception
	{
		g.addVertex("d1");
	}
	
	@Test public void addVertex1() throws Exception
	{
		IGraph.Vertex v = new IGraph.Vertex("testVertex","testData");
		g.addVertex("testVertex","testData");
		assertEquals(v,g.getVertex("testVertex"));
	}
	
	@Test(expected = IGraph.DuplicateVertexException.class) public void addVertexDup1() throws Exception
	{
		g.addVertex("d1","testDatablah");
	}
	
	@Test public void addEdge() throws Exception
	{
		IGraph.Edge e = new IGraph.Edge("d1","d2",null);
		g.addEdge("d1","d2");
		assertEquals(e,g.getEdge("d1","d2"));
	}
	
	@Test(expected = IGraph.DuplicateEdgeException.class) public void addEdgeDup() throws Exception
	{
		g.addEdge("d1","d2");
		g.addEdge("d1","d2");
	}
	
	@Test(expected = IGraph.DuplicateEdgeException.class) public void addEdgeDup2() throws Exception
	{
		g.setUndirectedGraph();
		g.addEdge("d1","d2");
		g.addEdge("d2","d1");
	}
	
	@Test public void addEdgeDup2Directed() throws Exception
	{
		g.setDirectedGraph();
		g.addEdge("d1","d2");
		g.addEdge("d2","d1");
	}
	
	@Test(expected = IGraph.DuplicateEdgeException.class) public void addEdgeDup1() throws Exception
	{
		g.addEdge("d1","d2","data");
		g.addEdge("d1","d2","datar");
	}
	
	@Test(expected = IGraph.NoSuchVertexException.class) public void addEdgeBad() throws Exception
	{
		g.addEdge("d1","da2");
	}
	
	@Test public void addEdge1() throws Exception
	{
		IGraph.Edge e = new IGraph.Edge("d1","d2","data");
		g.addEdge("d1","d2","data");
		assertEquals(e,g.getEdge("d1","d2"));
	}
	
	@Test public void getVertexData() throws Exception
	{
		IGraph.Vertex v = new IGraph.Vertex("testV","testData");
		g.addVertex("testV","testData");
		assertEquals("testData",g.getVertexData("testV"));
	}
	
	@Test public void setVertexData() throws Exception
	{
		g.addVertex("testV","testData");
		g.setVertexData("testV","testDataNew");
		assertEquals("testDataNew",g.getVertexData("testV"));
	}
	
	@Test public void getEdgeData() throws Exception
	{
		IGraph.Edge e = new IGraph.Edge("d1","d2","data");
		g.setUndirectedGraph();
		g.addEdge("d1","d2","data");
		assertEquals("data",g.getEdgeData("d1","d2"));
		assertEquals("data",g.getEdgeData("d2","d1"));
	}
	
	@Test public void setEdgeData() throws Exception
	{
		g.addEdge("d1","d2","testData");
		g.setEdgeData("d1","d2","testDataNew");
		assertEquals("testDataNew",g.getEdgeData("d1","d2"));
	}
	
	@Test public void getVertex() throws Exception
	{
		IGraph.Vertex v = new IGraph.Vertex("testV","testData");
		g.addVertex("testV","testData");
		assertEquals(v,g.getVertex("testV"));
	}
	
	@Test public void getEdge() throws Exception
	{
		IGraph.Edge e = new IGraph.Edge("d1","d2","testData");
		g.addEdge("d1","d2","testData");
		assertEquals(e,g.getEdge("d1","d2"));
		// assertEquals(e,g.getEdge("d2","d1")); // This is trivially false, as
		// the d2 -> d1 edge is not the same as d1 -> d2, even if the graph is
		// undirected (they MEAN the same thing, but are not equal as far as
		// .equals is concerned)
	}
	
	@Test public void getVertices() throws Exception
	{
		IGraph.Vertex v0 = new IGraph.Vertex("testV0","testData");
		IGraph.Vertex v1 = new IGraph.Vertex("testV1","testData");
		IGraph.Vertex v2 = new IGraph.Vertex("testV2","testData");
		IGraph.Vertex v3 = new IGraph.Vertex("testV3","testData");
		g.addVertex("testV0");
		g.addVertex("testV1");
		g.addVertex("testV2");
		g.addVertex("testV3");
		List<IGraph.Vertex> l = g.getVertices();
		assertTrue(l.contains(v0));
		assertTrue(l.contains(v1));
		assertTrue(l.contains(v2));
		assertTrue(l.contains(v3));
	}
	
	@Test public void getEdges() throws Exception
	{
		g.addVertex("testV0");
		g.addVertex("testV1");
		g.addVertex("testV2");
		g.addVertex("testV3");
		g.addVertex("v0");
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		IGraph.Edge e0 = new IGraph.Edge("testV0","v0",null);
		IGraph.Edge e1 = new IGraph.Edge("testV1","v1",null);
		IGraph.Edge e2 = new IGraph.Edge("testV2","v2",null);
		IGraph.Edge e3 = new IGraph.Edge("testV3","v3",null);
		g.addEdge("testV0","v0");
		g.addEdge("testV1","v1");
		g.addEdge("testV2","v2");
		g.addEdge("testV3","v3");
		List<IGraph.Edge> l = g.getEdges();
		assertTrue(l.contains(e0));
		assertTrue(l.contains(e1));
		assertTrue(l.contains(e2));
		assertTrue(l.contains(e3));
	}
	
	@Test public void getNeighborsUndirected() throws Exception
	{
		g.addVertex("testV0");
		g.addVertex("testV1");
		g.addVertex("testV2");
		g.addVertex("testV3");
		g.addVertex("v0");
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		IGraph.Vertex v0 = new IGraph.Vertex("v0","testData");
		IGraph.Vertex v1 = new IGraph.Vertex("v1","testData");
		IGraph.Vertex v2 = new IGraph.Vertex("testV2","testData");
		g.addEdge("testV0","v0");
		g.addEdge("testV0","v1");
		g.addEdge("testV2","testV0"); // testV2 is NOT a neighbor of testV0, as
		// this is a directed edge INTO testV0
		g.addEdge("testV3","v3");
		List<IGraph.Vertex> l = g.getNeighbors("testV0");
		assertTrue(l.contains(v0));
		assertTrue(l.contains(v1));
		// assertTrue(l.contains(v2));
		assertEquals(/* 3 */2,l.size());
	}
	
	@Test public void getNeighborsDirected() throws Exception
	{
		g.setDirectedGraph();
		g.addVertex("testV0");
		g.addVertex("testV1");
		g.addVertex("testV2");
		g.addVertex("testV3");
		g.addVertex("v0");
		g.addVertex("v1");
		g.addVertex("v2");
		g.addVertex("v3");
		IGraph.Vertex v0 = new IGraph.Vertex("v0","testData");
		IGraph.Vertex v1 = new IGraph.Vertex("v1","testData");
		g.addEdge("testV0","v0");
		g.addEdge("testV0","v1");
		g.addEdge("testV2","testV0");
		g.addEdge("testV1","testV0");
		g.addEdge("testV3","v3");
		List<IGraph.Vertex> l = g.getNeighbors("testV0");
		assertTrue(l.contains(v0));
		assertTrue(l.contains(v1));
		assertEquals(2,l.size());
	}
	
	@Test public void kruscal() throws Exception
	{
		IGraph<String,Weighted> k = GraphAlgorithms.Kruscal(kg);
		assertEquals(9,k.getEdges().size());


		assertNull(k.getEdge("d","b"));
		assertNull(k.getEdge("a","d"));
		assertNull(k.getEdge("d","h"));
		assertNull(k.getEdge("e","i"));
		assertNull(k.getEdge("e","b"));
		assertNull(k.getEdge("i","j"));
		assertNull(k.getEdge("f","c"));
		assertNull(k.getEdge("c","g"));
		assertNotNull(k.getEdge("a","b"));
		assertNotNull(k.getEdge("d","e"));
		assertNotNull(k.getEdge("e","h"));
		assertNotNull(k.getEdge("b","f"));
		assertNotNull(k.getEdge("f","i"));
		assertNotNull(k.getEdge("f","e"));
		assertNotNull(k.getEdge("g","j"));
		assertNotNull(k.getEdge("c","b"));
		assertNotNull(k.getEdge("f","g"));
	}
	
	private static class Weighted implements IWeight
	{
		double weight;
		
		public Weighted(double weight)
		{
			this.weight = weight;
		}
		
		public double getWeight()
		{
			return weight;
		}
		
		public boolean equals(Object obj)
		{
			if(obj == null)
				return false;
			if(this == obj)
				return true;
			if(getClass() != obj.getClass())
				return false;
			return weight == ((Weighted)obj).weight;
		}
	}
	
	// Algorithm tests
	@Test public void Krusal1() throws Exception
	{
		IGraph<String,Weighted> kgr = GraphAlgorithms.Kruscal(kg2);
		
		List<Vertex<String>> ve = kg2.getVertices();
		List<Vertex<String>> va = kgr.getVertices();
		
		assertEquals(ve.size(),va.size());
		
		for(Vertex<String> v : ve)
			assertTrue(va.contains(v));
		
		assertEquals(3,kgr.getEdges().size());
		
		// This will error if the edges don't exist
		kgr.getEdgeData("A","B");
		kgr.getEdgeData("A","C");
		kgr.getEdgeData("C","D");
	}
	
	@Test public void Krusal2() throws Exception
	{
		IGraph<String,Weighted> kgr = GraphAlgorithms.Kruscal(kg3);
		
		List<Vertex<String>> ve = kg3.getVertices();
		List<Vertex<String>> va = kgr.getVertices();
		
		assertEquals(ve.size(),va.size());
		
		for(Vertex<String> v : ve)
			assertTrue(va.contains(v));
		
		assertEquals(4,kgr.getEdges().size());
		
		// This will error if the edges don't exist
		kgr.getEdgeData("A","D");
		kgr.getEdgeData("A","C");
		kgr.getEdgeData("A","B");
		kgr.getEdgeData("E","B");
	}
	
	@Test public void TPS0() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp0);
		
		assertEquals(0,tpr.size());
	}
	
	@Test public void TPS1() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp1);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		
		assertEquals(2,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(B));
	}
	
	@Test public void TPS2() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp2);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		
		assertEquals(3,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(C));
	}
	
	@Test public void TPS3() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp3);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		
		assertEquals(4,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(B));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(C));
		assertTrue(tpr.indexOf(C) < tpr.indexOf(D));
	}
	
	@Test public void TPS4() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp4);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		Vertex<List> E = new Vertex<List>("E",null);
		
		assertEquals(5,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(E));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(E));
		assertTrue(tpr.indexOf(C) < tpr.indexOf(E));
		assertTrue(tpr.indexOf(D) < tpr.indexOf(E));
	}
	
	@Test public void TPS5() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp5);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		Vertex<List> E = new Vertex<List>("E",null);
		Vertex<List> F = new Vertex<List>("F",null);
		
		assertEquals(6,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(B));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(C));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(D));
		assertTrue(tpr.indexOf(C) < tpr.indexOf(E));
		assertTrue(tpr.indexOf(D) < tpr.indexOf(E));
		assertTrue(tpr.indexOf(E) < tpr.indexOf(F));
	}
	
	@Test public void TPS6() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp6);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		Vertex<List> E = new Vertex<List>("E",null);
		Vertex<List> F = new Vertex<List>("F",null);
		Vertex<List> G = new Vertex<List>("G",null);
		Vertex<List> H = new Vertex<List>("H",null);
		Vertex<List> I = new Vertex<List>("I",null);
		Vertex<List> J = new Vertex<List>("J",null);
		
		assertEquals(10,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(B));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(C));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(D));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(E));
		assertTrue(tpr.indexOf(C) < tpr.indexOf(F));
		assertTrue(tpr.indexOf(D) < tpr.indexOf(G));
		assertTrue(tpr.indexOf(F) < tpr.indexOf(I));
		assertTrue(tpr.indexOf(G) < tpr.indexOf(I));
		assertTrue(tpr.indexOf(E) < tpr.indexOf(H));
		assertTrue(tpr.indexOf(H) < tpr.indexOf(J));
	}
	
	@Test public void TPS7() throws Exception
	{
		List<Vertex<List>> tpr = GraphAlgorithms.TopologicalSort(tp7);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		Vertex<List> E = new Vertex<List>("E",null);
		
		assertEquals(5,tpr.size());
		assertTrue(tpr.indexOf(A) < tpr.indexOf(C));
		assertTrue(tpr.indexOf(B) < tpr.indexOf(C));
		assertTrue(tpr.indexOf(C) < tpr.indexOf(D));
		assertTrue(tpr.indexOf(C) < tpr.indexOf(E));
	}
	
	@Test public void ATPS1() throws Exception
	{
		List<List<Vertex<List>>> tpr = GraphAlgorithms.AllTopologicalSort(atp1);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		
		List<List<Vertex<List>>> tpe = new ArrayList<List<Vertex<List>>>(2);
		
		List<Vertex<List>> s = new ArrayList<Vertex<List>>(4);
		s.add(A);
		s.add(B);
		s.add(C);
		s.add(D);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(4);
		s.add(A);
		s.add(C);
		s.add(B);
		s.add(D);
		tpe.add(s);
		
		assertEquals(tpe.size(),tpr.size());
		assertEquals(tpr, tpe);
	}
	
	@Test public void ATPS2() throws Exception
	{
		List<List<Vertex<List>>> tpr = GraphAlgorithms.AllTopologicalSort(atp2);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		
		List<List<Vertex<List>>> tpe = new ArrayList<List<Vertex<List>>>(2);
		
		List<Vertex<List>> s = new ArrayList<Vertex<List>>(3);
		s.add(A);
		s.add(B);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(3);
		s.add(B);
		s.add(A);
		s.add(C);
		tpe.add(s);
		
		assertEquals(tpe.size(),tpr.size());
		
		assertEquals(tpr, tpe);
	}
	
	@Test public void ATPS3() throws Exception
	{
		List<List<Vertex<List>>> tpr = GraphAlgorithms.AllTopologicalSort(atp3);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		Vertex<List> E = new Vertex<List>("E",null);
		Vertex<List> F = new Vertex<List>("F",null);
		
		List<List<Vertex<List>>> tpe = new ArrayList<List<Vertex<List>>>(4);
		
		List<Vertex<List>> s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(B);
		s.add(C);
		s.add(D);
		s.add(E);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(B);
		s.add(A);
		s.add(C);
		s.add(D);
		s.add(E);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(B);
		s.add(C);
		s.add(E);
		s.add(D);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(B);
		s.add(A);
		s.add(C);
		s.add(E);
		s.add(D);
		s.add(F);
		tpe.add(s);
		
		assertEquals(tpe.size(),tpr.size());
		
		for(int i = 0;i < tpe.size();i++)
			assertTrue(tpr.contains(tpe.get(i)));
	}
	
	@Test public void ATPS4() throws Exception
	{
		List<List<Vertex<List>>> tpr = GraphAlgorithms.AllTopologicalSort(atp4);
		
		Vertex<List> A = new Vertex<List>("A",null);
		Vertex<List> B = new Vertex<List>("B",null);
		Vertex<List> C = new Vertex<List>("C",null);
		Vertex<List> D = new Vertex<List>("D",null);
		Vertex<List> E = new Vertex<List>("E",null);
		Vertex<List> F = new Vertex<List>("F",null);
		
		List<List<Vertex<List>>> tpe = new ArrayList<List<Vertex<List>>>(20);
		
		List<Vertex<List>> s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(B);
		s.add(C);
		s.add(D);
		s.add(E);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(B);
		s.add(D);
		s.add(C);
		s.add(E);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(D);
		s.add(B);
		s.add(C);
		s.add(E);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(A);
		s.add(B);
		s.add(C);
		s.add(E);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(B);
		s.add(D);
		s.add(E);
		s.add(C);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(D);
		s.add(B);
		s.add(E);
		s.add(C);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(D);
		s.add(E);
		s.add(B);
		s.add(C);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(A);
		s.add(B);
		s.add(E);
		s.add(C);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(A);
		s.add(E);
		s.add(B);
		s.add(C);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(E);
		s.add(A);
		s.add(B);
		s.add(C);
		s.add(F);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(B);
		s.add(D);
		s.add(E);
		s.add(F);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(D);
		s.add(B);
		s.add(E);
		s.add(F);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(D);
		s.add(E);
		s.add(B);
		s.add(F);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(A);
		s.add(D);
		s.add(E);
		s.add(F);
		s.add(B);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(A);
		s.add(B);
		s.add(E);
		s.add(F);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(A);
		s.add(E);
		s.add(B);
		s.add(F);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(A);
		s.add(E);
		s.add(F);
		s.add(B);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(E);
		s.add(A);
		s.add(B);
		s.add(F);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(E);
		s.add(A);
		s.add(F);
		s.add(B);
		s.add(C);
		tpe.add(s);
		
		s = new ArrayList<Vertex<List>>(6);
		s.add(D);
		s.add(E);
		s.add(F);
		s.add(A);
		s.add(B);
		s.add(C);
		tpe.add(s);
		
		assertEquals(tpe.size(),tpr.size());
		
		for(int i = 0;i < tpe.size();i++)
			assertTrue(tpr.contains(tpe.get(i)));
	}
	
	Graph g;
	Graph<String,Weighted> kg;
	Graph<String,String> graph2;
	Graph<String,String> graph3;
	Graph<String,String> graph4;
	Graph<String,String> graph5;
	Graph<String,String> graph6;
	
	Graph<String,Weighted> kg2;
	Graph<String,Weighted> kg3;
	
	Graph<List,Number> tp0;
	Graph<List,Number> tp1;
	Graph<List,Number> tp2;
	Graph<List,Number> tp3;
	Graph<List,Number> tp4;
	Graph<List,Number> tp5;
	Graph<List,Number> tp6;
	Graph<List,Number> tp7;
	
	Graph<List,Number> atp1;
	Graph<List,Number> atp2;
	Graph<List,Number> atp3;
	Graph<List,Number> atp4;
}