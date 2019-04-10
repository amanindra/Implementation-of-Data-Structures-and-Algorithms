/*
 @authors
 Manindra Kumar Anantaneni : mxa180038
 Vineet Vats: vxv180008
Here in this question we are implementing a bounded-sized queue BoundedQueue<T>, using arrays.
 */


package mxa180038;
import java.lang.Object;
import java.util.Scanner;
import java.lang.Exception;

public class BoundedQueue<T> implements OurQueue {     //Implementing the Interface OurQueue
    BoundedQueue(int size) {
        qSize = 0;
        this.maxSize = size;
        front = this.qSize = 0;
        rear = maxSize - 1;
        bQueue = new Object[maxSize];
    }

    int qSize;
    int maxSize;    // this variable will set the limit for the queue.
    int front;
    int rear;
    Object[] bQueue;


    public int size(){  // Returns the current size of the queue
        return qSize;

    }

    public boolean isEmpty(){
        if(qSize == 0) return true;
        else return false;
    }

    @Override
    public boolean offer(Object o) {    // Adding the element at the rear and it will not return an exception if the queue is full, bit instead gives a warning
        if( qSize == maxSize )
            return false;
        else if(isEmpty()) {
            this.rear = qSize;
            bQueue[this.rear] =  o;
            this.qSize += 1;
            this.rear += 1;
            return true;
        }
        else if (qSize < maxSize && this.rear == maxSize - 1){
            this.rear = 0;
            bQueue[this.rear] =  o;
            this.qSize += 1;
            return true;
        }
        else
            bQueue[this.rear] = o;
            this.qSize += 1;
            this.rear += 1;
            return true;
    }

    @Override
    public Object poll() {      //remove and return the element at the front of the queue, return null if the queue is empty
        if (qSize > 0) {
            Object o = bQueue[front];
            this.front = this.front + 1;
            if (this.front >= maxSize - 1) this.front = 0;
            this.qSize -= 1;
            return o;
        } else return null;
    }



    @Override
    public Object peek() {      //Allows to see the element in the front of the queue without removing it from the queue
        return bQueue[front];
    }


    public void clear(){        //Resets the values of front, rear, and qSize to their initial values

        bQueue[this.front] = null;
        //bQueue[this.rear] = null;
        this.front = 0;
        this.rear = 0;
        this.qSize = 0;
    }


    public void toArray(T[] o)
    {
        int front = this.front;
        for(int i = 0; i< qSize; i++)
        {
            o[i] = (T) this.bQueue[front];
            front += 1;
            if (front >= maxSize - 1) front = 0;
            System.out.println(o[i]);
        }
    }

    public  void displayMenu(){     //A function to display the menu for the user to select
        System.out.println("Enter your choice: \n 1 Offer (Add) \n 2 Poll (Remove) \n 3 Peek \n 4 Size of the Queue \n 5 Clear the Queue \n 6 Convert to Array");
    }

    public static void main (String[] args) throws Exception{
        int inputSize,currentSize;
        Scanner in = new Scanner (System.in);

        System.out.println("Input Maximum Size: ");     //Asks the user to input the maximum size of the queue to be
        inputSize = Integer.valueOf(in.nextInt());

        System.out.println("Input Current Size: ");     //Takes input on how many elements the user wants to input the first time
        currentSize = Integer.valueOf(in.nextInt());

        BoundedQueue<Integer> boundedQueue = new BoundedQueue<>(inputSize);

        System.out.println("Input your values");    //Takes the first currentSize elements

        if(currentSize <= inputSize) {
            for (int i = 0; i < currentSize; i++) {
                boundedQueue.offer(Integer.valueOf(in.nextInt()));
            }
        } else      //Throws exception
        {
            System.out.println("Sorry! Current Size cannot be more than Maximum Size");
            throw new StackOverflowError();
        }

        boundedQueue.displayMenu();

        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:
                    if (boundedQueue.offer(Integer.valueOf(in.nextInt()))) {
                        boundedQueue.displayMenu();
                        break;
                    }
                    else{
                        System.out.println("Queue is full");
                        boundedQueue.displayMenu();
                        break;
                    }
                case 2:
                    System.out.println(Integer.valueOf((Integer) boundedQueue.poll()));
                    boundedQueue.displayMenu();
                    break;
                case 3:
                    System.out.println(boundedQueue.peek());
                    boundedQueue.displayMenu();
                    break;
                case 4:
                    System.out.println(boundedQueue.size());
                    boundedQueue.displayMenu();
                    break;
                case 5:
                    boundedQueue.clear();
                    boundedQueue.displayMenu();
                    break;
                case 6:
                    Integer[] arr = new Integer[boundedQueue.maxSize];
                    boundedQueue.toArray(arr);
                    boundedQueue.displayMenu();
                    break;
                 default:
                    break whileloop;
            }
        }
    }
}
