package learning.design;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ThreadsafeWindowedAvg {

    Map<String, Integer> hashMap = new HashMap<>();
    PriorityQueue<ThreadsafeWindowedAvg.TTLEntry> ttlEntries = new PriorityQueue<>(
            Comparator.comparing(o -> o.expiryTime));
    volatile int expirationTime;
    volatile int sum;
    volatile int mapSize;
    Thread cleanupThread;

    class TTLEntry {
        long expiryTime;
        String key;

        TTLEntry(long time, String key){
            this.expiryTime = time;
            this.key = key;
        }
    }


    private ThreadsafeWindowedAvg(int k){
        this.expirationTime = k;
        this.sum = 0;
        this.mapSize = 0;
    }

    public static ThreadsafeWindowedAvg getInstance(int k){
        ThreadsafeWindowedAvg wa = new ThreadsafeWindowedAvg(k);
        wa.cleanupThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                wa.cleanupStaleItems();
            }
        });
        wa.cleanupThread.start();
        return wa;
    }

    public synchronized void put(String key,Integer value){
        if (!ttlEntries.isEmpty()){
            this.cleanupStaleItems();
        }
        long currTime = System.currentTimeMillis() + (expirationTime * 1000L);
        ttlEntries.add(new ThreadsafeWindowedAvg.TTLEntry(currTime, key));
        hashMap.put(key, value);
        sum += value;
        this.mapSize++;

    }

    public synchronized Integer get(String key){
        if (!ttlEntries.isEmpty()){
            this.cleanupStaleItems();
        }
        return hashMap.get(key);

    }

    public synchronized double getAvg(){
        if (!ttlEntries.isEmpty()){
            this.cleanupStaleItems();
        }
        if (mapSize>0)
            return ((double)sum)/mapSize;
        return -1;

    }

    private void cleanupStaleItems(){
            synchronized (this) {
                //System.out.println("Acquiring lock");
                long currTime = System.currentTimeMillis();
                while (!ttlEntries.isEmpty() && currTime >= ttlEntries.peek().expiryTime) {
                    String keyToRemove = ttlEntries.poll().key;
                    sum -= hashMap.remove(keyToRemove);
                    this.mapSize--;
                }
            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ThreadsafeWindowedAvg wa = getInstance(1);
        wa.put("A", 10);
        wa.put("B", 10);
        wa.put("C", 10);
        System.out.println(wa.get("A"));
        System.out.println(wa.getAvg());
        Thread.sleep(2000);
        System.out.println(wa.getAvg());
        wa.put("10", 1000);
        System.out.println(wa.getAvg());
        for (int i = 0; i < 12; i++) {
            Thread.sleep(100);
            System.out.println(wa.getAvg());
            System.out.println(wa.cleanupThread.getState());
            if (i == 5){
                System.out.println(wa.cleanupThread.getState());
                wa.cleanupThread.interrupt();
            }
        }

    }

}
