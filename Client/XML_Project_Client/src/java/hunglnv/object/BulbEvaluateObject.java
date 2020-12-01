/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglnv.object;

import java.io.Serializable;

/**
 *
 * @author hungj
 */
public class BulbEvaluateObject implements Serializable{
    
    private Bulb bulb;
    private Flower flower;
    private int quantity;
    private int price;
    private float stateTemparature;
    
    public float getStateTemparature() {
        return stateTemparature;
    }

    public void setStateTemparature(float stateTemparature) {
        this.stateTemparature = stateTemparature;
    }

    public BulbEvaluateObject(Bulb bulb, Flower flower, int quantity, int price, float stateTemparature) {
        this.bulb = bulb;
        this.flower = flower;
        this.quantity = quantity;
        this.price = price;
        this.stateTemparature = stateTemparature;
    }


    public BulbEvaluateObject() {
    }

    public Bulb getBulb() {
        return bulb;
    }

    public void setBulb(Bulb bulb) {
        this.bulb = bulb;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
}
