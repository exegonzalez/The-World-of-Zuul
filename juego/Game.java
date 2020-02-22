package juego;

import com.sun.xml.internal.ws.util.StringUtils;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Deque<String> mover;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        mover = new ArrayDeque<>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, sotano, azotea;
        Item transport, llave, galletita, manzana, pizza, mesa, silla, espada, pala, escoba, cocina, mate, termo;
        
        // create the rooms
        outside = new LockRoom("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        sotano = new Room("in the sotano");
        azotea = new Room("in the azotea");
      
        //create the items
        mesa = new Item("Esto es una mesa",5,"mesa");
        silla = new Item("Esto es una silla",10,"silla");
        espada = new Item("Esto es una espada",3,"espada");
        pala = new Item("Esto es una pala",5,"pala");
        escoba = new Item("Esto es una escoba",0.75,"escoba");
        cocina = new Item("Esto es una cocina",35,"cocina");
        mate = new Item("Esto es un mate",0.50,"mate");
        termo = new Item("Esto es un termo",1,"termo");
        llave = new Item("Esto es una llave maestra", 6, "llave");
        transport = new Transport("Esto es un transportador",1,"portal");
        galletita = new Comestible(15, "esto es una galletita magica", 1.5, "cookie");
        manzana = new Comestible(1, "esto es una manzana", 0.15, "manzana");
        pizza = new Comestible(-10, "esto es una pizza en mal estado", 0.25, "pizza");
        // initialise room exits
        
        outside.setExits(null, theater, lab, pub, null, null);
        theater.setExits(null, null, null, outside, null, null);
        pub.setExits(null, outside, null, null, null, null);
        lab.setExits(outside, office, null, null, azotea, sotano);
        office.setExits(null, null, null, lab, null, null);
        azotea.setExits(null, null, null, null, null, lab);
        sotano.setExits(null, null, null, null, theater, null);

        //Cargar items en habitacion
        lab.getItems().put("mate", mate);
        lab.getItems().put("termo", termo);
        lab.getItems().put("espada", espada);
        theater.getItems().put("pala", pala);
        theater.getItems().put("cocina", cocina);
        theater.getItems().put("cookie", galletita);
        theater.getItems().put("manzana", manzana);
        theater.getItems().put("portal", transport);
        theater.getItems().put("llave", llave);
        theater.getItems().put("pizza", pizza);
        sotano.getItems().put("silla", silla);
        sotano.getItems().put("mesa", mesa);
        sotano.getItems().put("escoba", escoba);
        currentRoom = theater;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void createPlayer(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Ingrese nombre del jugador: ");
        System.out.print("> "); 
        String nombre = reader.nextLine();
        player = new Player(StringUtils.capitalize(nombre));
        //reader.close();
        
    }
    
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        createPlayer();
        System.out.println();
        System.out.println("Welcome "+player.getName()+" to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        
        System.out.println(currentRoom.getLongDescription());
            
    }
    
    

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
        private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println(currentRoom.getLongDescription());
        }
        else if (commandWord.equals("backpack")) {
            System.out.println(player.getItemsBackpack());
        }
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("charge")) {
            if(player.getItems().get(command.getSecondWord()) instanceof Transport){
                Transport t = (Transport) player.getItems().get(command.getSecondWord());
                t.setRoom(currentRoom);
                System.out.println("transportador disparado en la habitación: "+currentRoom.getDescription());
            }
            else{
                System.out.println("el item no es un transportador");
            }
        }
        else if (commandWord.equals("fire")) {
            if(player.getItems().get(command.getSecondWord()) instanceof Transport){
                Transport t = (Transport) player.getItems().get(command.getSecondWord());
                if(t.isCharge()){
                    currentRoom = t.getRoom();
                    System.out.println(currentRoom.getLongDescription());
                }else{
                    System.out.println("el transportador no esta cargado....");
                }
            }
            else{
                System.out.println("el item no es un transportador");
            }
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("eat")) {
            player.eat(command.getSecondWord());
        }
        else if (commandWord.equals("back")) {
            if(mover.size()>0){
                String direccion = mover.pop();
                Command back = new Command("go", direccion);
                goRoom(back);
                mover.pop();
            }
            else{
                System.out.println("no se puede volver para atras");}
        }
        else if (commandWord.equals("open")) {
            if(currentRoom.getRooms().containsKey(command.getSecondWord())){
                if(!currentRoom.getRooms().get(command.getSecondWord()).isOpen()){
                    if(player.getItems().containsKey("llave")){
                        ((LockRoom) currentRoom.getRooms().get(command.getSecondWord())).setLock_door(false);     
                        System.out.println("puerta abierta");
                    }
                    else{
                        System.out.println("no tiene la llave para abrir");
                    }
                }
                else{
                    System.out.println("la puerta ya esta abierta!");
                }
            }
            else{
                System.out.println("no hay puertas al "+command.getSecondWord());
            }
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(CommandWords.getCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void takeItem(Command command){
        if(!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Take what?");
                return;
            }
        else{
            Item current_item = currentRoom.quitItem(command.getSecondWord());
            if(player.getMax_weigth()-player.currentWeight()>=current_item.getWeigth()){
                if(current_item!=null){
                    player.getItems().put(command.getSecondWord(), current_item);
                }
                else{
                    System.out.println("There is no item!");
                }
            }
            else{
                    currentRoom.getItems().put(command.getSecondWord(), current_item);
                    System.out.println("no puede llevar mas carga!!!");
                }
        }
    }
    
    private void dropItem(Command command){
        if(!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Drop what?");
                return;
            }
        else{
            Item current_item = player.quitItem(command.getSecondWord());
            if(current_item!=null){
                currentRoom.getItems().put(command.getSecondWord(), current_item);
            }
            else{
                System.out.println("There is no item!");
            }
        }
    }
    
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExit("north");
            mover.push("south");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExit("east");
            mover.push("west");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExit("south");
            mover.push("north");
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.getExit("west");
            mover.push("east");
        }
        if(direction.equals("up")) {
            nextRoom = currentRoom.getExit("up");
            mover.push("down");
        }
        if(direction.equals("down")) {
            nextRoom = currentRoom.getExit("down");
            mover.push("up");
        }
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if(nextRoom.isOpen()){
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
            }
            else{
                 System.out.println("la puerta esta cerrada no se puede avanzar...");
             }
                
            }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
