**1.1 Suchen Sie den Konstruktor der Klasse Game und schreiben Sie hier den Code der ein Objekt der Klasse erzeugt (). <br>
Wo wird dies im Programm gemacht?** <br>

Konstruktor:<br>
public Game() { <br>
parser = new Parser(System.in);

    // create rooms
    outside = new Room("outside G block on Peninsula campus");
    lab = new Room("lab, a lecture theatre in A block");
    tavern = new Room("the Seahorse Tavern (the campus pub)");
    gblock = new Room("the G Block");
    office = new Room("the computing admin office");

    // initialise room exits
    outside.setExits(null, lab, gblock, tavern);
    lab.setExits(null, null, null, outside);
    tavern.setExits(null, outside, null, null);
    gblock.setExits(outside, office, null, null);
    office.setExits(null, null, null, gblock);

    currentRoom = outside;}

Instatnziert: <br>
public static void main(String[] args) { <br>
Game game = new Game(); <br> 
game.play(); }
