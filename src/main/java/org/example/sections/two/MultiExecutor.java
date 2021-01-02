package org.example.sections.two;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MultiExecutor {
    List<Runnable> tasks;

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll() {
        // Using method reference
        // tasks.forEach(Thread::new);

        // Using lambda
        tasks.forEach( task -> {
            new Thread(task).start();
        });
    }

    public static void main(String[] args) {
        // Runnable Tasks
        TestTask task1 = new TestTask();
        TestTask task2 = new TestTask();
        TestTask task3 = new TestTask();
        TestTask task4 = new TestTask();

        // Thread Tasks
        TestThread thread1 = new TestThread();
        TestThread thread2 = new TestThread();
        TestThread thread3 = new TestThread();
        TestThread thread4 = new TestThread();

        List<Runnable> tasksList = new ArrayList<>(Arrays.asList(task1, task2, task3, task4));
        List<Runnable> threadsList = new ArrayList<>(Arrays.asList(thread1, thread2, thread3, thread4));

        MultiExecutor runnableExecutor = new MultiExecutor(tasksList);
        MultiExecutor threadExecutor = new MultiExecutor(threadsList);

        //runnableExecutor.executeAll();
        threadExecutor.executeAll();
    }
}
