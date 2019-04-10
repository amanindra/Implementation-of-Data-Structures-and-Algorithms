/* Team Members:
Manindra Kumar Anantaneni : mxa180038
Vineet Vats - vxv180008
 */
package mxa180038;
//Starter code for bounded-size Binary Heap implementation

import java.util.Comparator;
import java.util.Scanner;
import java.lang.Exception;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    int pqSize, maxSize;
    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(T[] q) {
        // Use a lambda expression to create comparator from compareTo
        this(q, (T a, T b) -> a.compareTo(b));
        maxSize = q.length;
        pqSize = 0;

    }

    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        comp = c;
        //pqSize = 0;
    }



    public void add(T x) throws Exception { /* It adds an element to the binary heap and throw exception if pq is full */
        if(pqSize == maxSize) throw new Exception("Queue is Full!");
        else {
            pq[pqSize] = x;
            percolateUp(pqSize);
            pqSize++;
        }
    }

    public boolean offer(T x) { /* It adds an element to the binary heap and return false if pq is full */
        if(pqSize == maxSize) return false;
        else {
            pq[pqSize] = x;
            percolateUp(pqSize);
            pqSize++;
            return true;
        }

    }

    public T remove() throws Exception { /* It removes the first element in the binary heap and it return  emptythrow exception if pq is empty */
        if(pqSize < 0) throw new Exception("Queue is empty");
        else {
            return removeLogic();
        }
    }

    public T removeLogic(){
        T min = pq[0];
        pq[0] = pq[pqSize - 1];
        pqSize--;
        percolateDown(0);
        return min;
    }

    public T poll() { /* It removes the first element in the binary heap and it return false if pq is empty  */
        if(pqSize <= 0) {
            return null;
        }
        else
        {
            return removeLogic();
        }

    }

    public T peek() { /*It returns the first element in the priority queue having highest priority and return null if pq is empty */
        if(pqSize == 0) {
            return null;
        }
        else {
            T min = pq[0];
            return min;
        }
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) { // This function is used in add and offer functions. We are adding a node at the leaf of a binary heap and it percolates up if the node is having higher priority.
        T x = pq[i];
        while(i > 0 && pq[parent(i)].compareTo(x) > 0)
        {
            pq[i] = pq[parent(i)];
            i = parent(i);
            pq[i] = x;
        }
    }

    /** pq[i] may violate heap order with children */
    void percolateDown(int i) { //percolate down function is used in remove and poll function in which we are removing the root node and sending the leaf node to the root and automatically the bigger value percolates down.
        T x = pq[i];
        int c = leftChild(i);
        while (c <= pqSize - 1){
            if( c < pqSize - 1 && pq[c].compareTo(pq[c+1])>0){
                c++;
            }
            if(x.compareTo(pq[c]) <= 0) break;
            pq[i] = pq[c];
            i = c;
            c = leftChild(i);
        }
        pq[i] = x;
    }

    int parent(int i) {// It returns the parent of a node.
        return (i-1)/2;
    }

    int leftChild(int i) {//It returns the Left child of a node. It was given in the starter code.
        return 2*i + 1;
    }

    public  void displayMenu(){     //A function to display the menu for the user to select
        System.out.println("Enter your choice: \n 1 Add \n 2 Offer \n 3 Remove \n 4 Poll  \n 5 Peek \n 6 Print the Queue (in Array form) ");
    }

    void print() {
        int i;
        for (i = 0; i < pqSize; i++) {
            System.out.println("Array: "+pq[i]);
        }
    }


    public static void main(String[] args) throws Exception {
        int inputSize, currentSize;
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the Maximum size");
        inputSize = Integer.valueOf(in.nextInt());

        System.out.println("Enter the current size");
        currentSize = Integer.valueOf(in.nextInt());

        Integer[] heapArray = new Integer[inputSize];


        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(heapArray);

        System.out.println("Input your values");    //Takes the first currentSize elements

        if (currentSize <= inputSize) {
            for (int i = 0; i < currentSize; i++) {
                binaryHeap.add(Integer.valueOf(in.nextInt()));
            }
        } else      //Throws exception
        {
            throw new Exception("Sorry! Current Size cannot be more than Maximum Size");
        }

        binaryHeap.displayMenu();

        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:
                    binaryHeap.add(in.nextInt());
                    binaryHeap.print();
                    binaryHeap.displayMenu();
                    break;
                case 2:
                    System.out.println(binaryHeap.offer(in.nextInt()));
                    binaryHeap.print();
                    binaryHeap.displayMenu();
                    break;
                case 3:
                    System.out.println(binaryHeap.remove());
                    binaryHeap.print();
                    binaryHeap.displayMenu();
                    break;
                case 4:
                    System.out.println((binaryHeap.poll()));
                    binaryHeap.print();
                    binaryHeap.displayMenu();
                    break;
                case 5:
                    System.out.println(binaryHeap.peek());
                    binaryHeap.displayMenu();
                    break;
                case 6:
                    binaryHeap.print();
                    break;
                default:
                    break whileloop;
            }
        }
    }
}
