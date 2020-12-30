package org.example.sections.two;

public class TestThread extends Thread implements Thread.UncaughtExceptionHandler{
    @Override
    public void run() {
        System.out.println(String.format("[extends Thread] - %s: is doing some work", this.getName()));

    }

    @Override
    public void uncaughtException(java.lang.Thread t, Throwable e) {

    }
}
