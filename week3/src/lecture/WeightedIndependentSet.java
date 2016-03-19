package lecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Input: A path graph G = (V, E) with non-negative weights on
 * vertices.
 * Desired output: Subset of non-adjacent vertices - an independent
 * set - of maximum total weight.
 * @author yxiao
 *
 */
public class WeightedIndependentSet {
    /**
     * The problem can be solve in linear time via dynamic programming.
     * Given an array A[n], A[0] = 0, A[1] = w1, for i = 2,3,...,n, A[i]:=
     * max{A[i-1], A[i-2] + wi}.
     * @param array
     */
    public static List<Integer> solve(int[] array) {
        List<Integer> res = new ArrayList<>();
        
        if (array == null || array.length == 0) {
            return res;
        }
        
        int[] dp = new int[array.length + 1];
        
        dp[0] = 0;
        for (int i = 0; i < array.length; ++i) {
            dp[i + 1] = array[i];
        }
        
        int i = dp.length - 1;
        
        while (i > 1) {  // scan through array from right to left.
            if (dp[i - 1] > dp[i - 2] + dp[i]) {  // we want to exclude ith element.
                res.add(dp[i - 1]);
                i -= 1;
            } else {  // we want to include ith element.
                res.add(dp[i]);
                i -= 2;  // skip the last second one since we include the ith, no adjacent allowed.
            }
        }
        
        if (i == 1) {  // special case to guard the last iteration out of bound exception.
            res.add(dp[i]);
        }
        
        return res;
    } 
    
    public static void main(String[] args) {
        int[] array = {1, 4, 5, 4, 10};
        
        System.out.println(WeightedIndependentSet.solve(array));
    }
}
