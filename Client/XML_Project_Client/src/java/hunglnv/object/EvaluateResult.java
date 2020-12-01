/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.object;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hungj
 */
@XmlRootElement(name = "EvaluateResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class EvaluateResult {
    
    @XmlElement(name = "Flower")
    Flower flower;
    
    @XmlElement(name = "TempaState")
    TempaState tempa;
   
    @XmlElement(name = "Message")
    String msg;
    
    @XmlElement(name = "Check")
    int check;
    
    public void setCheck(int check) {
        this.check = check;
    }
    
    @XmlElement(name = "Bulb")
    Bulb bulb;


    public Bulb getBulb() {
        return bulb;
    }

    public void setBulb(Bulb bulb) {
        this.bulb = bulb;
    }
    
    
    public EvaluateResult(Flower flower, TempaState tempa, String msg) {
        this.flower = flower;
        this.tempa = tempa;
        this.msg = msg;
    }

    public EvaluateResult() {
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public TempaState getTempa() {
        return tempa;
    }

    public void setTempa(TempaState tempa) {
        this.tempa = tempa;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
