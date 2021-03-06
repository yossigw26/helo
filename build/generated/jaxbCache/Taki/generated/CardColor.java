//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.01 at 10:11:12 AM IST 
//


package generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cardColor.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="cardColor">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="blue"/>
 *     &lt;enumeration value="red"/>
 *     &lt;enumeration value="yellow"/>
 *     &lt;enumeration value="green"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "cardColor")
@XmlEnum
public enum CardColor {

    @XmlEnumValue("blue")
    BLUE("blue"),
    @XmlEnumValue("red")
    RED("red"),
    @XmlEnumValue("yellow")
    YELLOW("yellow"),
    @XmlEnumValue("green")
    GREEN("green");
    private final String value;

    CardColor(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CardColor fromValue(String v) {
        for (CardColor c: CardColor.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
