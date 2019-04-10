package mxa180038;

//import java.util.Collection;

public interface OurQueue<E> { // extends Collection<E> {
    boolean offer(E e);
    E peek();
    E poll();
    void clear();
    //void toArray(E a[]);
}
