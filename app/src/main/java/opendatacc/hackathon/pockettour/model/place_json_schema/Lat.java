
package opendatacc.hackathon.pockettour.model.place_json_schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lat {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("datatype")
    @Expose
    private String datatype;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Lat withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The datatype
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * @param datatype The datatype
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public Lat withDatatype(String datatype) {
        this.datatype = datatype;
        return this;
    }

    /**
     * @return The value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    public Lat withValue(String value) {
        this.value = value;
        return this;
    }

}
