package gui;

import level.Level;
import level.Chamber;
import level.Passage;
import level.Space;
import level.Door;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import dnd.models.Monster;
import dnd.models.Treasure;

public class Controller {
    private Gui myGui;
    private Level myLevel;
    // private static int NUM_CHAMBERS = 5;

    public Controller(Gui theGui){
        myLevel = new Level();
        myGui = theGui;
        myLevel.createLevel();
    }
    
    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("level.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myLevel);
            out.close();
            fileOut.close();
            System.out.println("Saved to level.ser");
        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }
    
    public void load() {
        try {
            FileInputStream fileIn = new FileInputStream("level.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myLevel = (Level) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded level.ser");
        } catch (Exception e) {
            System.out.println("Load failed");
        }
    }
    
    private Monster monsterByDescription(String description) {
        Monster myMonster = new Monster();
        if (description.equals("Ant, giant")) {
            myMonster.setType(2);
        } else if (description.equals("Badger")) {
            myMonster.setType(4);
        } else if (description.equals("Beetle, fire")) {
            myMonster.setType(14);
        } else if (description.equals("Demon, manes")) {
            myMonster.setType(15);
        } else if (description.equals("Dwarf")) {
            myMonster.setType(17);
        } else if (description.equals("Ear Seeker")) {
            myMonster.setType(18);
        } else if (description.equals("Elf")) {
            myMonster.setType(19);
        } else if (description.equals("Gnome")) {
            myMonster.setType(21);
        } else if (description.equals("Goblin")) {
            myMonster.setType(26);
        } else if (description.equals("Hafling")) {
            myMonster.setType(28);
        } else if (description.equals("Hobgoblin")) {
            myMonster.setType(33);
        } else if (description.equals("Human Bandit")) {
            myMonster.setType(48);
        } else if (description.equals("Kobold")) {
            myMonster.setType(54);
        } else if (description.equals("Orc")) {
            myMonster.setType(66);
        } else if (description.equals("Piercer")) {
            myMonster.setType(70);
        } else if (description.equals("Rat, giant")) {
            myMonster.setType(83);
        } else if (description.equals("Rot grub")) {
            myMonster.setType(85);
        } else if (description.equals("Shrieker")) {
            myMonster.setType(96);
        } else if (description.equals("Skeleton")) {
            myMonster.setType(98);
        } else {
            myMonster.setType(99);
        }
        return myMonster;
    }
    
    private Treasure treasureByDescription(String description) {
        Treasure myTreasure = new Treasure();
        if (description.equals("1000 copper pieces/level")) {
            myTreasure.chooseTreasure(24);
        } else if (description.equals("1000 silver pieces/level")) {
            myTreasure.chooseTreasure(50);
        } else if (description.equals("750 electrum pieces/level")) {
            myTreasure.chooseTreasure(65);
        } else if (description.equals("250 gold pieces/level")) {
            myTreasure.chooseTreasure(80);
        } else if (description.equals("100 platinum pieces/level")) {
            myTreasure.chooseTreasure(90);
        } else if (description.equals("1-4 gems/level")) {
            myTreasure.chooseTreasure(94);
        } else if (description.equals("1 piece jewellery/level")) {
            myTreasure.chooseTreasure(97);
        } else {
            myTreasure.chooseTreasure(98);
        }
        return myTreasure;
    }
    
    public void chamberAddMonster(int index, String description) {
        Chamber c = myLevel.getChambers().get(index);
        c.addMonster(monsterByDescription(description));
    }
    
    public void chamberAddTreasure(int index, String description) {
        Chamber c = myLevel.getChambers().get(index);
        c.addTreasure(treasureByDescription(description));
    }
    
    public void passageAddMonster(int index, String description) {
        Passage p = myLevel.getPassages().get(index);
        p.addRoamingMonster(monsterByDescription(description));
    }
    
    public void passageAddTreasure(int index, String description) {
        Passage p = myLevel.getPassages().get(index);
        p.addHiddenTreasure(treasureByDescription(description));
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
    
    public String[] getChamberMonsterStrings(int index) {
        ArrayList<Monster> chamberMonsters = myLevel.getChambers().get(index).getMonsters();
        String[] monsterStrings = new String[chamberMonsters.size()];
        for (int i = 0; i < chamberMonsters.size(); i++) {
            monsterStrings[i] = chamberMonsters.get(i).getDescription();
        }
        return monsterStrings;
    }
    
    public String[] getChamberTreasureStrings(int index) {
        ArrayList<Treasure> chamberTreasures = myLevel.getChambers().get(index).getTreasures();
        String[] treasureStrings = new String[chamberTreasures.size()];
        for (int i = 0; i < chamberTreasures.size(); i++) {
            treasureStrings[i] = chamberTreasures.get(i).getDescription();
        }
        return treasureStrings;
    }
    
    public String[] getPassageMonsterStrings(int index) {
        ArrayList<Monster> chamberMonsters = myLevel.getPassages().get(index).getMonsters();
        String[] monsterStrings = new String[chamberMonsters.size()];
        for (int i = 0; i < chamberMonsters.size(); i++) {
            monsterStrings[i] = chamberMonsters.get(i).getDescription();
        }
        return monsterStrings;
    }
    
    public String[] getPassageTreasureStrings(int index) {
        ArrayList<Treasure> chamberTreasures = myLevel.getPassages().get(index).getTreasures();
        String[] treasureStrings = new String[chamberTreasures.size()];
        for (int i = 0; i < chamberTreasures.size(); i++) {
            treasureStrings[i] = chamberTreasures.get(i).getDescription();
        }
        return treasureStrings;
    }
    
    public String[] getChamberDoorStrings(int index) {
        int doorIndex;
        ArrayList<Door> chamberDoors = myLevel.getChambers().get(index).getDoors();
        String[] doorStrings = new String[chamberDoors.size()];
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
        Door d;
        String description;
        ArrayList<Space> spaces;
        index = (int) (door.charAt(5) - '0');
        if (door.length() == 7) {
            index = index * 10;
            index += (int) (door.charAt(6) - '0');
        }
        d = myLevel.getDoors().get(index);
        description = d.getDescription();
        spaces = d.getSpaces();
        for (Space s : spaces) {
            for (Chamber c : myLevel.getChambers()) {
                if (s == c) {
                    description += "\nConnected to " + myLevel.getChambers().indexOf(c) + " Chamber";
                }
            }
            for (Passage p : myLevel.getPassages()) {
                if (s == p) {
                    description += "\nConnected to " + (myLevel.getPassages().indexOf(p) + 5) + " Passage";
                }
            }
        }
        return description;
    }
    
    public void chamberRemoveMonstersAndTreasures(int index) {
        Chamber c = myLevel.getChambers().get(index);
        c.removeMonsters();
        c.removeTreasures();
    }
    
    public void passageRemoveMonstersAndTreasures(int index) {
        Passage p = myLevel.getPassages().get(index);
        p.removeMonsters();
        p.removeTreasures();
    }

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

}
