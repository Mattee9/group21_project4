package edu.oregonstate.cs361.battleship;


import java.util.ArrayList;
import java.util.Random;


/**
 * hmm
 */


public class Hard extends BattleshipModel {


    public Hard() {
        /**  computer_aircraftCarrier;
         computer_battleship;
         computer_cruiser;
         computer_clipper;
         computer_dinghy;
         */
        super();
        placeCompShip("computer_aircraftCarrier");
        placeCompShip("computer_battleship");
        placeCompShip("computer_cruiser");
        placeCompShip("computer_clipper");
        placeCompShip("computer_dinghy");
    }

    public void placeCompShip(String shipName) {
        int max = 10;
        int min = 1;
        int set = 0;
        Random random = new Random();
        int randRow = random.nextInt(max - min + 1) + min;
        int randCol = random.nextInt(max - min + 1) + min;
        int randori = random.nextInt(2);
        Coordinate placeStart = new Coordinate(randRow,randCol);
        Coordinate placeEnd = new Coordinate(randRow,randCol);
        if (randori == 0){
            placeEnd.setAcross(randRow+getShip(shipName).getLength());
            placeEnd.setDown(randCol);}
        else {
            placeEnd.setAcross(randRow);
            placeEnd.setDown(randCol+getShip(shipName).getLength());}


        while (set == 0){
            System.out.println(checkCollision(placeStart,placeEnd,"vertical"));
            if (!checkCollision(placeStart,placeEnd,"vertical")) {
                System.out.println("placing");
                System.out.println(Integer.toString(randRow));
                System.out.println(Integer.toString(randCol));
                this.getShip(shipName).setLocation(placeStart,placeEnd);
                set = 1;
            }
            else{
                System.out.println("gettingnewints");
                randRow = random.nextInt(max - min + 1) + min;
                randCol = random.nextInt(max - min + 1) + min;
                placeStart = new Coordinate(randRow,randCol);
                placeEnd = new Coordinate(randRow+4, randCol);
                System.out.println("new rands");
            }
        }
    }
    public void shootAtPlayer() {
        if (compPlayer.shootstate==0) {
        int max = 10;
        int min = 1;
        Random random = new Random();
        int randRow = random.nextInt(max - min + 1) + min;
        int randCol = random.nextInt(max - min + 1) + min;

        Coordinate coor = new Coordinate(randRow,randCol);
              if (!playerShot(coor)) {
                shootAtPlayer();
            }
              }
    }
    }

