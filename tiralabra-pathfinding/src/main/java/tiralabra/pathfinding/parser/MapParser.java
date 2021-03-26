package tiralabra.pathfinding.parser;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author henripal
 */
public class MapParser {
    public char[][] parseMap(File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            int height = Integer.parseInt(scanner.nextLine().substring(7));
            int width = Integer.parseInt(scanner.nextLine().substring(6));
            scanner.nextLine();

            char[][] mapArray = new char[height][width];

            for (int y = 0; y < height; y++) {
                String row = scanner.nextLine();
                
                for (int x = 0; x < width; x++) {
                    mapArray[y][x] = row.charAt(x);
                }
            }
            return mapArray;
            
        } catch (Exception e) {
            System.out.println("An error occured parsing the map file.");
            e.printStackTrace();
        }
        return null;
    }

}
