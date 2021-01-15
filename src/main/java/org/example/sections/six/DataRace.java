package org.example.sections.six;

import java.util.stream.IntStream;

public class DataRace {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();

        // no data race when using java 8 streams?
//        new Thread(() -> {
//            IntStream.range(0,Integer.MAX_VALUE).forEach( i -> {
//                System.out.println("thread1: " + i);
//                sharedClass.increment();
//            });
//        }).start();
//
//        new Thread(() -> {
//            IntStream.range(0,Integer.MAX_VALUE).forEach( i -> {
//                System.out.println("thread2: " + i);
//                sharedClass.checkForDataRace();
//            });
//        }).start();

        new Thread(() -> {
            for(int i=0; i<Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        }).start();

        new Thread(() -> {
            for(int i=0; i<Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        }).start();
    }

    private static class SharedClass {
        // use volatile to overcome data races
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        // Runs on a different thread (thread2) and checks values of x and y and relies on their specif order
        // of execution. Can lead to data race is y is increment before x due to compiler enhancements,
        // causing invariant to not hold true
        public void checkForDataRace() {
            if(y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}
