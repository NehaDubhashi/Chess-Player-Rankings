import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BinaryMinHeap <T extends Comparable<T>> implements MyPriorityQueue<T> {
    private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
                     // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    public BinaryMinHeap(){
        // This line just creates an array of type T. We're doing it this way just
        // because of weird java generics stuff (that I frankly don't totally understand)
        // If you want to create a new array anywhere else (e.g. to resize) then
        // You should mimic this line. The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i){
        if (i == 0 || i > size) { //can't percolate with only one node
            throw new IllegalArgumentException("Invalid heap input.");
        }
        int parent = (i - 1)/2;
        T val = arr[i];  // value at current location
        while(i > 0 && (arr[i].compareTo(arr[parent]) < 0)){  // until location is root or heap property holds
            arr[i] = arr[parent];  // move parent value to this location
            arr[parent] = val; // put current value into parent’s location 
            i = parent;  // make current location the parent
            if (i != 0) {
                parent = (i - 1)/2;
            }  
        } 
        for (int j = 0; j < size; j++) {
            itemToIndex.put(arr[j], j); // Update map with new indices
        }
    }

    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i){
        if (i < 0 || i >= size) { 
            throw new IllegalArgumentException("Invalid heap input MHPD");
        }
        int left = i*2 + 1;  // index of left child
        int right = i*2 + 2;  // index of right child
        T val = arr[i];  // value at location
        while(left < size){  // until location is leaf
            int toSwap = right;
            if(right >= size || (arr[left].compareTo(arr[right]) < 0 )){  // if there is no right child or if left child is smaller
                toSwap = left;  // swap with left
            } // now toSwap has the smaller of left/right, or left if right does not exist
            if (arr[toSwap].compareTo(val) < 0) {  // if the smaller child is less than the current value
                arr[i] = arr[toSwap];
                arr[toSwap] = val; // swap parent with smaller child
                i = toSwap; // update current node to be smaller child
                left = i*2 + 1;
                right = i*2 + 2; 
                for (int j = 0; j < size; j++) {
                    itemToIndex.put(arr[j], j); // Update map with new indices
                }
            }
            else{ return;} // if we don’t swap, then heap property holds
        }
    }

    // copy all items into a larger array to make more room.
    private void resize(){
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
        for (int i = 0; i < size; i++) {
            larger[i] = arr[i]; // copy old elements into resized array
        }
        arr = larger; 
    }

    public void insert(T item){
        if(size == arr.length){resize();}
        arr[size] = item;
        size++;
        if (size > 1) {
            percolateUp(size - 1);
        }   
    }

    public T extract(){
        if (size == 0) { 
            throw new IllegalStateException("MinHeap is empty");
        } else if (size == 1) {
            T temp = arr[0];
            itemToIndex.remove(temp);
            size--;
            return temp;
        } else {
            T min = arr[0];
            itemToIndex.remove(min); // remove first node from map
            arr[0] = arr[size - 1];
            size--;
            percolateDown(0);
            itemToIndex.put(arr[0], 0); // update map
            return min;
        }
    }

    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index){
        if (size == 0) { 
            throw new IllegalStateException("MinHeap is empty");
        } else if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid heap input.");
        }
        T temp;
        if (size == 1) { // Case where only one node exists
            extract();
        } 
        temp = arr[index]; // Every other case
        arr[index] = arr[size - 1];
        size--;
        updatePriority(index);
        return temp;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void remove(T item){
        remove(itemToIndex.get(item));
    }

    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index){
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Invalid heap input.");
        }
        if (index == 0) {
            percolateDown(index);
        } else {
            int parent = (index - 1) / 2;
            if (arr[index].compareTo(arr[parent]) < 0) {
                // If item is smaller than its parent, percolate up
                percolateUp(index);
            } else {
                // Otherwise, percolate down
                percolateDown(index);
        } 
        }
    }

    // This method gets called after the client has 
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that 
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void updatePriority(T item){
	if(!itemToIndex.containsKey(item)){
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
	}
        updatePriority(itemToIndex.get(item));
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public boolean isEmpty(){
        return size == 0;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public int size(){
        return size;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public T peek(){
        if (isEmpty()) {
            throw new IllegalStateException("MinHeap is empty");
        }
        return arr[0];
    }
    
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public List<T> toList(){
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++){
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // For debugging
    public String toString(){
        if(size == 0){
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for(int i = 1; i < size; i++ ){
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }
    
}
