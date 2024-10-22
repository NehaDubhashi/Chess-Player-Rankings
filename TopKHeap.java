import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class TopKHeap<T extends Comparable<T>> {
    private BinaryMinHeap<T> topK; // Holds the top k items
    private BinaryMaxHeap<T> rest; // Holds all items other than the top k
    private int size; // Maintains the size of the data structure
    private final int k; // The value of k
    private Map<T, MyPriorityQueue<T>> itemToHeap; // Keeps track of which heap contains each item.
    
    // Creates a topKHeap for the given choice of k.
    public TopKHeap(int k){
        topK = new BinaryMinHeap<>();
        rest = new BinaryMaxHeap<>();
        size = 0;
        this.k = k;
        itemToHeap = new HashMap<>();
    }

    // Returns a list containing exactly the
    // largest k items. The list is not necessarily
    // sorted. If the size is less than or equal to
    // k then the list will contain all items.
    // The running time of this method should be O(k).
    public List<T> topK(){
        return topK.toList();
    }

    // Add the given item into the data structure.
    // The running time of this method should be O(log(n)+log(k)).
    public void insert(T item){
        if (item == null) {
            throw new IllegalArgumentException("Item is invalid.");
        }
        if (topK.size() < k) { // If we have not fully populated the topK heap 
            topK.insert(item);
            itemToHeap.put(item, topK);
        }
        else {
            if (item.compareTo(topK.peek()) > 0) { // If item is bigger than minHeap's lowest value
                T newRestNode = topK.extract();
                rest.insert(newRestNode); 
                itemToHeap.put(newRestNode, rest);
                topK.insert(item); 
                itemToHeap.put(item, topK);
            } else { // if in maxHeap
                rest.insert(item);
                itemToHeap.put(item, rest);
            }
        }
        size++;
    }

    // Indicates whether the given item is among the 
    // top k items. Should return false if the item
    // is not present in the data structure at all.
    // The running time of this method should be O(1).
    // We have provided a suggested implementation,
    // but you're welcome to do something different!
    public boolean isTopK(T item){
        return itemToHeap.get(item) == topK;
    }

    // To be used whenever an item's priority has changed.
    // The input is a reference to the items whose priority
    // has changed. This operation will then rearrange
    // the items in the data structure to ensure it
    // operates correctly.
    // Throws an IllegalArgumentException if the item is
    // not a member of the heap.
    // The running time of this method should be O(log(n)+log(k)).
    public void updatePriority(T item){
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("This item is not a member of the heap.");
        } 
        if (item == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        if (!isTopK(item)) { // Reinstate heap if we changed priority in rest
            rest.updatePriority(item);
        } else {
            topK.updatePriority(item); // Reinstate heap if we changed priority in topK
        }
        if (!rest.isEmpty() && !topK.isEmpty() && rest.peek().compareTo(topK.peek()) > 0)  { // If max of rest > min of topK
            T newTopKNode = rest.extract();
            T newRestNode = topK.extract();
            rest.insert(newRestNode);
            itemToHeap.put(newRestNode, rest);                
            topK.insert(newTopKNode);
            itemToHeap.put(newTopKNode, topK);
        } 
    } 


    // Removes the given item from the data structure
    // throws an IllegalArgumentException if the item
    // is not present.
    // The running time of this method should be O(log(n)+log(k)).
    public void remove(T item){
        if (!itemToHeap.containsKey(item)) {
            throw new IllegalArgumentException("This item is not a member of the heap.");
        }
        if (item == null) {
            throw new IllegalArgumentException("Invalid input.");
        }
        if (isTopK(item)) { // if removing from topK
            topK.remove(item);
            itemToHeap.remove(item);
            if (!rest.isEmpty()) {
                T topKNewNode = rest.extract();
                topK.insert(topKNewNode);
                itemToHeap.put(topKNewNode, topK);
            }
        } else if (!rest.isEmpty()) { // if removing from rest
            rest.remove(item);
            itemToHeap.remove(item);
        }  
        size--; 
    }
}
