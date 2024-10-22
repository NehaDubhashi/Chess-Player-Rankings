import java.util.List;

public interface MyPriorityQueue<T extends Comparable<T>>{

    // Adds the given item into the priority queue
    public void insert(T item);

    // removes and returns the "top priority" item from the priority queue
    // Throws an IllegalStateException if the data structure is empty.
    // The running time of this method should be O(log n).
    public T extract();

    // returns the "top priority" item from the priority queue
    // Throws an IllegalStateException if the data structure is empty.
    // The running time of this method should be O(1).
    public T peek();

    // To be called after an item's priority has been changed
    // by client code.
    // The input is a reference to the items whose priority
    // has changed. This operation will then update the 
    // underlying data structure as necessary to ensure 
    // it operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log n).
    public void updatePriority(T item);

    // Indicates whether or not there are items in the data structure.
    // The running time of this method should be O(1).
    public boolean isEmpty();

    // Returns the number of items in the data structure
    // The running time of this method should be O(1).
    public int size();

    // Returns all contents of the priority queue as a list.
    // For autograding purposes, the order of the items must 
    // exactly match the order they are in the heap's
    // underlying array.
    // The running time of this method should be O(n).
    public List<T> toList();

    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log n).
    public void remove(T item);
}
