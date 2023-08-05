package org.example;

import org.example.ENUM.Color;
import org.example.ENUM.Value;

import java.util.*;



public class Deck {

    private List<CustomCard> cards;
    private int cardsInDeck;
    private static volatile Deck instance;


    Deck() {

        cards = new ArrayList<>(53);
    }
    public int size(){
        return cards.size();
    }
    public static Deck getInstance() {
        Deck result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Deck.class) {
            if (instance == null) {
                instance = new Deck();
            }
            return instance;
        }
    }

    public void initializeDeck () {
        Color[] colors =  Color.values();
        cardsInDeck = 0;

        for (Color color : Color.values()) {
            for (Value value : Value.values()) {
                if (color != Color.Joker && value != Value.Joker) {
                    cards.add(new CustomCard(color, value));
                    cardsInDeck++;
                }
            }
        }

        for (Color color : Color.values()) {
            for (Value value : Value.values()) {
                if (color != Color.Joker && value != Value.Joker) {
                    cards.add(new CustomCard(color, value));
                    cardsInDeck++;
                }
            }
        }
        cards.add(new CustomCard(Color.Joker, Value.Joker));
        cardsInDeck++;

    }
    public void shuffle() {
        int n = cards.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(cards, i, change);
        }
    }

    private void swap(List<CustomCard> a, int i, int change) {
        CustomCard helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }
    public boolean isEmpty () {
        return cardsInDeck == 0;
    }

    public synchronized  CustomCard dealCard() {
        if (!cards.isEmpty()) {
            cardsInDeck--;
            return cards.remove(0);
        } else {
            return null; // No more cards in the deck
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return cardsInDeck == deck.cardsInDeck && Objects.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards, cardsInDeck);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                ", cardsInDeck=" + cardsInDeck +
                '}';
    }
}
