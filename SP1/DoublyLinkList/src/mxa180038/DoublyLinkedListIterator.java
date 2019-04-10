/*
@author Manindra Kumar Anantaneni (mxa180038)
@author Shiva Prasad Reddy
*/

package mxa180038;

public interface DoublyLinkedListIterator<T>{
    boolean hasNext();
    boolean hasPrev();
    T next();
    T previous();
    void add(T x);
    void remove();

}
