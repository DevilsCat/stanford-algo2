package lecture;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

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
    
    /**
     * Stores all shortest paths for this graph.
     */
    private Optional<Integer>[][] sp;
    
    public FolydWarshallAPSP(DirectedGraph<Integer, Integer> graph) {
        this.graph = graph;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Integer[][] solve() {
        final int n = graph.getVertexNo();
        // create a 3-D array as dp cache
        Integer[][][] dp = new Integer[n + 1][n][n];
        Optional<Integer>[][][] spTrace = new Optional[n + 1][n][n];
        
        Integer[][] slice = dp[0];
        Optional<Integer>[][] spSlice = spTrace[0];
        
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
        // When k = 0, a shortest path from i to j has no intermediate vertices
        // at all. In this case, we set the initial path to nil if 
        // (1) i = j or 
        // (2) i and j are not directly connected.
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == j || !graph.getAdjEdge(i, j).isPresent()) {
                    spSlice[i][j] = Optional.empty();
                } else {
                    spSlice[i][j] = Optional.of(i);
                }
            }
        }
        
        // recurrence
        for (int k = 1; k < n + 1; ++k) {
            slice = dp[k];
            Integer[][] lastSlice = dp[k - 1];
            
            spSlice = spTrace[k];
            Optional<Integer>[][] lastSpSlice = spTrace[k - 1];
            
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    Integer intermediateVal = lastSlice[i][k - 1] == Integer.MAX_VALUE || lastSlice[k - 1][j] == Integer.MAX_VALUE ?
                            Integer.MAX_VALUE : lastSlice[i][k - 1] + lastSlice[k - 1][j];
                    Integer lastSliceVal = lastSlice[i][j];
                    
                    if (lastSliceVal <= intermediateVal) {
                        slice[i][j] = lastSliceVal;
                        spSlice[i][j] = lastSpSlice[i][j];
                    } else {
                        slice[i][j] = intermediateVal;
                        spSlice[i][j] = lastSpSlice[k - 1][j];
                    }
                    
                }
            }
        }
        
        sp = spTrace[n];
        return dp[n];
    }
    
    public Collection<Integer> queryShortestPath(int fromVid, int toVid) {
        final int n = sp.length;
        if (fromVid < 1 || fromVid > n || toVid < 1 || toVid > n) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Stack<Integer> spStack = new Stack<>();
        
        // shift to zero-based index
        fromVid -= 1;
        toVid   -= 1;
        
        spStack.push(toVid);
        
        
        do {
            Optional<Integer> optVertexId = sp[fromVid][spStack.peek()];
            if (!optVertexId.isPresent()) {
                return new Stack<>();  // no valid path.
            }
            spStack.push(optVertexId.get());
        } while (spStack.peek() != fromVid);
        
        return spStack.stream().map(vertexId -> vertexId + 1).collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        try {
            CourseraParser parser = new CourseraParser("resources/folydwarshall-tiny.txt", "vertexNo", "edgeNo");
            DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(Integer.parseInt(parser.getSummary("vertexNo")));
            
            parser.parseLines(line -> {
                String[] tokens = line.split(" ", 3);
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
            });
            
            FolydWarshallAPSP algo = new FolydWarshallAPSP(graph);
            
            Integer[][] apsp = algo.solve();
            
            for (int i = 0; i < apsp.length; ++i) {
                for (int j = 0; j < apsp[0].length; ++j) {
                    System.out.print(apsp[i][j] + "  ");
                }
                System.out.println();
            }
            
            Collection<Integer> sp = algo.queryShortestPath(1, 3);
            
            System.out.println("Shortest path from vertex 1 to vertex 3");
            System.out.println(sp.stream().map(i -> i.toString()).collect(Collectors.joining(" <-- ")));
            
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
