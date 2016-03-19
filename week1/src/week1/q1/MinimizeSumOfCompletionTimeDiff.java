package week1.q1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;

public class MinimizeSumOfCompletionTimeDiff {
    Job[] jobs;
    int numJobs;
    
    public MinimizeSumOfCompletionTimeDiff(final String filename) throws Exception {        
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        numJobs = Integer.parseInt(bufferedReader.readLine());
        jobs = new Job[numJobs];
        for (int i = 0; i < numJobs; ++i) {
            String[] line = bufferedReader.readLine().split(" ", 2);
            jobs[i] = new Job(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
        }
        bufferedReader.close();
    }
    
    private long run() {
        if (numJobs <= 0) { return Long.MIN_VALUE; }  // error check.
        
        // Greedy algorithm
        Arrays.sort(jobs, Collections.reverseOrder());
        
        // Compute weighted sum.
        int[] completionTimes = new int[numJobs];
        
        completionTimes[0] = jobs[0].length;
        for (int i = 1; i < numJobs; ++i) {
            completionTimes[i] = completionTimes[i - 1] + jobs[i].length;
        }
        
        long sum = 0;
        for (int i = 0; i < numJobs; ++i) {
            sum += completionTimes[i] * jobs[i].weight;
        }
        
        return sum;
    }
    
    private class Job implements Comparable<Job> {
        int weight;
        int length;
        
        public Job(int weight, int length) {
            this.weight = weight;
            this.length = length;
        }
        
        @Override
        public int compareTo(Job that) {
            int resThis = this.weight - this.length;
            int resThat = that.weight - that.length;
            if (resThis == resThat) {
                return this.weight - that.weight;
            } else {
                return resThis - resThat;
            }
        }
        
        @Override
        public String toString() {
            return "Job [weight=" + weight + ", length=" + length + "]";
        }
    }
    
    public static void main(String[] args) {

        try {
            long res = new MinimizeSumOfCompletionTimeDiff("resources/jobs.txt").run();
            System.out.println("res: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
}
