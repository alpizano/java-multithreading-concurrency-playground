package org.example.sections.two;

import java.util.ArrayList;
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
        // thread creation using lambda
        Thread t1 = new Thread(() -> {
            // do some stuff here

        });

        List<Runnable> input = new ArrayList<>();
        input.add(new TestTask());
    }
}
