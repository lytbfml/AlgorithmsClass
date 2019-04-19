/**
 * Created by Yangxiao on 12/6/2016.
 */
import graph.Graph;
import graph.IGraph;
import graphalgorithms.IWeight;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static graphalgorithms.GraphAlgorithms.ShortestPath;


public class OSMMap {
	/**
	 * Test LoadMap
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		OSMMap op = new OSMMap();
		op.LoadMap("AmesMap.txt");
	}
	
	/**
	 * Output the approximate miles of road in Ames
	 *
	 * @param args
	 */
	public static void main2(String[] args) {
		OSMMap op = new OSMMap();
		op.LoadMap("src/cs311/hw8/AmesMap.txt");
		System.out.println(op.TotalDistance());
	}
	
	/**
	 * Receive input from command line, and output the sequence of street names that gives the
	 * shortest route that travels
	 * through the latitude and longitude pairs in the order read from the file.
	 *
	 * @param args
	 */
	public static void main3(String[] args) {
		OSMMap op = new OSMMap();
		String mapF = args[0];
		String inputF = args[1];
		
		//		System.out.println(mapF + " " + inputF);
		
		Scanner scan = null;
		try {
			scan = new Scanner(new File(inputF));
			List<Location> routeList = new ArrayList<>();
			
			//scan all location
			while (scan.hasNext()) {
				String lat = scan.next();
				String lon = scan.next();
				double latd = Double.parseDouble(lat);
				double lond = Double.parseDouble(lon);
				routeList.add(new Location(latd, lond));
			}
			long time = System.nanoTime();
			op.LoadMap(mapF);
			
			List<String> list = new ArrayList<>();
			for (int i = 0; i < routeList.size() - 1; i++) {
				list.addAll(op.ShortestRoute(routeList.get(i), routeList.get(i + 1)));
			}
			List<String> list2 = new ArrayList<>();
			list2 = op.StreetRoute(list);
			
			for (String s : list2) {
				System.out.println(s);
			}
			//			System.out.println((System.nanoTime() - time) / 1000000000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Graph and its edges and vertices
	private Graph<vertexData, edgeDataT> g;
	private List<IGraph.Vertex<vertexData>> vertices;
	private List<IGraph.Edge<edgeDataT>> edges;
	
	/**
	 * Constructor that initialize an empty graph
	 */
	public OSMMap() {
		this.g = new Graph<>();
		g.setDirectedGraph();
	}
	
	/**
	 * Load map from a file and create graph to store it
	 *
	 * @param filename xml file name
	 */
	public void LoadMap(String filename) {
		g = new Graph<>();
		g.setDirectedGraph();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(filename));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		if (doc == null || builder == null) {
			System.out.println("Error!!!");
			return;
		}
		
		doc.getDocumentElement().normalize();
		//get all nodes
		NodeList nList = doc.getElementsByTagName("node");
		int nLength = nList.getLength();
		
		//Add all nodes to graph as vertices
		for (int i = 0; i < nLength; i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String nodeID = eElement.getAttribute("id");
				double lat = Double.parseDouble(eElement.getAttribute("lat"));
				double lon = Double.parseDouble(eElement.getAttribute("lon"));
				g.addVertex(nodeID, new vertexData(lat, lon));
			}
		}
		
		//Get all way nodes
		NodeList wList = doc.getElementsByTagName("way");
		int wLength = wList.getLength();
		
		//Find all way nodes with highway and name
		for (int i = 0; i < wLength; i++) {
			Node wNode = wList.item(i);
			boolean oneWay = false;
			int isEdge = 0;
			String edName = "";
			
			if (wNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) wNode;
				//search highway and name attributes for each way node
				NodeList tagList = eElement.getElementsByTagName("tag");
				int tagLen = tagList.getLength();
				for (int j = 0; j < tagLen; j++) {
					Node tNode = tagList.item(j);
					
					if (tNode.getNodeType() == Node.ELEMENT_NODE) {
						Element tEl = (Element) tNode;
						
						String k = tEl.getAttribute("k");
						
						if (k.equals("highway")) {
							isEdge += 1;
						}
						
						if (k.equals("name")) {
							isEdge += 1;
							edName = tEl.getAttribute("v");
						}
						
						if (k.equals("oneway")) {
							if (tEl.getAttribute("v").equals("yes")) {
								oneWay = true;
							}
						}
					}
				}
				//If name and highwat found add to edges
				if (isEdge >= 2) {
					ArrayList<String> listVer = new ArrayList<String>();
					//Get all vertex in current way node
					NodeList nListEdge = eElement.getElementsByTagName("nd");
					int nELen = nListEdge.getLength();
					for (int j = 0; j < nELen; j++) {
						Node ndNode = nListEdge.item(j);
						if (ndNode.getNodeType() == Node.ELEMENT_NODE) {
							Element nEl = (Element) ndNode;
							listVer.add(nEl.getAttribute("ref"));
						}
					}
					//Add edges to graph
					for (int j = 0; j < listVer.size() - 1; j++) {
						String name1 = listVer.get(j);
						String name2 = listVer.get(j + 1);
						IGraph.Vertex<vertexData> v1 = g.getVertex(name1);
						IGraph.Vertex<vertexData> v2 = g.getVertex(name2);
						double dis = DistanceCalculator.distance(v1.getVertexData().getLat(),
						                                         v1.getVertexData()
								                                         .getLon(),
						                                         v2.getVertexData().getLat(),
						                                         v2.getVertexData().getLon(), "M");
						g.addEdge(name1, name2, new edgeDataT(dis, edName));
						if (!oneWay) {
							g.addEdge(name2, name1, new edgeDataT(dis, edName));
						}
					}
				}
			}
		}
		//Store vertices and edges in Graph g
		vertices = g.getVertices();
		edges = g.getEdges();
	}
	
	/**
	 * @return miles of roadway
	 */
	public int TotalDistance() {
		double total = 0;
		
		for (IGraph.Edge<edgeDataT> edge : edges) {
			total += edge.getEdgeData().getWeight();
		}
		int re = (int) (total / 2);
		return re;
	}
	
	/**
	 * Found closest vertex near Location loc
	 *
	 * @param loc Location that have lat and lon
	 * @return
	 */
	public String ClosestRoad(Location loc) {
		List<IGraph.Vertex<vertexData>> vList = vertices;
		double dis = Double.MAX_VALUE;
		String closestID = "";
		double lat = loc.getLatitude();
		double lon = loc.getLongitude();
		
		for (IGraph.Vertex<vertexData> vertex : vList) {
			if (g.getNeighbors(vertex.getVertexName()).size() > 0) {
				double temp = DistanceCalculator.distance(lat, lon, vertex.getVertexData().getLat(),
				                                          vertex.getVertexData().getLon(),
				                                          "M");
				if (temp < dis) {
					dis = temp;
					closestID = vertex.getVertexName();
				}
			}
		}
		
		return closestID;
	}
	
	/**
	 * Find shortest route from fromLocation to toLocation
	 *
	 * @param fromLocation start point
	 * @param toLocation   end point
	 * @return list of string that contains
	 */
	public List<String> ShortestRoute(Location fromLocation, Location toLocation) {
		String start = ClosestRoad(fromLocation);
		String end = ClosestRoad(toLocation);
		
		List<IGraph.Edge<edgeDataT>> path = ShortestPath(g, start, end);
		List<String> pList = new ArrayList<>();
		pList.add(path.get(0).getVertexName1());
		
		for (IGraph.Edge<edgeDataT> edge : path) {
			pList.add(edge.getVertexName2());
		}
		
		return pList;
	}
	
	/**
	 * @param vList list of strings that contains ids
	 * @return List of strings that contains streets name
	 */
	public List<String> StreetRoute(List<String> vList) {
		List<String> sList = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < vList.size() - 1; i++) {
			String v1 = vList.get(i);
			String v2 = vList.get(i + 1);
			
			if (v1.equals(v2)) {
				continue;
			}
			
			edgeDataT data = g.getEdgeData(v1, v2);
			String street = data.getStreet();
			if (count == 0) {
				sList.add(street);
				count += 1;
			} else if (!sList.get(count - 1).equals(street)) {
				sList.add(street);
				count += 1;
			}
		}
		return sList;
	}
	
	/**
	 * Find approximate tour
	 *
	 * @param IDs list of ids that represent path
	 * @return TSP tour list
	 */
	//     0.   Using a cloesest neighbor algorithm to find TSP
	//	   1.   V = {1, ..., n-1}          // Vertices except for 0.
	//     2.   U = {0}                    // Vertex 0.
	//     3.   while V not empty
	//     4.      u = most recently added vertex to U
	//     5.      Find vertex v in V closest to u
	//     6.      Add v to U and remove v from V.
	//     7.   endwhile
	//     8.   Output vertices in the order they were added to U
	public List<String> ApproximateTSP(List<String> IDs) {
		List<IGraph.Vertex<vertexData>> vertices = g.getVertices();
		List<IGraph.Edge<edgeDataT>> edges = g.getEdges();
		List<String> path = new ArrayList<>(IDs);
		List<String> tour = new ArrayList<>();
		String start = IDs.get(0);
		path.remove(start);
		tour.add(start);
		
		int i = 0;
		while (!path.isEmpty()) {
			double min = Double.MAX_VALUE;
			String next = "";
			String current = tour.get(i);
			i++;
			List<IGraph.Vertex<vertexData>> vlist = g.getNeighbors(current);
			for (IGraph.Vertex<vertexData> vv : vlist) {
				double data = g.getEdgeData(current, vv.getVertexName()).getWeight();
				if (min > data) {
					min = data;
					next = vv.getVertexName();
				}
			}
			tour.add(next);
			path.remove(next);
		}
		return tour;
	}
	
	public Graph<vertexData, edgeDataT> getGraph() {
		return g;
	}
	
	/**
	 * Generic data "V" that store lat and lon
	 */
	public static class vertexData {
		private double lat;
		private double lon;
		
		public vertexData(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
		
		public double getLat() {
			return lat;
		}
		
		public double getLon() {
			return lon;
		}
	}
	
	/**
	 * Generic data "E"
	 */
	public static class edgeDataT implements IWeight {
		
		private double distance;
		private String streetN;
		
		edgeDataT() {
			this(0.0, "");
		}
		
		edgeDataT(double dis, String name) {
			this.distance = dis;
			this.streetN = name;
		}
		
		public String getStreet() {
			return streetN;
		}
		
		public double getDis() {
			return distance;
		}
		
		@Override
		public double getWeight() {
			return distance;
		}
	}
	
	/**
	 * Location class that store lat and lon
	 */
	public static class Location {
		double lat;
		double lon;
		
		public Location(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
		
		// returns the latitude of the location
		public double getLatitude() {
			return lat;
		}
		
		// returns the longitude of the location
		public double getLongitude() {
			return lon;
		}
	}
}
