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
public class Transport extends Item{
    
    private Room room;

    public Transport(String description, double weigth, String name) {
        super(description, weigth, name);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    public boolean isCharge(){
        return room != null;
    }
}
