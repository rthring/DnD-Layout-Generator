package gui;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Gui<toReturn> extends Application {
    /**
     * Provides access to the Level.
     */
    private Controller theController;
    /**
     * The root element of this GUI.
     */
    private BorderPane root;
    /**
     * The BorderPane of the edit window.
     */
    private BorderPane editBorder;
    /**
     * The TextArea for the descriptions.
     */
    private TextArea descriptionPane;
    /**
     * The stage that is passed in on initialization.
     */
    private Stage primaryStage;
    /**
     * The stage for editing window.
     */
    private Stage editStage;
    /**
     * Initialize the GUI.
     */
    @Override
    public void start(Stage assignedStage) {
        theController = new Controller(this);
        primaryStage = assignedStage;
        root = setUpRoot();
        setUpEditStage();
        Scene scene = new Scene(root, 720, 480);
        primaryStage.setTitle("Level Graphical User Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Sets up the root for the stage.
     * @return the root with every thing attached to it.
     */
    private BorderPane setUpRoot() {
        BorderPane temp = new BorderPane();
        temp.setTop(setUpMenu());
        temp.setRight(setRightSidePanel());
        ObservableList<String> spaceList = FXCollections.observableArrayList(theController.getNameList());
        temp.setLeft(createListView(spaceList));
        Button editButton = createButton("Edit", "-fx-background-color: #ff0000; -fx-background-radius: 10, 10, 10, 10;");
        editButton.setOnAction((ActionEvent event) -> {
            editStage.show();
        });
        temp.setBottom(editButton);
        descriptionPane = new TextArea();
        descriptionPane.setLayoutX(472);
        descriptionPane.setLayoutY(124);
        temp.setCenter(descriptionPane);
        return temp;
    }
    /**
     * Sets up the menubar.
     * @return the menubar node.
     */
    private Node setUpMenu () {
        Menu m = new Menu("File");
        MenuItem m1 = new MenuItem("Save File");
        MenuItem m2 = new MenuItem("Load File");
        m.getItems().add(m1);
        m1.setOnAction((ActionEvent event) -> {
            theController.save();
        });
        m2.setOnAction((ActionEvent event) -> {
           theController.load(); 
        });
        m.getItems().add(m2);
        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        return mb;
    }
    /**
     * Create the list of chambers and passages on the left of the root.
     * @param spaces The names of the chambers and passages being sent in.
     * @return a node of the listview of the chambers and passages.
     */
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
    /**
     * Create a new listview based on the given names. Used when loading a level.
     * @param names The names of the chambers and passages.
     */
    public void newListView(ArrayList<String> names) {
        ObservableList<String> spaces = FXCollections.observableArrayList(names);
        root.setLeft(createListView(spaces));
    }
    /**
     * Set up the button panel for the right side of the stage.
     * @return a node of the button panel.
     */
    private Node setRightSidePanel() {
        VBox temp = new VBox();
        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        ComboBox doors = new ComboBox();
        doors.setOnAction((event2) -> {
            changeDescriptionText(theController.getDoorDescription(doors.getSelectionModel().getSelectedItem().toString()));
        });
        temp.getChildren().add(doors);
        return temp;
    }
    /**
     * Set up the stage for where editing of chambers and passages can occur.
     */
    private void setUpEditStage () {
        editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.initOwner(primaryStage);
        editBorder = new BorderPane();
        editBorder.setTop(setUpEditTop());
        editBorder.setCenter(setUpEditCenter());
        editBorder.setBottom(setUpEditBottom());
        Scene editScene = new Scene(editBorder, 500, 130);
        editStage.setScene(editScene);
    }
    /**
     * Set up the top of the editing stage.
     * @return the node for the top of the editing stage.
     */
    private Node setUpEditTop () {
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
        return editTop;
    }
    /**
     * Set up the center of the editing stage.
     * @return the node for the center of the editing stage.
     */
    private Node setUpEditCenter () {
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
        return editCenter;
    }
    /**
     * Set up the bottom of the editing stage.
     * @return the node for the bottom of the editing stage.
     */
    private Node setUpEditBottom () {
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
        return editBottom;
    }
    /**
     * Button creation method to ensure all buttons are similar and their format is in only one place.
     * @param text The text that goes on the button.
     * @param format The format of the button.
     * @return The created button.
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }
    /**
     * Change the description of the textarea.
     * @param text The text that the description is supposed to be changed to.
     */
    private void changeDescriptionText(String text) {
        descriptionPane.setText(text);
    }
    /**
     * Set the editing window current monsters combobox with the monsters of the currently selected Chamber/Passage.
     * @param index The index of the Chamber/Passage
     */
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
    /**
     * Set the editing window current treasures combobox with the treasures of the currently selected Chamber/Passage.
     * @param index The index of the Chamber/Passage
     */
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
    /**
     * Set the Door combobox full of the selected Chamber/Passage's Doors.
     * @param index
     */
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
    /**
     * Add the selected monster to the chamber/passages current monsters.
     */
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
    /**
     * Add the selected treasure to the chamber/passages current treasures.
     */
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
    /**
     * Delete the selected monster from the current monsters.
     */
    private void delMonster() {
        int index;
        ComboBox monsters = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren().get(1);
        index = monsters.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        monsters.getItems().remove(index);
    }
    /**
     * Delete the selected treasure from the current treasures.
     */
    private void delTreasure() {
        int index;
        ComboBox treasures = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren().get(1);
        index = treasures.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        treasures.getItems().remove(index);
    }
    /**
     * Save the changes made to the current monsters and treasures.
     */
    private void saveChanges() {
        String description;
        ComboBox monsters = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(0)).getChildren().get(1);
        ComboBox treasures = (ComboBox)((VBox)((HBox)editBorder.getCenter()).getChildren().get(1)).getChildren().get(1);
        int size = monsters.getItems().size();
        int index = ((ListView)root.getLeft()).getSelectionModel().getSelectedIndex();
        if (index == -1) {
            return;
        }
        if (index < 5) {
            theController.chamberRemoveMonstersAndTreasures(index);
            for (int i = 0; i < size; i++) {
                description = monsters.getItems().get(i).toString();
                theController.chamberAddMonster(index, description);
            }
            size = treasures.getItems().size();
            for (int i = 0; i < size; i++) {
                description = treasures.getItems().get(i).toString();
                theController.chamberAddTreasure(index, description);
            }
        } else {
            theController.passageRemoveMonstersAndTreasures(index - 5);
            for (int i = 0; i < size; i++) {
                description = monsters.getItems().get(i).toString();
                theController.passageAddMonster(index - 5, description);
            }
            size = treasures.getItems().size();
            for (int i = 0; i < size; i++) {
                description = treasures.getItems().get(i).toString();
                theController.passageAddTreasure(index - 5, description);
            }
        }
        changeDescriptionText(theController.getDescription(index));
    }
    /**
     * Launch
     * @param args An array of command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
