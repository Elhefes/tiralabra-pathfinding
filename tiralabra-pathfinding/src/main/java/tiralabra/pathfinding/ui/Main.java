package tiralabra.pathfinding.ui;



import java.io.File;
import tiralabra.pathfinding.parser.*;

/**
 *
 * @author henripal
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MapParser mapParser = new MapParser();
        
        mapParser.parseMap(new File("./maps/Paris_1_512.map"));
        
    }
    
}
