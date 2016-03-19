package cluster.tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import cluster.unionfind.BasicUnionFind;
import cluster.unionfind.UnionFind;

import static org.hamcrest.CoreMatchers.*;

public class UnionFoundTest {
    
    private Character[] elts= {'a', 'b', 'c', 'd'};
    
    private UnionFind unionFound;
    
    @Before
    public void onInit() {
        unionFound = new BasicUnionFind(elts.length);
    }
    
    @Test
    public void unionTest() throws Exception{
        Integer cid0 = unionFound.find(0);
        Integer cid1 = unionFound.find(1);
        
        assertThat("Before union: ", cid0, not(equalTo(cid1)));
        unionFound.union(cid0, cid1);
        
        assertThat("After union: ", unionFound.find(0), is(equalTo(unionFound.find(1))));
        
        Integer cid2 = unionFound.find(0);
        Integer cid3 = unionFound.find(2);
        
        assertThat("Before another union: ", cid2, not(equalTo(cid3)));
        
        unionFound.union(cid2, cid3);
        
        assertThat(unionFound.find(cid2), is(equalTo(unionFound.find(cid3))));
        
    }
}
