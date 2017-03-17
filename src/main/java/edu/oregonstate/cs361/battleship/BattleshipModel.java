package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by michaelhilton on 1/4/17.
 */
public class BattleshipModel {
    public Player compPlayer;

    private Mil_Ship aircraftCarrier;
    private Mil_Ship battleship;
    private Mil_Ship cruiser;
    private Civ_Ship clipper;
    private Civ_Ship dinghy;

    private Mil_Ship computer_aircraftCarrier;
    private Mil_Ship computer_battleship;
    private Mil_Ship computer_cruiser;
    private Civ_Ship computer_clipper;
    private Civ_Ship computer_dinghy;

    ArrayList<Coordinate> playerHits;
    private ArrayList<Coordinate> playerMisses;
    ArrayList<Coordinate> computerHits;
    private ArrayList<Coordinate> computerMisses;
    ArrayList<Coordinate> playerPlaces;
    ArrayList<Coordinate> computerPlaces;


    private int shipCount = 0;

    boolean scanResult = false;
    boolean shipSunk = false;


    public BattleshipModel() {
        playerHits = new ArrayList<>();
        playerMisses= new ArrayList<>();
        computerHits = new ArrayList<>();
        computerMisses= new ArrayList<>();
        playerPlaces= new ArrayList<>();
        computerPlaces= new ArrayList<>();

        aircraftCarrier = new Mil_Ship("AircraftCarrier",5, 5, new Coordinate(0,0),new Coordinate(0,0));
        battleship = new Mil_Ship("Battleship",4, 4, new Coordinate(0,0),new Coordinate(0,0));
        cruiser = new Mil_Ship("Cruiser",3, 3, new Coordinate(0,0),new Coordinate(0,0));
        clipper = new Civ_Ship("Clipper",3, 1, new Coordinate(0,0),new Coordinate(0,0));
        dinghy = new Civ_Ship("dinghy",1, 1, new Coordinate(0,0),new Coordinate(0,0));

        computer_aircraftCarrier = new Mil_Ship("Computer_AircraftCarrier",5, 5, new Coordinate(2,2),new Coordinate(2,6));
        computer_battleship = new Mil_Ship("Computer_Battleship",4, 4, new Coordinate(2,8),new Coordinate(5,8));
        computer_cruiser = new Mil_Ship("Computer_Cruiser",3, 3, new Coordinate(4,1),new Coordinate(4,3));
        computer_clipper = new Civ_Ship("Computer_Clipper",3, 1, new Coordinate(7,3),new Coordinate(7,5));
        computer_dinghy = new Civ_Ship("Computer_dinghy",1, 1, new Coordinate(9,6),new Coordinate(9,6));
    }

    public void lifeCheck(Ship in){
        shipSunk = false;
        if (in.isAlive()) {
            if (in.getHealth()==0) {
                in.makeDed();
                shipSunk = true;
            } else {
                shipSunk = false;
            }
        } else {
            shipSunk = false;
        }
    }


    public Ship getShip(String shipName) {
        if (shipName.equalsIgnoreCase("aircraftcarrier")) {
            return aircraftCarrier;
        } if(shipName.equalsIgnoreCase("battleship")) {
            return battleship;
        } if(shipName.equalsIgnoreCase("Cruiser")) {
            return cruiser;
        } if(shipName.equalsIgnoreCase("clipper")) {
            return clipper;
        }if(shipName.equalsIgnoreCase("dinghy")) {
            return dinghy;}
            if (shipName.equalsIgnoreCase("computer_aircraftcarrier")) {
                return computer_aircraftCarrier;
            } if(shipName.equalsIgnoreCase("computer_battleship")) {
                return computer_battleship;
            } if(shipName.equalsIgnoreCase("computer_Cruiser")) {
                return computer_cruiser;
            } if(shipName.equalsIgnoreCase("computer_clipper")) {
                return computer_clipper;
            }if(shipName.equalsIgnoreCase("computer_dinghy")) {
            return computer_dinghy;
        }
         else {
            return null;
        }
    }

    public void storeCoords (Ship in, int opt, String orientation) {
        if (orientation.equals("horizontal" )) {
            for (int i = 0; i < in.getLength(); i++) {
                Coordinate temp = new Coordinate(in.getStart().getAcross(), in.getStart().getDown());
                temp.setDown(temp.getDown() + i);
                playerPlaces.add(temp);
            }
        } else if (orientation.equals("vertical")) {
            for (int i = 0; i < in.getLength(); i++) {
                Coordinate temp = new Coordinate(in.getStart().getAcross(), in.getStart().getDown());
                temp.setAcross(temp.getAcross() + i);
                playerPlaces.add(temp);

            }
        }

        //for (int i = 0; i < playerPlaces.size(); i++) {
        //    playerPlaces.get(i).print();
        //}
    }

    public boolean checkCollision (Coordinate start, Coordinate end, String orientation) {
        Coordinate temp = new Coordinate(start.getAcross(), start.getDown());
        System.out.println("check collision");
        if (orientation.equals("horizontal")) {
            for (int i = 0; i <= (end.getDown() - start.getDown()); i++){
                temp.setDown(start.getDown() + i);
                //System.out.println("checking: ");
                //start.print();
                //end.print();
                //temp.print();
                if (playerPlaces.contains(temp)){
                    //System.out.println("collision");
                    return true;
                } else {
                    //System.out.println("no collision");
                    continue;
                }
            }
        } else {
            for (int i = 0; i <= (end.getAcross() - start.getAcross()); i++) {
                temp.setAcross(start.getAcross() + i);
                start.print();
                end.print();
                temp.print();
                if (playerPlaces.contains(temp)) {
                    System.out.println("collision"  );
                    return true;
                } else {
                    System.out.println("no collision");
                    continue;
                }
            }
        }
        return false;
    }

    public BattleshipModel placeShip(String shipName, String row, String col, String orientation) {
        int rowint = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);

        if (this.getShip(shipName).isAlive()) {
            this.setShipCount(this.getShipCount() + 1);
        }

        if (orientation.equals("horizontal")) {
            if (this.getShip(shipName).getLength() + colInt > 11) {
                return this;
            }
        } else {
            if (this.getShip(shipName).getLength() + rowint > 11) {
                return this;
            }
        }

        if(orientation.equals("horizontal")){
            if (shipName.equalsIgnoreCase("aircraftcarrier") || shipName.equalsIgnoreCase("computer_aircraftcarrier")) {
                if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 4), orientation)) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 4));
                    storeCoords(this.getShip(shipName), 1, "horizontal");
                }
            } if(shipName.equalsIgnoreCase("battleship") || shipName.equalsIgnoreCase("computer_battleship")) {
                if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 3), orientation))
                {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 3));
                    storeCoords(this.getShip(shipName), 1, "horizontal");
                } else { return this; }
            } if(shipName.equalsIgnoreCase("Cruiser") || shipName.equalsIgnoreCase("computer_cruiser")) {
                if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2), orientation)) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2));
                    storeCoords(this.getShip(shipName), 1, "horizontal");
                }
            } if(shipName.equalsIgnoreCase("clipper") || shipName.equalsIgnoreCase("clipper")) {
                if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2), orientation)) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 2));
                    storeCoords(this.getShip(shipName), 1, "horizontal");
                }
            }if(shipName.equalsIgnoreCase("dinghy") || shipName.equalsIgnoreCase("computer_dinghy")) {
                if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt), orientation)) {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt + 0));
                    storeCoords(this.getShip(shipName), 1, "horizontal");
                }
            }
        }else{
            //vertical
                if (shipName.equalsIgnoreCase("aircraftcarrier") || shipName.equalsIgnoreCase("computer_aircraftcarrier")) {
                    if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint + 4, colInt), orientation))
                    {
                        this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 4, colInt));
                        storeCoords(this.getShip(shipName), 1, "vertical");
                    }
                } if(shipName.equalsIgnoreCase("battleship") || shipName.equalsIgnoreCase("computer_battleship")) {
                    if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint + 3, colInt), orientation))
                    {
                        this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 3, colInt));
                        storeCoords(this.getShip(shipName), 1, "vertical");
                    }
                } if(shipName.equalsIgnoreCase("Cruiser") || shipName.equalsIgnoreCase("computer_cruiser")) {
                    if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt), orientation))
                    {
                        this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt));
                        storeCoords(this.getShip(shipName), 1, "vertical");
                    }
                } if(shipName.equalsIgnoreCase("clipper") || shipName.equalsIgnoreCase("computer_clipper")) {
                    if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt), orientation))
                    {
                    this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 2, colInt));
                    storeCoords(this.getShip(shipName), 1, "vertical");
                    }
                }if(shipName.equalsIgnoreCase("dinghy") || shipName.equalsIgnoreCase("computer_dinghy")) {
                    if (!checkCollision(new Coordinate(rowint, colInt), new Coordinate(rowint, colInt), orientation))
                    {
                        this.getShip(shipName).setLocation(new Coordinate(rowint, colInt), new Coordinate(rowint + 0, colInt));
                        storeCoords(this.getShip(shipName), 1, "vertical");
                    }
                }
        }
        return this;
    }

    public void shootAtComputer(int row, int col) {
        Coordinate coor = new Coordinate(row,col);
        if(computerMisses.contains(coor)){
            System.out.println("dupe");
        }

        if(computer_aircraftCarrier.covers(coor) && !computerHits.contains(coor)){
            computerHits.add(coor);
            computer_aircraftCarrier.decHealth();
            lifeCheck(computer_aircraftCarrier);
        }else if (computer_battleship.covers(coor) && !computerHits.contains(coor)){
            computerHits.add(coor);
            computer_battleship.decHealth();
            lifeCheck(computer_battleship);
        }else if (computer_cruiser.covers(coor) && !computerHits.contains(coor)){
            computerHits.add(coor);
            computer_cruiser.decHealth();
            lifeCheck(computer_cruiser);
        }else if (computer_clipper.covers(coor) && !computerHits.contains(coor)){
            computerHits.add(coor);
            computer_clipper.decHealth();
            lifeCheck(computer_clipper);
        }else if (computer_dinghy.covers(coor) && !computerHits.contains(coor)){
            computerHits.add(coor);
            computer_dinghy.decHealth();
            lifeCheck(computer_dinghy);
        } else {
            computerMisses.add(coor);
        }
    }

    public void shootAtPlayer() {
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

    boolean playerShot(Coordinate coor) {
        if(playerMisses.contains(coor) || playerHits.contains(coor)){
            System.out.println("duop-a-loop");
            return false;
        }

        if(aircraftCarrier.covers(coor)){
            playerHits.add(coor);
            if (compPlayer.shootstate == 0){
                compPlayer.Firsthit = coor;
            }
            aircraftCarrier.decHealth();
            lifeCheck(aircraftCarrier);
            System.out.println("Aircraft Carrier Hit! Health: "+aircraftCarrier.getHealth());
        }else if (battleship.covers(coor)){
            playerHits.add(coor);
            battleship.decHealth();
            lifeCheck(battleship);
        }else if (cruiser.covers(coor)){
            playerHits.add(coor);
            cruiser.decHealth();
            lifeCheck(cruiser);
        }else if (clipper.covers(coor)){
            playerHits.add(coor);
            clipper.decHealth();
            lifeCheck(clipper);
        }else if (dinghy.covers(coor)){
            playerHits.add(coor);
            dinghy.decHealth();
            lifeCheck(dinghy);
        } else {
            playerMisses.add(coor);
        }
        return true;
    }


    public void scan(int rowInt, int colInt) {
        Coordinate coor = new Coordinate(rowInt,colInt);
        scanResult = false;
        if(computer_aircraftCarrier.scan(coor)){
            scanResult = true;
        } else if (computer_cruiser.scan(coor)){
            scanResult = true;
        }else if (computer_clipper.scan(coor)){
            scanResult = true;
        }else if (computer_dinghy.scan(coor)){
            scanResult = true;
        } else {
            scanResult = false;
        }
    }

    public boolean getScanResult() {
        return scanResult;
    }

    public void setShipCount(int num) { shipCount = num; }

    public int getShipCount() {
//        console.log(shipCount);
        return shipCount;
    }
}