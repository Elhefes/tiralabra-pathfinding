package tiralabra.pathfinding.datastructures;

/**
 *
 * @author henripal
 */
public class Vertex implements Comparable<Vertex> {
    private int x;
    private int y;
    private double distance;
    private Vertex previousVertex;
    
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.distance = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vertex getPreviousVertex() {
        return previousVertex;
    }

    public void setPreviousVertex(Vertex previousVertex) {
        this.previousVertex = previousVertex;
    }
    
    public int compareTo(Vertex vertex) {
        if (this.distance > vertex.getDistance()) {
            return 1;
        }
        return -1;
    }

}
