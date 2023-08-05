package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerFactory {
    static Scanner input = new Scanner(System.in);
     static List <Player> players = new ArrayList<>();
     public static ArrayList<String> playersName = new ArrayList<>();
    public static List<Player> createPlayer(int numOfPlayers) {

        if (isValid(numOfPlayers)) {
            for (int i = 0; i < numOfPlayers; i++) {
                players.add(new Player("Computer " + ""+i));
                playersName.add("Computer " + ""+i);
            }
        }else {
            System.out.println("invalid number of players");
        }
        return players;
    }

    public static boolean isValid(int numberOfPlayers){
        if (numberOfPlayers >= 2){
            return true;
        } else {
            return false;
        }
    }
}
