package datastructures;

/**
 *
 * @author henripal
 */
public class SuccessorList {
    private Vertex[] list;
    private int index;
    
    public SuccessorList()  {
        list = new Vertex[8];
        index = 0;
    }
    
    public void add(Vertex a)  {
        list[index] = a;
        index += 1;
    }
    
    public Vertex get(int i)   {
        if (i > index)  {
            return null;
        }
        return list[i];
    }
    
    public void removeLast()    {
        if (index == 0) {
            return;
        }
        index -= 1;
    }
    
    public int size()   {
        return index;
    }
    
}