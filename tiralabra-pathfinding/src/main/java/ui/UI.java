package ui;

import parser.MapParser;
import algorithms.Dijkstra;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private Button changeMapButton;
    private Button dijkstraButton;
    private Button clearMapButton;
    private ToggleButton setStartButton;
    private ToggleButton setEndButton;
    private Dijkstra dijkstra;
    private Logic logic;
    private MapParser mapParser;
    private char[][] map;
    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    
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
        
        logic = new Logic();
        mapParser = new MapParser();
        map = mapParser.parseMap(new File("./maps/Paris_1_512.map"));
        
        int mapHeight = map.length;
        int mapLength = map[0].length;
        
        rectMap = new Rectangle[mapHeight][mapLength];        
        
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapLength; y++) {
                Rectangle rect = addSquare();
                if (map[y][x] == '.') {
                    rect.setFill(Color.LIGHTGRAY);
                } else {
                    rect.setFill(Color.BLACK);
                }
                mapGrid.add(rect, x, y);
                rectMap[y][x] = rect;
            }
        }
        
        dijkstra = new Dijkstra(map);
        
        mainStage.setMinWidth(mapLength + 400);
        mainStage.setMinHeight(mapHeight);

        mainStage.setScene(defaultScene);
        mainStage.show();
    }
    
    private Rectangle addSquare() {
        Rectangle rect = new Rectangle(rectSize, rectSize);
        
        rect.setOnMousePressed((MouseEvent e) -> {
            int rectX = GridPane.getColumnIndex(rect);
            int rectY = GridPane.getRowIndex(rect);
            
            if (map[rectY][rectX] != '.') {
                return;
            }
            
            if (setStartButton.isSelected()) {
                if (startX != -1) {
                    rectMap[startY][startX].setFill(Color.LIGHTGRAY);
                }
                startX = rectX;
                startY = rectY;
                rectMap[rectY][rectX].setFill(Color.RED);
            } else if (setEndButton.isSelected()) {
                if (endX != -1) {
                    rectMap[startY][startX].setFill(Color.LIGHTGRAY);
                }
                endX = rectX;
                endY = rectY;
                rectMap[rectY][rectX].setFill(Color.RED);
            }
        });
        return rect;
    }
    
    public VBox addRightBar() {
        VBox rightBar = new VBox();
        rightBar.setPadding(new Insets(15, 12, 15, 12));
        rightBar.setSpacing(10);
        rightBar.setStyle("-fx-background-color: #336699;");
        
        changeMapButton = new Button("Change map");
        changeMapButton.setPrefSize(120, 20);

        dijkstraButton = new Button("Dijkstra");
        dijkstraButton.setPrefSize(120, 20);
        
        clearMapButton = new Button("Clear map");
        clearMapButton.setPrefSize(120, 20);
        
        setStartButton = new ToggleButton("Set start");
        setStartButton.setPrefSize(120, 20);
        
        setEndButton = new ToggleButton("Set end");
        setEndButton.setPrefSize(120, 20);
        
        rightBar.getChildren().addAll(changeMapButton, dijkstraButton, clearMapButton, setStartButton, setEndButton);
        
        changeMapButton.setOnMouseClicked((MouseEvent) -> {
            File mapFile = logic.chooseFile();
            if (mapFile != null) {
                map = mapParser.parseMap(mapFile);
                resetMap();
            }
        });
        
        dijkstraButton.setOnMouseClicked((MouseEvent) -> {
            setStartButton.setSelected(false);
            setEndButton.setSelected(false);
            if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
                boolean[][] path = dijkstra.findShortestPath(startX, startY, endX, endY);
                if (path != null) {
                    drawMap(path);
                }
            } else {
                System.out.println("Before running the pathfinder you must provide the start and end points!");
            }
        });
        
        clearMapButton.setOnMouseClicked((MouseEvent) -> {
            setStartButton.setSelected(false);
            setEndButton.setSelected(false);
            resetMap();            
        });
        
        setStartButton.setOnMouseClicked((MouseEvent) -> {
            setEndButton.setSelected(false);            
        });
        
        setEndButton.setOnMouseClicked((MouseEvent) -> {
            setStartButton.setSelected(false);            
        });

        return rightBar;
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
        startX = -1;
        startY = -1;
        endX = -1;
        endY = -1;
            
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                Rectangle rect = rectMap[y][x];
                if (map[y][x] == '.') {
                    rect.setFill(Color.LIGHTGRAY);
                } else {
                    rect.setFill(Color.BLACK);
                }
            }
        }
        
        dijkstra = new Dijkstra(map);
    }

}
