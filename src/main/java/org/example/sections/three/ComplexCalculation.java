package org.example.sections.three;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ComplexCalculation {
    public static void main(String[] args) {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        BigInteger answer;

        BigInteger base1 = new BigInteger("5");
        BigInteger power1 = new BigInteger("9");
        BigInteger base2 = new BigInteger("4");
        BigInteger power2 = new BigInteger("10");

        answer = complexCalculation.calculateResult(base1,power1,base2,power2);
        System.out.println(answer);
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)  {
        BigInteger result;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        List<PowerCalculatingThread> powerCalculatingThreads;
        powerCalculatingThreads = Arrays.asList(new PowerCalculatingThread(base1,power1),
                        new PowerCalculatingThread(base2, power2));

        powerCalculatingThreads.forEach(Thread::start
        );

        powerCalculatingThreads.forEach( thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Caught interrupted exception, exiting gracefully...");
                e.printStackTrace();
            }
        });

        return powerCalculatingThreads.stream()
                .map(PowerCalculatingThread::getResult)
                // reduce second arg is really (x,y) -> x.add(y)
                .reduce(BigInteger.ZERO, BigInteger::add);
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
            for (BigInteger i = BigInteger.ONE; i.compareTo(power) != 1; i = i.add(BigInteger.ONE)) {
                System.out.println(String.format("Iteration: %d with result: %d", i, result));
                result = result.multiply(base);
            }

            // Implement loop using IntStream
            //IntStream.range(0,power.intValue()).forEach( );
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
