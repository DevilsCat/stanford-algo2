package lecture;

import java.util.ArrayList;
import java.util.List;

public class OptBST {
    
    private List<Node> nodes;
    
    public OptBST(List<Node> nodes) {
        this.nodes = nodes;
    }
    
    public double solve() {
        
        nodes.sort((lhs, rhs) -> lhs.getValue() - rhs.getValue());
        
        int n = nodes.size();
        
        double[][] dp = new double[n][n];
        
        for (int s = 0; s <= n - 1; ++s) {
            for (int i = 0; i < n; ++i) {
                if (i + s >= n) { break; }
                dp[i][i + s] = findMinSubProblem(dp, i, s);
            }
        }
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        
        return dp[0][n - 1];
    }

    private double findMinSubProblem(double[][] dp, int i, int s) {
        double min = Double.MAX_VALUE;
        for (int r = i; r <= i + s; ++r) {
            double subVal = freqAccu(i, i + s) + getValOrZeroIfOutofBound(dp, i, r - 1) + getValOrZeroIfOutofBound(dp, r + 1, i + s);
            if (min > subVal) {
                min = subVal;
            }
            
        }
        return min;
    }
    
    private double freqAccu(int from, int to) {
        if (to > nodes.size()) {
            to = nodes.size();
        }
        return nodes.subList(from, to + 1).stream().map(node -> node.getFrequency()).reduce((acc, freq) -> acc + freq).orElse(0.0);
    }
    
    private double getValOrZeroIfOutofBound(double[][] arr, int i, int j) {
        if (i < 0 || i >= arr.length || j < 0 || j >= arr[0].length || i > j) {
            return 0;
        }
        return arr[i][j];
    }
    
    public static void main(String[] args) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(1, 0.05));
        nodes.add(new Node(2, 0.4));
        nodes.add(new Node(3, 0.08));
        nodes.add(new Node(4, 0.04));
        nodes.add(new Node(5, 0.1));
        nodes.add(new Node(6, 0.1));
        nodes.add(new Node(7, 0.23));
        
        System.out.println(nodes);
        
        System.out.println(new OptBST(nodes).solve());
    }
}
