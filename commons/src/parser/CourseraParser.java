package parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.sound.sampled.Line;

public class CourseraParser {
    private Map<String, String> summary;
    private Stream<String> stream;
    private BufferedReader bufferedReader;
    
    public CourseraParser(String filename, String...summaryKeys) throws IOException {
        FileReader fileReader = new FileReader(filename);
        bufferedReader = new BufferedReader(fileReader);
        
        String[] summaryTokens = bufferedReader.readLine().split(" ");
        summary = new HashMap<>();
        
        if (summaryKeys.length != summaryTokens.length) {
            bufferedReader.close();
            throw new IllegalArgumentException("The expected and real summary keys didn't match.");
        }
        
        for (int i = 0; i < summaryTokens.length; ++i) {
            summary.put(summaryKeys[i], summaryTokens[i]);
        }
        
        stream = bufferedReader.lines();
    }
    
    public String getSummary(String key) {
        return summary.get(key);
    }
    
    public void parseLines(Consumer<String> consumer) {
        stream.forEach(line -> {
            consumer.accept(line);
        });
    }
    
    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
