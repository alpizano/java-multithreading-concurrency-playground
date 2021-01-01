package org.example.sections.three;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ComplexCalculation {
    public static void main(String[] args) throws InterruptedException {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        BigInteger answer;
        answer = complexCalculation.calculateResult(BigInteger.valueOf(3L), BigInteger.valueOf(5L));
        System.out.println(answer);
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        //public BigInteger calculateResult(BigInteger base1, BigInteger power1) throws InterruptedException {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        List<PowerCalculatingThread> powerCalculatingThreads;
        powerCalculatingThreads = Arrays.asList(new PowerCalculatingThread(new BigInteger("3"), new BigInteger("3")),
                        new PowerCalculatingThread(new BigInteger("3"), new BigInteger("3")));

        powerCalculatingThreads.forEach(thread -> {
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        System.out.println("Caught interrupted exception. Exiting gracefully...");
                        e.printStackTrace();
                    }
                }
        );

        BigInteger sum = BigInteger.ZERO;
        powerCalculatingThreads.stream().mapToInt(thread -> thread.getResult()).sum();


//        PowerCalculatingThread powerCalculatingThread = new PowerCalculatingThread(BigInteger.valueOf(3L), BigInteger.valueOf(5L));
//        powerCalculatingThread.start();
//        powerCalculatingThread.join();
        return powerCalculatingThread.getResult();
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            // base = 3, power = 5
            // 3^5 = 3*3*3*3*3
            for (BigInteger i = BigInteger.ONE; i.compareTo(power) != 1; i = i.add(BigInteger.ONE)) {
                System.out.println(String.format("Iteration: %d with result: %d", i, result));
                result = result.multiply(base);
            }

            //IntStream.range(0,power)

        }

        public BigInteger getResult() {
            return result;
        }
    }
}
