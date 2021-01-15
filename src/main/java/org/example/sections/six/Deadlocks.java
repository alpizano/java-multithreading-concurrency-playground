package org.example.sections.six;

import java.util.Random;

public class Deadlocks {
    public static void main(String[] args) {
    Intersection intersection = new Intersection();
    TrainA trainA = new TrainA(intersection);
    TrainB trainB = new TrainB(intersection);

    new Thread(trainA).start();
    new Thread(trainB).start();

    }

    public static class TrainA implements Runnable {
        public Intersection intersection;

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTime = new Random().nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    intersection.takeRoadB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class TrainB implements Runnable {
        public Intersection intersection;

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTime = new Random().nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    intersection.takeRoadA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Intersection {
        private  Object roadA = new Object();
        private  Object roadB = new Object();

        public void takeRoadA() throws InterruptedException {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread: " + Thread.currentThread().getName());

                synchronized (roadB) {
                    System.out.println("Train is passing through road A: "+ Thread.currentThread().getName());
                    Thread.sleep(1);
                }
            }

            // won't cause deadlock
//            synchronized (roadB) {
//                System.out.println("Train is passing through road A: "+ Thread.currentThread().getName());
//                Thread.sleep(1);
//            }
        }

        public void takeRoadB() throws InterruptedException {
            synchronized (roadB) {
                System.out.println("Road B is locked by thread: " + Thread.currentThread().getName());

                synchronized (roadA) {
                    System.out.println("Train is passing through road B: "+ Thread.currentThread().getName());
                    Thread.sleep(1);
                }
            }

            // wont' cause deadlock
//            synchronized (roadA) {
//                System.out.println("Train is passing through road B: "+ Thread.currentThread().getName());
//                Thread.sleep(1);
//            }
        }

    }
}
