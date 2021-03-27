package tiralabra.pathfinding.algorithms;

import java.util.PriorityQueue;
import javafx.scene.shape.Rectangle;
import tiralabra.pathfinding.datastructures.Vertex;

/**
 *
 * @author henripal
 */
public class Dijkstra {
    private char[][] map;
    private boolean visited[][];
    private double distance[][];
    private boolean path[][];
    private PriorityQueue<Vertex> heap;
    private Vertex lastVertex;
    
    public Dijkstra(char[][] map) {
        this.map = map;
        int mapLength = map.length;
        int mapWidth = map[0].length;
        this.visited = new boolean[mapLength][mapWidth];
        this.path = new boolean[mapLength][mapWidth];
        this.distance = new double[mapLength][mapWidth];
        
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapLength; y++) {
                distance[y][x] = Double.MAX_VALUE;
            }
        }
    }
    
    /***
     * Finds the shortest path from start point to the
     * end point with Dijkstra's algorithm.
     * 
     * @param startX starting node's x coordinate.
     * @param startY starting node's y coordinate.
     * @param endX ending node's x coordinate.
     * @param endY ending node's y coordinate.
     * @return the shortest path as an boolean array.
     */
    public boolean[][] findShortestPath(int startX, int startY, int endX, int endY) {
        distance[startY][startX] = 0;
        heap = new PriorityQueue<>();
        
        Vertex firstVertex = new Vertex(startX, startY, null);
        heap.add(firstVertex);
        
        while (!heap.isEmpty()) {
            Vertex vertex = heap.poll();
            
            if (vertex.getX() == endX && vertex.getY() == endY) {
                lastVertex = vertex;
            }    
            
            visited[vertex.getY()][vertex.getX()] = true;
            processNeighbours(vertex);
            
        }
        generateFinalPath(lastVertex);
        return path;
    }
    
    /***
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
                
                if (isInWithinMapLimits(x, y) && map[y][x] == '.' && !visited[y][x]) {
                    double currentDistance = vertex.getDistance();
                    double distanceToNextVertex = 1;
                    
                    //diagonal neighbours
                    if (x != startX && y != startY) {
                        distanceToNextVertex = Math.sqrt(2);
                    }
                    
                    double newDistance = currentDistance + distanceToNextVertex;
                    if (newDistance < distance[y][x]) {
                        distance[y][x] = newDistance;
                        Vertex newVertex = new Vertex(x, y, vertex);
                        heap.add(newVertex);
                    }
                }
            }
        }
    }
    
    /***
     * Checks whether the coordinates are out of map.
     * 
     * @param x x-coordinate of the vertex
     * @param y y-coordinate of the vertex
     * @return whether the coordinates are out of bounds
     */
    private boolean isInWithinMapLimits(int x, int y) {
        return (x > 0 && x < map[0].length && y > 0 && y < map.length);
    }
    
    /***
     * Generates the final path which Dijkstra found.
     * 
     * @param lastVertex the last vertex of the path.
     */
    private void generateFinalPath(Vertex lastVertex) {
        path[lastVertex.getY()][lastVertex.getX()] = true;
        
        Vertex previousVertex = lastVertex.getPreviousVertex();
        if (previousVertex != null) {
            generateFinalPath(previousVertex);
        }
    }
    
}