package datastructures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author henripal
 */
public class PriorityHeapTest {
    private PriorityHeap heap;
    
    @Before
    public void setUp() {
        heap = new PriorityHeap();
    }
    
    @Test
    public void heapPeekWorks1()    {
        Vertex vertex = new Vertex(0, 0, 5);
        heap.add(vertex);
        heap.add(new Vertex(0, 0, 10));
        assertEquals(vertex, heap.peek());
    }
    
    @Test
    public void heapPeekWorks2() {
        Vertex vertex = new Vertex(0, 0, 30);
        heap.add(new Vertex(0, 0, 70));
        heap.add(vertex);
        assertEquals(vertex, heap.peek());
    }
    
    @Test
    public void heapPollWorks1() {
        Vertex vertex = new Vertex(0, 0, 15);
        heap.add(new Vertex(0, 0, 20));
        heap.add(vertex);
        assertEquals(vertex, heap.poll());
    }
    
    @Test
    public void heapPollWorks2() {
        Vertex vertex = new Vertex(0, 0, 22);
        heap.add(new Vertex(0, 0, 44));
        heap.add(vertex);
        heap.add(new Vertex(0, 0, 66));
        assertEquals(vertex, heap.poll());
    }
    
    @Test
    public void heapGrowingWorks() {
        for (int i = 0; i < 100; i++) {
            heap.add(new Vertex(i, i ,i));
        }
        assertEquals(100, heap.getSize());
    }
    
    @Test 
    public void heapIsSortedCorrectly() {
        heap.add(new Vertex(4, 4, 4));
        heap.add(new Vertex(2, 2, 2));
        heap.add(new Vertex(6, 6, 6));
        heap.add(new Vertex(3, 3, 3));
        heap.add(new Vertex(1, 1, 1));
        heap.add(new Vertex(5, 5, 5));
        heap.poll();
        assertEquals(2, heap.poll().getX());
    }
    
    @Test
    public void pollRemovesVertex()   {
        heap.add(new Vertex(0, 0, 20));
        heap.add(new Vertex(0, 0, 0));
        heap.poll();
        assertEquals(1, heap.getSize());
    }
    
    @Test
    public void returnNullWhenHeapIsEmpty()   {
        heap.add(new Vertex(0, 0, 1));
        heap.poll();
        assertNull(heap.poll());
    }
    
    @Test
    public void returnNotEmptyWhenHeapNotEmpty()   {
        heap.add(new Vertex(0, 0, 7));
        assertFalse(heap.isEmpty());
    }
    
    @Test
    public void returnEmptyWhenHeapEmpty() {
        heap.add(new Vertex(0, 0, 7));
        heap.poll();
        assertTrue(heap.isEmpty());
    }
}
