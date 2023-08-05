package org.example.ENUM;

public enum Color {
            RED , BLACK , Joker ;

            private static Color[] colors = Color.values();

            public static Color getColors(int i) {
                return colors[i];
            }

        }