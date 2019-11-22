package gui;

import level.Level;
import level.Chamber;
import level.Passage;
import java.util.ArrayList;

public class Controller {
    private Gui myGui;
    private Level myLevel;
    // private static int NUM_CHAMBERS = 5;

    public Controller(Gui theGui){
        myGui = theGui;
        myLevel = new Level();
        myLevel.createLevel();
    }

    public ArrayList<String> getNameList(){
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Chamber> chambers = myLevel.getChambers();
        ArrayList<Passage> passages = myLevel.getPassages();
        int i;
        for(i = 0; i < chambers.size(); i++) {
            nameList.add(i + " Chamber");
        }
        for (int p = 0; p < passages.size(); p++) {
            nameList.add((i + p) + " Passage");
        }
        return nameList;
    }

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

    public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return String.join("\n", getNameList());
    }

}
