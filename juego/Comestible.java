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
public class Comestible extends Item {
    
    private double increment_weigth;

    public Comestible(double increment_weigth, String description, double weigth, String name) {
        super(description, weigth, name);
        this.increment_weigth = increment_weigth;
    }
    
    public void IncrementWeigth(Player player){
        player.setMax_weigth(player.getMax_weigth() + increment_weigth);
    }
    
    public String printDescription(){
        return super.printDescription() + " incrementa el peso en " + increment_weigth;
    }
   
}
