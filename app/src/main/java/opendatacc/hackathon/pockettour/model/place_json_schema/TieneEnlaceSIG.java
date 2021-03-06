
package opendatacc.hackathon.pockettour.model.place_json_schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TieneEnlaceSIG {

    @SerializedName("type")
    @Expose
    private String type;
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

    public TieneEnlaceSIG withType(String type) {
        this.type = type;
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

    public TieneEnlaceSIG withValue(String value) {
        this.value = value;
        return this;
    }

}
