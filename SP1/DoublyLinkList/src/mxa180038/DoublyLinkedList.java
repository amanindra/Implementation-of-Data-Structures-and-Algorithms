/*
@author Manindra Kumar Anantaneni (mxa180038)
@author Shiva Prasad Reddy
*/
package mxa180038;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        Entry<E> prev;

        Entry(E x, Entry<E> nxt, Entry<E> prev) {
            super(x, nxt);
            this.prev = prev;
        }
    }

    Entry<T> dHead;
    Entry<T> dTail;
    int dSize;

    public DoublyLinkedList() {
        dHead = new Entry<>(null, null, null);
        dTail = dHead;
        dSize = 0;
    }



    public  DLLIterator diterator() {
        return new DLLIterator();
    }


    public class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T> {

        Entry<T> dPrev;
        DoublyLinkedList.Entry<T> dCursor;

        DLLIterator(){ super();
            dCursor=dHead;
        }

        public boolean hasPrev() {return  dCursor.prev !=null;}

        public T previous(){
            dPrev= dCursor;
            dCursor = dCursor.prev;
            ready=true;
            return  dCursor.element;
        }
        @Override
        public void add(T x) {
            if(dCursor.next != null) {
                Entry<T> next = (Entry<T>) dCursor.next;
                Entry<T> dEntry= (new DoublyLinkedList.Entry<>(x, (Entry<T>) dCursor.next, dCursor));
                dCursor.next = dEntry;
                dEntry.next = next;
                next.prev = dEntry;
                dCursor = (Entry<T>) dCursor.next;
                size++;
            }
            else
            {
                Entry<T> dEntry = new DoublyLinkedList.Entry<>(x,null, dTail);
                dTail.next = dEntry;
                dTail = (Entry<T>) dTail.next;
                dCursor = dTail;
                size++;
            }
        }

        public boolean hasNext() {
            return dCursor.next != null;
        }

        public T next() {
            dPrev = dCursor;
            dCursor = (Entry<T>) dCursor.next;
            ready = true;
            return dCursor.element;
        }

        public void remove(){
            if(!ready){
                throw new NoSuchElementException();
            }
            dPrev.next = dCursor.next;
            ((Entry<T>)dCursor.next).prev =  dPrev;

            if(dCursor == dTail){
                dTail =  dPrev;
            }
            dCursor =  dPrev;
            ready = false;
            size--;
        }

    }

    public void add(T x) {
        add( new Entry<>(x, null, dTail));
    }

    public void add(DoublyLinkedList.Entry<T> dEntry) {
        dTail.next = dEntry;
        dTail = (Entry<T>) dTail.next;
        size++;
    }

    public void print(DoublyLinkedList dll) {
        System.out.print(size + ": ");
        Entry<T> I = dll.dHead;
        while(I.next != null) {
            if(I.element == null) I = (Entry<T>) I.next;

            System.out.print(I.element+ "");
            I= (Entry) I.next;
        }
        System.out.print(I.element);

        System.out.println();
    }

    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        DoublyLinkedList dList = new DoublyLinkedList<>();
        DoublyLinkedListIterator dIt = dList.diterator();

        for (int i = 1; i <= n; i++) {
           dList.add(Integer.valueOf(i));
        }

        dList.print(dList);
        Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:

                    if (dIt.hasNext()) {
                        System.out.println(dIt.next());
                    } else break whileloop;
                    break;
                case 2:
                    if (dIt.hasPrev())
                        System.out.println(dIt.previous());
                    else break whileloop;
                    break;
                case 3:
                    dIt.add(Integer.valueOf(in.nextInt()));
                    dList.print(dList);
                    break ;
                case 4:
                    dIt.remove();
                    dList.printList();
                    dList.print(dList);
                    break;

                default:
                    break whileloop;
            }
        }
    }
}

