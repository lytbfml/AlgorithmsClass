package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 *
 * @author Yangxiao Wang
 *
 */
public class myGraph<V, E> implements IGraph<V, E>
{
	
	//	public static void main(String args[] )
	//	{
	//		Graph<Integer, Double> g1 = new Graph<>();
	//		g1.setDirectedGraph();
	//		g1.addVertex("TestV1");
	//		g1.addVertex("TestV2");
	//		g1.addEdge("TestV1", "TestV2");
	//
	//		g1.getVertices().forEach(v -> System.out.println(v.getVertexName()));
	//		g1.getEdges().forEach(e -> System.out.println(e.getVertexName1()+" : "+e.getVertexName2()));
	//
	//	}
	
	
	private boolean isDirected = false;
	// Contain all the vertices
	private HashSet<Vertex<V>> vertices;
	// Contain all the edges
	private HashSet<Edge<E>> edges;
	
	
	/**
	 * Constructs two HashSet for edges and vertices
	 */
	public myGraph()
	{
		vertices = new HashSet<Vertex<V>>();
		edges = new HashSet<Edge<E>>();
	}
	
	@Override
	public void setDirectedGraph()
	{
		isDirected = true;
	}
	
	@Override
	public void setUndirectedGraph()
	{
		isDirected = false;
	}
	
	@Override
	public boolean isDirectedGraph()
	{
		return isDirected;
	}
	
	/**
	 * Find vertex that its name is vertexName
	 *
	 * @param vertexName
	 *
	 * @return Vertex if Vertex with vertexName was found, null otherwise
	 */
	public Vertex<V> hasVertex(String vertexName)
	{
		// Traversal the vertices
		for (Vertex<V> vs : vertices)
		{
			if (vs.getVertexName().equals(vertexName))
			{
				return vs;
			}
		}
		return null;
	}
	
	/**
	 * Find Edge that contain vertex1 and vertex2
	 *
	 *
	 * @param vertex1
	 * @param vertex2
	 *
	 * @return edge if edge with vertex1 and vertex2 was found, null otherwise
	 */
	public Edge<E> hasEdge(String vertex1, String vertex2)
	{
		Edge<E> re = null;
		boolean found = false;
		// Traversal the edges
		for (Edge<E> ed : edges)
		{
			if (ed.getVertexName1().equals(vertex1) && ed.getVertexName2().equals(vertex2))
			{
				// This is true if and only if its undirected graph and there are edge(v2, v1) and
				// edge(v1, v2)
				if (found)
				{
					// Remove edge(v2, v1) and set edge(v1, v2) = null
					edges.remove(re);
					re = ed;
					setEdgeData(vertex1, vertex2, null);
				}
				else
				{
					re = ed;
					found = true;
				}
			}
			// check for undirected graph, if edge(v2, v1) is existed, then edge(v1, v2) is found
			if (!isDirected && ed.getVertexName1().equals(vertex2)
					&& ed.getVertexName2().equals(vertex1))
			{
				// This is true if found edge(v2, v1) for undirected graph
				// and edge(v1, v2) is found already, remove edge(v2, v1)
				if (found)
				{
					// Remove edge(v2, v1) and set edge(v1, v2) = null
					edges.remove(ed);
					setEdgeData(vertex1, vertex2, null);
				}
				else
				{
					re = ed;
					found = true;
				}
			}
		}
		
		return re;
	}
	
	@Override
	public void addVertex(String vertexName) throws graph.IGraph.DuplicateVertexException
	{
		// if Vertex vertexName not found
		if (hasVertex(vertexName) != null)
		{
			throw new graph.IGraph.DuplicateVertexException();
		}
		
		Vertex<V> vertex = new Vertex<V>(vertexName, null);
		vertices.add(vertex);
	}
	
	@Override
	public void addVertex(String vertexName, V vertexData)
			throws graph.IGraph.DuplicateVertexException
	{
		// if Vertex vertexName not found
		if (hasVertex(vertexName) != null)
		{
			throw new graph.IGraph.DuplicateVertexException();
		}
		
		Vertex<V> vertex = new Vertex<V>(vertexName, vertexData);
		vertices.add(vertex);
	}
	
	@Override
	public void addEdge(String vertex1, String vertex2)
			throws graph.IGraph.DuplicateEdgeException,
			graph.IGraph.NoSuchVertexException
	{
		// if Vertex vertex1 or vertex2 not found
		if (hasVertex(vertex1) == null || hasVertex(vertex2) == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		// if edge existed
		if (hasEdge(vertex1, vertex2) != null)
		{
			throw new graph.IGraph.DuplicateEdgeException();
		}
		
		Edge<E> edge = new Edge<E>(vertex1, vertex2, null);
		edges.add(edge);
	}
	
	@Override
	public void addEdge(String vertex1, String vertex2, E edgeData)
			throws graph.IGraph.DuplicateEdgeException,
			graph.IGraph.NoSuchVertexException
	{
		// if Vertex vertex1 or vertex2 not found
		if (hasVertex(vertex1) == null || hasVertex(vertex2) == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		//if edge is already in graph
		if (hasEdge(vertex1, vertex2) != null)
		{
			throw new graph.IGraph.DuplicateEdgeException();
		}
		
		Edge<E> edge = new Edge<E>(vertex1, vertex2, edgeData);
		edges.add(edge);
	}
	
	@Override
	public V getVertexData(String vertexName) throws graph.IGraph.NoSuchVertexException
	{
		// if Vertex not found
		Vertex<V> found = hasVertex(vertexName);
		if (found == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		
		return found.getVertexData();
	}
	
	@Override
	public void setVertexData(String vertexName, V vertexData)
			throws graph.IGraph.NoSuchVertexException
	{
		Vertex<V> found = hasVertex(vertexName);
		// if Vertex not found
		if (found == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		//update value in vertex
		vertices.remove(found);
		Vertex<V> newVertex = new Vertex<V>(vertexName, vertexData);
		vertices.add(newVertex);
		
	}
	
	@Override
	public E getEdgeData(String vertex1, String vertex2)
			throws graph.IGraph.NoSuchVertexException,
			graph.IGraph.NoSuchEdgeException
	{
		// if Vertex vertex1 or vertex2 not found
		if (hasVertex(vertex1) == null || hasVertex(vertex2) == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		//if edge is not found
		Edge<E> found = hasEdge(vertex1, vertex2);
		if (found == null)
		{
			throw new graph.IGraph.NoSuchEdgeException();
		}
		
		return found.getEdgeData();
	}
	
	@Override
	public void setEdgeData(String vertex1, String vertex2, E edgeData)
			throws graph.IGraph.NoSuchVertexException,
			graph.IGraph.NoSuchEdgeException
	{
		// if Vertex vertex1 or vertex2 not found
		if (hasVertex(vertex1) == null || hasVertex(vertex2) == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		//if edge is not found
		Edge<E> found = hasEdge(vertex1, vertex2);
		if (found == null)
		{
			throw new graph.IGraph.NoSuchEdgeException();
		}
		//update edge
		edges.remove(found);
		Edge<E> newEdge = new Edge<E>(vertex1, vertex2, edgeData);
		edges.add(newEdge);
	}
	
	@Override
	public graph.IGraph.Vertex<V> getVertex(String VertexName)
	{
		//Throw an error if vertex is not in the graph
		Vertex<V> found = hasVertex(VertexName);
		if (found == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		
		return found;
	}
	
	@Override
	public graph.IGraph.Edge<E> getEdge(String vertexName1, String vertexName2)
	{
		
		if (hasVertex(vertexName1) == null || hasVertex(vertexName2) == null)
		{
			throw new graph.IGraph.NoSuchVertexException();
		}
		
		Edge<E> found = hasEdge(vertexName1, vertexName2);
		
		if (found == null)
		{
			throw new graph.IGraph.NoSuchEdgeException();
		}
		
		return found;
	}
	
	@Override
	public List<graph.IGraph.Vertex<V>> getVertices()
	{
		return new ArrayList<graph.IGraph.Vertex<V>>(vertices);
	}
	
	@Override
	public List<graph.IGraph.Edge<E>> getEdges()
	{
		return new ArrayList<graph.IGraph.Edge<E>>
				(edges);
	}
	
	@Override
	public List<graph.IGraph.Vertex<V>> getNeighbors(String vertex)
	{
		ArrayList<graph.IGraph.Vertex<V>> list = new ArrayList<>();
		for (Edge<E> ed : edges)
		{
			if (ed.getVertexName1().equals(vertex))
			{
				list.add(hasVertex(ed.getVertexName2()));
			}
			else if (ed.getVertexName2().equals(vertex) && !isDirected)
			{
				list.add(hasVertex(ed.getVertexName1()));
			}
		}
		
		return list;
	}
	
}