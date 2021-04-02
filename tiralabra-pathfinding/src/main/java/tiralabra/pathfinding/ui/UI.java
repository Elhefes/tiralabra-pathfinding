package tiralabra.pathfinding.ui;

import tiralabra.pathfinding.parser.MapParser;
import tiralabra.pathfinding.algorithms.Dijkstra;
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
    private Button dijkstraButton;
    private Button resetButton;
    private ToggleButton setStartButton;
    private ToggleButton setEndButton;
    private Dijkstra dijkstra;
    private char[][] parisMap;
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
        
        MapParser mapParser = new MapParser();
        parisMap = mapParser.parseMap(new File("./maps/Paris_1_512.map"));
        
        int mapHeight = parisMap.length;
        int mapLength = parisMap[0].length;
        
        rectMap = new Rectangle[mapHeight][mapLength];        
        
        for (int x = 0; x < mapHeight; x++) {
            for (int y = 0; y < mapLength; y++) {
                Rectangle rect = addSquare();
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
            
            if (parisMap[rectY][rectX] != '.') {
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

        dijkstraButton = new Button("Dijkstra");
        dijkstraButton.setPrefSize(120, 20);
        
        resetButton = new Button("Reset");
        resetButton.setPrefSize(120, 20);
        
        setStartButton = new ToggleButton("Set start");
        setStartButton.setPrefSize(120, 20);
        
        setEndButton = new ToggleButton("Set end");
        setEndButton.setPrefSize(120, 20);
        
        rightBar.getChildren().addAll(dijkstraButton, resetButton, setStartButton, setEndButton);
        
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
        
        resetButton.setOnMouseClicked((MouseEvent) -> {
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
    }

}
