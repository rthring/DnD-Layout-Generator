package gui;
import javafx.collections.FXCollections;

import java.io.File;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class Gui<toReturn> extends Application {
    /* Even if it is a GUI it is useful to have instance variables
    so that you can break the processing up into smaller methods that have
    one responsibility.
     */
    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private BorderPane editBorder;
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization
    private Stage editStage;  //The stage that is passed in on initialization

    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        theController = new Controller(this);
        primaryStage = assignedStage;
        /*Border Panes have  top, left, right, center and bottom sections */
        root = setUpRoot();
        descriptionPane = createPopUp(472, 120, "");
        Scene scene = new Scene(root, 720, 480);
        primaryStage.setTitle("Level Graphical User Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        Menu m = new Menu("File");
        MenuItem m1 = new MenuItem("Save File");
        MenuItem m2 = new MenuItem("Load File");
        m.getItems().add(m1);
        
        m.getItems().add(m2);
        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        temp.setTop(mb);
        Node buttons = setButtonPanel();  //separate method for the left section
        temp.setRight(buttons);
        ObservableList<String> spaceList = FXCollections.observableArrayList(theController.getNameList());
        temp.setLeft(createListView(spaceList));
        GridPane room = new ChamberView(4,4);
        temp.setCenter(room);
        Button editButton = createButton("Edit", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        editButton.setOnAction((ActionEvent event) -> {
            editStage.show();
        });
        temp.setBottom(editButton);
        editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.initOwner(primaryStage);
        editBorder = new BorderPane();
        HBox editTop = new HBox();
        Button addMonsterButton = createButton("Add monster", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        addMonsterButton.setOnAction((ActionEvent event) -> {
            addMonster();
        });
        editTop.getChildren().add(addMonsterButton);
        Button addTreasureButton = createButton("Add treasure", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        addTreasureButton.setOnAction((ActionEvent event) -> {
            addTreasure();
        });
        editTop.getChildren().add(addTreasureButton);
        Button delMonsterButton = createButton("Delete monster", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        delMonsterButton.setOnAction((ActionEvent event) -> {
            delMonster();
        });
        editTop.getChildren().add(delMonsterButton);
        Button delTreasureButton = createButton("Delete treasure", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        delTreasureButton.setOnAction((ActionEvent event) -> {
            delTreasure();
        });
        editTop.getChildren().add(delTreasureButton);
        Button saveButton = createButton("Save changes", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        saveButton.setOnAction((ActionEvent event) -> {
            saveChanges();
            editStage.hide();
        });
        editTop.getChildren().add(saveButton);
        editBorder.setTop(editTop);
        HBox editCenter = new HBox();
        
        VBox editCenterLeft = new VBox();
        Label curMonsterLabel = new Label("Current Monsters");
        editCenterLeft.getChildren().add(curMonsterLabel);
        ComboBox curMonsters = new ComboBox();
        curMonsters.setMinWidth(130);
        editCenterLeft.getChildren().add(curMonsters);
        editCenter.getChildren().add(editCenterLeft);
        VBox editCenterRight = new VBox();
        Label curTreasureLabel = new Label("Current Treasures");
        editCenterRight.getChildren().add(curTreasureLabel);
        ComboBox curTreasures = new ComboBox();
        curTreasures.setMinWidth(130);
        editCenterRight.getChildren().add(curTreasures);
        editCenter.getChildren().add(editCenterRight);
        
        editBorder.setCenter(editCenter);
        HBox editBottom = new HBox();
        
        VBox editBotLeft = new VBox();
        Label newMonsterLabel = new Label("New Monster");
        editBotLeft.getChildren().add(newMonsterLabel);
        ComboBox allMonsters = new ComboBox();
        allMonsters.setMinWidth(130);
        String [] monsters = {"Ant, giant", 
                "Badger",
                "Beetle, fire",
                "Demon, manes",
                "Dwarf",
                "Ear Seeker",
                "Elf",
                "Gnome",
                "Goblin",
                "Hafling",
                "Hobgoblin",
                "Human Bandit",
                "Kobold",
                "Orc",
                "Piercer",
                "Rat, giant",
                "Rot grub",
                "Shrieker",
                "Skeleton",
                "Zombie"};
        allMonsters.setItems(FXCollections.observableArrayList(monsters));
        editBotLeft.getChildren().add(allMonsters);
        editBottom.getChildren().add(editBotLeft);
        VBox editBotRight = new VBox();
        Label newTreasureLabel = new Label("New Treasure");
        editBotRight.getChildren().add(newTreasureLabel);
        ComboBox allTreasures = new ComboBox();
        allTreasures.setMinWidth(130);
        String [] treasures = {"1000 copper pieces/level",
                "1000 silver pieces/level",
                "750 electrum pieces/level",
                "250 gold pieces/level",
                "100 platinum pieces/level",
                "1-4 gems/level",
                "1 piece jewellery/level",
                "1 magic item (roll on Magic item table"};
        allTreasures.setItems(FXCollections.observableArrayList(treasures));
        editBotRight.getChildren().add(allTreasures);
        
        editBottom.getChildren().add(editBotRight);
        editBorder.setBottom(editBottom);
        Scene editScene = new Scene(editBorder, 500, 130);
        editStage.setScene(editScene);
        return temp;
    }

    private Node createListView(ObservableList<String> spaces){
        ListView temp = new ListView<String>(spaces);
        temp.setPrefWidth(150);
        temp.setPrefHeight(150);
        temp.setOnMouseClicked((MouseEvent event)->{
            changeDescriptionText(theController.getDescription(temp.getSelectionModel().getSelectedIndex()));
            setComboBox(temp.getSelectionModel().getSelectedIndex());
            setMonsters(temp.getSelectionModel().getSelectedIndex());
            setTreasures(temp.getSelectionModel().getSelectedIndex());
        });
        return temp;
    }

    private Node setButtonPanel() {
        /*this method should be broken down into even smaller methods, maybe one per button*/
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        /*This button listener is only changing the view and doesn't need
        to contact the controller
         */
        Button showButton = createButton("Show Description", "-fx-background-color: #FFFFFF; ");
        showButton.setOnAction((ActionEvent event) -> {
            descriptionPane.show(primaryStage);
        });
        temp.getChildren().add(showButton);
        /*this button listener is an example of getting data from the controller */
        Button hideButton = createButton("Hide Description", "-fx-background-color: #FFFFFF; ");
        hideButton.setOnAction((ActionEvent event) -> {
            descriptionPane.hide();
        });
        temp.getChildren().add(hideButton);
        ComboBox doors = new ComboBox();
        doors.setOnAction((event2) -> {
            changeDescriptionText(theController.getDoorDescription(doors.getSelectionModel().getSelectedItem().toString()));
        });
        temp.getChildren().add(doors);
        return temp;
    }

    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        popup.setWidth(40);
        TextArea textA = new TextArea(text);
        popup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMaxWidth(432);
        textA.setMinHeight(436);
        return popup;
    }

    /*generic button creation method ensure that all buttons will have a
    similar style and means that the style only need to be in one place
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    private void changeDescriptionText(String text) {
        ObservableList<Node> list = descriptionPane.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }
        }
    }
    
    private void setMonsters(int index) {
        String labels[];
        ComboBox monsters;
        ObservableList<Node> list = ((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren();
        if (index < 5) {
            labels = theController.getChamberMonsterStrings(index);
        } else {
            labels = theController.getPassageMonsterStrings(index - 5);
        }
        monsters = (ComboBox) list.get(1);
        monsters.setItems(FXCollections.observableArrayList(labels));
    }
    
    private void setTreasures(int index) {
        String labels[];
        ComboBox treasures;
        ObservableList<Node> list = ((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren();
        if (index < 5) {
            labels = theController.getChamberTreasureStrings(index);
        } else {
            labels = theController.getPassageTreasureStrings(index - 5);
        }
        treasures = (ComboBox) list.get(1);
        treasures.setItems(FXCollections.observableArrayList(labels));
    }
    
    private void setComboBox (int index) {
        String labels[];
        ObservableList<Node> list = root.getChildren();
        if (index < 5) {
            labels = theController.getChamberDoorStrings(index);
        } else {
            labels = theController.getPassageDoorStrings(index - 5);
        }
        for (Node t : list) {
            if (t instanceof VBox) {
                ObservableList<Node> list2 = ((VBox) t).getChildren();
                for (Node p : list2) {
                    if (p instanceof ComboBox) {
                        ComboBox temp = (ComboBox) p;
                        temp.setOnAction((event2) -> {
                        });
                        temp.setItems(FXCollections.observableArrayList(labels));
                        temp.setOnAction((event2) -> {
                            changeDescriptionText(theController.getDoorDescription(temp.getSelectionModel().getSelectedItem().toString()));
                        });
                    }
                }
            }
        }
    }
    
    private void addMonster() {
        String monster;
        int index;
        try {
            monster = ((ComboBox)((VBox)((HBox)editBorder.getBottom()).getChildren().get(0)).getChildren().get(1)).getSelectionModel().getSelectedItem().toString();
        } catch (Exception e) {
            return;
        }
        ComboBox curMonsters = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren().get(1);
        curMonsters.getItems().add(monster);
    }
    
    private void addTreasure() {
        String treasure;
        try {
            treasure = ((ComboBox)((VBox)((HBox)editBorder.getBottom()).getChildren().get(1)).getChildren().get(1)).getSelectionModel().getSelectedItem().toString();
        } catch (Exception e) {
            return;
        }
        ComboBox curTreasures = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren().get(1);
        curTreasures.getItems().add(treasure);
    }
    
    private void delMonster() {
        int index;
        ComboBox monsters = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren().get(1);
        index = monsters.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        monsters.getItems().remove(index);
    }
    
    private void delTreasure() {
        int index;
        ComboBox treasures = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren().get(1);
        index = treasures.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        treasures.getItems().remove(index);
    }
    
    private void saveChanges() {
        String description;
        int size;
        int index = ((ListView)root.getLeft()).getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        if (index < 5) {
            theController.chamberRemoveMonstersAndTreasures(index);
            ComboBox monsters = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren().get(1);
            size = monsters.getItems().size();
            for (int i = 0; i < size; i++) {
                description = monsters.getItems().get(i).toString();
                theController.chamberAddMonster(index, description);
            }
            ComboBox treasures = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren().get(1);
            size = treasures.getItems().size();
            for (int i = 0; i < size; i++) {
                description = treasures.getItems().get(i).toString();
                theController.chamberAddTreasure(index, description);
            }
            changeDescriptionText(theController.getDescription(index));
        } else {
            index = index - 5;
            theController.passageRemoveMonstersAndTreasures(index);
            ComboBox monsters = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren().get(1);
            size = monsters.getItems().size();
            for (int i = 0; i < size; i++) {
                description = monsters.getItems().get(i).toString();
                theController.passageAddMonster(index, description);
            }
            ComboBox treasures = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren().get(1);
            size = treasures.getItems().size();
            for (int i = 0; i < size; i++) {
                description = treasures.getItems().get(i).toString();
                theController.passageAddTreasure(index, description);
            }
            changeDescriptionText(theController.getDescription(index + 5));
        }
    }


//    private GridPane createGridPanel() {
//        GridPane t = new GridPane();
//        /*t.setStyle(
//                "-fx-border-style: solid inside;" +
//                        "-fx-border-width: 2;" +
//                        "-fx-border-insets: 1;" +
//                        "-fx-border-radius: 9;" +
//                        "-fx-border-color: black;");*/
//        Node[] tiles = makeTiles();  //this should be a roomview object
//        t.add(tiles[0],0,0,1,1);
//        t.add(tiles[1],0,1,1,1);
//        t.add(tiles[2],0,2,1,1);
//        t.add(tiles[3],0,3,1,1);
//        t.add(tiles[4],1,0,1,1);
//        t.add(tiles[5],1,1,1,1);
//        t.add(tiles[6],1,2,1,1);
//        t.add(tiles[7],1,3,1,1);
//        t.add(tiles[8],2,0,1,1);
//        t.add(tiles[9],2,1,1,1);
//        t.add(tiles[10],2,2,1,1);
//        t.add(tiles[11],2,3,1,1);
//        t.add(tiles[12],3,0,1,1);
//        t.add(tiles[13],3,1,1,1);
//        t.add(tiles[14],3,2,1,1);
//        t.add(tiles[15],3,3,1,1);
//        //t.setHgap(0);
//        //t.setVgap(0);
//          return t;
//    }

//    private TilePane createTilePanel() {
//        TilePane t = new TilePane();
//        t.setStyle(
//                "-fx-border-style: solid inside;" +
//                        "-fx-border-width: 2;" +
//                        "-fx-border-insets: 1;" +
//                        "-fx-border-radius: 9;" +
//                        "-fx-border-color: black;");
//        Node[] tiles = makeTiles();  //this should be a roomview object
//        int len = tiles.length/4; //hacky way to make a 4x4
//        t.setOrientation(Orientation.HORIZONTAL);
//        t.setTileAlignment(Pos.CENTER_LEFT);
//        t.setHgap(0);
//        t.setVgap(0);
//        t.setPrefColumns(4);
//        t.setMaxWidth(4 *50);  //should be getting the size from the roomview object
//        ObservableList list = t.getChildren();
//        list.addAll(tiles);  //write a method that adds the roomview objects
//        return t;
//    }

//    private Node[] makeTiles() {
//    String floor = "/res/floor.png";
//    String treasure = "/res/tres.png";
//
//        Node[] toReturn = {
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(treasure),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor),
//                floorFactory(floor)
//            };
//
//        return toReturn;
//}
//
//    public Node floorFactory(String img) {
//        Image floor = new Image(getClass().getResourceAsStream(img));
//        Label toReturn = new Label();
//        ImageView imageView = new ImageView(floor);
//        imageView.setFitWidth(50);
//        imageView.setFitHeight(50);
//        toReturn.setGraphic(imageView);
//        return toReturn;
//    }

    public static void main(String[] args) {
        launch(args);
    }

}
