package KDTree;

import java.util.Comparator;

public class ComparatorX implements Comparator<Nd> {
    @Override
    public int compare(Nd o1, Nd o2) { //lon = x //lat = y
        if (o1.getLon() < o2.getLon()) {
            return -1;
        } else if (o1.getLon() > o2.getLon()) {
            return 1;
        }
        return 0;
    }
}
