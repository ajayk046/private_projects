import java.security.NoSuchAlgorithmException;
public class NewCustomerQueue {
    private Customer[] array;
    //private PHashtable table;

    private int size;
    private int root;
    private int end_index;
    private int capacity;
    private Customer emptyCustomer;
    private CBlockChain table;

    /*TO BE COMPLETED IN PART 1*/

    //constructor: set variables
    //capacity = initial capacity of array

    public NewCustomerQueue(int capacity) {

        array = new  Customer[capacity];
        size = 0;
        root = 0;

        this.capacity = capacity;
        emptyCustomer = new Customer("NULL",0,Integer.MAX_VALUE);
        table = new CBlockChain(capacity);
    }

    public void arrSwap(int x, int y) {
        Customer temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    public void heapifyUp(int ind) {
        int swimIndex = (int) Math.floor((ind - 1) / 2);
        if (ind != 0) {
            if (array[ind].compareTo(array[swimIndex]) > 0) {
                array[ind].setPosInQueue(swimIndex);
                array[swimIndex].setPosInQueue(ind);
                arrSwap(ind,swimIndex);
                heapifyUp(swimIndex);
            }
        }


    }

    //helper function to sink entries downwards
    public void heapifyDown(int ind) {

        if (ind >= (size-1)/2 && size != 2) {
            return;
        }


        Customer cur = array[ind];
        Customer left = array[(2 * ind) + 1];
        Customer right = array[(2 * ind) + 2];

         if (cur != null && left != null && right == null) {
            if (cur.compareTo(left) < 0) {

                int newIndex = (2 * ind) + 1;
                array[ind].setPosInQueue(newIndex);
                array[newIndex].setPosInQueue(ind);
                arrSwap(ind, newIndex);
                heapifyDown(newIndex);
            }
        }

        else if (cur != null && left != null && right != null) {
            if (cur.compareTo(left) < 0 ||  cur.compareTo(right) < 0) {

                if (left.compareTo(right) <= 0) {
                    int newIndex = (2 * ind) + 2;
                    array[ind].setPosInQueue(newIndex);
                    array[newIndex].setPosInQueue(ind);
                    arrSwap(ind, newIndex);

                    heapifyDown(newIndex);
                }

                else {
                    int newIndex = (2 * ind) + 1;
                    array[ind].setPosInQueue(newIndex);
                    array[newIndex].setPosInQueue(ind);
                    arrSwap(ind, newIndex);
                    heapifyDown(newIndex);

                }
            }
        }





    }

    //insert Customer c into queue
    //return the final index at which the customer is stored
    //return -1 if the customer could not be inserted
    public int insert(Customer c) throws NoSuchAlgorithmException{
        if (size != capacity) {

            array[end_index] = c;
            c.setPosInQueue(end_index);
            heapifyUp(end_index);
            table.put(c);
            end_index++;
            size++;

            return end_index;
        }

        return -1;
    }

    //remove and return the Customer with the highest investment level
    //if there are multiple Customers with the same investment level,
    //return the one who arrived first
    public Customer delMax() throws NoSuchAlgorithmException{
        if (!isEmpty()) {

            Customer customer = array[root];
            arrSwap(end_index - 1, root);
            array[end_index - 1].setPosInQueue(-1);
            array[root].setPosInQueue(root);
            array[end_index - 1] = null;

            size--;
            end_index--;
            table.remove(customer.name());
            heapifyDown(root);
            customer.setPosInQueue(-1);
            return customer;
        }
        return null;
    }

    //return but do not remove the first customer in the queue
    public Customer getMax() {
        //TO BE COMPLETED
        return array[root];
    }

    //return the number of Customers currently in the queue
    public int size() {
        //TO BE COMPLETED
        return size;
    }

    //return true if the queue is empty; false else
    public boolean isEmpty() {
        //TO BE COMPLETED
        return (size == 0) ;
    }

    //used for testing underlying data structure
    public Customer[] getArray() {
        return array;
    }

    /*TO BE COMPLETED IN PART 2*/

    //remove and return the Customer with
    //name s from the queue
    //return null if the Customer isn't in the queue
    public Customer remove(String s) throws NoSuchAlgorithmException{
        Customer customer = table.get(s);
        Customer toRemove = null;

        if (customer == null) {
            return null;
        }
        if (customer.posInQueue() == 0) {
            return delMax();
        }


        int old = customer.posInQueue();
        int index = end_index -1;


        if (customer.posInQueue() == index) {
            toRemove = array[old];
            array[index].setPosInQueue(-1);
            array[index] = null;
            size--;
            end_index--;
            return toRemove;
        }

        index = end_index - 1;
        toRemove = array[old];
        arrSwap(index, old);

        array[old].setPosInQueue(old);

        array[index].setPosInQueue(-1);
        array[index] = null;
        size--;
        end_index--;

        if (array[old].compareTo(toRemove) > 0) {
            heapifyUp(old);
        }
        else {
            heapifyDown(old);
        }


        toRemove.setPosInQueue(-1);

        return toRemove;
    }

    //update the emergency level of the Customer
    //with name s to investment
    public void update(String s, int investment) throws NoSuchAlgorithmException{
        //TO BE COMPLETED
        Customer customer = table.get(s);
        int old = customer.investment();

        customer.setInvestment(investment);
        table.remove(s);
        table.put(customer);

        if (customer.investment() <= old) {
            heapifyDown(customer.posInQueue());

        }
        else {
            heapifyUp(customer.posInQueue());
        }
    }
}
    