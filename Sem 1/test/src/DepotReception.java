/**
 * Created by Andrei on 15/11/2016.
 */
public class DepotReception {
    public static void main(String[]args)
    {
        Depot depot=new Depot();
        String s[]= Useful.generateStorageAreaInfo();
        depot.addStorage(s[0],s[1]);
        String i[]=Useful.generateItemInfo();

        System.out.println("New item has been created "+ i[0] +" "+ i[1]+" "+ i[2]+" "+ i[3]);
        Item it=new Item(i[0],i[1],i[2],i[3]);
        depot.addNewItem(it);
        /*there are several functionalities implemented: adding a new Item, adding a new storage, Lending a specific item.
            After this structure has been built any other functionality is quite easy to add with a little time*/
    }
}
