package org.example.sections.six;

import java.util.stream.IntStream;

public class DataRace {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();

        new Thread(() -> {
            IntStream.range(0,Integer.MAX_VALUE).forEach( i -> sharedClass.increment());
        }).start();

        new Thread(() -> {
            IntStream.range(0,Integer.MAX_VALUE).forEach( i -> sharedClass.checkForDataRace());
        }).start();
    }

    private static class SharedClass {
        private int x = 0;
        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if(y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}
