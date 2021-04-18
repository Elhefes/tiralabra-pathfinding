package ui;

import algorithms.AStar;
import parser.MapParser;
import algorithms.Dijkstra;
import algorithms.IDAStar;
import datastructures.Result;
import datastructures.Vertex;
import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private final int rectSize = 1;        
    private BorderPane borderPane;
    private Scene defaultScene;
    private GridPane mapGrid;
    private Rectangle[][] rectMap;
    private Button changeMapButton;
    private Button runButton;
    private CheckBox dijkstraCheckBox;
    private CheckBox aStarCheckBox;
    private CheckBox IDAStarCheckBox;
    private Button clearMapButton;
    private ToggleButton setStartButton;
    private ToggleButton setEndButton;
    private TextField startXTextField;
    private TextField startYTextField;
    private TextField endXTextField;
    private TextField endYTextField;
    private Label pathLengthLabel;
    private Label nodesProcessedLabel;
    private String nodesProcessedString;
    private Label timeSpentLabel;
    private String timeSpentString;
    private Dijkstra dijkstra;
    private AStar aStar;
    private IDAStar idAStar;
    private Logic logic;
    private MapParser mapParser;
    private char[][] map;
    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private Stage mainStage;
    
    @Override
    public void start(Stage mainStage) throws Exception {        
        mainStage.setTitle("Pathfinding visualizer");
        this.mainStage = mainStage;

        borderPane = new BorderPane();
        mapGrid = new GridPane();
        BorderPane.setAlignment(mapGrid, Pos.CENTER);
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
        idAStar = new IDAStar(map, rectMap);
        
        mainStage.setMinWidth(mapLength);
        mainStage.setMinHeight(mapHeight + 100);
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
        rightBar.setPadding(new Insets(20, 20, 20, 20));
        rightBar.setSpacing(10);
        
        changeMapButton = new Button("Change map");
        changeMapButton.setPrefSize(120, 20);
        
        Label coordinatesLabel = new Label("Set start and end\ncoordinates:");
        Label algorithmLabel = new Label("Pick algorithm(s):");
        dijkstraCheckBox = new CheckBox("Dijkstra");
        dijkstraCheckBox.setStyle("-fx-text-fill: #008000;");
        aStarCheckBox = new CheckBox("A* Search");
        aStarCheckBox.setStyle("-fx-text-fill: #0000FF;");
        IDAStarCheckBox = new CheckBox("Iterative Deepening A*");
        IDAStarCheckBox.setStyle("-fx-text-fill: #0000FF;");

        runButton = new Button("Run");
        runButton.setPrefSize(120, 20);
        
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
        
        startCoordinatesHBox.getChildren().addAll(new Label("X:"), startXTextField, new Label("Y:"), startYTextField);
        
        setEndButton = new ToggleButton("Set end");
        setEndButton.setPrefSize(120, 20);
        
        HBox endCoordinatesHBox = new HBox();
        endCoordinatesHBox.setSpacing(5);
        endXTextField = new TextField("0");
        endXTextField.setMaxWidth(50);
        endYTextField = new TextField("0");
        endYTextField.setMaxWidth(50);
        
        endCoordinatesHBox.getChildren().addAll(new Label("X:"), endXTextField, new Label("Y:"), endYTextField);
        
        pathLengthLabel = new Label();
        nodesProcessedLabel= new Label();
        timeSpentLabel = new Label();
        
        rightBar.getChildren().addAll(
                changeMapButton,
                coordinatesLabel,
                setStartButton,
                startCoordinatesHBox,
                setEndButton,
                endCoordinatesHBox,
                algorithmLabel,
                dijkstraCheckBox,
                aStarCheckBox,
                //IDAStarCheckBox,
                runButton,
                clearMapButton,
                pathLengthLabel,
                nodesProcessedLabel,
                timeSpentLabel
        );
        
        changeMapButton.setOnMouseClicked((MouseEvent) -> {
            File mapFile = logic.chooseFile();
            if (mapFile != null) {
                map = mapParser.parseMap(mapFile);
                if (map != null) {
                    initiateMap();
                }
            }
        });
        
        runButton.setOnMouseClicked((MouseEvent) -> {
            timeSpentString = "Time spent:";
            nodesProcessedString = "Nodes processed:";
            setStartButton.setSelected(false);
            setEndButton.setSelected(false);
            setStartButton.setText("Set start");
            setEndButton.setText("Set end");
            
            resetMap();
            
            startX = Integer.parseInt(startXTextField.getText());
            startY = Integer.parseInt(startYTextField.getText());
            endX = Integer.parseInt(endXTextField.getText());
            endY = Integer.parseInt(endYTextField.getText());
            
            if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
                if (dijkstraCheckBox.isSelected()) {
                    Result searchResult = dijkstra.findShortestPath(startX, startY, endX, endY);
                    if (searchResult.pathWasFound()) {
                        drawPath(searchResult.getLastVertex(), Color.GREEN);
                        timeSpentString += "\n-Dijkstra: "+ searchResult.getTimeSpent() + " ms";
                        nodesProcessedString += "\n-Dijkstra: " + searchResult.getProcessedNodes();
                        updateLabels(searchResult);
                    } else {
                        System.out.println("No path found!");
                    }
                }

                if (aStarCheckBox.isSelected()) {
                    Result searchResult = aStar.findShortestPath(startX, startY, endX, endY);
                    if (searchResult.pathWasFound()) {
                        drawPath(searchResult.getLastVertex(), Color.BLUE);
                        timeSpentString += "\n-A* Search: " + searchResult.getTimeSpent() + " ms";
                        nodesProcessedString += "\n-A* Search: " + searchResult.getProcessedNodes();
                        updateLabels(searchResult);
                    } else {
                        System.out.println("No path found!");
                    }
                }
                
                if (IDAStarCheckBox.isSelected()) {
                    Result searchResult = idAStar.findShortestPath(startX, startY, endX, endY);
                    if (searchResult.pathWasFound()) {
                        drawPath(searchResult.getLastVertex(), Color.RED);
                        timeSpentString += "\n-IDA*: "+ searchResult.getTimeSpent() + " ms";
                        nodesProcessedString += "\n-IDA*: " + searchResult.getProcessedNodes();
                        updateLabels(searchResult);
                    } else {
                        System.out.println("No path found!");
                    }
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
            initiateMap();            
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
    
    private void drawPath(Vertex lastVertex, Color color) {
        while (true) {
            rectMap[lastVertex.getY()][lastVertex.getX()].setFill(color);
            lastVertex = lastVertex.getPreviousVertex();
            if (lastVertex == null) {
                break;
            }
        }
    }
    
    private void initiateMap() {
        startX = -1;
        startY = -1;
        endX = -1;
        endY = -1;
            
        resetMap();        
        startXTextField.setText("");
        startYTextField.setText("");
        endXTextField.setText("");
        endYTextField.setText("");
        pathLengthLabel.setText("");
        nodesProcessedLabel.setText("");
        timeSpentLabel.setText("");
    }
    
    private void resetMap() {
        if (map.length != rectMap.length) {
            borderPane.getChildren().clear();
            mapGrid.getChildren().clear();
            
            System.out.println(map.length);
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
            System.out.println("setting");
            borderPane.setCenter(mapGrid);
            borderPane.setRight(addRightBar());
            System.out.println("set!");
        } else {
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
        }
        
        dijkstra = new Dijkstra(map);
        aStar = new AStar(map);
        idAStar = new IDAStar(map, rectMap);
    }
    
    private void updateLabels(Result result) {
        pathLengthLabel.setText("Shortest path length:\n" + result.getPathLength());
        nodesProcessedLabel.setText(nodesProcessedString);
        timeSpentLabel.setText(timeSpentString);
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
