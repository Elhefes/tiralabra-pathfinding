package parser;

import java.io.File;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author henripal
 */
public class MapParserTest {
    static MapParser mapParser;
    static File file1;
    static File file2;
    static File file3;
    
    @BeforeClass 
    public static void setUp() {
        mapParser = new MapParser();
        file1 = new File("./maps/Paris_1_512.map");
        file2 = new File("./maps/London_1_512.map");
        file3 = new File("invalid");
    }
    
    @Test
    public void parsingMap1Works() {
        try {
            char[][] map = mapParser.parseMap(file1);
            assertTrue(map[0][0] == '.');
            assertTrue(map[100][50] == '@');
        } catch (Exception e) {
            assert false;
        }
    }
    
    @Test
    public void parsingMap2Works() {
        try {
            char[][] map = mapParser.parseMap(file2);
            assertTrue(map[100][300] == '.');
            assertTrue(map[300][300] == '@');
        } catch (Exception e) {
            assert false;
        }
    }
    
    @Test
    public void parsingInvalidMapDoesntWork() {
        try {
            char[][] map = mapParser.parseMap(file3);
            assertTrue(map == null);
        } catch (Exception e) {
            assert false;
        }
    }
}
