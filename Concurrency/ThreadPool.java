package lld.concurrency;

// Implement thread pool in java

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/*
public class ThreadPool {
    private final int poolSize;
    private final WorkerThread[] workers;
    private final BlockingQueue<Runnable> taskQueue;

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new WorkerThread[poolSize];
        
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void submit(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void shutdown() {
        for (WorkerThread worker : workers) {
            worker.interrupt();
        }
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(5);

        // Submit tasks to the pool
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            threadPool.submit(() -> {
                System.out.println("Task " + taskId + " is executing by thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate task execution time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Shutdown the thread pool after all tasks are completed
        threadPool.shutdown();
    }
}
*/

/**
 * Explanation:
 * 
 * - The ThreadPool class represents the thread pool. It contains an array of worker threads (WorkerThread) and a task queue (taskQueue) to hold tasks to be executed.
 * - The constructor initializes the thread pool with the specified number of worker threads (poolSize). Each worker thread continuously retrieves tasks from the task queue and executes them.
 * - The submit method adds a new task to the task queue for execution by the worker threads.
 * - The WorkerThread class extends Thread and represents a worker thread in the thread pool. Each worker thread continuously retrieves tasks from the task queue and executes them.
 * - The run method of the WorkerThread class retrieves tasks from the task queue using take() and executes them.
 * - The shutdown method interrupts all worker threads, causing them to stop processing tasks and exit gracefully.
 * - In the main method, we create a thread pool with 5 worker threads and submit 10 tasks to the pool. Each task simply prints a message indicating its execution and simulates some work by sleeping for 1 second.
 * - Finally, we shutdown the thread pool after all tasks are completed.
 * 
 * This implementation provides a basic thread pool functionality for executing tasks concurrently with a fixed number of worker threads. It can be extended to support features such as task prioritization, dynamic resizing of the thread pool, and handling exceptions thrown by tasks.
 */

// Thread Safe THreadPool:

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int poolSize;
    private final WorkerThread[] workers;
    private final BlockingQueue<Runnable> taskQueue;
    private final Object lock = new Object();

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new WorkerThread[poolSize];
        
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void submit(Runnable task) {
        synchronized (lock) {
            try {
                taskQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    try {
                        Runnable task = taskQueue.take();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    public void shutdown() {
        synchronized (lock) {
            for (WorkerThread worker : workers) {
                worker.interrupt();
            }
        }
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(5);

        // Submit tasks to the pool
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            threadPool.submit(() -> {
                System.out.println("Task " + taskId + " is executing by thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Simulate task execution time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Shutdown the thread pool after all tasks are completed
        threadPool.shutdown();
    }
}
 /*
In this implementation, we synchronize access to the task queue and worker threads using a lock object (lock). This ensures that only one thread can modify the task queue or access worker threads at a time, preventing concurrent modification and potential race conditions.

Additionally, we synchronize access to the shutdown() method to ensure that all worker threads are interrupted safely. This prevents any deadlocks or hangs during shutdown.
  */
