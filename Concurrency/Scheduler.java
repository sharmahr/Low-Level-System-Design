package lld.concurrency;

// Implement a scheduler in java
import java.util.concurrent.locks.*;

class Scheduler {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private volatile boolean isRunning = true;

    public void scheduleTask(Runnable task, long interval) {
        Thread thread = new Thread(() -> {
            while (isRunning) {
                try {
                    lock.lock();
                    long startTime = System.currentTimeMillis();
                    task.run();
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    long waitTime = interval - elapsedTime;
                    if (waitTime > 0) {
                        condition.await(waitTime, java.util.concurrent.TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread.start();
    }

    public void stopScheduler() {
        lock.lock();
        try {
            isRunning = false;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        // Schedule a task to print "Hello, World!" every 1 second
        scheduler.scheduleTask(() -> {
            System.out.println("Hello, World!");
        }, 1000);

        // Schedule a task to print "Goodbye!" every 2 seconds
        scheduler.scheduleTask(() -> {
            System.out.println("Goodbye!");
        }, 2000);

        // Stop the scheduler after 10 seconds
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        scheduler.stopScheduler();
    }
}

/*
Explanation:

The Scheduler class manages scheduling tasks at specific intervals.
It uses a ReentrantLock named lock to ensure thread safety when accessing shared data.
The Condition named condition is used to pause and resume the execution of tasks.
The isRunning flag is volatile to ensure visibility across threads.
The scheduleTask method takes a Runnable task and an interval in milliseconds. It creates a new thread to execute the task repeatedly at the specified interval.
Inside the thread, it enters a loop that executes the task, calculates the elapsed time, and waits for the remaining time until the next execution.
The stopScheduler method stops the scheduler by setting isRunning to false and signaling all waiting threads to resume execution.
In the main method, we create an instance of the Scheduler class and schedule two tasks to print messages at different intervals. We then stop the scheduler after 10 seconds to demonstrate its usage.
This scheduler provides a simple yet effective way to schedule tasks in a multi-threaded environment using concurrency primitives in Java.
 */



