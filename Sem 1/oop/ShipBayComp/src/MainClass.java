import entities.CargoItem;
import entities.Compartment;
import entities.Passenger;
import entities.Shipbay;
import entities.Ship;

/**
 * Created by andre on 14/01/2017.
 * The administrator of the ship bay will want to check on the situation of the ships currently in the bay:
 Sorted by name
 Sorted by profit

 For a ship, he’ll want to see:
 The summary (name, number of compartments, profit)

 */
public class MainClass {
    public static void main(String args[])
    {
        Shipbay bay=new Shipbay();
        Passenger p1 = new Passenger("Andrei");
        Compartment c= new Compartment("PASSENGER",3);
        Ship s=new Ship("Atlanta");
        c.addCarriable(p1);
        p1 = new Passenger("Mihai");
        c.addCarriable(p1);
        p1 = new Passenger("Matei");
        c.addCarriable(p1);
        s.addCompartment(c);
        c= new Compartment("CARGO",3);
        CargoItem ci=new CargoItem("telefoane",1300);
        c.addCarriable(ci);
        ci=new CargoItem("laptopuri",5000);
        c.addCarriable(ci);
        ci=new CargoItem("tower",6000);
        c.addCarriable(ci);
        s.addCompartment(c);
        bay.receiveShip(s);
        bay.printShipsByProfit();
        c.addCarriable(ci);
    }
}
