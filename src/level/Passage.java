package level;
import dnd.models.Monster;
import dnd.models.Treasure;

import java.util.ArrayList;
import java.util.HashMap;

public class Passage extends Space implements java.io.Serializable {
    /**
     * Holds all PassageSections of this passage.
     */
    private ArrayList<PassageSection> thePassage;
    /**
     * Holds all doors within this passage.
     */
    private ArrayList<Door> myDoors;
    /**
     * Holds all monsters within this passage.
     */
    private ArrayList<Monster> myMonsters;
    /**
     * Holds all monsters without passage sections.
     */
    private ArrayList<Monster> roamingMonsters;
    /**
     * Holds all treasures within this passage.
     */
    private ArrayList<Treasure> myTreasures;
    /**
     * Holds all treasures without passage sections.
     */
    private ArrayList<Treasure> hiddenTreasures;
    /**
     * Stores PassageSections using Doors as keys.
     */
    private HashMap<Door, PassageSection> doorMap;
    /**
     * Default constructor for the Passage.
     * Initializes both the HashMap for Doors and ArrayList for sections so they can be added to.
     */
    public Passage() {
        thePassage = new ArrayList<PassageSection>();
        doorMap = new HashMap<Door, PassageSection>();
        myDoors = new ArrayList<Door>();
        myMonsters = new ArrayList<Monster>();
        roamingMonsters = new ArrayList<Monster>();
        myTreasures = new ArrayList<Treasure>();
        hiddenTreasures = new ArrayList<Treasure>();
    }
    /**
     * Add the given Section to the Passage.
     * @param toAdd the Section that is to be added.
     */
    public void addPassageSection(PassageSection toAdd) {
        thePassage.add(toAdd);
        if (toAdd.getDoor() != null) {
            doorMap.put(toAdd.getDoor(), toAdd);
            myDoors.add(toAdd.getDoor());
        }
        if (toAdd.getMonster() != null) {
            myMonsters.add(toAdd.getMonster());
        }
        if (toAdd.getTreasure() != null) {
            myTreasures.add(toAdd.getTreasure());
        }
    }
    /**
     * Add a door connection to the current Section.
     * @param newDoor the door that is to be connected to the current section.
     */
    @Override
    public void setDoor(Door newDoor) {
        PassageSection current;
        int index = thePassage.size() - 1;
        if (index >= 0) {
            current = thePassage.get(index);
            removeDoor(index);
            current.setDoor(newDoor);
            doorMap.put(newDoor, current);
            myDoors.add(newDoor);
        }
    }
    /**
     * Checks if a section has a door, and if it does it removes it from all lists/maps.
     * @param index of the door.
     */
    private void removeDoor(int index) {
        Door d = getDoor(index);
        if (d != null) {
            myDoors.remove(d);
            doorMap.remove(d);
        }
    }
    /**
     *
     * @return list of Doors connected to this Passage.
     */
    @Override
    public ArrayList<Door> getDoors() {
        return myDoors;
    }
    /**
     * Get the list of monsters.
     * @return list of monsters.
     */
    public ArrayList<Monster> getMonsters() {
        return myMonsters;
    }
    /**
     * Get the list of treasures.
     * @return list of treasures.
     */
    public ArrayList<Treasure> getTreasures() {
        return myTreasures;
    }
    /**
     * Get the Door from the section at the given index.
     * @param i is the index of the section that contains the Door that is to be received.
     * @return the door in the section at index i.
     */
    public Door getDoor(int i) {
        PassageSection passage = thePassage.get(i);
        for (Door d: doorMap.keySet()) {
            if (doorMap.get(d) == passage) {
                return d;
            }
        }
        return null;
    }
    /**
    *
    * @param keyDoor the door that is contained in the wanted Passage Section
    * @return the number that represents the position of the Passage Section within the Passage. Starts at 1.
    */
   public int getSectionNum(Door keyDoor) {
       PassageSection section = doorMap.get(keyDoor);
       int numSections = thePassage.size();
       for (int i = 0; i < numSections; i++) {
           if (thePassage.get(i) == section) {
               return i + 1;
           }
       }
       return -1;
   }
    /**
     * Add a monster to the Section at the given index.
     * @param theMonster the monster to be added.
     * @param i the index of the wanted Section.
     */
    public void addMonster(Monster theMonster, int i) {
        if ((i >= 0) && (i < thePassage.size())) {
            thePassage.get(i).addMonster(theMonster);
        }
    }
    /**
     * Add a monster  without a passagesection.
     * @param theMonster to be added.
     */
    public void addRoamingMonster(Monster theMonster) {
        roamingMonsters.add(theMonster);
    }
    /**
     * Add a treasure without a passagesection.
     * @param theTreasure to be added.
     */
    public void addHiddenTreasure(Treasure theTreasure) {
        hiddenTreasures.add(theTreasure);
    }
    /**
     * Remove all roaming monsters.
     */
    public void removeMonsters() {
        roamingMonsters.clear();
    }
    /**
     * Remove all hidden treasures.
     */
    public void removeTreasures() {
        hiddenTreasures.clear();
    }
    /**
     * Get the Monster at the given indexed Section.
     * @param i the index of the Section with the Monster.
     * @return the Monster of the Section.
     */
    public Monster getMonster(int i) {
        if ((i >= 0) && (i < thePassage.size())) {
            return thePassage.get(i).getMonster();
        }
        return null;
    }
    /**
     *
     * @return a description of the entire passage.
     */
    @Override
    public String getDescription() {
        String description = "";
        int numSections = thePassage.size();
        for (int i = 0; i < numSections; i++) {
            description += "Passage section: " + (i + 1) + "\n" + thePassage.get(i).getDescription() + "\n";
        }
        for (int i = 0; i < roamingMonsters.size(); i++) {
            description += "Monster " + (i + 1) + " description: " + roamingMonsters.get(i).getDescription() + "\n";
        }
        for (int i = 0; i < hiddenTreasures.size(); i++) {
            description += "Treasure " + (i + 1) + " description: " + hiddenTreasures.get(i).getDescription() + "\n";
        }
        return description;
    }
}
