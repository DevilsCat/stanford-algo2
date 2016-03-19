package hammingdist;

import java.util.ArrayList;
import java.util.List;

public class BitSetGenerator {
    public static int MAX_INT_NUM = 32;
    public static List<Integer> generateOneBitCombines(int k) {
        if (k < 0 || k > MAX_INT_NUM) { return new ArrayList<>(); }
        
        List<Integer> combines = new ArrayList<>();
        for (int i = 0; i < k; ++i) {
            combines.add(set(i));
        }
        return combines;
    }
    
    public static List<Integer> generateTwoBitsCombines(int k) {
        if (k < 0 || k > MAX_INT_NUM) { return new ArrayList<>(); }
        
        List<Integer> combines = new ArrayList<>();
        for (int i = 0; i < k; ++i) {
            for (int j = i + 1; j < k; ++j) {
                combines.add(set(set(i), j));
            }
        }
        
        return combines;
    }
    
    private static int set(int at) {
        return 1 << at;
    }
    
    private static int set(int target, int at) {
        return target | 1 << at;
    }
    
    public static void main(String[] args) {
        System.out.println(generateOneBitCombines(3));
        System.out.println(generateTwoBitsCombines(3));
    }
    
}
