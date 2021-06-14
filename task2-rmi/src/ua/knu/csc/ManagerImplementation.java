package ua.knu.csc;

import java.math.BigInteger;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ManagerImplementation extends UnicastRemoteObject implements ManagerInterface {
    private static final ArrayList<Customer> customers = new ArrayList<>();
    static {
        customers.add(new Customer(4, "Jack", "Jackson", "Carson Street", new BigInteger("111222333444555"), new BigInteger("999333")));
        customers.add(new Customer(8, "John", "Johnson", "Broad Street", new BigInteger("999888777666555"), new BigInteger("113344")));
        customers.add(new Customer(17, "Bob", "Thompson", "Pearl Street", new BigInteger("555222777444111"), new BigInteger("774425")));
        customers.add(new Customer(1, "Emma", "Flores", "Red Street", new BigInteger("777333444665111"), new BigInteger("888555")));
    }

    protected ManagerImplementation() throws RemoteException { }

    @Override
    public List<Customer> getAllCustomersInAlphabetOrder() throws RemoteException {
        synchronized (customers) {
            return customers.stream().sorted(new Comparator<Customer>() {
                @Override
                public int compare(Customer customer1, Customer customer2) {
                    String customer1FullName = customer1.getForename() + customer1.getSurname();
                    String customer2FullName = customer2.getForename() + customer2.getSurname();
                    return customer1FullName.compareTo(customer2FullName);
                }
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<Customer> getCustomersWithCardNumberInRange(BigInteger lowerBound, BigInteger upperBound) throws RemoteException {
        synchronized (customers) {
            return customers.stream().filter(new Predicate<Customer>() {
                @Override
                public boolean test(Customer customer) {
                    BigInteger cardNumber = customer.getCardNumber();
                    return ((cardNumber.compareTo(lowerBound) >= 0) && (cardNumber.compareTo(upperBound) <= 0));
                }
            }).collect(Collectors.toList());
        }
    }
}
