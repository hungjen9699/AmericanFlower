package hunglnv.object;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Flower complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Flower">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flowerID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flowerCategoryID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flowerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="imgLink" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="season" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="germination" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maturity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="temparature" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@Entity
@Table(name = "Flower")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Flower", propOrder = {
    //    "flowerID",
//    "flowerCategoryID",
//    "flowerName",
//    "imgLink",
//    "season",
//    "germination",
//    "maturity",
//    "temparature",
//    "link"
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Flower.findAll", query = "SELECT t FROM Flower t")
    , @NamedQuery(name = "Flower.findByFlowerID", query = "SELECT t FROM Flower t WHERE t.flowerID = :flowerID")
    , @NamedQuery(name = "Flower.findByFlowerName", query = "SELECT t FROM Flower t WHERE t.flowerName = :flowerName")
    , @NamedQuery(name = "Flower.findByFlowerNameAndCategoryId", query = "SELECT t FROM Flower t WHERE t.flowerName = :name AND t.flowerCategory.flowerCategoryID = :flowerCategoryID")
    , @NamedQuery(name = "Flower.findByImgLink", query = "SELECT t FROM Flower t WHERE t.imgLink = :imgLink")
    , @NamedQuery(name = "Flower.findBySeason", query = "SELECT t FROM Flower t WHERE t.season = :season")
    , @NamedQuery(name = "Flower.findByGermination", query = "SELECT t FROM Flower t WHERE t.germination = :germination")
    , @NamedQuery(name = "Flower.findByMaturity", query = "SELECT t FROM Flower t WHERE t.maturity = :maturity")
    , @NamedQuery(name = "Flower.findByTemparature", query = "SELECT t FROM Flower t WHERE t.temparature = :temparature")
    , @NamedQuery(name = "Flower.findByLink", query = "SELECT t FROM Flower t WHERE t.link = :link")})
public class Flower implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FlowerID", nullable = false)
    protected int flowerID;

    @XmlElement(required = true)
    @Column(name = "FlowerName", length = 100)
    protected String flowerName;
   
    @XmlElement(required = true)
    @Column(name = "ImgLink", length = 200)
    protected String imgLink;
   
    @XmlElement(required = true)
    @Column(name = "Season", length = 100)
    protected String season;

    @XmlElement(required = true)
    @Column(name = "Germination")
    protected int germination;

    @XmlElement(required = true)
    @Column(name = "Maturity")
    protected int maturity;

    @XmlElement(required = true)
    @Column(name = "Temparature", precision = 53)
    protected float temparature;

    @XmlElement(required = true)
    @Column(name = "Link", length = 200)
    protected String link;

    @JoinColumn(name = "flowerCategoryID", referencedColumnName = "flowerCategoryID")
    @XmlElement
    @ManyToOne
    private FlowerCategory flowerCategory;

    public FlowerCategory getFlowerCategory() {
        return flowerCategory;
    }

    public void setFlowerCategory(FlowerCategory flowerCategory) {
        this.flowerCategory = flowerCategory;
    }

    /**
     * Gets the value of the flowerID property.
     *
     * @return 
     */
    public int getFlowerID() {
        return flowerID;
    }

    /**
     * Sets the value of the flowerID property.
     *
     * @param value
     */
    public void setFlowerID(int value) {
        this.flowerID = value;
    }

    /**
     * Gets the value of the flowerCategoryID property.
     *
     */
    /**
     * Gets the value of the flowerName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFlowerName() {
        return flowerName;
    }

    /**
     * Sets the value of the flowerName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFlowerName(String value) {
        this.flowerName = value;
    }

    /**
     * Gets the value of the imgLink property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getImgLink() {
        return imgLink;
    }

    /**
     * Sets the value of the imgLink property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setImgLink(String value) {
        this.imgLink = value;
    }

    /**
     * Gets the value of the season property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSeason() {
        return season;
    }

    /**
     * Sets the value of the season property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSeason(String value) {
        this.season = value;
    }

    /**
     * Gets the value of the germination property.
     *
     * @return 
     */
    public int getGermination() {
        return germination;
    }

    /**
     * Sets the value of the germination property.
     *
     * @param value
     */
    public void setGermination(int value) {
        this.germination = value;
    }

    /**
     * Gets the value of the maturity property.
     *
     * @return 
     */
    public int getMaturity() {
        return maturity;
    }

    /**
     * Sets the value of the maturity property.
     *
     * @param value
     */
    public void setMaturity(int value) {
        this.maturity = value;
    }

    /**
     * Gets the value of the temparature property.
     *
     * @return
     */
    public float getTemparature() {
        return temparature;
    }

    /**
     * Sets the value of the temparature property.
     *
     * @param value
     */
    public void setTemparature(float value) {
        this.temparature = value;
    }

    /**
     * Gets the value of the link property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLink(String value) {
        this.link = value;
    }

}
