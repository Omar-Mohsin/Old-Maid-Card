package org.example;

import org.example.ENUM.Color;
import org.example.ENUM.Value;

import java.util.Objects;

public class CustomCard {
        private Color color;
        private Value value;

    private boolean isJoker;

        // Constructor for regular cards (non-Joker cards)
        public CustomCard(Color color, Value value) {
            this.color = color;
            this.value = value;
          this.isJoker = false;
        }


        public Color getColor() {
            return color;
        }

        public Value getValue() {
            return value;
        }

    public boolean isJoker() {
        if ((color == Color.Joker) && (value == Value.Joker)){
            isJoker = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomCard that = (CustomCard) o;
       return color == that.color && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value, isJoker);
    }

    @Override
    public String toString() {
        return "CustomCard{" +
                "color=" + color +
                ", value=" + value +
                ", isJoker=" + isJoker +
                '}';
    }
}


