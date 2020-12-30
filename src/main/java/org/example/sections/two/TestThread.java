package org.example.sections.two;

public class TestThread extends Thread implements Thread.UncaughtExceptionHandler{
    static int count;

    public TestThread() {
        count++;
    }

    @Override
    public void run() {
        System.out.println(String.format("[extends Thread] - %s: is doing some work, Thread %s", this.getName(),count));

    }

    @Override
    public void uncaughtException(java.lang.Thread t, Throwable e) {

    }
}
