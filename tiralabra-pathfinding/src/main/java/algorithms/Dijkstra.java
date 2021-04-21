package algorithms;

import datastructures.PriorityHeap;
import datastructures.Result;
import java.util.PriorityQueue;
import datastructures.Vertex;

/**
 *
 * @author henripal
 */
public class Dijkstra {
    private char[][] map;
    private boolean visited[][];
    private double distance[][];
    private PriorityHeap heap;
    private int processedNodes = 0;
    private long startTime;
    private long timeSpent;
    
    public Dijkstra(char[][] map) {
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
     * end point with Dijkstra's algorithm.
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
        heap = new PriorityHeap();
        
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
     * they are it adds them to the heap so that Dijkstra
     * can process them.
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
                        Vertex newVertex = new Vertex(x, y, newDistance, vertex);
                        heap.add(newVertex);
                    }
                }
            }
        }
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