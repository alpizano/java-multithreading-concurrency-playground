package org.example.sections.five;

import java.util.stream.IntStream;

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        InventoryCounter inventoryCounter2 = new InventoryCounter();
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        //IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter2);

        // race condition when both run at same time for i++ and i-- non ATOMIC operations
        incrementingThread.start();
        //incrementingThread.join();

        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();


        System.out.println(inventoryCounter.getItems());
       // System.out.println(inventoryCounter2.getItems());

    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            IntStream.rangeClosed(0,1000).forEach( i -> {
                inventoryCounter.decrement();
            });
        }
    }

    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            IntStream.rangeClosed(0,1000).forEach( i -> {
                inventoryCounter.increment();
            });
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        final Object lock = new Object();
        //Object lock = new Object();

        //public synchronized void increment() {
        public void increment() {
            synchronized (lock) {
                items++;
            }
        }

        //public synchronized void decrement() {
        public void decrement() {
            synchronized (lock) {
                items--;
            }
        }

        public int getItems() {
            return items;
        }
    }
}
