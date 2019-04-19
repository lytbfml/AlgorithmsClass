package graphalgorithms;

import graph.Graph;
import graph.IGraph;
import graph.IGraph.Edge;
import graph.IGraph.Vertex;

import java.util.*;

/**
 * @author Yangxiao Wang
 */
public class GraphAlgorithms {
	
	// Represent status of a vertex
	public enum Status {
		undiscovered, discovered
	}
	
	/**
	 * Method that found shortest path from vertexStart ot vertexEnd in Graph g
	 *
	 * @param g           Graph g
	 * @param vertexStart Start location
	 * @param vertexEnd   End location
	 * @param <V>         Genric data in Vertex
	 * @param <E>         Genric data in Edge
	 * @return A list of Edges that represent the path from start to end
	 */
	public static <V, E extends IWeight> List<Edge<E>> ShortestPath(
			IGraph<V, E> g, String vertexStart, String vertexEnd) {
		
		//Initialize all data container
		Map<Vertex<V>, Double> dist = new HashMap<>();
		Map<Vertex<V>, Vertex<V>> pred = new HashMap<>();
		Set<Vertex<V>> discovered = new HashSet<>();
		List<Vertex<V>> vertices = g.getVertices();
		List<Edge<E>> edges = g.getEdges();
		
		//Set distance to all vertex INFINITE
		for (int i = 0; i < vertices.size(); i++) {
			dist.put(vertices.get(i), Double.MAX_VALUE);
			pred.put(vertices.get(i), null);
		}
		//Set distance to start vertex = 0
		dist.put(g.getVertex(vertexStart), 0.0);
		
		Comparator<Vertex<V>> comp = new Comparator<Vertex<V>>() {
			@Override
			public int compare(Vertex<V> o1, Vertex<V> o2) {
				if (dist.get(o1) < dist.get(o2)) {
					return -1;
				} else if (dist.get(o1) > dist.get(o2)) {
					return 1;
				} else {
					return 0;
				}
			}
		};
		
		PriorityQueue<Vertex<V>> pq = new PriorityQueue<>(10, comp);
		pq.add(g.getVertex(vertexStart));
		
		//Dijkstra's algorithm implementation
		while (!pq.isEmpty()) {
			Vertex<V> current = pq.poll();
			if (current.getVertexName() == vertexEnd) {
				pq.clear();
				break;
			}
			discovered.add(current);
			List<Vertex<V>> neighbors = g.getNeighbors(current.getVertexName());
			for (Vertex<V> vex : neighbors) {
				if (!discovered.contains(vex)) {
					double dis = dist.get(current) +
							g.getEdge(current.getVertexName(), vex.getVertexName()).getEdgeData
									().getWeight();
					if (dis < dist.get(vex)) {
						dist.put(vex, dis);
						pred.put(vex, current);
					}
					pq.add(vex);
				}
			}
		}
		
		List<Edge<E>> listR = new ArrayList<>();
		Vertex<V> temp = pred.get(g.getVertex(vertexEnd));
		String last = vertexEnd;
		//Add edges to listR
		while (temp != null) {
			listR.add(g.getEdge(temp.getVertexName(), last));
			last = temp.getVertexName();
			temp = pred.get(temp);
		}
		Collections.reverse(listR);
		
		//		for (Map.Entry<Vertex<V>, Integer> entry : dist.entrySet())
		//		{
		//			System.out.println(entry.getKey().getVertexName() + " : " + entry.getValue());
		//		}
		return listR;
	}
	
	
	/**
	 * TopologicalSort
	 *
	 * @param g the graph
	 * @return list that contains topological sorted vertices
	 */
	public static <V, E> List<Vertex<V>> TopologicalSort(IGraph<V, E> g) {
		List<Vertex<V>> vertices = g.getVertices();
		Map<Vertex<V>, Status> discovered = new HashMap<>();
		
		// Set all vertices undiscovered
		for (int i = 0; i < vertices.size(); i++) {
			discovered.put(vertices.get(i), Status.undiscovered);
		}
		
		// Initialize a stack to store the topological order
		Stack<Vertex<V>> stack = new Stack<>();
		
		// call the recursive topological helper
		for (Map.Entry<Vertex<V>, Status> entry : discovered.entrySet()) {
			// Dfs search and modify topological ordered stack
			if (entry.getValue() == Status.undiscovered) {
				TopologicalSortHelper(g, discovered, entry.getKey(), stack);
			}
		}
		
		List<Vertex<V>> list = new ArrayList<Vertex<V>>();
		// Convert stack to a list
		while (!stack.isEmpty()) {
			list.add(stack.pop());
		}
		
		return list;
	}
	
	/**
	 * TopologicalSort helper
	 *
	 * @param g          The graph
	 * @param discovered A HashMap<Vertex<V>, Status> that contain all vertices and its status
	 * @param vertex     Current vertex
	 * @param stack      stack that contain vertices in topological order
	 */
	private static <V, E> void TopologicalSortHelper(IGraph<V, E> g,
	                                                 Map<Vertex<V>, Status> discovered,
	                                                 Vertex<V> vertex,
	                                                 Stack<Vertex<V>> stack) {
		discovered.put(vertex, Status.discovered);
		List<Vertex<V>> adjacent = g.getNeighbors(vertex.getVertexName());
		
		for (Vertex<V> ver : adjacent) {
			// Dfs(g, ver) if ver is undiscovered
			if (discovered.get(ver) == Status.undiscovered) {
				// Recursive call
				TopologicalSortHelper(g, discovered, ver, stack);
			}
		}
		stack.push(vertex);
	}
	
	/**
	 * AllTopologicalSort
	 *
	 * @param g the graph
	 * @return list of all possible topological ordered vertices
	 */
	public static <V, E> List<List<Vertex<V>>> AllTopologicalSort(IGraph<V, E> g) {
		// Get vertices and edges from graph
		List<Vertex<V>> vertices = g.getVertices();
		List<Edge<E>> edges = g.getEdges();
		// setup two maps to track the status of each vertex
		// and the number of its incoming edges
		Map<Vertex<V>, Status> discovered = new HashMap<>();
		Map<Vertex<V>, Integer> inEdge = new HashMap<>();
		
		// Initialize inEdge with 0 for all vertices
		for (int i = 0; i < vertices.size(); i++) {
			inEdge.put(vertices.get(i), 0);
		}
		
		// Set all vertices undiscovered and set inEdge
		for (int i = 0; i < vertices.size(); i++) {
			// get all neighbors
			List<Vertex<V>> neighbors = g.getNeighbors(vertices.get(i).getVertexName());
			for (int j = 0; j < neighbors.size(); j++) {
				Vertex<V> neighbor = neighbors.get(j);
				// Neighbors' inEdge + 1
				inEdge.put(neighbor, inEdge.get(neighbor) + 1);
			}
			
			discovered.put(vertices.get(i), Status.undiscovered);
		}
		
		// Initialize a stack to store the topological order
		List<Vertex<V>> topoList = new ArrayList<>();
		List<List<Vertex<V>>> allList = new ArrayList<>();
		
		// Recursive topological helper
		AllTopoHelper(g, discovered, inEdge, topoList, allList);
		
		return allList;
	}
	
	/**
	 * @param g          the graph
	 * @param discovered Map of vertices and its status
	 * @param inEgde     Map of vertices and the number of its incoming edges
	 * @param topoList   temporally topological list for current path
	 * @param allList    Contains all topological list
	 */
	private static <V, E> void AllTopoHelper(IGraph<V, E> g, Map<Vertex<V>, Status> discovered,
	                                         Map<Vertex<V>, Integer> inEgde,
	                                         List<Vertex<V>> topoList,
	                                         List<List<Vertex<V>>> allList) {
		// Will be true if reach the final vertex
		boolean isDone = true;
		
		// Traversal map
		for (Map.Entry<Vertex<V>, Status> entry : discovered.entrySet()) {
			// If no incoming edges and undiscovered
			if (inEgde.get(entry.getKey()) == 0 && entry.getValue() == Status.undiscovered) {
				Vertex<V> vertex = entry.getKey();
				// Discover the vertex
				discovered.put(vertex, Status.discovered);
				// add to temporally list
				topoList.add(vertex);
				
				// Get all adjacent and pretend we delete this vertex and the edges toward them
				List<Vertex<V>> adjacent = g.getNeighbors(vertex.getVertexName());
				for (int i = 0; i < adjacent.size(); i++) {
					Vertex<V> ver = adjacent.get(i);
					inEgde.put(ver, inEgde.get(ver) - 1);
				}
				// Recursive call repeat he algorithm again when 'delete' current vertex and its
				// edges
				AllTopoHelper(g, discovered, inEgde, topoList, allList);
				
				// Put current vertex and its outgoing edges back to graph
				discovered.put(vertex, Status.undiscovered);
				topoList.remove(topoList.size() - 1);
				// increasing its adjacent's incoming edges by 1
				for (int i = 0; i < adjacent.size(); i++) {
					Vertex<V> ver = adjacent.get(i);
					inEgde.put(ver, inEgde.get(ver) + 1);
				}
				// Start back-tracking
				// Start another possible route and set isDone to false
				isDone = false;
			}
		}
		
		// If a topological order is completed add it to allList
		if (isDone) {
			allList.add(new ArrayList<>(topoList));
		}
	}
	
	/**
	 * @param g the graph
	 * @return The minimum spanning tree of graph g
	 */
	public static <V, E extends IWeight> IGraph<V, E> Kruscal(IGraph<V, E> g) {
		// PriorityQueue that implement a Comparator so the queue will order edges automatically
		// based on its weight
		PriorityQueue<Edge<E>> pq = new PriorityQueue<>(new Comparator<Edge<E>>() {
			@Override
			public int compare(Edge<E> o1, Edge<E> o2) {
				if (o1.getEdgeData().getWeight() < o2.getEdgeData().getWeight()) {
					return -1;
				}
				if (o1.getEdgeData().getWeight() > o2.getEdgeData().getWeight()) {
					return 1;
				}
				
				return 0;
			}
		});
		
		// Counter
		int count = 0;
		// all vertices
		List<Vertex<V>> vertices = g.getVertices();
		// all edges
		List<Edge<E>> edges = g.getEdges();
		// Contain all connected component
		List<List<String>> compSet = new ArrayList<>();
		
		// Edges that will be in the minimum spanning tree
		List<Edge<E>> edgesP = new ArrayList<>();
		
		// add all edges
		for (Edge<E> temp : edges) {
			pq.add(temp);
		}
		
		// Terminate when all edges are processed or minimum spanning tree is completed
		while (count < vertices.size() - 1 && !pq.isEmpty()) {
			// Poll the edge that has minimum weight
			Edge<E> temp = pq.remove();
			String v1 = temp.getVertexName1();
			String v2 = temp.getVertexName2();
			
			if (find(compSet, v1, v2)) {
				edgesP.add(temp);
				count++;
				mergeCom(compSet);
			}
		}
		
		IGraph<V, E> nG = new Graph();
		// A minimum spanning tree is for undirected graph
		nG.setUndirectedGraph();
		
		// Add all vertices
		for (Vertex<V> tV : vertices) {
			nG.addVertex(tV.getVertexName(), tV.getVertexData());
		}
		// Add all minimum spanning tree edges
		for (Edge<E> tE : edgesP) {
			nG.addEdge(tE.getVertexName1(), tE.getVertexName2(), tE.getEdgeData());
		}
		
		return nG;
	}
	
	/**
	 * Merge component
	 *
	 * @param compSet all connected component
	 */
	private static void mergeCom(List<List<String>> compSet) {
		for (int i = 0; i < compSet.size(); i++) {
			for (int j = i + 1; j < compSet.size(); j++) {
				// Find common vertex
				if (!Collections.disjoint(compSet.get(i), compSet.get(j))) {
					// Convert to set in order to merge it without duplicates
					Set<String> set = new HashSet<>(compSet.get(i));
					set.addAll(compSet.get(j));
					// convert it back and update
					compSet.set(i, new ArrayList<String>(set));
					// Remove the other component after merge
					compSet.remove(j);
					return;
				}
			}
		}
	}
	
	/**
	 * @param compSet all connected component
	 * @param name1   vertex 1
	 * @param name2   vertex 2
	 * @return false if v1 and v2 are already in one component at the same time, true otherwise.
	 */
	private static boolean find(List<List<String>> compSet, String name1, String name2) {
		//Traversal
		for (List<String> list : compSet) {
			if (list.contains(name1)) {
				//return false if v1 and v2 are in one component at the same time
				if (list.contains(name2)) {
					return false;
				} else {
					//When there is v1, add v2
					list.add(name2);
					return true;
				}
			} else if (list.contains(name2)) {
				//when there is v2, add v1
				list.add(name1);
				return true;
			}
		}
		//if none of the vertices is found, add them to a new component
		List<String> newL = new ArrayList<>();
		newL.add(name1);
		newL.add(name2);
		compSet.add(newL);
		return true;
	}
	
}
