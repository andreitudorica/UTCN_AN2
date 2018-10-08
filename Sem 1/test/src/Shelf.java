/**
 * Created by Andrei on 15/11/2016.
 */
public class Shelf extends Storage {
    Item containingItems[];
    int index;
    int noOfItems;
    int LimitNo;
    int usedSpots;
    public Shelf(int lim,int shl)
    {
        LimitNo=lim;
        usedSpots=0;
        index=shl;
    }
    public int addItem(Item it)
    {
        if(usedSpots<LimitNo)
        {
            containingItems[noOfItems]=it;
            noOfItems++;
            usedSpots++;
            System.out.println("Item " + it.name + "has been added on shelf " + this.index);
            return 1;
        }
        System.out.println("No room on shelf" + this.index);
        return 0;
    }
}
