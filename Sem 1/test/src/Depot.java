/**
 * Created by Andrei on 15/11/2016.
 */
public class Depot {

    Storage containingStorages[]={};
    public int noOfStorages=0;

    public Depot()
    {
        noOfStorages=0;
    }


    public void addStorage(String ID,String shelves){
        Storage s=new Storage(ID,shelves);
        noOfStorages++;
        System.out.println(s.ID+' '+s.noOfShelves);
    }
    public void lendSpecificItem(String Code){
        for(int i=0;i<noOfStorages;i++)
            for(int j=0;j<containingStorages[i].noOfShelves;j++)
                for(int k=0;k<containingStorages[i].containingShelves[j].noOfItems;k++)
                    if(containingStorages[i].containingShelves[j].containingItems[k].code==Code)
                    {
                        Item it=containingStorages[i].containingShelves[j].containingItems[k];
                        if(!it.isLended) {
                            it.LendItem();
                            System.out.println("Item " + it.name + "has been lended");
                        }
                        else
                            System.out.println("Item " + it.name + "is no longer in the depot (might have been lended earlier)");
                        i = noOfStorages;
                        j = containingStorages[i].noOfShelves;
                        break;
                    }
    }
    public void addNewItem(Item it)
    {
        for(int i=0;i<noOfStorages;i++)
            for(int j=0;j<containingStorages[i].noOfShelves;j++)
                if(containingStorages[i].containingShelves[j].addItem(it)==1)
                {
                    i=noOfStorages;
                    break;
                }
    }
}
