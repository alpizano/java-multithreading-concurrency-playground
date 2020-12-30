package org.example.sections.two;

public class TestTask implements Runnable{

    @Override
    public void run() {
        System.out.println(String.format("[implements Runnable] - %s is doing some work", Thread.currentThread().getName()));
    }
}
