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
public class LockRoom extends Room{
    
    private boolean lock_door;

    public LockRoom(String description) {
        super(description);
        this.lock_door = true;
    }

    public boolean isLock_door() {
        return lock_door;
    }

    public void setLock_door(boolean lock_door) {
        this.lock_door = lock_door;
    }
    
       
    
}
