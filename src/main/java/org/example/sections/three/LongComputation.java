package org.example.sections.three;

import java.math.BigInteger;

public class LongComputation {
    public static void main(String[] args) {

        Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("10")));
        thread.start();
        thread.interrupt();

        // can manually set thread to daemon thread and will exit when Main thread exits
        //thread.setDaemon(true);

        System.out.println("Your number is" + 123.00000);
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(String.format("%d^%d = %d",base,power,pow(base,power)));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) !=0; i= i.add(BigInteger.ONE)) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread was interrupted. Exiting gracefully.....");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }

}
