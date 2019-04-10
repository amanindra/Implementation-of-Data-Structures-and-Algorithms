package mxa180038;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

import java.util.List;
import java.util.LinkedList;

/**
 * @author RAMA NARAYAN LAKSHMANAN(rxl174430), MANINDRA KUMAR ANANTANENI(mxa180038)
 * Select k largest elements from an array
 */
public class SP11 {
    public static Random random = new Random();
    public static int numTrials = 10;

    public static void main(String[] args) {

        int k = 2;
        /*int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }*/

        List<Integer> l1 = new LinkedList<>();
        l1.add(10);
        /*l1.add(10000);
        l1.add(100000);
        l1.add(1000000);
        l1.add(10000000);
        l1.add(100000000);*/

        List<Integer> l2 = new LinkedList<>();
        l2.add(1);
        l2.add(2);
        for(int n:l1) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt(n);
            }
            for (int choice:l2) {
                Timer timer = new Timer();
                Integer[] kLargest = new Integer[k];
                switch (choice) {
                    case 1:
                        for (int z = 0; z < numTrials; z++) {
                            Shuffle.shuffle(arr);
                            timer.start();
                            selectK1(arr, k);
                            display(arr, k);
                        }
                        break;
                    case 2:
                        for (int z = 0; z < numTrials; z++) {
                            Shuffle.shuffle(arr);
                            timer.start();
                            kLargest = selectK2(arr, k);
                            display(kLargest);
                            break;
                        }

                }
                timer.scale(numTrials);

                System.out.println("Choice: " + choice + "\nInput: " + n  + "\n" + timer);
                display();
            }
            display();
        }
    }

    /**
     * Select k largest elements using priority queue
     * @param arr - array from which we need to select k largest elements
     * @param k - number of largest elements to select
     * @return unordered array of k largest elements
     */
    public static Integer[] selectK2(int arr[], int k) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>(k);
        for (int i = 0; i < arr.length; i++) {
            if (minQueue.size() < k) {
                minQueue.add(arr[i]);
            } else {
                if (minQueue.peek() < arr[i]) {
                    minQueue.poll();
                    minQueue.add(arr[i]);
                }
            }
        }
        Integer[] kLargest = new Integer[k];
        return minQueue.toArray(kLargest);
    }

    /**
     * Select K largest integers, using partition from QuickSort
     * @param arr array from which we need to select k largest elements
     * @param k number of largest elements to select
     * @return unordered array of k largest elements
     */

    public static void selectK1(int[] arr, int k) {
        select(arr, k);
    }
    static void select(int[] arr, int k){
        select(arr, 0, arr.length - 1, k);
    }

    static void display(){
        System.out.println("------------------------------------------------");
    }

    static void display(Integer[] arr){
        for(int  i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    static void display(int[] arr, int k){
        for(int  i = arr.length - k; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

    /**
     * Select function to select k largest elements, store them in a list and return the list
     * @param arr array from which we need to select k largest elements
     * @param p Starting index
     * @param n Last index
     * @param k Select k elements from the arr
     */

    static int select(int[] arr, int p, int n, int k){
        if(k<=0) return 0;

        if(p > n) return 0;

        int q = partition(arr, p, n);
        int left = q - p;
        if(left == k){
            return arr[q];
        }
        else if(left < k){
                select(arr, q+1, n, k-left-1);
        }
        else if(left>k) select(arr, p, q - 1, k);

        return 0;
    }


    private static void swap(int[] arr ,int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int partition(int[] arr, int p, int r){

        int tmp = random.nextInt(r - p + 1) + p;
        int i = p - 1;
        swap(arr, tmp, r);
        int x = arr[r];
        for(int j = p; j < r; j++){
            if(arr[j] > x) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1 , r);
        return i + 1;
    }



    /**
     * sorts array using insertion sort algorithm
     *
     * @param arr - array to sort
     */
    public static void insertionSort(int[] arr, int p, int r) {
        for (int i = p + 1; i <= r; i++) {
            int tmp = arr[i];
            int j = i - 1;
            while (j >= p && tmp < arr[j]) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = tmp;
        }
    }

    /**
     * Timer class for roughly calculating running time of programs
     *
     * @author rbk Usage: Timer timer = new Timer(); timer.start(); timer.end();
     *         System.out.println(timer); // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if (!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
            if (!ready) {
                end();
            }
            return memUsed;
        }

        public void scale(int num) {
            elapsedTime /= num;
        }

        public String toString() {
            if (!ready) {
                end();
            }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / "
                    + (memAvailable / 1048576) + " MB.";
        }
    }

    /**
     * @author rbk : based on algorithm described in a book
     */

    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static <T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from + 1;
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        public static <T> void shuffle(T[] arr, int from, int to) {
            int n = to - from + 1;
            Random random = new Random();
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static <T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static <T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length - 1, message);
        }

        public static <T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for (int i = from; i <= to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }
}