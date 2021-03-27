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
    private Rectangle[][] rectMap;
    
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
    
    public void findShortestPath(int startX, int startY, int endX, int endY) {
        distance[startY][startX] = 0;
        heap = new PriorityQueue<>();
        
        Vertex currentVertex = new Vertex(startX, startY);
        heap.add(currentVertex);
        
        while (!heap.isEmpty()) {
            Vertex vertex = heap.poll();
            if (visited[vertex.getY()][vertex.getX()]) {
                continue;
            }
            if (vertex.getX() == endX && vertex.getY() == endY) {
                System.out.println("found shortest path");
                return;
            }    
            
            visited[vertex.getY()][vertex.getX()] = true;
            rectMap[vertex.getY()][vertex.getX()].setFill(javafx.scene.paint.Color.RED);
            processNeighbours(vertex);
            
        }
        
    }
    
    /***
     * The method goes through every neighbour of the vertex
     * and checks if they are available to be processed. If
     * they are it adds them to the heap.
     * 
     * @param vertex the vertex which neighbours need to be processed.
     */
    private void processNeighbours(Vertex vertex) {
        int startX = vertex.getX();
        int startY = vertex.getY();
        //System.out.println(startX + " " +startY);
        
        for (int x = startX - 1; x <= startX + 1; x++) {
            for (int y = startY - 1; y <= startY + 1; y++) {
                if (x == startX && y == startY) {
                    continue;
                }
                
                //System.out.println("x: " + x + ", y: " + y);
                if (isInWithinMapLimits(x, y) && map[y][x] == '.') {
                    double nextVertexesDistance = distance[y][x];
                    double currentDistance = vertex.getDistance();
                    double distanceToNextVertex = 1;
                    
                    //diagonal neighbours
                    if (x != 0 && y != 0) {
                        distanceToNextVertex = Math.sqrt(2);
                    }
                    
                    double newDistance = currentDistance + distanceToNextVertex;
                    if (newDistance < nextVertexesDistance) {
                        distance[y][x] = newDistance;
                        heap.add(new Vertex(x, y));
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
    
    //temporary function for dijstra visualisation
    public void setMap(Rectangle[][] rectMap) {
        this.rectMap = rectMap;
    }
}