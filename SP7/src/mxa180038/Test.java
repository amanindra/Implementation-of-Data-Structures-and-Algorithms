
/**
 *
 * @authors
 * axk175430 - Akshay Kanduri
 * sxm180018 - Shivan Mankotia
 */

package mxa180038;

import mxa180038.Timer;
import java.io.*;
import java.util.*;
public class Test {

    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner sc;
        System.out.println("Enter size");
        File file = new File("C:/Users/Dell/OneDrive/Desktop/UTD Study/Fall 2018/Implementation of Data Structures and Algorithms/Long Projects/LongProject2/src/mxa180038/lp2-t13.txt");
        sc = new Scanner(file);
        String operation;
        long operand;
        //int s = in.nextInt();
        long result = 0;
        int modValue = 999983;
        /* Make object of HashTable */
        DoubleHashing<Integer> ht = new DoubleHashing(16);
        //Integer[] arr = new Integer[s];

        Random random = new Random();
        int max =10000;
        int min = 0;
        //HashMap jHashMap = new HashMap();
        // Initialize the timer



        /*  Perform HashTable operations  */

        System.out.println("PART 1: Compare Double hashing's performance with Java HashMap/HashSet on millions of operations: add, contains, and remove.");


        Timer timer;

        /*System.out.println("\nEnter Operations for Double Hashing: ");
        System.out.println("\nHash Table Operations\n");
        System.out.println("1. Add");
        System.out.println("2. Remove");
        System.out.println("3. Contains:");


        System.out.println("Enter choice: ");*/


        /*whileloop:
        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add"://System.out.println("Add:");
                    int add =sc.nextInt();
                    ht.add(add);
                    ht.showHashTable();
                    break;
                case "Remove"://System.out.println("Remove:");
                    int r =sc.nextInt();
                    ht.remove(r);
                    ht.showHashTable();
                    break;
                case "Contains"://System.out.println("Contains:");
                    int c =sc.nextInt();
                    System.out.println(ht.contains(c));
                    break;
                default:  // Exit loop
                    break whileloop;

            }
        }
        ht.showHashTable();
        timer.end();*/



        System.out.println("\n\nPART 2: Generate an array of random integers, and calculate how many distinct numbers it has. Compare running times of HashSet and your hashing implementation, for large n.");

        Scanner in = new Scanner(System.in);
        System.out.println("Enter Size of Array:");
        int arr_size = in.nextInt();

        Integer[] arr = new Integer[arr_size];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = random.nextInt(max-min);
        }
        //Print the Random Array
        /*System.out.println("\nRandom Array: ");
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }*/
        System.out.println("-----------------------------------------------------------------");

        // Entering elements in My Double hash table
        timer = new Timer();

        DoubleHashing<Integer> ht2 = new DoubleHashing(arr_size);
        ht2.distinctElements(arr);
        timer.end();

        System.out.println("\n Double Hashing Performance:");
        System.out.println(timer);

        // Entering elements in java's Double hashset table
        timer = new Timer();
        HashSet<Integer> hs = new HashSet<>();
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (!hs.contains(arr[i])) {
                count++;
                hs.add(arr[i]);

            }

        }

        timer.end();

        System.out.println("\nJava Hashset Performance:");
        System.out.println("\n Distinct Elements:"+ count);
        System.out.println(timer);

    }








}

