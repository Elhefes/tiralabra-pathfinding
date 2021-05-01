package datastructures;

/**
 * Minimum priority queue implementation using a heap.
 * @author henripal
 */
public class PriorityHeap {
    private Vertex[] heap;
    private int index;
    
    public PriorityHeap() {
        this.heap = new Vertex[10];
        this.index = 0;
    }
    
    /**
     * Adds the vertex to the minimum heap.
     * @param vertex vertex you want to add.
     */
    public void add(Vertex vertex) {
        index++;
        if (index == heap.length) {
            Vertex[] newHeap = new Vertex[heap.length * 2];  
            for (int i = 0; i < heap.length; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
        int i = index;
        while (i > 1 && heap[olderIndex(i)].compareTo(vertex) == 1) {
            heap[i] = heap[olderIndex(i)];
            i = olderIndex(i);
        }
        heap[i] = vertex;
    }
    
    
    /**
     * Retrieves and removes the first element of the queue.
     * @return the element at the head of the queue.
     */
    public Vertex poll() {
        Vertex vertex = heap[1];             
        heap[1] = heap[index];      
        heap[index] = null;
        index--;
        sortHeap(1);
        return vertex;
    }
    
    /**
     * Retrieves the first element of the queue.
     * @return the element at the head of the queue.
     */
    public Vertex peek() {
        return heap[1];
    }
    
    /**
     * Sorts the heap recursively by size.
     * @param i 
     */
    public void sortHeap(int i) {
        int left = i * 2;
        int right = left + 1;
        int minimum = 0;
        if (right <= index) {
            minimum = heap[right].compareTo(heap[left]) == 1 ? left : right;
            if (heap[i].compareTo(heap[minimum]) == 1) {
                Vertex vertex = heap[i];
                heap[i] = heap[minimum];
                heap[minimum] = vertex;
                sortHeap(minimum);
            }
        } else if (left == index && heap[i].compareTo(heap[left]) == 1) {
            Vertex vertex = heap[i];
            heap[i] = heap[left];
            heap[left] = vertex;
        }
    }
    
    public boolean contains(Vertex vertex) {
        if (index == 0) return false;
        for (int i = 0; i < index; i++) {
            if (vertex.equals(heap[i])) {
                return true;
            }
        }
        return false;
    }
    
    public int olderIndex(int index) {
        return index / 2;
    }
    
    public boolean isEmpty() {
        return index == 0;
    }
    
    public int getSize() {
        return index;
    }
    
}
