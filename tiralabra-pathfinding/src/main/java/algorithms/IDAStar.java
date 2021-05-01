package algorithms;

import datastructures.PriorityHeap;
import datastructures.Result;
import datastructures.Vertex;

/**
 *
 * @author henripal
 */
public class IDAStar {
    private final char[][] map;
    private boolean pathFound;
    private long timeout;
    private int visitedNodes;
    private double bound;
    private Vertex startVertex;
    private Vertex endVertex;  
    private Vertex lastVertex;
    
    public IDAStar(char map[][]) {
        this.map = map;
    }
    
    /**
     * Finds the shortest path from start point to the
     * end point using Iterative Deepening A* search algorithm.
     * 
     * @param startX starting node's x coordinate.
     * @param startY starting node's y coordinate.
     * @param endX ending node's x coordinate.
     * @param endY ending node's y coordinate.
     * @param timeoutSeconds the time in seconds you want to wait until timeout.
     * @return the search result as an Result object.
     */
    public Result findShortestPath(int startX, int startY, int endX, int endY, long timeoutSeconds) {
        visitedNodes = 0;
        pathFound = false;
        long startTime = System.nanoTime();
        this.timeout = 1000000000 * timeoutSeconds;
        
        endVertex = new Vertex(endX, endY, 0);
        PriorityHeap path = new PriorityHeap();
        Vertex root = new Vertex(startX, startY, 0);
        root.setDistance(h(root));
        path.add(root);
        bound = h(root);
        
        while (true) {
            if (System.nanoTime() - startTime > this.timeout) {
                System.out.println("Timed out!");
                return new Result();
            }
            double t = search(path, 0, bound);
            if (pathFound || t == -1) {
                long timeSpent = (System.nanoTime() - startTime) / 1000000;
                System.out.println("COUNTTI ON " + countLength(lastVertex));
                return new Result(lastVertex, lastVertex.getDistance(), visitedNodes, timeSpent);
            }
            if (t == Double.MAX_VALUE) {
                return new Result();
            }
            bound = t;
        }
    }

    /**
     * Searches for the shortest path recursively.
     * @param path the current search path
     * @param g the cost to reach the current node
     * @param bound the threshold of the function
     * @return double the result of the search
     */
    private double search(PriorityHeap path, double g, double bound) {
        visitedNodes++;
        Vertex node = path.peek();
        double f = g + h(node);
        if (f > bound) return f;
        if (node.getX() == endVertex.getX() && node.getY() == endVertex.getY()) {
            pathFound = true;
            System.out.println("\ng: " + g);
            lastVertex = node;
            lastVertex.setDistance(h(node));
            System.out.println("XDDDDD" +lastVertex.getDistance());
            return -1;
        }
            
        double min = Double.MAX_VALUE;
        PriorityHeap neighbours = successors(node, g);
        while (!neighbours.isEmpty()) {
            Vertex succ = neighbours.poll();
            if (!path.contains(succ)) {
                path.add(succ);
//                double cost = g + cost(node, succ);
//                succ.setDistance(cost + cost(succ, endVertex));
//                double t = search(path, cost, bound);
                double t = search(path, g + cost(node, succ), bound);
                if (pathFound) return -1;
                if (t < min) min = t;
                path.poll();
            }
        }
        return min;
    }
    
    private double countLength(Vertex lastVertex) {
        double length = 0;
        while (true) {
            Vertex nextVertex = lastVertex.getPreviousVertex();
            if (nextVertex == null) {
                break;
            }
            length += cost(lastVertex, nextVertex);
            lastVertex = nextVertex;
        }
        return length;
    }
    
    /**
     * Gets the neighbour vertices of the vertex in the order their distances.
     * @param vertex the vertex which neighbours you need to check.
     * @return a minimum heap priority queue sorted by the distances of the vertices.
     */
    private PriorityHeap successors(Vertex vertex, double g) {
        PriorityHeap heap = new PriorityHeap();
        int startX = vertex.getX();
        int startY = vertex.getY();
        
        for (int x = startX - 1; x <= startX + 1; x++) {
            for (int y = startY - 1; y <= startY + 1; y++) {
                if (x == startX && y == startY) {
                    continue;
                }
                if (isWithinMapLimits(x, y) && map[x][y] == '.') {
                    Vertex newVertex = new Vertex(x, y, 0, vertex);
                    newVertex.setDistance(g + h(newVertex));
                    heap.add(newVertex);
                }
            }
        }
        return heap;
    }
    
    /**
     * The heuristic function which calculates the
     * distance from the current vertex to the end vertex.
     * 
     * @param startX the starting vertices X coordinate.
     * @param startY the starting vertices Y coordinate.
     * @param endX the ending vertices X coordinate.
     * @param endY the ending vertices Y coordinate.
     * @return the distance from the vertex to the end vertex.
     */
    public double h(Vertex vertex) {
        return cost(vertex, endVertex);
    }
    
    public double cost(Vertex startVertex, Vertex endVertex) {
        return Math.sqrt(Math.pow(endVertex.getX() - startVertex.getX(), 2) + Math.pow(endVertex.getY() - startVertex.getY(), 2));
    }
    
    /**
     * Checks whether the coordinates are inside of map.
     * 
     * @param x x-coordinate of the vertex.
     * @param y y-coordinate of the vertex.
     * @return whether the coordinates are inside of the map bounds.
     */
    private boolean isWithinMapLimits(int x, int y) {
        return (x > 0 && x < map[0].length && y > 0 && y < map.length);
    }
}