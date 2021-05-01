package algorithms;

import datastructures.PriorityHeap;
import datastructures.Result;
import datastructures.Vertex;
import java.util.ArrayList;

/**
 *
 * @author henripal
 */
public class IDAStar {
    private final char[][] map;
    private PriorityHeap path;
    private boolean pathFound;
    private long timeout;
    private int visitedNodes;
    private double bound;
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
        path = new PriorityHeap();
        path.add(new Vertex(startX, startY, 0));
        bound = heuristic(startX, startY, endX, endY);
        
        while (true) {
            if (System.nanoTime() - startTime > this.timeout) {
                System.out.println("Timed out!");
                return new Result();
            }
            double t = search(0, bound);
            if (pathFound) {
                long timeSpent = (System.nanoTime() - startTime) / 1000000;
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
    private double search(double g, double bound) {
        visitedNodes++;
        Vertex node = path.peek();
        double f = g + heuristic(node.getX(), node.getY(), endVertex.getX(), endVertex.getY());
        if (f > bound) return f;
        if (node.getX() == endVertex.getX() && node.getY() == endVertex.getY()) {
            pathFound = true;
            lastVertex = node;
            return -1;
        }
            
        double min = Double.MAX_VALUE;
        PriorityHeap neighbours = successors(node, g);
        while (!neighbours.isEmpty()) {
            Vertex succ = neighbours.poll();
            if (!path.contains(succ)) {
                path.add(succ);
                double cost = g + heuristic(node.getX(), node.getY(), succ.getX(), succ.getY());
                succ.setDistance(cost + heuristic(succ.getX(), succ.getY(), endVertex.getX(), endVertex.getY()));
                double t = search(cost, bound);
                if (pathFound) return -1;
                if (t < min) min = t;
                path.poll();
            }
        }
        return min;
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
                    newVertex.setDistance(g + heuristic(newVertex.getX(), newVertex.getY(), endVertex.getX(), endVertex.getY()));
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
    public double heuristic(int startX, int startY, int endX, int endY) {
        return Math.sqrt((endY - startY) * (endY - startY) + (endX - startX) * (endX - startX));
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