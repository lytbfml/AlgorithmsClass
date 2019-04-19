import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Yangxiao Wang, Cheng Song
 */
public class GraphProcessor {
	//Structure to store graph data
	LinkedHashMap<String, LinkedList<String>> graph;
	//Contains all the strongly connected components
	ArrayList<ArrayList<String>> comp;
	
	/**
	 * Constructor
	 *
	 * @param graphData graphData hold the absolute path of a file that stores a
	 *                  directed graph.
	 */
	public GraphProcessor(String graphData) {
		Scanner scan = null;
		comp = new ArrayList<>();
		try {
			scan = new Scanner(new File(graphData));
			int numV = scan.nextInt();
			graph = new LinkedHashMap<>();
			
			while (scan.hasNext()) {
				String v1 = scan.next();
				String v2 = scan.next();
				if (graph.containsKey(v1)) {
					graph.get(v1).add(v2);
				} else {
					graph.put(v1, new LinkedList<String>(Arrays.asList(v2)));
				}
				
				if (!graph.containsKey(v2)) {
					graph.put(v2, new LinkedList<String>());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (scan != null) {
			scan.close();
		}
		
		getComp();
	}
	
	
	/**
	 * Returns the out degree of v
	 *
	 * @param v The vertex that the method investigate
	 * @return the out degree of the vertex v
	 */
	public int outDegree(String v) {
		if (!graph.containsKey(v)) {
			return -1;
		}
		return graph.get(v).size();
	}
	
	
	/**
	 * @param u
	 * @param v
	 * @return Returns true if u and v belong to the same SCC; otherwise returns false
	 */
	public boolean sameComponent(String u, String v) {
		for (ArrayList<String> arr : comp) {
			if (arr.contains(u) && arr.contains(v)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Return all the vertices that belong to the same Strongly Connected Component of v (including
	 * v).
	 * This method must return an array list of Strings, thus the return type is ArrayList<String>
	 *
	 * @param v Vertex
	 * @return an ArrayList<String> of all vertices that belong to the same Strongly Connected
	 * Component of v
	 */
	public ArrayList<String> componentVertices(String v) {
		ArrayList<String> re = new ArrayList<>();
		
		for (ArrayList<String> arr : comp) {
			if (arr.contains(v)) {
				re = new ArrayList<>(arr);
			}
		}
		return re;
	}
	
	/**
	 * Helper method that generates all component in the graph using Kosaraju's algorithm
	 * 1. Do dfs traversal over the graph and add processed vertices in a stack
	 * 2. Reverse the direction of all edges to get new graph <newG>
	 * 3. Pop a vertex from stack and do dfs on the popped vertex, add the current vertex to an
	 * array
	 * 4. Add the finsihed array to <comp> and repeat step 3
	 */
	private void getComp() {
		ArrayList<String> re = new ArrayList<>();
		//0 = unvisited, 1 = visited
		HashMap<String, Integer> visited = new HashMap<>();
		List<String> vertices = new ArrayList<String>(graph.keySet());
		Stack<String> stack = new Stack<>();
		
		for (String vertex : vertices) {
			visited.put(vertex, 0);
		}
		
		for (Map.Entry<String, Integer> entry : visited.entrySet()) {
			if (entry.getValue() == 0)
				visit(entry.getKey(), visited, stack, graph);
		}
		
		//The new edge-reversed graph
		LinkedHashMap<String, LinkedList<String>> newG = reverse();
		for (String vertex : vertices) {
			visited.put(vertex, 0);
		}
		
		while (!stack.isEmpty()) {
			String temp = stack.pop();
			if (visited.get(temp) == 0) {
				ArrayList<String> tempArr = new ArrayList<>();
				dfs(temp, visited, newG, tempArr);
				comp.add(tempArr);
			}
		}
	}
	
	/**
	 * Perform DFS traversal over the reversed graph, it add current node to stack and then finding
	 * its adjacent
	 *
	 * @param v       Starting vertex v
	 * @param visited HashMap tracking for visited
	 * @param newG    the reversed graph
	 * @param tempArr Array to store the current Strongly connected component
	 */
	private void dfs(String v, HashMap<String, Integer> visited,
	                 LinkedHashMap<String, LinkedList<String>> newG,
	                 ArrayList<String> tempArr) {
		visited.put(v, 1);
		tempArr.add(v);
		LinkedList<String> adjacent = new LinkedList<>(newG.get(v));
		for (String x : adjacent) {
			if (visited.get(x) != 1) {
				dfs(x, visited, newG, tempArr);
			}
		}
	}
	
	/**
	 * Reverse the direction of all edges and return the new graph
	 *
	 * @return the reversed graph
	 */
	private LinkedHashMap<String, LinkedList<String>> reverse() {
		LinkedHashMap<String, LinkedList<String>> newG = new LinkedHashMap<>();
		for (Map.Entry<String, LinkedList<String>> entry : graph.entrySet()) {
			if (!newG.containsKey(entry.getKey())) {
				newG.put(entry.getKey(), new LinkedList<String>());
			}
			
			LinkedList<String> ad = entry.getValue();
			for (String v : ad) {
				if (newG.containsKey(v)) {
					newG.get(v).add(entry.getKey());
				} else {
					newG.put(v, new LinkedList<String>(Arrays.asList(entry.getKey())));
				}
				
			}
		}
		return newG;
	}
	
	/**
	 * Helper method that do DFS on the graph, push the processed vertex to stack
	 *
	 * @param v
	 * @param visited
	 * @param stack   stack that storing the dfs
	 * @param graph
	 */
	private static void visit(String v, HashMap<String, Integer> visited, Stack<String> stack,
	                          LinkedHashMap<String, LinkedList<String>> graph) {
		if (visited.get(v) == 1) {
			return;
		}
		visited.put(v, 1);
		
		LinkedList<String> adjacent = new LinkedList<>(graph.get(v));
		for (String x : adjacent) {
			if (visited.get(x) == 0) {
				visit(x, visited, stack, graph);
			}
		}
		stack.push(v);
	}
	
	
	/**
	 * @return the size of the largest component
	 */
	public int largestComponent() {
		int max = -1;
		for (ArrayList<String> s : comp) {
			int len = s.size();
			if (len > max)
				max = len;
		}
		return max;
	}
	
	
	/**
	 * @return the number of strongly connect components
	 */
	public int numComponents() {
		return comp.size();
	}
	
	
	/**
	 * @param u Source
	 * @param v Destination
	 * @return the BFS path from u to v; If there is no path from u to v, then this method returns
	 * an empty list
	 */
	public ArrayList<String> bfsPath(String u, String v) {
		if (u.equals(v) || (!graph.containsKey(u)) || (!graph.containsKey(v))) {
			return new ArrayList<String>();
		}
		
		ArrayList<String> re = new ArrayList<>();
		Queue<String> q = new LinkedList<>();
		HashMap<String, String> visited = new HashMap<>();
		visited.put(u, null);
		q.add(u);
		boolean found = false;
		
		while (!q.isEmpty()) {
			String current = q.poll();
			if (current.equals(v)) {
				found = true;
				break;
			}
			LinkedList<String> adjacent = new LinkedList<>(graph.get(current));
			for (String node : adjacent) {
				if (!visited.containsKey(node)) {
					visited.put(node, current);
					q.add(node);
				}
			}
		}
		if (!found) {
			return new ArrayList<String>();
		}
		
		String cursor = v;
		re.add(v);
		while (!cursor.equals(u)) {
			re.add(visited.get(cursor));
			cursor = visited.get(cursor);
		}
		Collections.reverse(re);
		
		return re;
	}
	
	//Output for Report
	public static void main(String[] args) {
		
		GraphProcessor gp = new GraphProcessor("WikiCS.txt");
		String maxOut = "";
		int max = -1;
		int edgeCount = 0;
		for (String v : gp.graph.keySet()) {
			edgeCount += gp.graph.get(v).size();
			int out = gp.outDegree(v);
			if (out > max) {
				max = out;
				maxOut = v;
			}
		}
		System.out.println("Vertex with highest out degree: " + maxOut + " = " + max);
		System.out.println("Number of component: " + gp.numComponents());
		System.out.println("Size of largest component: " + gp.largestComponent());
		System.out.println(
				"Number of vertices: " + gp.graph.size() + "\nNumber of edges: " + edgeCount);
	}
}
