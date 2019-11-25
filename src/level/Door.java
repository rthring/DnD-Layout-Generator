package level;

import dnd.models.Trap;
import java.util.ArrayList;
import java.util.Random;

public class Door implements java.io.Serializable {
    /**
     * States whether the door has a trap.
     */
    private boolean isTrapped;
    /**
     * States whether the door is open or closed.
     */
    private boolean isOpen;
    /**
     * States whether the door is an archway.
     */
    private boolean isArchway;
    /**
     * States whether the door is locked.
     */
    private boolean isLocked;
    /**
     * Holds trap if the door is trapped.
     */
    private Trap doorTrap;
    /**
     * Holds spaces connected to the door.
     */
    private ArrayList<Space> mySpaces;
    /**
     * Default constructor for a Door.
     * Assigns properties randomly.
     */
    public Door() {
        initializeProperties();
        setTrapped(randomInt(20) == 1, randomInt(20));
        setArchway(randomInt(10) == 1);
        if (!isArchway) {
            isOpen = false;
            setLocked(randomInt(6) == 1);
        }
    }
    /**
     * Initializes all of this objects properties to default values.
     */
    private void initializeProperties() {
        mySpaces = new ArrayList<Space>();
        doorTrap = new Trap();
        isTrapped = false;
        isOpen = true;
        isArchway = false;
        isLocked = false;
    }
    /**
     * Returns a random integer from 1 to max.
     * @param max The max random integer that can be returned.
     * @return The random integer from 1 to max.
     */
    private int randomInt(int max) {
        Random rand = new Random();
        return (rand.nextInt(max)) + 1;
    }
    /**
     * Set isTrapped to the value of flag. If flag is true, then create a trap using the roll if it is given.
     * @param flag decides if Door is trapped or not.
     * @param roll used to determine type of trap
     */
    public void setTrapped(boolean flag, int roll) {
        if (!isArchway) {
            isTrapped = flag;
            if (flag) {
                doorTrap.chooseTrap(roll);
            }
        }
    }
    /**
     * Set isArchway to value of flag.
     * @param flag decides if door is an archway or not.
     */
    public void setArchway(boolean flag) {
        isArchway = flag;
        if (flag) {
            doorTrap = new Trap();
            isTrapped = false;
            isOpen = true;
            isLocked = false;
        }
    }
    /**
     * Set isLocked to value of flag.
     * @param flag decides if door is locked or not.
     */
    public void setLocked(boolean flag) {
        if (!isArchway) {
            isLocked = flag;
            if (flag) {
                isOpen = false;
            }
        }
    }
    /**
     * Set isOpen to value of flag.
     * @param flag decides if door is open or not.
     */
    public void setOpen(boolean flag) {
        if ((!isArchway) && (!isLocked)) {
            isOpen = flag;
        }
    }
    /**
     *
     * @return whether or not the door is trapped.
     */
    public boolean isTrapped() {
        return isTrapped;
    }
    /**
    *
    * @return whether or not the door is an archway.
    */
   public boolean isArchway() {
       return isArchway;
   }
   /**
   *
   * @return whether or not the door is locked.
   */
  public boolean isLocked() {
      return isLocked;
  }
    /**
     *
     * @return whether or not the door is open.
     */
    public boolean isOpen() {
        return isOpen;
    }
    /**
     *
     * @return description of the Door's trap.
     */
    public String getTrapDescription() {
        if (isTrapped) {
            return doorTrap.getDescription();
        }
        return "";
    }
    /**
     * Connect the door to the given space.
     * @param theSpace the space that the door needs to be connected to.
     */
    public void setSpace(Space theSpace) {
        mySpaces.add(theSpace);
    }
    /**
    *
    * @return list of the spaces the Door is connected to.
    */
   public ArrayList<Space> getSpaces() {
       return mySpaces;
   }
    /**
     *
     * @return description of this Door.
     */
    public String getDescription() {
        String description = "";
        description += "Archway: " + isArchway + ", Locked: " + isLocked + ", Open: " + isOpen + ", Trapped: " + isTrapped;
        if (isTrapped) {
            description += ", Trap description: " + doorTrap.getDescription();
        }
        return description;
    }
}
