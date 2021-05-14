package datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author henripal
 */
public class SuccessorListTest {
    private SuccessorList list;
    
    @Before
    public void setUp() {
        list = new SuccessorList();
    }
    
    @Test
    public void addingVerticesWorks() {
        for (int i = 0; i < 8; i++) {
            list.add(new Vertex(0, 0, 0));
        }
        assertEquals(8, list.size());
    }
    
    @Test
    public void removingLastItemWorks() {
        for (int i = 0; i < 8; i++) {
            list.add(new Vertex(0, 0, 0));
        }
        list.removeLast();
        assertEquals(7, list.size());
    }
    
    @Test
    public void gettingLastFromEmptyListDoesNothing() {
        Vertex vertex = list.get(0);
        assertNull(vertex);
    }
    
    @Test 
    public void gettingWorksCorrectlyWithManyVertices() {
        list.add(new Vertex(4, 4, 4));
        list.add(new Vertex(2, 2, 2));
        list.add(new Vertex(6, 6, 6));
        list.add(new Vertex(3, 3, 3));
        list.add(new Vertex(1, 1, 1));
        list.add(new Vertex(5, 5, 5));
        assertEquals(6, list.get(2).getX());
    }
    
}
