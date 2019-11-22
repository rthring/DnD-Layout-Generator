package level;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.Random;
import java.util.ArrayList;
import dnd.models.Stairs;
import dnd.models.Trap;

public class Chamber extends Space {
    /**
     * The contents of the Chamber.
     */
    private ChamberContents myContents;
    /**
     * The shape of the Chamber.
     */
    private ChamberShape mySize;
    /**
     * List of the doors connected to the Chamber.
     */
    private ArrayList<Door> myDoors;
    /**
     * List of the treasure in the Chamber.
     */
    private ArrayList<Treasure> myTreasures;
    /**
     * List of the monsters in the Chamber.
     */
    private ArrayList<Monster> myMonsters;
    /**
     * List of the stairs in the Chamber.
     */
    private ArrayList<Stairs> myStairs;
    /**
     * List of the traps in the Chamber.
     */
    private ArrayList<Trap> myTraps;
    /* note:  Some of these methods would normally be protected or private, but because we
    don't want to dictate how you set up your packages we need them to be public
    for the purposes of running an automated test suite (junit) on your code.  */
    /**
     * Default constructor for the Chamber.
     * Assigns random shape and contents to Chamber.
     */
    public Chamber() {
        initializeProperties();
        myContents.chooseContents(randomInt(20));
        addContents();
        mySize = ChamberShape.selectChamberShape(randomInt(20));
        addStartingDoors(mySize.getNumExits());
    }
    /**
     * Parameter constructor for the Chamber.
     * Assigns shape and contents to Chamber using parameters.
     * @param theShape The shape that is to be given to the Chamber.
     * @param theContents The contents that is to be given to the Chamber.
     */
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        initializeProperties();
        myContents = theContents;
        addContents();
        mySize = theShape;
        addStartingDoors(mySize.getNumExits());
    }
    /**
     * Initializes all of this objects properties.
     */
    private void initializeProperties() {
        myDoors = new ArrayList<Door>();
        myTreasures = new ArrayList<Treasure>();
        myMonsters = new ArrayList<Monster>();
        myStairs = new ArrayList<Stairs>();
        myTraps = new ArrayList<Trap>();
        myContents = new ChamberContents();
    }
    /**
     * Returns a random integer from 1 to max.
     * @param max The max random integer that can be returned.
     * @return Random integer from 1 to max.
     */
    private int randomInt(int max) {
        Random rand = new Random();
        return (rand.nextInt(max)) + 1;
    }
    /**
     * Adds to the ArrayLists based on the Chamber's contents.
     */
    private void addContents() {
        String description = myContents.getDescription();
        if (description != null) {
            if (description.contains("treasure")) {
                myTreasures.add(randomTreasure());
            }
            if (description.contains("monster")) {
                myMonsters.add(randomMonster());
            }
            if (description.contains("stairs")) {
                myStairs.add(randomStairs());
            }
            if (description.contains("trap")) {
                myTraps.add(randomTrap());
            }
        }
    }
    /**
     * Generates a random Treasure object.
     * @return a random Treasure object.
     */
    private Treasure randomTreasure() {
        Treasure t = new Treasure();
        t.chooseTreasure(randomInt(100));
        t.setContainer(randomInt(20));
        return t;
    }
    /**
     * Generates a random Monster object.
     * @return a random Monster object.
     */
    private Monster randomMonster() {
        Monster m = new Monster();
        m.setType(randomInt(100));
        return m;
    }
    /**
     * Generates a random Treasure object.
     * @return a random Treasure object.
     */
    private Stairs randomStairs() {
        Stairs s = new Stairs();
        s.setType(randomInt(20));
        return s;
    }
    /**
     * Generates a random Treasure object.
     * @return a random Treasure object.
     */
    private Trap randomTrap() {
        Trap t = new Trap();
        t.chooseTrap(randomInt(20));
        return t;
    }
    /**
     * Adds doors to the Chamber based on the number of exits in the Chamber.
     * @param numExits The number of exits in the chamber.
     */
    private void addStartingDoors(int numExits) {
        Door d;
        int i;
        for (i = 0; i < numExits; i++) {
            d = new Door();
            d.setSpace(this);
            myDoors.add(d);
        }
    }
    /**
     * Sets the shape of the Chamber. Updates doors to reflect new shape.
     * @param theShape The shape to be given to the Chamber.
     */
    public void setShape(ChamberShape theShape) {
        int numExits;
        mySize = theShape;
        numExits = mySize.getNumExits();
        if (numExits == 0) {
            mySize.setNumExits(1);
            numExits = 1;
        }
        myDoors.clear();
        addStartingDoors(numExits);
    }
    /**
     * Add a Door to the Chamber's list of Doors.
     * @param newDoor is the Door to be added to the list.
     */
    @Override
    public void setDoor(Door newDoor) {
        newDoor.setSpace(this);
        myDoors.add(newDoor);
        mySize.setNumExits(mySize.getNumExits() + 1);
    }
    /**
     * Remove the last door of the Chamber's list of Door's.
     */
    public void removeDoor() {
        int i = myDoors.size() - 1;
        if (i >= 0) {
            myDoors.remove(i);
        }
    }
    /**
     * Gets a list of all of the Doors of this Chamber.
     */
    @Override
    public ArrayList<Door> getDoors() {
        return myDoors;
    }
    /**
     * @return the very last door in the Chamber's list of doors.
     */
    public Door getLastDoor() {
        int i = myDoors.size() - 1;
        if (i < 0) {
            return null;
        }
        return myDoors.get(i);
    }
    /**
     * Adds a monster to the Chamber's list of monsters.
     * @param theMonster The monster to be added to the Chamber.
     */
    public void addMonster(Monster theMonster) {
        myMonsters.add(theMonster);
    }
    /**
     * Gets the list of monsters in the Chamber.
     * @return the Monsters in the Chamber.
     */
    public ArrayList<Monster> getMonsters() {
        return myMonsters;
    }
    /**
     * Gets a string to fully describe the monsters in this Chamber.
     * @return a string describing the Chamber's monsters.
     */
    private String getMonsterString() {
        String description = "";
        int numMonsters = myMonsters.size();
        for (int i = 0; i < numMonsters; i++) {
            description += "Monster " + (i + 1) + " description: " + myMonsters.get(i).getDescription() + "\n";
        }
        return description;
    }
    /**
     * Adds treasure to the Chmaber's list of treasure.
     * @param theTreasure The treasure to be added to the Chamber.
     */
    public void addTreasure(Treasure theTreasure) {
        myTreasures.add(theTreasure);
    }
    /**
     * Gets the list of treasure in the Chamber.
     * @return the treasure in the Chamber.
     */
    public ArrayList<Treasure> getTreasures() {
        return myTreasures;
    }
    /**
     * Gets a string to fully describe the Treasure.
     * @return a string describing all treasure in this Chamber.
     */
    private String getTreasureString() {
        Treasure t;
        String description = "";
        int numTreasure = myTreasures.size();
        for (int i = 0; i < numTreasure; i++) {
            t = myTreasures.get(i);
            description += "Treasure " + (i + 1) + " description: " + t.getDescription() + "\n"
            + "Treasure container: " + t.getContainer() + "\n";
            try {
                description += "Treasure is guarded by " + t.getProtection() + "\n";
            } catch (NotProtectedException e) {
                description += "Treasure is unguarded\n";
            }
        }
        return description;
    }
    /**
     * Gets a string describing the Chamber's traps.
     * @return a string describing the Chamber's traps.
     */
    private String getTrapString() {
        String description = "";
        int numTraps = myTraps.size();
        for (int i = 0; i < numTraps; i++) {
            description += "Trap " + (i + 1) + " description: " + myTraps.get(i).getDescription() + "\n";
        }
        return description;
    }
    /**
     *
     * @return a string describing the Chamber's traps.
     */
    private String getStairsString() {
        String description = "";
        int numStairs = myStairs.size();
        for (int i = 0; i < numStairs; i++) {
            description += "Stairs " + (i + 1) + " description " + myStairs.get(i).getDescription() + "\n";
        }
        return description;
    }
    /**
     *
     * @return a string describing the Shape of the Chamber.
     */
    private String getChamberShapeString() {
        int length = 0;
        int width = 0;
        int area = 0;
        String description = "Shape: " + mySize.getShape() + "\n";
        try {
            length = mySize.getLength();
            width = mySize.getWidth();
            description += "The chamber is " + length + " feet by " + width + " feet.\n";
        } catch (UnusualShapeException e) {
            area = mySize.getArea();
            description += "The chamber's area is " + area + " square feet.\n";
        }
        return description;
    }
    /**
     *
     * @return a string describing the contents of the Chamber.
     */
    private String getChamberContentsString() {
        String description = "Contents: " + myContents.getDescription() + "\n";
        if (myContents.getDescription().contains("treasure")) {
            description += getTreasureString();
        } else if (myContents.getDescription().contains("monster")) {
            description += getMonsterString();
        } else if (myContents.getDescription().contains("trap")) {
            description += getTrapString();
        } else if (myContents.getDescription().contains("stairs")) {
            description += getStairsString();
        }
        return description;
    }
    /**
     *
     * @return a string fully describing the entire Chamber.
     */
    @Override
    public String getDescription() {
        String description = getChamberShapeString();
        description += getChamberContentsString();
        description += "Number of Doors: " + myDoors.size() + "\n";
        return description;
    }
}
