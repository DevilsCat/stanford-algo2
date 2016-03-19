package huffman.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import huffman.datastructures.MinPQ;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class MinPQTest {

    private MinPQ<Integer> pq;
    
    private List<Integer> list;
    
    private Random random = new Random();
    
    @Before
    public void onInit() {
        
        list = new ArrayList<>();
        for (int i = 0; i < 10000; ++i) {
            Integer rand = random.nextInt(1000);
            list.add(rand);
        }
    }
    
    @Test
    public void randInsertPersistMinValTest() {
        pq = new MinPQ<>();
        for (Integer num : list) {
            pq.insert(num);
        }
        
        // sort the list in ascending order.
        Collections.sort(list);
        
        assertThat(pq.size(), is(equalTo(list.size())));
        
        for (Integer expect : list) {
            assertThat(expect, is(equalTo(pq.min())));
            assertThat(expect, is(equalTo(pq.delMin())));
        }
    }
    
    @Test
    public void heapifyTest() {
        pq = new MinPQ<>(list.toArray(new Integer[list.size()]));
        
        Collections.sort(list);
        
        for (Integer expect : list) {
            assertThat(expect, is(equalTo(pq.min())));
            assertThat(expect, is(equalTo(pq.delMin())));
        }
    }
    
}
