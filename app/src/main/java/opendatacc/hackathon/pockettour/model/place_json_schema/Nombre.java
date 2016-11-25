
package opendatacc.hackathon.pockettour.model.place_json_schema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nombre {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("xml:lang")
    @Expose
    private String xmlLang;
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

    public Nombre withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * @return The xmlLang
     */
    public String getXmlLang() {
        return xmlLang;
    }

    /**
     * @param xmlLang The xml:lang
     */
    public void setXmlLang(String xmlLang) {
        this.xmlLang = xmlLang;
    }

    public Nombre withXmlLang(String xmlLang) {
        this.xmlLang = xmlLang;
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

    public Nombre withValue(String value) {
        this.value = value;
        return this;
    }

}
