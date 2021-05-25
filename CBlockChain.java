
import java.util.ArrayList;


public class CBlockChain {
    private ArrayList[] table;
    private int capacity;
    private int size;

    //set the table size to the first
    //prime number p >= capacity
    public CBlockChain(int capacity) {
        this.capacity = getNextPrime(capacity);
        this.size = 0;
        this.table = new ArrayList[this.capacity];
    }


    //return the Customer with the given name
    //or null if the Customer is not in the table
    public Customer get(String name) {
        Customer c = null;
        if (table[getHash(name)] == null) {
            return null;
        }

        for (Object o : table[getHash(name)]) {
            c = (Customer) o;
            if (c.name().equals(name)) {
                return c;
            }
        }

        return c;
    }

    public int getHash (String name) {
        int hash = name.hashCode() % capacity;
        while (hash < 0) {
            hash += capacity;
        }

        return hash;
    }


    //put Customer c into the table
    public void put(Customer c) {
        int index = getHash(c.name());

        if(table[index] == null) {
            table[index] = new ArrayList<Customer>();
        }
        table[index].add(c);
        size++;

    }

    //remove and return the Customer with the given name
    //from the table
    //return null if Customer doesn't exist
    public Customer remove(String name) {

        if (table[getHash(name)] == null) {
            return null;
        }
        for (Object o : table[getHash(name)]) {
            Customer c = (Customer) o;
            if (c.name().equals(name)) {
                table[getHash(name)].remove(o);
                size--;
                return c;
            }
        }
        return null;
    }

    //return the number of Customers in the table
    public int size() {
        //TO BE COMPLETED
        return size;
    }

    //returns the underlying structure for testing
    public ArrayList<Customer>[] getArray() {
        return table;
    }

    //get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;
        int rem = num % 6;
        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }
        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }
        return num;
    }


    //determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;
        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}


