package tiralabra.pathfinding.ui;

import tiralabra.pathfinding.parser.MapParser;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author henripal
 */
public class UI extends Application {
    private final int RECT_SIZE = 1;        
    private BorderPane borderPane;
    private Scene defaultScene;
    private GridPane grid;
    
    @Override
    public void start(Stage mainStage) throws Exception {        
        mainStage.setTitle("Pathfinding visualizer");

        borderPane = new BorderPane();
        grid = new GridPane();
        BorderPane.setAlignment(grid, Pos.CENTER);
        BorderPane.setMargin(grid, new Insets(10, 10, 10, 10));
        borderPane.setCenter(grid);
        defaultScene = new Scene(borderPane);
        
        MapParser mapParser = new MapParser();
        char[][] parisMap = mapParser.parseMap(new File("./maps/Paris_1_512.map"));
        System.out.println("Loaded map.");
        
        int mapHeight = parisMap.length;
        int mapLength = parisMap[0].length;
        
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapLength; y++) {
                Rectangle rect = new Rectangle(RECT_SIZE, RECT_SIZE);
                if (parisMap[y][x] == '.') {
                    rect.setFill(Color.LIGHTGRAY);
                } else {
                    rect.setFill(Color.BLACK);
                }
                grid.add(rect, x, y);
            }
        }
        
        mainStage.setMinWidth(mapLength + 400);
        mainStage.setMinHeight(mapHeight);

        mainStage.setScene(defaultScene);
        mainStage.show();
    }

}
