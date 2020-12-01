/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.object;

/**
 *
 * @author hungj
 */
import java.io.Serializable;
import java.util.List;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "Flowers")
@XmlAccessorType (XmlAccessType.FIELD)
public class Flowers implements Serializable
{
    @XmlElement(name = "Flower")
    private List<Flower> flowers = null;

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }
 
   
}