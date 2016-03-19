package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import lecture.Knapsack;
import lecture.Vertex;

public class Q1 {
    public static void main(String[] args) throws Exception {
        FileReader fileReader = new FileReader("resources/knapsack1.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String[] summary = bufferedReader.readLine().split(" ", 2);
        final int totalWeight = Integer.parseInt(summary[0]);
        final int nVertices = Integer.parseInt(summary[1]);
        
        List<Vertex> vertices = new ArrayList<>(nVertices);
        
        bufferedReader.lines().forEach(line -> {
            String[] tokens = line.split(" ", 2);    
            vertices.add(new Vertex(
                    Integer.parseInt(tokens[0]), 
                    Integer.parseInt(tokens[1])));
        });
        
        System.out.println(Knapsack.solve(vertices, totalWeight));
        
        bufferedReader.close();
    }
}
