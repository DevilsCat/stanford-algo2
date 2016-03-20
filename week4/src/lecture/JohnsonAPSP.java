package lecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import algorithm.Algorithm;
import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import graph.DirectedGraph;
import graph.Edge;
import parser.CourseraParser;

/**
 * Input: Directed graph G = (V, E), general edge lengths c_e.
 * <p>
 * Goal:
 * <li>Either compute the length of a shortest u -> v, path for all pairs of vertices u, v in V Or,</li>
 * <li>Correctly report that G contains a negative cycle</li>
 * <pre>
 * (1) Form G' by adding a new vertex s and a new edge (s, v) with
 *     length 0 for each v in G.
 * (2) Run Bellman-Ford on G' with source vertex s. [If B-F detects 
 * 	   a negative-cost cycle in G', halt + report this].
 * (3) For each v in G, define p_v = length of a shortest s -> v path in G'.
 * 	   For each edge e = (u, v) in G, define c'_e = c_e + p_u - p_v.
 * (4) For each pair u,v for each v in G: Run Dijkstra's algorithm in G, with
 *     edge lengths {c_e'}, with source vertex u, to compute the shortest-path
 *     distance d'(u,v) for each v in G.
 * (5) For each pair u,v in G, return the shortest-path distance
 *     d(u, v) := d'(u, v) - p_u + p_v
 * </pre>
 */
public class JohnsonAPSP implements Algorithm<Void> {
    
    private DirectedGraph<Integer, Integer> graph;
    
    private DijkstraSP[] dijkastrSPs;
    
    private Integer[] p;
    
    public JohnsonAPSP(DirectedGraph<Integer, Integer> graph) {
       this.graph = graph; 
    }
        
    public DirectedGraph<Integer, Integer> getGraph() {
        return graph;
    }

    
    @Override
    public Void solve() {
        DirectedGraph<Integer, Integer> graphPrime = new DirectedGraph<>(graph.getVertexNo() + 1, graph.getEdgeNo() + graph.getVertexNo());
        // clone the existing edges
        for (Edge<Integer> edge : graph.getEdges()) {
            graphPrime.addEdge(edge.from.getId() + 1, edge.to.getId() + 1, edge.getWeight());
        }
        // add a new vertex s as from vertex id equals last vertex index in new graph
        int sId = graphPrime.getVertexNo() - 1;
        for (int i = 0; i < graphPrime.getVertexNo() - 1; ++ i) {
        	graphPrime.addEdge(sId + 1, i + 1, 0);
        }
        
        // run Bellman-Ford algorithm.
        BellmanFordShortestPath.Result bfResult = new BellmanFordShortestPath(graphPrime, sId + 1).solve();
        if (bfResult.hasNegativeCycle) {
        	throw new RuntimeException("Graph contains negative cycle");
        }
        p = bfResult.shortestPathVals;
        
        // re-weight original graph.
        for (Edge<Integer> edge : graph.getEdges()) {
        	edge.setWeight(edge.getWeight() + p[edge.getFrom().getId()] - p[edge.getTo().getId()]);
        }
        
        // convert to "Princeton's direct graph" and leverage the dijkstra sp algorithm
        EdgeWeightedDigraph princetonDigraph = new EdgeWeightedDigraph(graph.getVertexNo());
        
        for (Edge<Integer> edge : graph.getEdges()) {
        	DirectedEdge princetonDiEdge = new DirectedEdge(edge.getFrom().getId(), edge.getTo().getId(), edge.getWeight());
        	princetonDigraph.addEdge(princetonDiEdge);
        }
        
        dijkastrSPs = new DijkstraSP[princetonDigraph.V()];
        
        for (int i = 0; i < princetonDigraph.V(); ++i) {
        	dijkastrSPs[i] = new DijkstraSP(princetonDigraph, i);
        }
        return null;
    }
    
    public boolean hasPathFromTo(int fromIdx, int toIdx) {
    	validateVertexIdx(fromIdx);
    	validateVertexIdx(toIdx);
    	// shift to zero based index
    	fromIdx -= 1;
    	toIdx   -= 1;
    	
    	return dijkastrSPs[fromIdx].hasPathTo(toIdx);
    }
    
    public List<Integer> pathFromTo(int fromIdx, int toIdx) {
    	validateVertexIdx(fromIdx);
    	validateVertexIdx(toIdx);
    	// shift to zero based index
    	fromIdx -= 1;
    	toIdx   -= 1;
    	
    	List<Integer> vIds = new ArrayList<>();
    	vIds.add(fromIdx + 1);
    	
    	for (DirectedEdge diEdge : dijkastrSPs[fromIdx].pathTo(toIdx)) {
    		vIds.add(diEdge.to() + 1);
    	}
    	
    	return vIds;
    }
    
    public int distFromTo(int fromIdx, int toIdx) {
    	validateVertexIdx(fromIdx);
    	validateVertexIdx(toIdx);
    	// shift to zero based index
    	fromIdx -= 1;
    	toIdx   -= 1;
    	
    	return (int)dijkastrSPs[fromIdx].distTo(toIdx) - p[fromIdx] + p[toIdx]; 
    }
    
    private void validateVertexIdx(int vertexIdx) {
    	if (vertexIdx < 1 || vertexIdx > graph.getVertexNo()) {
    		throw new ArrayIndexOutOfBoundsException();
    	}
    }

    public static void main(String[] args) {
        try {
            CourseraParser parser = new CourseraParser("resources/johnson-tiny.txt", "vertexNo", "edgeNo");
            DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(
                    Integer.parseInt(parser.getSummary("vertexNo")),
                    Integer.parseInt(parser.getSummary("edgeNo")));
            parser.parseLines(line -> {
                String[] tokens = line.split(" ");
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            });
            
            JohnsonAPSP algo = new JohnsonAPSP(graph);
            
            algo.solve();
            
            testResultFromTo(algo, 1, 2);
            testResultFromTo(algo, 2, 5);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void testResultFromTo(JohnsonAPSP algo, int fromIdx, int toIdx) {
    	if (algo.hasPathFromTo(fromIdx, toIdx)) {
        	System.out.println(String.format("Shortest path from vertex %s to vertex %s: ", fromIdx, toIdx));
        	System.out.println(algo.pathFromTo(fromIdx, toIdx));
        	System.out.println("The distance is : " + algo.distFromTo(fromIdx, toIdx));
        } else {
        	System.out.println(String.format("No path from vertex 1 to vertex 2.", fromIdx, toIdx));
        }
    	System.out.println();
    }
    
}
