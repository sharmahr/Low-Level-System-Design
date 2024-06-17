package learning.design;

/*
Reentrant Lock
ReentrantReadWriteLock
Semaphores
BlockingQueue
producerConsumer
ThreadPool
ExecutorService
ScheduledExecutorService
ThreadPoolExecutor
ScheduledThreadPoolExecutor
CyclicBarrier

 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/*
You are given key value pairs, and an expiration time k. At any point, you should be able to
calculate the average
 */
public class WindowedAverage {
    Map<String, Integer> hashMap = new HashMap<>();
    PriorityQueue<TTLEntry> ttlEntries = new PriorityQueue<>(Comparator.comparing(o -> o.expiryTime));
    int sum;
    int mapSize;

    class TTLEntry {
        Long expiryTime;
        String key;

        TTLEntry(long time, String key){
            this.expiryTime = time;
            this.key = key;
        }
    }


    public WindowedAverage(int k){
        this.sum = 0;
        this.mapSize = 0;
    }

    public void put(String key,Integer value, int k){
        long currTime = System.currentTimeMillis() + (k * 1000L);
        ttlEntries.add(new TTLEntry(currTime, key));
        hashMap.put(key, value);
        sum += value;
        this.mapSize++;

    }

    public Integer get(String key){
        cleanupStaleItems();
        return hashMap.get(key);

    }

    public double getAvg(){
        cleanupStaleItems();
        if (mapSize>0)
            return ((double)sum)/mapSize;
        return -1;

    }

    private void cleanupStaleItems(){
        long currTime = System.currentTimeMillis();
        while (!ttlEntries.isEmpty() && currTime >= ttlEntries.peek().expiryTime){
            String keyToRemove = ttlEntries.poll().key;
            sum -= hashMap.remove(keyToRemove);
            this.mapSize--;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        WindowedAverage wa = new WindowedAverage(1);

        wa.put("A", 10, 3);
        wa.put("B", 10, 2);
        wa.put("C", 10, 1);
        System.out.println(wa.get("A"));
        System.out.println(wa.getAvg());
        Thread.sleep(2000);
        System.out.println(wa.getAvg());
        System.out.println(wa.get("C"));

    }

}
