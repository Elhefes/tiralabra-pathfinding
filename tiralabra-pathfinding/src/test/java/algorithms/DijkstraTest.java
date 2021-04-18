package algorithms;

import datastructures.Result;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import parser.MapParser;

/**
 *
 * @author henripal
 */
public class DijkstraTest {
    private Dijkstra dijkstra;
    private MapParser mapParser;
    private File mapFile;
    private char[][] map;
    
    @Before
    public void setUp() {
        mapParser = new MapParser();
        mapFile = new File("./maps/Paris_1_512.map");
        map = mapParser.parseMap(mapFile);
    }
    
    @Before
    public void before() {
        map = mapParser.parseMap(mapFile);
        dijkstra = new Dijkstra(map);
    }
    
    @Test
    public void testParisPath1() {
        Result result = dijkstra.findShortestPath(0, 0, 500, 500);
        assertEquals(757.4844148224649, result.getPathLength(), 0.00001);
        assertEquals(194834, result.getProcessedNodes());
    }
    
    @Test
    public void testParisPath2() {
        Result result = dijkstra.findShortestPath(473, 7, 161, 504);
        assertEquals(764.7859300126298, result.getPathLength(), 0.00001);
        assertEquals(195326, result.getProcessedNodes());
    }
    
    @Test
    public void testParisPath3() {
        Result result = dijkstra.findShortestPath(0, 0, 50, 50);
        assertEquals(70.7106781186547, result.getPathLength(), 0.00001);
        assertEquals(3466, result.getProcessedNodes());
    }
    
    @Test
    public void testNonExistingPath() {
        Result result = dijkstra.findShortestPath(108, 493, 0, 0);
        assertFalse(result.pathWasFound());
    }
    
    @Test
    public void sameStartAndEndPointsReturnCorrectResult() {
        Result result = dijkstra.findShortestPath(10, 10, 10, 10);
        assertEquals(0.0, result.getPathLength(), 0.0);
        assertEquals(0, result.getProcessedNodes());
    }
}
