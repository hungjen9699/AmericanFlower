package hunglnv.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for FlowerCategory complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="FlowerCategory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="flowerCategoryID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flowerCategoryName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@Table(name = "FlowerCategory")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlowerCategory", propOrder = { //    "flowerCategoryID",
//    "flowerCategoryName",
//    "link"
})
@XmlRootElement(name = "flowerCategory")
@NamedQueries({
    @NamedQuery(name = "FlowerCategory.findAll", query = "SELECT t FROM FlowerCategory t")
    , @NamedQuery(name = "FlowerCategory.findByFlowerCategoryID", query = "SELECT t FROM FlowerCategory t WHERE t.flowerCategoryID = :flowerCategoryID")
    , @NamedQuery(name = "FlowerCategory.findByFlowerCategoryName", query = "SELECT t FROM FlowerCategory t WHERE t.flowerCategoryName = :flowerCategoryName")
    , @NamedQuery(name = "FlowerCategory.findByLink", query = "SELECT t FROM FlowerCategory t WHERE t.link = :link")})
public class FlowerCategory implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FlowerCategoryID", nullable = false)
    protected int flowerCategoryID;
    

    @XmlElement(required = true)
    @Column(name = "FlowerCategoryName", length = 100)
    protected String flowerCategoryName;

    @XmlElement(required = true)
    @Column(name = "Link", length = 200)
    protected String link;

    @XmlTransient
    @OneToMany(mappedBy = "flowerCategory")
    protected Collection<Flower> flowerCollection;


    /**
     * Gets the value of the flowerCategoryID property.
     *
     * @return
     */
    public int getFlowerCategoryID() {
        return flowerCategoryID;
    }

    /**
     * Sets the value of the flowerCategoryID property.
     *
     * @param value
     */
    public void setFlowerCategoryID(int value) {
        this.flowerCategoryID = value;
    }

    /**
     * Gets the value of the flowerCategoryName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFlowerCategoryName() {
        return flowerCategoryName;
    }

    /**
     * Sets the value of the flowerCategoryName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFlowerCategoryName(String value) {
        this.flowerCategoryName = value;
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
    
    @XmlTransient
    public Collection<Flower> getFlowerCollection() {
        return flowerCollection;
    }

    public void setFlowerCollection(Collection<Flower> flowerCollection) {
        this.flowerCollection = flowerCollection;
    }
      public void addFlower(Flower flower) {
        if(getFlowerCollection()== null) {
            this.flowerCollection = new ArrayList<>();
        }
        this.flowerCollection.add(flower);
    }

}
