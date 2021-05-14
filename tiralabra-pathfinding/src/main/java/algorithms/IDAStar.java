package algorithms;

import java.util.ArrayList;
import datastructures.Result;
import datastructures.Vertex;

/**
 *
 * @author henripal
 */
public class IDAStar {
    private final char[][] map;
    private long timeout;
    private int processedNodes = 0;
    private Vertex lastVertex;
    private int endX, endY;
    private final boolean[][] visited;
    private boolean timedOut = false;
    private long startTime;
    
    public IDAStar(char[][] map) {
        this.map = map;
        this.visited = new boolean[map.length][map[0].length];
    }
    
    /**
     * Finds the shortest path from start point to the
     * end point using Iterative Deepening A* search algorithm.
     * 
     * @param startX starting nodes x coordinate.
     * @param startY starting nodes y coordinate.
     * @param endX ending nodes x coordinate.
     * @param endY ending nodes y coordinate.
     * @param timeoutSeconds the time in seconds you want to wait until timeout.
     * @return the search result as an Result object.
     */
    public Result findShortestPath(int startX, int startY, int endX, int endY, long timeoutSeconds) {
        this.endX = endX;
        this.endY = endY;
        this.timeout = 1000000000 * timeoutSeconds;
        this.lastVertex = new Vertex(startX, startY, 0, null);
        this.visited[startY][startX] = true;
        
        Vertex startVertex = lastVertex;
        Vertex endVertex = new Vertex(endX, endY, 0);
        double bound = cost(lastVertex, endVertex);
        
        this.startTime = System.nanoTime();
        
        while (true) {
            if (System.nanoTime() - startTime > this.timeout) {
                System.out.println("Timed out!");
                return new Result();
            }
            double t = search(startVertex, 0, bound);
            if (t == -1) {
                if (timedOut) {
                    System.out.println("Timed out!");
                    return new Result();
                }
                if (this.lastVertex.getX() == this.endX && this.lastVertex.getY() == this.endY) {
                    long timeSpent = (System.nanoTime() - startTime) / 1000000;
                    return new Result(lastVertex, lastVertex.getDistance(), processedNodes, timeSpent);
                }
            }
            bound = t;
        }
    }   

    /**
     * Searches for the shortest path recursively with depth-first search.
     * 
     * @param node the current node thats being processed
     * @param g the cost to reach the current node
     * @param bound the threshold of the function
     * @return double the result of the search
     */
    private double search(Vertex node, double g, double bound) {
        processedNodes++;
        if (System.nanoTime() - startTime > this.timeout) {
            timedOut = true;
            return -1;
        }
        double f = g + heuristic(node.getX(), node.getY());
        node.setDistance(g);
        if (f > bound) return f;
        if (node.getX() == endX && node.getY() == endY) {
            lastVertex = node;
            return -1;
        }
        
        double min = Double.MAX_VALUE;
        ArrayList<Vertex> neighbours = successors(node);
        for (int i = 0; i < neighbours.size(); i++) {
            Vertex succ = neighbours.get(i);
            if (!this.visited[succ.getY()][succ.getX()]) {
                this.visited[succ.getY()][succ.getX()] = true;
                double t = search(succ, g + cost(node, succ), bound);
                if (t == -1) return -1;
                if (t < min) min = t;
                this.visited[succ.getY()][succ.getX()] = false;
            }
        }
        return min;
    }
    
    /**
     * Gets the neighbour vertices of the vertex in the order their distances.
     * @param vertex the vertex which neighbours you need to check.
     * @return a minimum heap priority queue sorted by the distances of the vertices.
     */
    private ArrayList<Vertex> successors(Vertex vertex) {
        ArrayList<Vertex> neighbours = new ArrayList<>();
        int startX = vertex.getX();
        int startY = vertex.getY();
        
        for (int x = startX - 1; x <= startX + 1; x++) {
            for (int y = startY - 1; y <= startY + 1; y++) {
                if (x == startX && y == startY) {
                    continue;
                }
                if (isWithinMapLimits(x, y) && map[y][x] == '.') {
                    Vertex newVertex = new Vertex(x, y, 0, vertex);
                    newVertex.setDistance(cost(newVertex, vertex));
                    neighbours.add(newVertex);
                }
            }
        }
        return neighbours;
    }
    
    /**
     * Calculates the heuristic cost from start to the end.
     * 
     * @param startX starting points x coordinate.
     * @param startY starting points y coordinate.
     * @return the heuristic distance between the two points.
     */
    private double heuristic(int startX, int startY) {
        double x = startX - endX;
        double y = startY - endY;
        double xAbs = x > 0 ? x : -x;
        double yAbs = y > 0 ? y : -y;
        double dMax = xAbs > yAbs ? xAbs : yAbs;
        double dMin = xAbs < yAbs ? xAbs : yAbs;
        
        return Math.sqrt(2) * dMin + (dMax - dMin);
    }
    
    /**
     * Calculates the direct cost from start to the end.
     * 
     * @param startVertex the starting vertex.
     * @param endVertex the ending vertex. 
     * @return the direct cost from starting vertex to to the end vertex.
     */
    private double cost(Vertex startVertex, Vertex endVertex) {
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