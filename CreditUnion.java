import java.security.NoSuchAlgorithmException;
public class CreditUnion {

    private NewCustomerQueue cq;
    private int cr_threshold;
    private int capacity;
    private int processed = 0;
    private int seenByManager = 0;
    private int sentToBank = 0;
    private int walkedOut = 0;

    public CreditUnion(int cap, int cr_threshold) {
        //TO BE COMPLETED
        capacity = cap;
        this.cr_threshold = cr_threshold;
        cq = new NewCustomerQueue(cap);
    }

    public int cr_threshold() {
        return this.cr_threshold;
    }

    public int capacity() {
        return this.capacity;
    }

    /*process a new Customer:
     *if their investment value is higher than the cr_threshold,
     *send them directly to the emergency room and return null
     *otherwise, try to insert them into the queue
     *if the queue is full, compare their investment to the highest
     *investment currently in the queue; if their investment is higher,
     *send them to the Bank and return null; if the current max
     *is higher, send the max Customer to the Bank, insert
     *the new Customer into the queue, and return the name
     *of the max Customer
     */
    public String process(String name, int investment) throws NoSuchAlgorithmException{
        //TO BE COMPLETED
        processed++;
        Customer customer = new Customer(name, investment, processed);

        if (customer.investment() > cr_threshold()) {
            sendToBank(customer);
            return null;
        }
        if (cq.insert(customer) == -1) {
            if (customer.compareTo(cq.getMax()) > 0) {
                sendToBank(customer);
                return null;
            }
            else {
                Customer newCustomer = cq.delMax();
                sendToBank(newCustomer);
                cq.insert(customer);
                return newCustomer.name();
            }
        }


        return customer.name();
    }

    /*a Manager is available--send the Customer with
     *highest investment value to be seen; return the name
     *of the Customer or null if the queue is empty*/
    public String seeNext() throws NoSuchAlgorithmException{
        if (!cq.isEmpty()) {

            Customer c = cq.delMax();
            seeManager(c);
            return c.name();
        }
        return null;
    }

    /*Customer experiences an emergency, raising their
     *investment value; if the investment value exceeds the
     *cr_threshold, send them directly to the bank;
     *else update their investment value in the queue;
     *return true if the Customer is removed from the queue
     *and false otherwise*/
    public boolean handle_emergency(String name) throws NoSuchAlgorithmException{
        //TO BE COMPLETED
        Customer[] customers = cq.getArray();
        Customer priority = null;
        if (customers.length == 0) {
            return false;
        }

        for (int i = 0; i < customers.length; i++) {
            if (customers[i] != null && customers[i].name().equals(name)) {
                priority = customers[i];
            }
        }

        cq.update(name, priority.investment());
        if (priority.investment() <= cr_threshold()) {
            return false;
        }

        Customer toSend = cq.remove(name);
        sendToBank(toSend);

        return true;
    }

    /*Customer decides to walk out
     *remove them from the queue*/
    public void walk_out(String name)throws NoSuchAlgorithmException {
        //TO BE COMPLETED
        cq.remove(name);
        walkedOut++;
    }

    /*Indicates that Customer c has been sent to the Bank*/
    private void sendToBank(Customer c) {
        System.out.println("Customer " + c + " sent to Bank.");
        sentToBank++;
    }

    /*Indicates that a Customer is being seen by a Manager*/
    private void seeManager(Customer c) {
        System.out.println("Customer " + c + " is seeing a manager.");
        seenByManager++;
    }

    public int processed() {
        return processed;
    }

    public int sentToBank() {
        return sentToBank;
    }

    public int seenByManager() {
        return seenByManager;
    }

    public int walkedOut() {
        return walkedOut;
    }
}