/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mxa180038;

import java.util.Random;

/**
 *
 * @author Manindra Kumar Anantaneni
 */

public class DoubleHashing<K> implements SecondHash<K> {
    private HashEntry[] hashTable;
    int size;
    int tableSize;

    HashEntry[] hashTable2; // Temporary table used when increasing the size the hash table to double its size

    public int hashCode2(K key) {
        return 1 + ((key.hashCode()) % (hashTable.length-2));
    } // Logic of the Second Hash Function used in our Double Hashing

    enum Status{
        Deleted, Filled
    } // To indicate the status of a node, whether it is filled or has been deleted recently

    /**
     * Class HashEntry defines the structure of our entry.
     * Our entry consists of fields key of generic type K, and a field to indicate the status of an entry
     * @param <K>
     */

    static class HashEntry<K>
    {
        K key;
        //V value;
        Status status;

        /* Constructor */
        HashEntry(K key){
            this.key = key;
            //this.value = value;
            this.status = Status.Filled;
        }

        public K getKey(){
            return key;
        }
        /*public V getValue(){
            return value;
        }*/
    }

    /**
     * Constructor of our class DoubleHashing
     * It initializes the hash table size, the size field to store the current size of the hash table, and create a hashTable of size given
     * @param ts
     */

    public DoubleHashing(int ts)
    {
        tableSize = ts;
        size = 0;
        hashTable = new HashEntry[ts];
    }

    static int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    static int indexFor(int h, int length) { // length = table.length is a power of 2
        return h & (length-1);
    }

    /**
     * The first hash function used in our double hashing
     * @param key
     * @return
     */

    public int hash1(K key){
        int index1 = indexFor(hash(key.hashCode()), tableSize);
        return index1;

    }

    /**
     * This function generates the required hash ocde using both hash1, and hash2 of our DoubleHashing
     * @param key
     * @param k
     * @return
     */


    public int hashFunction (K key, int k){
        int i = (hash1(key) + k * hashCode2(key)) % tableSize;
        //System.out.println(i);
        return i;
    }

    /**
     *
     * @param x the value that needs to be found in our hash table
     * @return the spot/location where we found the key x, or the position in the table where the position is free
     */


    private int find(K x){
        int k = 0, i, start;
        int xSpot = -1;

        //if(x == null) return 0;

        i = hashFunction(x, k);

        start = i;



        do{
            HashEntry<K> e = hashTable[i];

            if(e!= null){
                //System.out.println("value"+e.key);
                if(e.status == Status.Deleted && xSpot<0) {
                    i = (i + 1) % tableSize;
                    continue;
                }
                else if(x.equals(e.key)){
                        xSpot = i;
                        return xSpot;
                 }
                 i = (i+1)%tableSize; //To only get the values within the range of tableSize, and to rollover and start from the beginning

            }
            else
            {
                if(xSpot < 0)  xSpot = i;
                return xSpot;
            }
        } while (i!=start);
        return xSpot;
    }

    public boolean contains(K key) {
        int loc = find(key);
        if (hashTable[loc]!=null)
            return true;
        else
            return false;
    }

    /**
     * We reconstruct our hash table when the load factor (current Size of the table divided by Maximum size of the table) is greater than a certain limit
     * Here we have taken a limit of 0.6
     */

    public void rehash(){
        hashTable2 = hashTable;
        tableSize *= 2;
        hashTable = new HashEntry[tableSize];
        size = 0;
        for (int i = 0; i < hashTable2.length; i++){
            HashEntry<K> e;
            e = hashTable2[i];
            if(e != null && e.status == Status.Filled)
            {
                hashTable[find((K) e)] = e;
                size++;
                //System.out.println("true");
            }
        }
    }

    /**
     *
     * @param x add this value to the table
     * @return if the value x is already present in the table we return false, otherwise we add the entry into the table at a pseudo random spot
     * generated using the hashFunction (Contains hash1, and hash2 for DoubleHashing)
     */


    public boolean add (K x){
        if (isEmpty())
        {
            int i = hash1(x);
            hashTable[i] = new HashEntry(x);
            hashTable[i].status = Status.Filled;
            size++;
            return true;
        }
        //System.out.println(tableSize);
        //System.out.println(size);
        if(size >= tableSize*0.5) {
            rehash();
        }

        int loc = find(x);

        if(hashTable[loc] != null && hashTable[loc].key.equals(x)) return false;
        else {
            hashTable[loc] = new HashEntry(x);
            hashTable[loc].status = Status.Filled;
            size++;
            return true;
        }
    }


    public int size() {
        return size;
    }

    public boolean isEmpty(){

        return size == 0;
    }

    /**
     *
     * @param key we remove this entry from the table, and put the value of key as null, and change the status of the entry to deleted
     *            while rehashing the table we free up the deleted spots
     * @return we return the key if it is present in the table, otherwise we return null
     */

    public K remove(K key){
        int loc = find(key);
        K result;
        if (hashTable[loc]!=null && hashTable[loc].key.equals(key)){
            result = (K) hashTable[loc].key;
            hashTable[loc].key = null;
            //hashTable[loc].value = null;
            hashTable[loc].status = Status.Deleted;
            size--;
            return result;
        }
        else
            return null;
    }

    /**
     * Used to show the elements present in our hash table
     */

    public void showHashTable()
    {
        for(int i = 0; i < tableSize; i++)
        {
            if(hashTable[i]!=null)
            System.out.println(i + " key: " + hashTable[i].key );
        }
    }

    /*public V get(K k){
        int loc = find(k);

        if(hashTable[loc]!= null && hashTable[loc].status != Status.Deleted)
            return (V) hashTable[loc].getValue();
        else return null;
    }*/

    /*static DoubleHashing<Integer, Integer> exactlyOnce(HashEntry[] arr){
        DoubleHashing<Integer, Integer> h = new DoubleHashing<>(arr.length);
        int count = 0;
        for(int i = 0; i<arr.length;i++) {

            if (arr[i] != null) {
                Integer c = h.get((Integer) arr[i].key);
                //HashEntry<K, Integer> t = h.hashTable[c];
                if (c == null) {
                    h.add((Integer) arr[i].value, 1);
                    count++;
                } else {
                    h.add((Integer) arr[i].value, c + 1);
                    if (c == 1) count--;
                }
            }
        }
        return h;
        /*HashEntry<Integer, Integer>[] result = new HashEntry[count];
        int index = 0;
        for(int i = 0; i<arr.length;i++){
            if(arr[i]!=null) {
                if (h.get((Integer) arr[i].key)!=null &&  h.get((Integer) arr[i].key) == 1) result[index++] = arr[i];
                if (index == count) break;
            }
        }
        return  result;*/
    //}

    static int randomInt(int min, int max){
        Random r = new Random();
        return r.nextInt((max-min)+1)+min;
    }

    public void distinctElements(K[] arr){
        DoubleHashing<K> h = new DoubleHashing<>(arr.length);
        int count = 0;
        for(int i = 0; i<arr.length;i++){
            if(!h.contains(arr[i])){
                count++;
                h.add(arr[i]);
            }
        }
        System.out.println("No. of distinct elements:" + count);
    }
    public int count(){
        int c = tableSize;
        for(int i = 0; i< tableSize;i++){
            if (hashTable[i] == null)
                c--;
        }
        return c;
    }


    /*public static void main(String[] args) {

        DoubleHashing<Integer> dh;
        dh = new DoubleHashing<>(16);

        /*for(int i = 0; i<100; i++){
            System.out.println(dh.add(randomInt(100001, 999999)));
        }

        dh.showHashTable();

        for(int j = randomInt(1,10); j>0;j--) {

            for (int i = 0; i < 10; i++) {

                System.out.println(dh.remove(randomInt(100001, 999999)));
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(dh.add(randomInt(100001, 999999)));
            }
        }

        /*System.out.println(dh.add(12497));

        System.out.println(dh.add(28754));

        System.out.println(dh.add(34678));

        System.out.println(dh.add(45500));

        System.out.println(dh.add(56699));

        System.out.println(dh.add(67891));

        System.out.println(dh.remove(45500));

        dh.showHashTable();

        System.out.println(dh.add(70011));

        System.out.println(dh.add(81209));

        System.out.println(dh.add(99194));*/

        /*System.out.println(dh.add(300));

        System.out.println(dh.add(10));

        System.out.println(dh.add(310));

        System.out.println(dh.add(10));

        dh.showHashTable();


    }*/

}
