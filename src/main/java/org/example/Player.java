package org.example;



import org.example.ENUM.Color;

import java.util.*;

import static org.example.PlayerFactory.players;

public class Player implements Runnable {

    String playerName;
    ArrayList<CustomCard> playerHand;
    private boolean hasJoker;
    private boolean isMyTurn;
    public Player(String playerName) {
        this.playerName= playerName;
        this.hasJoker = false;
        playerHand = new ArrayList<>();
        this.isMyTurn = false;
    }
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void run() {
        int playerIndex = players.indexOf(this);

        while (!hasJoker) {
            synchronized (this) {
                while (!isMyTurn) {
                    try {
                        wait(); // Wait until it's this player's turn
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Check for matching pairs and remove them from the hand
                discardMatchingPairs();

                // After the turn, set isMyTurn to false to allow the next player's turn.
                isMyTurn = false;

                // Notify the next player that it's their turn.
                int nextPlayerIndex = (playerIndex + 1) % players.size();
                players.get(nextPlayerIndex).setIsMyTurn(true);
            }
        }
    }

    boolean checkForJoker() {
        if (playerHand == null) {
            return false;
        }

        for (CustomCard card : playerHand) {
            if (card != null && card.isJoker()) {
                hasJoker = true;
                break;
            }
        }
        return hasJoker;
    }

    public synchronized void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
        if (isMyTurn) {
            notifyAll();
        }
    }
    private void discardMatchingPairs() {
        Map<Color, List<CustomCard>> cardsByColor = new HashMap<>();

        for (CustomCard card : playerHand) {
            if (card != null && !card.isJoker()) { // Exclude Joker cards and null cards
                Color color = card.getColor();
                cardsByColor.computeIfAbsent(color, k -> new ArrayList<>()).add(card);
            }
        }

        // Remove pairs from the hand
        List<CustomCard> toRemove = new ArrayList<>();
        for (List<CustomCard> cardsWithSameColor : cardsByColor.values()) {
            if (cardsWithSameColor.size() >= 2) {
                toRemove.addAll(cardsWithSameColor.subList(0, 2));
            }
        }
        synchronized (handLock) {
            playerHand.removeAll(toRemove);
            // Also remove the pairs from the entire game
            for (Player player : players) {
                player.playerHand.removeAll(toRemove);
            }
        }

        Game.playerFinishedTurn();
    }
    private final Object handLock = new Object(); // Object for synchronization
    public void addCardsToHand(List<CustomCard> cards) {
        synchronized (handLock) {
            playerHand.addAll(cards);
        }
    }
    public void addCardToHand(CustomCard card) {
        synchronized (handLock) {
            playerHand.add(card);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                '}';
    }
}
