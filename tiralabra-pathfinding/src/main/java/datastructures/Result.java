package datastructures;

/**
 * Object which contains all the info for the algorithms found path.
 * Includes whether the algorithm found a path, the path length, 
 * the amount of processed nodes, time spent with the search and the 
 * last vertex. The vertices contain a reference to the previous 
 * node, so it can be used to draw the path to the UI.
 * 
 * @author henripal
 */
public class Result {
    private boolean pathFound;
    private Vertex lastVertex;
    private double pathLength;
    private int processedNodes;
    private long timeSpent;
    
    public Result() {
        this.pathFound = false;
    }
    
    public Result(Vertex lastVertex, double pathLength, int processedNodes, long timeSpent) {
        this.pathFound = true;
        this.lastVertex = lastVertex;
        this.pathLength = pathLength;
        this.processedNodes = processedNodes;
        this.timeSpent = timeSpent;
    }
    
    public boolean pathWasFound() {
        return pathFound;
    }

    public Vertex getLastVertex() {
        return lastVertex;
    }

    public double getPathLength() {
        return pathLength;
    }

    public int getProcessedNodes() {
        return processedNodes;
    }

    public long getTimeSpent() {
        return timeSpent;
    }
}
