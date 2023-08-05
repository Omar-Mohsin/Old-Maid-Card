package org.example.ENUM;

public enum Value {
        one, two, three, four, five, six, seven, eight, nine, ten , jack , queen , king , Joker ;

            private static final Value[] values = Value.values();

            public static Value getValues(int i) {
                return values[i];
            }
        }