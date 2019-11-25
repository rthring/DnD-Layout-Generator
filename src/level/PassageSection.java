package level;
import dnd.models.Monster;
import dnd.models.Treasure;
public class PassageSection implements java.io.Serializable {
    /**
     * Holds monster if there is one in this section.
     */
    private Monster myMonster;
    /**
     * Holds treasure if there is one in this section.
     */
    private Treasure myTreasure;
    /**
     * Holds the door if there is a door in this section.
     */
    private Door myDoor;
    /**
     * Holds the description of this section.
     */
    private String myDescription;
    /**
     * Parameter constructor for a PassageSection.
     * Assigns description to PassageSection using parameters.
     * @param description is used to set the PassageSection's description and other properties.
     */
    public PassageSection(String description) {
        myDescription = description + "\n";
        myMonster = null;
        myTreasure = null;
        myDoor = null;
    }
    /**
     *
     * @return the door that is in this section.
     */
    public Door getDoor() {
        return myDoor;
    }
    /**
     *
     * @return the monster that is in this section.
     */
    public Monster getMonster() {
        return myMonster;
    }
    /**
    *
    * @return the treasure that is in this section.
    */
   public Treasure getTreasure() {
       return myTreasure;
   }
    /**
     * Connect the given door to this section.
     * @param theDoor the door that is to be connected to this Section.
     */
    public void setDoor(Door theDoor) {
        myDoor = theDoor;
    }
    /**
     * Add the given monster to this section.
     * @param theMonster the monster to be added.
     */
    public void addMonster(Monster theMonster) {
        if (myMonster != null) {
            myDescription = myDescription.replace(myMonster.getDescription(), theMonster.getDescription());
            myMonster = theMonster;
        } else {
            myMonster = theMonster;
            myDescription += "Monster description: " + myMonster.getDescription() + "\n";
        }
    }
    /**
     * Add the given treasure to this section.
     * @param theTreasure the treasure to be added.
     */
    public void addTreasure(Treasure theTreasure) {
        if (myTreasure != null) {
            myDescription = myDescription.replace(myTreasure.getDescription(), theTreasure.getDescription());
            myTreasure = theTreasure;
        } else {
            myTreasure = theTreasure;
            myDescription += "Treasure description: " + myTreasure.getDescription() + "\n";
        }
    }
    /**
    *
    * @return the description of this Section.
    */
   public String getDescription() {
       return myDescription;
   }
}
