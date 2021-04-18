package algorithms;

import datastructures.Result;
import java.util.PriorityQueue;
import datastructures.Vertex;

/**
 *
 * @author henripal
 */
public class AStar {
    private char[][] map;
    private boolean visited[][];
    private double distance[][];
    private PriorityQueue<Vertex> heap;
    private int endX;
    private int endY;
    private int processedNodes = 0;
    private long startTime;
    private long timeSpent;
    
    public AStar(char[][] map) {
        this.map = map;
        int mapLength = map.length;
        int mapWidth = map[0].length;
        this.visited = new boolean[mapLength][mapWidth];
        this.distance = new double[mapLength][mapWidth];
        
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapLength; y++) {
                distance[y][x] = Double.MAX_VALUE;
            }
        }
    }
    
    /**
     * Finds the shortest path from start point to the
     * end point with A* search algorithm.
     * 
     * @param startX starting node's x coordinate.
     * @param startY starting node's y coordinate.
     * @param endX ending node's x coordinate.
     * @param endY ending node's y coordinate.
     * @return the search result as an Result object.
     */
    public Result findShortestPath(int startX, int startY, int endX, int endY) {
        startTime = System.nanoTime();
        distance[startY][startX] = 0;
        heap = new PriorityQueue<>();
        this.endX = endX;
        this.endY = endY;
        
        Vertex firstVertex = new Vertex(startX, startY, 0, null);
        heap.add(firstVertex);
        
        while (!heap.isEmpty()) {
            Vertex latestVertex = heap.poll();
            processedNodes++;
            int x = latestVertex.getX();
            int y = latestVertex.getY();
            
            if (visited[y][x]) {
                continue;
            }
            
            if (latestVertex.getX() == endX && latestVertex.getY() == endY) {
                timeSpent = (System.nanoTime() - startTime) / 1000000;
                Result result = new Result(latestVertex, distance[endY][endX], processedNodes, timeSpent);
                return result;
            }
            
            visited[y][x] = true;
            processNeighbours(latestVertex);
        }
        return new Result();
    }
    
    /**
     * The method goes through every neighbour of the vertex
     * and checks if they are available to be processed. If
     * they are it adds them to the heap so that A* can process them.
     * 
     * @param vertex the vertex which neighbours need to be processed.
     */
    private void processNeighbours(Vertex vertex) {
        int startX = vertex.getX();
        int startY = vertex.getY();
        
        for (int x = startX - 1; x <= startX + 1; x++) {
            for (int y = startY - 1; y <= startY + 1; y++) {
                if ((x == startX && y == startY)) {
                    continue;
                }
                
                if (isWithinMapLimits(x, y) && map[y][x] == '.') {
                    double currentDistance = distance[startY][startX];
                    double distanceToNextVertex = 1;
                    
                    //Diagonal neighbours
                    if (x != startX && y != startY) {
                        distanceToNextVertex = Math.sqrt(2);
                    }
                    
                    double newDistance = currentDistance + distanceToNextVertex;
                    if (newDistance < distance[y][x]) {
                        distance[y][x] = newDistance;
                        
                        double distanceToEnd = distanceToTheEnd(vertex);
                        Vertex newVertex = new Vertex(x, y, newDistance + distanceToEnd, vertex);
                        heap.add(newVertex);
                    }
                }
            }
        }
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