package assignment;

import java.io.IOException;

import graph.DirectedGraph;
import lecture.JohnsonAPSP;
import parser.CourseraParser;

public class ShorstestShortestPath {
	public static void main(String[] args) {
		try {
            CourseraParser parser = new CourseraParser("resources/g3.txt", "vertexNo", "edgeNo");
            DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(
                    Integer.parseInt(parser.getSummary("vertexNo")),
                    Integer.parseInt(parser.getSummary("edgeNo")));
            parser.parseLines(line -> {
                String[] tokens = line.split(" ");
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            });
            
            JohnsonAPSP algo = new JohnsonAPSP(graph);
            
            algo.solve();
            
            // find the shortest shortest path
            final int vertexNo = Integer.parseInt(parser.getSummary("vertexNo"));
            int shortestShortestPathVal = Integer.MAX_VALUE;
            for (int i = 1; i <= vertexNo; ++i) {
            	for (int j = 1; j <= vertexNo; ++j) {
            		shortestShortestPathVal = algo.distFromTo(i, j) < shortestShortestPathVal ? 
            				algo.distFromTo(i, j) : shortestShortestPathVal;
            	}
            }
            System.out.println(shortestShortestPathVal);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
