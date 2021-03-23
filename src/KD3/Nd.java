package KD3;

public class Nd {
    long id;
    float lat;
    float lon;

    Nd(long id, float lat, float lon){ //lon = x //lat = y
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}