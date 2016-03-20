package lecture;

import java.io.IOException;
import java.util.List;

import algorithm.Algorithm;
import graph.DirectedGraph;
import graph.Edge;
import graph.Vertex;
import parser.CourseraParser;

/**
 * Input: Directed graph <em>G = (V, E)</em>, edge lengths <em>ce</em> for each <em>e in E</em>,
 *        source vertex <em>s in V</em> (No parallel edges).
 * <p>
 * Goal: For every destination <em>v in V</em>, compute the length (sum of edge costs) of a shortest
 *       <em>s-v</em> path. 
 * <p>
 * Algorithm: For every <em>v in V</em>, <em>i in {1,2,...}</em>
 * <pre>
 * Li,v = min {
 *      L_(i-1),v              (case 1)
 *      min{L_(i-1),w + c_wv}  (case 2)
 * }
 * </pre>
 * This algorithm is distributed shortest path algorithm takes O(mn) time complexity.
 * <p>
 * @author yxiao
 *
 */
public class BellmanFordShortestPath implements Algorithm<BellmanFordShortestPath.Result> {

    private DirectedGraph<Integer, Integer> graph;
    
    /**
     * the index of start vertex, it's 1 based.
     */
    private int start;
    
    public BellmanFordShortestPath(DirectedGraph<Integer, Integer> graph, int start) {
        if (start <= 0 || graph.getVertexNo() < start) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.graph = graph;
        this.start = start;
    }
    
    @Override
    public Result solve() {
        int n = graph.getVertexNo();
        Integer[][] dp = new Integer[n + 1][n];
        
        // initialize the base case.
        // fill the first row as positive infinity, except the start point.
        for (int i = 0; i < n; ++i) {
            dp[0][i] = i == start - 1 ? 0 : Integer.MAX_VALUE;
        }
        
        // dynamic programming on the rest iterations based on formula.
        for (int i = 1; i < n + 1; ++i) {
            for (int j = 0; j < n; ++j) {
                List<Edge<Integer>> indegreeEdges = graph.getIndgreeEdgeForVertex(j);
                int minIndegreeVal = computeMinIndegreeValue(indegreeEdges, dp[i - 1]);
                dp[i][j] = Math.min(minIndegreeVal, dp[i - 1][j]);
            }
        }
        
        printDp(dp);  // for debug.
        
        Result res = new Result();
        res.hasNegativeCycle = checkNegativeCycle(dp);
        
        if (!res.hasNegativeCycle) {
            res.shortestPathVals = dp[n];
        }
        
        // the result should be the last row.
        return res;
    }
    
    @SuppressWarnings("unchecked")
    private static int computeMinIndegreeValue(List<Edge<Integer>> indegreeEdges, Integer[] dp) {
        int minVal = Integer.MAX_VALUE;
        for (Edge<Integer> edge : indegreeEdges) {        
            Vertex<Integer> from = (Vertex<Integer>)edge.getFrom();
            int lastRoundVal = dp[from.getId()];
            if (lastRoundVal != Integer.MAX_VALUE && lastRoundVal + edge.getWeight() < minVal) {
                minVal = lastRoundVal + edge.getWeight();
            }
        }
        return minVal;
    }
    
    private static boolean checkNegativeCycle(Integer[][] dp) {
        Integer[] lastRow = dp[dp.length - 1];
        Integer[] lastSecRow = dp[dp.length - 2];
        for (int i = 0; i < lastRow.length; ++i) {
            if (lastRow[i] != lastSecRow[i]) {
                return true;
            }
        }
        return false;
    }
    
    private void printDp(Integer[][] dp) {
        for (int i = 0; i < dp.length; ++i) {
            for (int j = 0; j < dp[0].length; ++j) {
                System.out.print((dp[i][j] == Integer.MAX_VALUE ? "-1" : dp[i][j]) + " ");
            }
            System.out.println();
        }
    }
    
    public static class Result{
        public boolean hasNegativeCycle;
        public Integer[] shortestPathVals;
    }

    public static void main(String[] args) {
        try {
            CourseraParser parser = new CourseraParser("resources/bellmanford-negtiny.txt", "vertexNo", "edgeNo");
            DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(
                    Integer.parseInt(parser.getSummary("vertexNo")),
                    Integer.parseInt(parser.getSummary("edgeNo")));
            
            parser.parseLines(line -> {
                String[] tokens = line.split(" ", 3);
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            });

            final int start = 1;
            
            BellmanFordShortestPath.Result result = new BellmanFordShortestPath(graph, start).solve();
            
            
            
            if (result.hasNegativeCycle) {
                System.out.println("Graph has negative cycle.");
            } else {
                Integer[] res = result.shortestPathVals;
                for (int i = 0; i < res.length; ++i) {
                    System.out.println(String.format("From %d to %d has shortest path: %d", start, i + 1, res[i]));
                }
            }
            
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
