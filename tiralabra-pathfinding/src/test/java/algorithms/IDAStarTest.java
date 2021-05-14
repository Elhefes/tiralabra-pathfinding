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
public class IDAStarTest {
    private IDAStar idaStar;
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
        idaStar = new IDAStar(map);
    }
    
    @Test
    public void testParisPath1() {
        Result result = idaStar.findShortestPath(0, 0, 42, 35, 5);
        assertEquals(56.49747468305829, result.getPathLength(), 0.00001);
        assertEquals(5167080, result.getProcessedNodes());
    }
    
    @Test
    public void testParisPath2() {
        Result result = idaStar.findShortestPath(424, 51, 502, 89, 5);
        assertEquals(93.74011537017755, result.getPathLength(), 0.00001);
        assertEquals(179560, result.getProcessedNodes());
    }
    
    @Test
    public void testParisPath3() {
        Result result = idaStar.findShortestPath(238, 453, 332, 411, 5);
        assertEquals(111.396961966987, result.getPathLength(), 0.00001);
        assertEquals(525, result.getProcessedNodes());
    }
    
    @Test
    public void testParisPath4() {
        Result result = idaStar.findShortestPath(156, 187, 152, 303, 5);
        assertEquals(117.65685424949238, result.getPathLength(), 0.00001);
        assertEquals(463, result.getProcessedNodes());
    }
    
    @Test
    public void tooLongPathTimeOuts() {
        Result result = idaStar.findShortestPath(149, 287, 169, 331, 1);
        assertFalse(result.pathWasFound());
    }
    
    @Test
    public void testNonExistingPath() {
        Result result = idaStar.findShortestPath(108, 493, 0, 0, 5);
        assertFalse(result.pathWasFound());
    }
    
    @Test
    public void sameStartAndEndPointsReturnCorrectResult() {
        Result result = idaStar.findShortestPath(10, 10, 10, 10, 5);
        assertEquals(0.0, result.getPathLength(), 0.0);
        assertEquals(1, result.getProcessedNodes());
    }
}
