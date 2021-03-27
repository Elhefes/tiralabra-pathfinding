package tiralabra.pathfinding.ui;

import tiralabra.pathfinding.parser.MapParser;
import tiralabra.pathfinding.algorithms.Dijkstra;
import java.io.File;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author henripal
 */
public class UI extends Application {
    private final int rectSize = 2;        
    private BorderPane borderPane;
    private Scene defaultScene;
    private GridPane mapGrid;
    private Rectangle[][] rectMap;
    private Button dijkstraButton;
    private Button resetButton;
    private Dijkstra dijkstra;
    private char[][] parisMap;
    
    @Override
    public void start(Stage mainStage) throws Exception {        
        mainStage.setTitle("Pathfinding visualizer");

        borderPane = new BorderPane();
        mapGrid = new GridPane();
        BorderPane.setAlignment(mapGrid, Pos.CENTER);
        BorderPane.setMargin(mapGrid, new Insets(10, 10, 10, 10));
        borderPane.setCenter(mapGrid);
        borderPane.setRight(addRightBar());
        defaultScene = new Scene(borderPane);
        
        MapParser mapParser = new MapParser();
        parisMap = mapParser.parseMap(new File("./maps/Paris_1_512.map"));
        
        int mapHeight = parisMap.length;
        int mapLength = parisMap[0].length;
        
        rectMap = new Rectangle[mapHeight][mapLength];        
        
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapLength; y++) {
                Rectangle rect = new Rectangle(rectSize, rectSize);
                if (parisMap[y][x] == '.') {
                    rect.setFill(Color.LIGHTGRAY);
                } else {
                    rect.setFill(Color.BLACK);
                }
                mapGrid.add(rect, x, y);
                rectMap[y][x] = rect;
            }
        }
        
        dijkstra = new Dijkstra(parisMap);    
        dijkstra.setMap(rectMap);
        
        mainStage.setMinWidth(mapLength + 400);
        mainStage.setMinHeight(mapHeight);

        mainStage.setScene(defaultScene);
        mainStage.show();
        
    }
    
    public HBox addRightBar() {
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(15, 12, 15, 12));
        bottomBar.setSpacing(10);
        bottomBar.setStyle("-fx-background-color: #336699;");

        dijkstraButton = new Button("Dijkstra");
        dijkstraButton.setPrefSize(120, 20);
        
        resetButton = new Button("Reset");
        resetButton.setPrefSize(120, 20);
        
        bottomBar.getChildren().addAll(dijkstraButton, resetButton);
        
        dijkstraButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {                
                boolean[][] path = dijkstra.findShortestPath(20, 20, 500, 480);
                if (path != null) {
                    System.out.println("drawing");
                    drawMap(path);
                }
            }            
        });
        
        resetButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {                
                resetMap();
            }            
        });

        return bottomBar;
    }
    
    private void drawMap(boolean[][] path) {
        for (int x = 0; x < path[0].length; x++) {
            for (int y = 0; y < path.length; y++) {
                Rectangle rect = rectMap[y][x];
                if (path[y][x] == true) {
                    rect.setFill(Color.GREEN);
                }
            }
        }
    }
    
    private void resetMap() {
        for (int x = 0; x < parisMap[0].length; x++) {
            for (int y = 0; y < parisMap.length; y++) {
                Rectangle rect = rectMap[y][x];
                if (parisMap[y][x] == '.') {
                    rect.setFill(Color.LIGHTGRAY);
                } else {
                    rect.setFill(Color.BLACK);
                }
            }
        }
        
        dijkstra = new Dijkstra(parisMap);    
        dijkstra.setMap(rectMap);
    }

}
