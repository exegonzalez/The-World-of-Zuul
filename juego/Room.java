package juego;


import java.util.HashMap;


public class Room 
{
    private String description;
    private HashMap<String, Room> rooms;
    private HashMap<String, Item> items;

    public HashMap<String, Room> getRooms() {
        return rooms;
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
    
    public Room getExit(String direccion){
        return rooms.get(direccion);
    }
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        items = new HashMap<String, Item>();
        rooms = new HashMap<String, Room>();
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) 
    {
        rooms.put("north", north);
        rooms.put("east", east);
        rooms.put("south", south);
        rooms.put("west", west);
        rooms.put("up", up);
        rooms.put("down", down);
        
    }

    public void printExists(){
        System.out.println("You are " + getDescription());
        System.out.print("Salidas: ");
        if(getExit("north") != null) {
            System.out.print("north ");
        }
        if(getExit("east") != null) {
            System.out.print("east ");
        }
        if(getExit("south") != null) {
            System.out.print("south ");
        }
        if(getExit("west") != null) {
            System.out.print("west ");
        }
        if(getExit("up") != null) {
            System.out.print("up ");
        }
        if(getExit("down") != null) {
            System.out.print("down ");
        }
        System.out.println();
    }
    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getExitString(){
        String result = "Exits: ";
        for(String salida : rooms.keySet()){
            if(rooms.get(salida)!=null){
                result += " " + salida;
            }
        }
        return result;
    }
    
    public String getItemsString(){
        String result = "Items: ";
        for(Item item : items.values()){
            result += " " + item.getName();
        }
        return result;
    }
    
    public String getLongDescription(){
        return "You are "+ description + ".\n" + getExitString() + ".\n" + getItemsString();
    }
    
    public boolean isOpen(){
        if(this instanceof LockRoom){
            LockRoom lr = (LockRoom) this;
            if(lr.isLock_door()){return false;}
            else{return true;}
        }
        else{return true;}
    }
}
