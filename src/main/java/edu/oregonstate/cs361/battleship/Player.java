package edu.oregonstate.cs361.battleship;

/**
 * Created by Dennie on 3/15/2017.
 */

public class Player {
  
    public Player(){
        Firsthit = new Coordinate(11,11);
        Lasthit = new Coordinate(11,11);
        ShipCount = 0;
        shootstate = 0;
    }

    public Coordinate Firsthit;

    public Coordinate Lasthit;

    public int ShipCount;

    public int shootstate;



}


