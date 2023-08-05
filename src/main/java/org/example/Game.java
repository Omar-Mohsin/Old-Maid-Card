package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static int playersFinishedTurn = 0;

    static int numberOfPlayer;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter how many player to play");
         numberOfPlayer =  input.nextInt();
        List<Player> players = PlayerFactory.createPlayer(numberOfPlayer);
        for (Player player : players) {
            new Thread(player).start();
        }
        int numCardsPerPlayer = 53 / players.size();
        int remainingCards = 53 % numberOfPlayer;

        Deck deck = new Deck();
        deck.initializeDeck();


        deck.shuffle();

        int currentPlayer = 0;
        for (int i = 0; i < numberOfPlayer; i++) {
            List<CustomCard> dealtCards = new ArrayList<>();
            for (int j = 0; j < numCardsPerPlayer; j++) {
                CustomCard card = deck.dealCard();
                dealtCards.add(card);
            }
            players.get(i).addCardsToHand(dealtCards);
        }
        while (remainingCards > 0) {
            CustomCard card = deck.dealCard();
            players.get(currentPlayer).addCardToHand(card);
            currentPlayer = (currentPlayer + 1) % numberOfPlayer;
            remainingCards--;
        }

        for (Player player : players) {
            if (player.checkForJoker()) {
                System.out.println("Player " + player.getPlayerName() + " is the loser.");
                break;
            }
        }
        System.out.println();


        for (Player player : players) {
            System.out.println(player.playerHand);
        }

    }

    public static synchronized void playerFinishedTurn() {

    /*    playersFinishedTurn++;
        if (playersFinishedTurn >= numberOfPlayer) {
            playersFinishedTurn = 0;
            Game.class.notifyAll();
        }*/
        playersFinishedTurn = 0; // Reset the counter for the next round
        Game.class.notifyAll();
    }
}
