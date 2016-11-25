package opendatacc.hackathon.pockettour.model;


import java.net.URL;

public class PlaceItem {

    private int id;
    private URL resourceURL;
    private String photoLocation;
    private String name;

    public PlaceItem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public URL getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(URL resourceURL) {
        this.resourceURL = resourceURL;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoURL) {
        this.photoLocation = photoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
