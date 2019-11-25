package level;

import java.util.ArrayList;

public abstract class Space implements java.io.Serializable {
    /**
     *
     * @return description of Space object
     */
    public abstract  String getDescription();
    /**
     * Connect a given door to this Space.
     * @param theDoor the door to be connected with the Space object
     */
    public abstract void setDoor(Door theDoor);
    /**
     *
     * @return a list of Doors connected to the Space object
     */
    public abstract ArrayList<Door> getDoors();
}
