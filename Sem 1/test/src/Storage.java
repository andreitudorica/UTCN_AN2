import java.util.List;

/**
 * Created by Andrei on 15/11/2016.
 */
public class Storage extends Depot{
    String ID;
    Shelf containingShelves[]={};
    int noOfShelves=0;
    int floor;

    public int getFloor() {
        return floor;
    }

    public int getNoOfShelves() {
        return noOfShelves;
    }

    public String getID() {
        return ID;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setNoOfShelves(int noOfShelves) {
        this.noOfShelves = noOfShelves;
    }

    public Storage()
    {
        ID=null;
        noOfShelves=0;
        floor=0;
    }
    public Storage(String id,String Shelves)
    {
        ID=id;
        noOfShelves=Integer.parseInt(Shelves);
        for(int i=0;i<noOfShelves;i++)
            containingShelves[i]=new Shelf(25,i);
    }
}
