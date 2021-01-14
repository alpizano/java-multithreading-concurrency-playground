package org.example.sections.six;


import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Metrics metrics = new Metrics();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);
        BusinessLogic businessLogicThread2  = new BusinessLogic(metrics);

        Main main = new Main();

        MetricsPrinter metricsPrinter = main.new MetricsPrinter(metrics);
        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();
    }

    // without static modifier
    public class MetricsPrinter extends Thread {
    //public static class MetricsPrinter extends Thread {
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }
        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(100);
                }
                catch(InterruptedException e ) {

                }
                double currentAverage = metrics.getAverage();
                System.out.println("Current Average is: " + currentAverage);
            }
        }
    }

    public static class BusinessLogic extends Thread {
        private Metrics metrics;

        public BusinessLogic(Metrics metrics) {
            this.metrics=metrics;
        }

        @Override
        public void run() {
            while(true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(new Random().nextInt(10));
                }
                catch(InterruptedException e ) {
                    System.out.println("Got interrupted exception!: " + e.getMessage());
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end-start);
            }
        }
    }

    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return average;
        }

    }
}


