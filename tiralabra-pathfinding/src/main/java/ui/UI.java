package ui;

import algorithms.AStar;
import parser.MapParser;
import algorithms.Dijkstra;
import datastructures.Result;
import datastructures.Vertex;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private Button aStarButton;
    private Button clearMapButton;
    private ToggleButton setStartButton;
    private ToggleButton setEndButton;
    private TextField startXTextField;
    private TextField startYTextField;
    private TextField endXTextField;
    private TextField endYTextField;
    private Dijkstra dijkstra;
    private AStar aStar;
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
        //BorderPane.setMargin(mapGrid, new Insets(10, 10, 10, 10));
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
        aStar = new AStar(map);
        
        mainStage.setMinWidth(mapLength + 400);
        mainStage.setMinHeight(mapHeight);

        mainStage.setScene(defaultScene);
        mainStage.show();
    }
    
    private Rectangle addSquare() {
        Rectangle rect = new Rectangle(rectSize, rectSize);
        
        rect.setOnMousePressed((MouseEvent) -> {
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
                startXTextField.setText(String.valueOf(rectX));
                startYTextField.setText(String.valueOf(rectY));
            } else if (setEndButton.isSelected()) {
                if (endX != -1) {
                    rectMap[endY][endX].setFill(Color.LIGHTGRAY);
                }
                endX = rectX;
                endY = rectY;
                rectMap[rectY][rectX].setFill(Color.BLUE);
                endXTextField.setText(String.valueOf(rectX));
                endYTextField.setText(String.valueOf(rectY));
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
        
        aStarButton = new Button("A*");
        aStarButton.setPrefSize(120, 20);
        
        clearMapButton = new Button("Clear map");
        clearMapButton.setPrefSize(120, 20);
        
        setStartButton = new ToggleButton("Set start");
        setStartButton.setPrefSize(120, 20);
        
        HBox startCoordinatesHBox = new HBox();
        startCoordinatesHBox.setSpacing(5);
        startXTextField = new TextField("0");
        startXTextField.setMaxWidth(50);
        startYTextField = new TextField("0");
        startYTextField.setMaxWidth(50);
        
        startCoordinatesHBox.getChildren().addAll(new Label("X:"), startXTextField, new Label("Y"), startYTextField);
        
        setEndButton = new ToggleButton("Set end");
        setEndButton.setPrefSize(120, 20);
        
        HBox endCoordinatesHBox = new HBox();
        endCoordinatesHBox.setSpacing(5);
        endXTextField = new TextField("0");
        endXTextField.setMaxWidth(50);
        endYTextField = new TextField("0");
        endYTextField.setMaxWidth(50);
        
        endCoordinatesHBox.getChildren().addAll(new Label("X:"), endXTextField, new Label("Y"), endYTextField);
        
        rightBar.getChildren().addAll(
                changeMapButton,
                dijkstraButton, 
                aStarButton,
                clearMapButton, 
                setStartButton,
                startCoordinatesHBox,
                setEndButton,
                endCoordinatesHBox
        );
        
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
            setStartButton.setText("Set start");
            setEndButton.setText("Set end");
            
            startX = Integer.parseInt(startXTextField.getText());
            startY = Integer.parseInt(startYTextField.getText());
            endX = Integer.parseInt(endXTextField.getText());
            endY = Integer.parseInt(endYTextField.getText());
            
            if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
                Result searchResult = dijkstra.findShortestPath(startX, startY, endX, endY);
                if (searchResult.pathWasFound()) {
                    drawPath(searchResult.getLastVertex());
                } else {
                    System.out.println("No path found!");
                }
            } else {
                System.out.println("Before running the pathfinder you must provide the start and end points!");
            }
        });
        
        aStarButton.setOnMouseClicked((MouseEvent) -> {
            setStartButton.setSelected(false);
            setEndButton.setSelected(false);
            setStartButton.setText("Set start");
            setEndButton.setText("Set end");
            
            startX = Integer.parseInt(startXTextField.getText());
            startY = Integer.parseInt(startYTextField.getText());
            endX = Integer.parseInt(endXTextField.getText());
            endY = Integer.parseInt(endYTextField.getText());
            
            if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
                Result searchResult = aStar.findShortestPath(startX, startY, endX, endY);
                if (searchResult.pathWasFound()) {
                    drawPath(searchResult.getLastVertex());
                } else {
                    System.out.println("No path found!");
                }
            } else {
                System.out.println("Before running the pathfinder you must provide the start and end points!");
            }
        });
        
        clearMapButton.setOnMouseClicked((MouseEvent) -> {
            setStartButton.setSelected(false);
            setEndButton.setSelected(false);
            setStartButton.setText("Set start");
            setEndButton.setText("Set end");
            resetMap();            
        });
        
        setStartButton.setOnMouseClicked((MouseEvent) -> {
            if (setStartButton.isSelected()) {
                setStartButton.setText("Click on map!");
            } else {
                setStartButton.setText("Set start");
            }
            setEndButton.setText("Set end");
            setEndButton.setSelected(false);            
        });
        
        setEndButton.setOnMouseClicked((MouseEvent) -> {
            if (setEndButton.isSelected()) {
                setEndButton.setText("Click on map!");
            } else {
                setEndButton.setText("Set end");
            }
            setStartButton.setText("Set start");
            setStartButton.setSelected(false);            
        });
        
        startXTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newValue = startXTextField.getText().replaceAll("[^\\d]", "");
                startXTextField.setText(newValue);
                
                if (newValue.equals("")) {
                    return;
                }
                
                if (textFieldsHaveValidCoordinates(true)) {
                    if (startX != -1) {
                        rectMap[startY][startX].setFill(Color.LIGHTGRAY);
                    }
                    startX = Integer.parseInt(startXTextField.getText());
                    startY = Integer.parseInt(startYTextField.getText());
                    rectMap[startY][startX].setFill(Color.RED);
                } else {
                    startXTextField.setText(String.valueOf(startX));
                    startYTextField.setText(String.valueOf(startY));
                }
            }
        });
        
        startYTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newValue = startYTextField.getText().replaceAll("[^\\d]", "");
                startYTextField.setText(newValue);
                
                if (newValue.equals("")) {
                    return;
                }
                
                if (textFieldsHaveValidCoordinates(true)) {
                    if (startX != -1) {
                        rectMap[startY][startX].setFill(Color.LIGHTGRAY);
                    }
                    startX = Integer.parseInt(startXTextField.getText());
                    startY = Integer.parseInt(startYTextField.getText());
                    rectMap[startY][startX].setFill(Color.RED);
                } else {
                    startXTextField.setText(String.valueOf(startX));
                    startYTextField.setText(String.valueOf(startY));
                }
            }
        });
        
        endXTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newValue = endXTextField.getText().replaceAll("[^\\d]", "");
                endXTextField.setText(newValue);
                
                if (newValue.equals("")) {
                    return;
                }
                
                if (textFieldsHaveValidCoordinates(false)) {
                    if (endX != -1) {
                        rectMap[endY][endX].setFill(Color.LIGHTGRAY);
                    }
                    endX = Integer.parseInt(endXTextField.getText());
                    endY = Integer.parseInt(endYTextField.getText());
                    rectMap[endY][endX].setFill(Color.BLUE);
                } else {
                    endXTextField.setText(String.valueOf(endX));
                    endYTextField.setText(String.valueOf(endY));
                }
            }
        });
        
        endYTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newValue = endYTextField.getText().replaceAll("[^\\d]", "");
                endYTextField.setText(newValue);
                
                if (newValue.equals("")) {
                    return;
                }
                
                if (textFieldsHaveValidCoordinates(false)) {
                    if (endX != -1) {
                        rectMap[endY][endX].setFill(Color.LIGHTGRAY);
                    }
                    endX = Integer.parseInt(endXTextField.getText());
                    endY = Integer.parseInt(endYTextField.getText());
                    rectMap[endY][endX].setFill(Color.BLUE);
                } else {
                    endXTextField.setText(String.valueOf(endX));
                    endYTextField.setText(String.valueOf(endY));
                }
            }
        });

        return rightBar;
    }
    
    private void drawPath(Vertex lastVertex) {
        while (true) {
            rectMap[lastVertex.getY()][lastVertex.getX()].setFill(Color.GREEN);
            lastVertex = lastVertex.getPreviousVertex();
            if (lastVertex == null) {
                break;
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
        aStar = new AStar(map);
        
        startXTextField.setText("");
        startYTextField.setText("");
        endXTextField.setText("");
        endYTextField.setText("");
    }
    
    private boolean textFieldsHaveValidCoordinates(boolean startOrEnd) {
        String xText = startOrEnd ? startXTextField.getText() : endXTextField.getText();
        String yText = startOrEnd ? startYTextField.getText() : endYTextField.getText();
        if (xText.equals("") || yText.equals("")) {
            return false;
        }
        int x = Integer.parseInt(xText);
        int y = Integer.parseInt(yText);
        
        if (x < 0 || x > map[0].length || y < 0 || y > map.length) {
            return false;
        } else if (map[y][x] != '.') {
            return false;
        }
        return true;
    }

}
