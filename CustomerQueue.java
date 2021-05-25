public class CustomerQueue {
    private Customer[] heap;
    private int size;
    private int maxCapacity;
    private int root;
    private int end_index;
    Customer tempCustomer;
    //constructor: set variables
    //capacity = initial capacity of array


    public CustomerQueue(int capacity) {
        //TO BE COMPLETED

        tempCustomer = new Customer("NULL", 0, Integer.MAX_VALUE);
        size = 0;
        root = 0;
        end_index = 0;
        heap = new Customer[capacity];
        maxCapacity = capacity;
    }

    public void heapifyDown(int index) {
        if (index >= (size-1)/2 && size != 2) {
            return;
        }

        Customer cur = heap[index];
        Customer left = heap[(2 * index) + 1];
        Customer right = heap[(2 * index) + 2];

        if (cur.compareTo(left) < 0 ||  cur.compareTo(right) < 0) {

            if (left.compareTo(right) <= 0) {
                int newIndex = (2 * index) + 2;
                swap(index, newIndex);
                heapifyDown(newIndex);


            }
            else {
                int newIndex = (2 * index) + 1;
                swap(index, newIndex);

                heapifyDown(newIndex);
            }
        }

    }

    public void heapifyUp(int index) {

        int swimIndex = (int) Math.floor((index - 1) / 2);
        if (index != 0) {
            if (heap[index].compareTo(heap[swimIndex]) > 0) {
                swap(index, swimIndex);
                heapifyUp(swimIndex);
            }
        }
    }

    //insert Customer c into queue
    //return the final index at which the customer is stored
    //return -1 if the customer could not be inserted
    public int insert(Customer c) {
        //TO BE COMPLETED
        if (size == maxCapacity) {
            return -1;
        }

        heap[end_index] = c;
        heapifyUp(end_index++);
        size++;
        return end_index;


    }

    //remove and return the customer with the highest investment value
    //if there are multiple customers with the same investment value,
    //return the one who arrived root
    public Customer delMax() {
        //TO BE COMPLETED
        if (!isEmpty()) {

            Customer customer = heap[root];
            swap(end_index - 1, root);
            heap[end_index - 1] = tempCustomer;
            heapifyDown(root);
            end_index--;
            size--;


            return customer;
        }

        else {
            return null;
        }
    }


    //return but do not remove the first customer in the queue
    public Customer getMax() {
        //TO BE COMPLETED
        return heap[root];
    }

    //return the number of customers currently in the queue
    public int size() {
        //TO BE COMPLETED
        return size;
    }

    //return true if the queue is empty; false else
    public boolean isEmpty() {
        //TO BE COMPLETED
        if (size == 0) {
            return true;
        }
        return false;
    }

    //used for testing underlying data structure
    public Customer[] getArray() {
        return heap;
    }

    public void swap(int index1, int index2) {
        Customer temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

}
