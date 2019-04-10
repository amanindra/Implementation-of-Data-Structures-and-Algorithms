package Akshay;

import java.util.*;

public class longestIncsubseq {
        static Random rand = new Random(32424);
   
    /**
     * Implement the O(n) algorithm for Select discussed in class.
     *
     * @param A
     * @param k
     * @return
     */
    public static List<Integer> findKLargest3(int[] A, int k) {
        List<Integer> list = new LinkedList<>();
        findKLargest3(list,A,0,A.length-1,k);
        return list;
    }

    private static void findKLargest3(List<Integer> list,int[] A, int start, int end, int k) {
        if(k<=0){
            return;
        }
        if(start>end){
            return;
        }
        int q = partition3(A,start,end);
        int left = q-start;
        int right = end-q;
        if(left==k){
            for(int i=start;i<q;i++){
                list.add(A[i]);
            }
            return;
        }else if(left<k){  //right part
            for(int i=start;i<=q;i++){
                list.add(A[i]);
            }
            findKLargest3(list,A,q+1,end,k-left-1);
        }else if(left>k){  //left part
            findKLargest3(list,A,start,q-1,k);
        }

    }

    private static int partition3(int[] A, int start, int end){
        int tmp = rand.nextInt(end-start+1)+start;
        int i=start-1;
        swap(A,tmp,end);
        int pivot=A[end];
        for(int j=start;j<end;j++){
            if(A[j]>pivot){
                i++;
                swap(A,i,j);
            }
        }
        swap(A,i+1,end);
        return i+1;
    }

    private static void swap(int[] A ,int a, int b){
        int tmp = A[a];
        A[a] = A[b];
        A[b] = tmp;
     }

    public static void main(String[] args){
        int len =100000;
        int[] input = new int[len];
        for(int i=0;i<len;i++){
            input[i]=i;
        }
        
        Timer t3 = new Timer();
        List<Integer> res3 = findKLargest3(input,3);
        System.out.println("res 3 output :"+t3);
        for(Integer i:res3){
            System.out.print(" "+i);
        }
        System.out.println();

    }


}
