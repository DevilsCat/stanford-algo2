package lecture;

import java.io.IOException;
import java.util.Optional;

import algorithm.Algorithm;
import graph.DirectedGraph;
import graph.Edge;
import parser.CourseraParser;


/**
 * Input: Directed graph G = (V, E) with edge costs c_e for each edge e in E
 * <p>
 * Goal: Either
 * <li>Compute the length of a shortest u -> v, path for all pairs of vertices u, v in V</li>
 * <li>Correctly report that G contains a negative cycle</li>
 * <p>
 * Algorithm:
 * <pre>
 * Let A = 3-D array (indexed by i, j, k)
 * Base cases: For all i, j in V:
 *   A[i, j , 0] := (1) 0 if i = j
 *                  (2) c_ij if (i, j) in E
 *                  (3) +Inf if i != j and (i, j) not in E
 * For k = 1 to n
 *   For i = 1 to n
 *     For j = 1 to n
 *       A[i, j, k] := min (1) A[i, j, k - 1]                   (case 1)
 *                         (2) A[i, k, k - 1] + A[k, j, k - 1]  (case 2)
 * </pre>
 * Time complexity: O(1) per subproblem, O(n^3) overall.
 * <p>
 * 
 * @author yxiao
 *
 */
public class FolydWarshallAPSP implements Algorithm<Integer[][]> {

    private DirectedGraph<Integer, Integer> graph;
    
    public FolydWarshallAPSP(DirectedGraph<Integer, Integer> graph) {
        this.graph = graph;
    }
    
    @Override
    public Integer[][] solve() {
        final int n = graph.getVertexNo();
        // create a 3-D array as dp cache
        Integer[][][] dp = new Integer[n + 1][n][n];
        
        Integer[][] slice = dp[0];
        
        // base case initialize dp[0][][].
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == j) {
                    slice[i][j] = 0;
                } else {
                    Optional<Edge<Integer>> optEdge = graph.getAdjEdge(i, j);
                    Optional<Integer> optWeight = optEdge.map(edge -> edge.getWeight());
                    slice[i][j] = optWeight.orElse(Integer.MAX_VALUE);
                }
            }
        }
        
        // recurrence
        for (int k = 1; k < n + 1; ++k) {
            slice = dp[k];
            Integer[][] lastSlice = dp[k - 1];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    Integer intermidateVal = lastSlice[i][k - 1] == Integer.MAX_VALUE || lastSlice[k - 1][j] == Integer.MAX_VALUE ?
                            Integer.MAX_VALUE : lastSlice[i][k - 1] + lastSlice[k - 1][j];
                    slice[i][j] = Math.min(lastSlice[i][j], intermidateVal);
                }
            }
        }
        
        return dp[n];
    }
    
    public static void main(String[] args) {
        try {
            CourseraParser parser = new CourseraParser("resources/folydwarshall-tiny.txt", "vertexNo", "edgeNo");
            DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(Integer.parseInt(parser.getSummary("vertexNo")));
            
            parser.parseLines(line -> {
                String[] tokens = line.split(" ", 3);
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            });
            
            Integer[][] apsp = new FolydWarshallAPSP(graph).solve();
            
            for (int i = 0; i < apsp.length; ++i) {
                for (int j = 0; j < apsp[0].length; ++j) {
                    System.out.print(apsp[i][j] + "  ");
                }
                System.out.println();
            }
            
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
