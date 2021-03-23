package RangeSearch;

import java.util.Comparator;

public class ComparatorY implements Comparator<Nd> {
    @Override
    public int compare(Nd o1, Nd o2) { //lon = x //lat = y
        if (o1.getLat() < o2.getLat()) {
            return -1;
        } else if (o1.getLat() > o2.getLat()) {
            return 1;
        }
        return 0;
    }
}
