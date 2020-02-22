/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

/**
 *
 * @author Usuario
 */
public class Item {
    private String description;
    private double weigth;
    private String name;

    public Item(String description, double weigth, String name) {
        this.description = description;
        this.weigth = weigth;
        this.name = name;
    }
    
    public String printDescription(){
        return name + ": " + description;
    }

    public String getName() {
        return name;
    }

    public double getWeigth() {
        return weigth;
    }

    
    
    
}
