package gui;

import level.Level;
import level.Chamber;
import level.Passage;
import level.Door;
import java.util.ArrayList;

public class Controller {
    private Gui myGui;
    private Level myLevel;
    // private static int NUM_CHAMBERS = 5;

    public Controller(Gui theGui){
        myLevel = new Level();
        myGui = theGui;
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
    
    public String getDescription(int i) {
        ArrayList<Chamber> chambers = myLevel.getChambers();
        ArrayList<Passage> passages = myLevel.getPassages();
        if (i < 5) {
            return chambers.get(i).getDescription();
        } else {
            i = i - 5;
            return passages.get(i).getDescription();
        }
    }
    
    public String[] getChamberDoorStrings(int index) {
        int doorIndex;
        ArrayList<Door> chamberDoors = myLevel.getChambers().get(index).getDoors();
        String[] doorStrings = new String[myLevel.getChambers().get(index).getDoors().size()];
        ArrayList<Door> allDoors = myLevel.getDoors();
        for (int i = 0; i < chamberDoors.size(); i++) {
            doorIndex = allDoors.indexOf(chamberDoors.get(i));
            doorStrings[i] = "Door " + doorIndex;
        }
        return doorStrings;
    }
    
    public String[] getPassageDoorStrings(int index) {
        int doorIndex;
        ArrayList<Door> passageDoors = myLevel.getPassages().get(index).getDoors();
        String[] doorStrings = new String[myLevel.getPassages().get(index).getDoors().size()];
        ArrayList<Door> allDoors = myLevel.getDoors();
        for (int i = 0; i < passageDoors.size(); i++) {
            doorIndex = allDoors.indexOf(passageDoors.get(i));
            doorStrings[i] = "Door " + doorIndex;
        }
        return doorStrings;
    }
    
    public String getDoorDescription(String door) {
        int index;
        index = (int) (door.charAt(5) - '0');
        if (door.length() == 7) {
            index = index * 10;
            index += (int) (door.charAt(6) - '0');
        }
        return myLevel.getDoors().get(index).getDescription();
    }

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

}
