package level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Level {
    /**
     * Holds all chambers in the level.
     */
    private ArrayList<Chamber> chambers;
    /**
     * Holds all passages in the level.
     */
    private ArrayList<Passage> passages;
    /**
     * Holds all doors in the level.
     */
    private ArrayList<Door> doors;
    /**
     * Holds mapping of a door to its targeted chamber.
     */
    private HashMap<Door, Chamber> target;
    /**
     * Holds mapping of a door to its target door.
     */
    private HashMap<Door, Door> matches;
    /**
     * Holds mapping of Door to all of its targeted doors.
     */
    private HashMap<Door, ArrayList<Door>> finalMatches;

    /**
     * Initializes level object.
     */
    public Level() {
        chambers = new ArrayList<>();
        passages = new ArrayList<>();
        doors = new ArrayList<>();
        target = new HashMap<>();
        matches = new HashMap<>();
        finalMatches = new HashMap<>();
    }
    
    public void createLevel() {
        this.createChambers(5);
        this.findTargets();
        this.makeMatches();
        this.finalMatching();
        this.makePassages();
    }
    /**
     * Creates the given amount of chambers.
     * @param count Holds the amount of chambers to be generated.
     */
    private void createChambers(int count) {
        for (int i = 0; i < count; i++) {
            chambers.add(new Chamber());
        }
    }

    /**
     * Finds a chamber target for each door.
     */
    private void findTargets() {
        ArrayList<Chamber> targets;
        for (int i = 0; i < chambers.size(); i++) {
            for (Door d: chambers.get(i).getDoors()) {
                doors.add(d);
                targets = getPossibleTargets(i);
                target.put(d, targets.get(determineTarget(targets.size())));
            }
        }
    }

    /**
     *
     * @param index of the current chamber.
     * @return a list of all chambers excluding the current one.
     */
    private ArrayList<Chamber> getPossibleTargets(int index) {
        ArrayList<Chamber> targets = new ArrayList<>(chambers);
        targets.remove(index);
        return targets;
    }

    /**
     *
     * @param size The largest number to be generated
     * @return The random number between 0 and size - 1.
     */
    private int determineTarget(int size) {
        Random rand = new Random();
        return rand.nextInt(size);
    }

    /**
     * Make door to door pairs for connections.
     */
    private void makeMatches() {
        Chamber chamberTarget;
        Door doorTarget = null;
        Door possibleTarget = null;
        for (Chamber c: chambers) {
            for (Door dOne: c.getDoors()) {
                if (matches.get(dOne) == null) {
                    chamberTarget = target.get(dOne);
                    for (Door dTwo: chamberTarget.getDoors()) {
                        if (matches.get(dTwo) == null) {
                            if (target.get(dTwo) == c) {
                                doorTarget = dTwo;
                            } else {
                                possibleTarget = dTwo;
                            }
                        }
                    }
                    if (doorTarget == null) {
                        if (possibleTarget != null) {
                            doorTarget = possibleTarget;
                            matches.put(doorTarget, dOne);
                        } else {
                            doorTarget = chamberTarget.getDoors().get(determineTarget(chamberTarget.getDoors().size()));
                        }
                    } else {
                        matches.put(doorTarget, dOne);
                    }
                    matches.put(dOne, doorTarget);
                }
                doorTarget = null;
                possibleTarget = null;
            }
        }
    }

    /**
     * Make a list for each door of all of its connecting doors.
     */
    private void finalMatching() {
        ArrayList<Door> targetList;
        Door targetDoor;
        for (Door d: doors) {
            targetList = new ArrayList<>();
            finalMatches.put(d, targetList);
        }
        for (Door d: doors) {
            targetDoor = matches.get(d);
            targetList = finalMatches.get(d);
            if (!targetList.contains(targetDoor)) {
                targetList.add(targetDoor);
            }
            targetList = finalMatches.get(targetDoor);
            if (!targetList.contains(d)) {
                targetList.add(d);
            }
        }
    }

    /**
     * Make passage connections for each door connection.
     */
    private void makePassages() {
        boolean connected = false;
        for (Door d: finalMatches.keySet()) {
            for (Door targetDoor: finalMatches.get(d)) {
                for (Space sOne: d.getSpaces()) {
                    for (Space sTwo: targetDoor.getSpaces()) {
                        if (sOne == sTwo) {
                            connected = true;
                        }
                    }
                }
                if (!connected) {
                    connect(d, targetDoor);
                }
                connected = false;
            }
        }
    }

    /**
     * Connect 2 doors with a passage.
     * @param doorOne first door to be connected.
     * @param doorTwo second door to be connected.
     */
    private void connect(Door doorOne, Door doorTwo) {
        Passage connection = new Passage();
        PassageSection ps1 = new PassageSection("passage ends in Door to a Chamber");
        PassageSection ps2 = new PassageSection("passage ends in Door to a Chamber");
        connection.addPassageSection(ps1);
        connection.setDoor(doorOne);
        connection.addPassageSection(ps2);
        connection.setDoor(doorTwo);
        doorOne.setSpace(connection);
        doorTwo.setSpace(connection);
        passages.add(connection);
    }
    

    /**
     * Print out the generated level.
     */
    private void printLevel() {
        HashMap<Door, Integer> doorMap = new HashMap<Door, Integer>();
        HashMap<Chamber, Integer> chamberMap = new HashMap<Chamber, Integer>();
        printChambers(chamberMap, doorMap);
        printPassages(doorMap);
    }

    /**
     * Print the given chambers and their doors. Map them to their maps.
     * @param chamberMap Maps chamber to id numbers.
     * @param doorMap Maps doors to id numbers.
     */
    private void printChambers(HashMap<Chamber, Integer> chamberMap, HashMap<Door, Integer> doorMap) {
        int chamberCount = 1;
        int doorCount = 1;
        for (Chamber c: chambers) {
            System.out.println("Chamber " + chamberCount + "\n" + c.getDescription());
            chamberMap.put(c, chamberCount++);
            for (Door d: c.getDoors()) {
                System.out.println("Door " + doorCount + "\n" + d.getDescription());
                doorMap.put(d, doorCount++);
            }
            System.out.println("\n");
        }
    }

    /**
     * Print out all passages and their connecting doors.
     * @param doorMap Maps doors to id numbers.
     */
    private void printPassages(HashMap<Door, Integer> doorMap) {
        ArrayList<Door> passageDoors = new ArrayList<Door>();
        int passageCount = 1;
        for (Passage p: passages) {
            passageDoors = p.getDoors();
            System.out.println("Passage " + passageCount++ + " connects door " + doorMap.get(passageDoors.get(0))
            + " and " + doorMap.get(passageDoors.get(1)));
            System.out.println(p.getDescription());
        }
    }
    
    public ArrayList <Door> getDoorListChamber () {
        return doors;
    }
    
    public ArrayList <Door> getDoorListPassage () {
        return doors;
    }
    
    public ArrayList <Chamber> getChambers () {
        return chambers;
    }
    public ArrayList <Passage> getPassages () {
        return passages;
    }
    public ArrayList <Door> getDoors () {
        return doors;
    }
}
