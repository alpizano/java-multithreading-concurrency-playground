package org.example.sections.two;

public class TestTask implements Runnable{
    static int count;

    public TestTask() {
        count++;
    }

    @Override
    public void run() {
        System.out.println(String.format("[implements Runnable] - %s is doing some work, Task %s", Thread.currentThread().getName(),count));
    }
}
