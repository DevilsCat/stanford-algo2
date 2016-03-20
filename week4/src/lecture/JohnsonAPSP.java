package lecture;

import java.io.IOException;

import algorithm.Algorithm;
import graph.DirectedGraph;
import graph.Edge;
import parser.CourseraParser;

public class JohnsonAPSP implements Algorithm<Void> {
    
    private DirectedGraph<Integer, Integer> graph;
    
    public JohnsonAPSP(DirectedGraph<Integer, Integer> graph) {
       this.graph = graph; 
    }
        
    public DirectedGraph<Integer, Integer> getGraph() {
        return graph;
    }

    /**
     * (1) Form G' by adding a new vertex s and a new edge (s, v) with
     *     length 0 for each v in G.
     */
    @Override
    public Void solve() {
        DirectedGraph<Integer, Integer> graphPrime = new DirectedGraph<>(graph.getVertexNo() + 1, graph.getEdgeNo() + graph.getVertexNo());
        // clone the existing edges
        for (Edge<Integer> edge : graph.getEdges()) {
            graphPrime.addEdge(edge.from.getId(), edge.to.getId(), edge.getWeight());
        }
        // add a new vertex s as from vertex id equals last vertex index in new graph
        
        return null;
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
            
            new JohnsonAPSP(graph).solve();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
