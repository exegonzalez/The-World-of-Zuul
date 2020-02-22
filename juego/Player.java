/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.util.HashMap;

/**
 *
 * @author Usuario
 */
public class Player {
    private String name;
    private double max_weigth;
    private HashMap<String, Item> items;

    public void setMax_weigth(double max_weigth) {
        this.max_weigth = max_weigth;
    }

    public void eat(String nombre){
        if(items.containsKey(nombre)){
            if( items.get(nombre) instanceof Comestible ){
                System.out.println("max weigth: "+max_weigth);
                Comestible c = (Comestible) items.remove(nombre);
                c.IncrementWeigth(this);
                System.out.println("new max weigth: "+max_weigth);
            }else{
                System.out.println("ese objeto no es comestible");
            }
        }else{
            System.out.println("el objeto no esta en la mochila");
        }
    }
    
    public Player(String name) {
        this.items = new HashMap<String, Item>();
        this.name = name;
        this.max_weigth = 10;
    }
    
    public HashMap<String, Item> getItems() {
        return items;
    }
    
    public Item getItem(String item){
        return items.get(item);
    }
    
    public Item quitItem(String item){
        return items.remove(item);
    }

    public String getName() {
        return name;
    }

    public double getMax_weigth() {
        return max_weigth;
    }

    
    public String getItemsString(){
        String result = "Items in Backpack: ";
        for(Item item : items.values()){
            result += " " + item.printDescription()+".\n";
        }
        return result;
    }
    
    public String getItemsBackpack(){
        return getItemsString();
    }
    
    public double currentWeight(){
        double current = 0;
        for(Item item : items.values()){
            current += item.getWeigth();
        }
        return current; 
    }
    
}
