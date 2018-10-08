package comparators;

import entities.Shuttle;

import java.util.Comparator;

/**
 * Created by Andrei on 17/01/2017.
 */
public class ShuttleComparator implements Comparator<Shuttle> {

    @Override
    public int compare(Shuttle d1, Shuttle d2) {
        return Double.compare(d1.getProfit(), d2.getProfit());
    }
}