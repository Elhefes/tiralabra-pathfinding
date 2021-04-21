package algorithms;

import datastructures.Result;
import java.util.PriorityQueue;
import datastructures.Vertex;
import java.util.Stack;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author henripal
 */
public class IDAStar {
    private char[][] map;
    private int endX;
    private int endY;
    private int processedNodes = 0;
    private long startTime;
    private long timeSpent;
    private Stack<Vertex> path;
    private boolean[][] inPath;
    private Vertex resultVertex;
    private Rectangle[][] rectMap;
    
    public IDAStar(char[][] map) {
        this.map = map;
        this.inPath = new boolean[map.length][map[0].length];
        this.rectMap = rectMap;
    }
    
    /**
     * Finds the shortest path from start point to the
     * end point using Iterative Deepening A* search algorithm.
     * 
     * @param startX starting node's x coordinate.
     * @param startY starting node's y coordinate.
     * @param endX ending node's x coordinate.
     * @param endY ending node's y coordinate.
     * @return the search result as an Result object.
     */
    public Result findShortestPath(int startX, int startY, int endX, int endY) {
        startTime = System.nanoTime();
        this.endX = endX;
        this.endY = endY;
        path = new Stack<>();
        double bound = distanceToTheEnd(new Vertex(startX, startY, 0, null));
        Vertex firstVertex = new Vertex(startX, startY, bound, null);
        path.push(firstVertex);
        inPath[startY][startX] = true;
        
        while (true) {
            double t = search(path, 0, bound);
            //System.out.println("search: " + t + ", stack size: " + path.size());
            if (t == -1) {
                timeSpent = (System.nanoTime() - startTime) / 1000000;
                return new Result(resultVertex, resultVertex.getDistance(), processedNodes, timeSpent);
            }
            if (t == Double.MAX_VALUE) {
                return new Result();
            }
            bound = t;
        }
    }
    
    /**
     * Searches for the shortest path recursively.
     * @param g the cost to reach the current node.
     * @param bound when to stop the iteration
     * @return double the result of the search
     */
    private double search(Stack<Vertex> path, double g, double bound) {
        processedNodes++;
        Vertex vertex = path.peek();
        
        double f = g + distanceToTheEnd(vertex);
        if (f > bound) {
            return f;
        }
        
        if (vertex.getX() == endX && vertex.getY() == endY) {
            System.out.println("asd");
            resultVertex = vertex;
            resultVertex.setDistance(g);
            return -1;
        }
        
        double min = Double.MAX_VALUE;
        PriorityQueue<Vertex> heap = successors(vertex);
        while (!heap.isEmpty()) {
            Vertex successor = heap.poll();
            if (!inPath[successor.getY()][successor.getX()]) {
                path.push(successor);
                inPath[successor.getY()][successor.getX()] = true;
                rectMap[successor.getY()][successor.getX()].setFill(Color.BLACK);
                double t = search(path, g + successor.getDistance(), bound);
                //System.out.println("min: " + min + ", " + "search: " + t + ", stack size: " + path.size());
                if (t == -1) {
                    return -1;
                }
                if (t < min) {
                    min = t;
                }
                path.pop();
                inPath[successor.getY()][successor.getX()] = false;
            }
        }
        return min;
    }
    
    /**
     * Gets the neighbour vertices of the vertex in the order their distances.
     * @param vertex the vertex which neighbours you need to check.
     * @return a minimum heap priority queue sorted by the distances of the vertices.
     */
    private PriorityQueue<Vertex> successors(Vertex vertex) {
        PriorityQueue<Vertex> heap = new PriorityQueue<>();
        int startX = vertex.getX();
        int startY = vertex.getY();
        
        for (int x = startX - 1; x <= startX + 1; x++) {
            for (int y = startY - 1; y <= startY + 1; y++) {
                if ((x != startX && y != startY) && isWithinMapLimits(x, y) && map[y][x] == '.') {
                    Vertex newVertex = new Vertex(x, y, vertex.getDistance(), vertex);
                    newVertex.setDistance(distanceToTheEnd(newVertex));
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
     * @param vertex the current vertex that's being processed.
     * @return the distance from the vertex to the end vertex.
     */
    private double distanceToTheEnd(Vertex vertex) {
        double distanceToEnd = Math.sqrt(Math.pow(endX - vertex.getX(), 2) + Math.pow(endY - vertex.getY(), 2));
        return distanceToEnd;
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