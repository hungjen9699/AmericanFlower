//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.30 at 12:09:25 AM ICT 
//
package hunglnv.object;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 */
@Entity
@Table(name = "Account")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Account", propOrder = {
    "username",
    "fullname",
    "password"
})
@XmlRootElement(name = "account")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT t FROM Account t")
    , @NamedQuery(name = "Account.findByUsername", query = "SELECT t FROM Account t WHERE t.username = :username")
    , @NamedQuery(name = "Account.checkLogin", query = "SELECT t FROM Account t WHERE t.username = :username AND t.password = :password")
    , @NamedQuery(name = "Account.findByFullname", query = "SELECT t FROM Account t WHERE t.fullname = :fullname")
    , @NamedQuery(name = "Account.findByPassword", query = "SELECT t FROM Account t WHERE t.password = :password")})
public class Account implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "Username", nullable = false, length = 30)
    @XmlElement(required = true)
    protected String username;

    @Column(name = "Fullname", nullable = false, length = 50)
    @XmlElement(required = true)
    protected String fullname;

    @Column(name = "Password", nullable = false, length = 50)
    @XmlElement(required = true)
    protected String password;

    /**
     * Gets the value of the username property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the fullname property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Sets the value of the fullname property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFullname(String value) {
        this.fullname = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPassword(String value) {
        this.password = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "anhht.entity.Account[username=" + username + " ]";
    }
}