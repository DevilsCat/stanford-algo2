package lecture;

/**
 * Sequence Alignment
 * Example:
 * <pre>
 *   A G G G C T
 *   A G G - C A
 * </pre>
 * Input: Strings X = x1...xm, Y = y1...ym over some alphabet like {A,G,C,T}
 * Goal: Alignment with minimum possible total penalty.
 * <p> 
 * @author yxiao
 *
 */
public class SequenceAlignment {
    
    private static final int GAP_PENALTY = 1;
    private static final int MISMATCH_PENALTY = 1;
    
    /**
     * Dynamic programming solution.
     * defines 2D array "dp", dp[i][j] is penalty of optimal alignment of Xi & Yj
     * <p>
     * Recurrence: for all i = 1 ... m and j = 1,...,n:
     * <p>
     *  dp[i][j] = the minimum of:
     *  <li> (1) dp[i -  1][j - 1] + match penalty. </li>
     *  <li> (2) dp[i - 1][j] + gap penalty. </li>
     *  <li> (3) dp[i][j - 1] + gap penalty. </li>
     */
    public static String[] solve(String seq1, String seq2) throws Exception {
        
        if (seq1 == null || seq1.isEmpty() || seq2 == null || seq2.isEmpty()) {
            return null;
        }
        
        int[][] dp = new int[seq1.length()+ 1][seq2.length() + 1];
        
        // Initializes dp.
        for (int i = 0; i < dp.length; ++i) {
            dp[i][0] = i * GAP_PENALTY;
        }
        for (int i = 0; i < dp[0].length; ++i) {
            dp[0][i] = i * GAP_PENALTY;
        }
        
        // dynamic programming...
        for (int i = 1; i < dp.length; ++i) {
            for (int j = 1; j < dp[0].length; ++j) {
                int match = dp[i - 1][j - 1] + (seq1.charAt(i - 1) == seq2.charAt(j - 1) ? 0 : MISMATCH_PENALTY);
                int gapStr1 = dp[i][j - 1] + GAP_PENALTY;
                int gapStr2 = dp[i - 1][j] + GAP_PENALTY;
                dp[i][j] = Math.min(match, Math.min(gapStr1, gapStr2));
            }
        }
        
        return traceBack(dp, seq1, seq2);
    }
    
    private static String[] traceBack(final int[][] dp, final String seq1, final String seq2) throws Exception {
        StringBuilder alignedSeqSb1 = new StringBuilder();
        StringBuilder alignedSeqSb2 = new StringBuilder();
        
        int i = dp.length - 1;
        int j = dp[0].length - 1;
        
        while (i != 0 && j != 0) {
            if (dp[i][j] == dp[i - 1][j - 1] + (seq1.charAt(i - 1) == seq2.charAt(j - 1) ? 0 : MISMATCH_PENALTY)) {
                alignedSeqSb1.append(seq1.charAt(i - 1));
                alignedSeqSb2.append(seq2.charAt(j - 1));
                --i;
                --j;
            } else if (dp[i][j] == dp[i][j - 1] + GAP_PENALTY) {
                alignedSeqSb1.append('-');
                alignedSeqSb2.append(seq2.charAt(j - 1));
                --j;
            } else if (dp[i][j] == dp[i - 1][j] + GAP_PENALTY) {
                alignedSeqSb1.append(seq1.charAt(i - 1));
                alignedSeqSb2.append('-');
                --i;
            } else {
                throw new Exception("Invalid dynamic programming buffer.");
            }
        }
        
        while (i != 0) {
            alignedSeqSb1.append(seq1.charAt(i - 1));
            alignedSeqSb2.append('-');
            --i;
        }
        
        while (j != 0) {
            alignedSeqSb1.append(seq1);
            alignedSeqSb2.append(seq2.charAt(j - 1));
            --j;
        }
        
        return new String[] {alignedSeqSb1.reverse().toString(), alignedSeqSb2.reverse().toString()};
    } 
    
    /**
     * Self testing...
     * @param args
     */
    public static void main(String[] args) {
        String seq1 = "AGGGCT";
        String seq2 = "AGGCA";
        
        try {
            String[] res = SequenceAlignment.solve(seq1, seq2);
            for (String str : res) {
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
