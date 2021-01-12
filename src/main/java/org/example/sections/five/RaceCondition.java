package org.example.sections.five;

import java.util.stream.IntStream;

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);

        // race condition when both run at same time for i++ and i-- non ATOMIC operations
        incrementingThread.start();
        //incrementingThread.join();

        decrementingThread.start();


        incrementingThread.join();
        decrementingThread.join();

        System.out.println(inventoryCounter.getItems());

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

        public void increment() {
            items++;
        }

        public void decrement() {
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}
