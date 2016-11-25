
package opendatacc.hackathon.pockettour.model.place_json_schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MuseumJSON {

    @SerializedName("nombre")
    @Expose
    private Nombre nombre;
    @SerializedName("lat")
    @Expose
    private Lat lat;
    @SerializedName("long")
    @Expose
    private Long _long;
    @SerializedName("tieneEnlaceSIG")
    @Expose
    private TieneEnlaceSIG tieneEnlaceSIG;
    @SerializedName("descripcion")
    @Expose
    private Descripcion descripcion;
    @SerializedName("enlacedbpedia")
    @Expose
    private Enlacedbpedia enlacedbpedia;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * @return The nombre
     */
    public Nombre getNombre() {
        return nombre;
    }

    /**
     * @param nombre The nombre
     */
    public void setNombre(Nombre nombre) {
        this.nombre = nombre;
    }

    public MuseumJSON withNombre(Nombre nombre) {
        this.nombre = nombre;
        return this;
    }

    /**
     * @return The lat
     */
    public Lat getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(Lat lat) {
        this.lat = lat;
    }

    public MuseumJSON withLat(Lat lat) {
        this.lat = lat;
        return this;
    }

    /**
     * @return The _long
     */
    public Long getLong() {
        return _long;
    }

    /**
     * @param _long The long
     */
    public void setLong(Long _long) {
        this._long = _long;
    }

    public MuseumJSON withLong(Long _long) {
        this._long = _long;
        return this;
    }

    /**
     * @return The tieneEnlaceSIG
     */
    public TieneEnlaceSIG getTieneEnlaceSIG() {
        return tieneEnlaceSIG;
    }

    /**
     * @param tieneEnlaceSIG The tieneEnlaceSIG
     */
    public void setTieneEnlaceSIG(TieneEnlaceSIG tieneEnlaceSIG) {
        this.tieneEnlaceSIG = tieneEnlaceSIG;
    }

    public MuseumJSON withTieneEnlaceSIG(TieneEnlaceSIG tieneEnlaceSIG) {
        this.tieneEnlaceSIG = tieneEnlaceSIG;
        return this;
    }

    /**
     * @return The descripcion
     */
    public Descripcion getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion The descripcion
     */
    public void setDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
    }

    public MuseumJSON withDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    /**
     * @return The enlacedbpedia
     */
    public Enlacedbpedia getEnlacedbpedia() {
        return enlacedbpedia;
    }

    /**
     * @param enlacedbpedia The enlacedbpedia
     */
    public void setEnlacedbpedia(Enlacedbpedia enlacedbpedia) {
        this.enlacedbpedia = enlacedbpedia;
    }

    public MuseumJSON withEnlacedbpedia(Enlacedbpedia enlacedbpedia) {
        this.enlacedbpedia = enlacedbpedia;
        return this;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    public MuseumJSON withImage(String image) {
        this.image = image;
        return this;
    }

}
