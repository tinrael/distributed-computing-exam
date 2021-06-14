package ua.knu.csc;

import java.util.List;

import java.math.BigInteger;

import java.net.MalformedURLException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    private final ManagerInterface managerInterface;

    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        String url = "//localhost:123/ManagerImplementation";
        this.managerInterface = (ManagerInterface) Naming.lookup(url);
    }

    public void sendQuery(String query) throws RemoteException {
        String[] arguments = query.split("#");

        int code = Integer.parseInt(arguments[0]);

        Operation operation = Operation.getOperation(code);
        switch (operation) {
            case GET_ALL_CUSTOMERS_IN_ALPHABET_ORDER -> {
                List<Customer> customers = this.managerInterface.getAllCustomersInAlphabetOrder();

                for (Customer customer : customers) {
                    System.out.println(customer);
                }
            }
            case GET_CUSTOMERS_WITH_CARD_NUMBER_IN_RANGE -> {
                BigInteger lowerBound = new BigInteger(arguments[1]);
                BigInteger upperBound = new BigInteger(arguments[2]);

                List<Customer> customers = this.managerInterface.getCustomersWithCardNumberInRange(lowerBound, upperBound);

                for (Customer customer : customers) {
                    System.out.println(customer);
                }
            }
            default -> throw new IllegalArgumentException("Undefined operation.");
        }
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        Client client = new Client();

        System.out.println("<code>(#<argument>)*");

        System.out.println();

        System.out.println("Codes: ");
        System.out.println("\t" + Operation.GET_ALL_CUSTOMERS_IN_ALPHABET_ORDER.getCode() + " - GET_ALL_CUSTOMERS_IN_ALPHABET_ORDER");
        System.out.println("\t" + Operation.GET_CUSTOMERS_WITH_CARD_NUMBER_IN_RANGE.getCode() + " - GET_CUSTOMERS_WITH_CARD_NUMBER_IN_RANGE");

        System.out.println();

        System.out.println("Examples: ");
        System.out.println("\t0");
        System.out.println("\t1#111222333444555#999888777666555");

        System.out.println();

        System.out.println("To exit enter 'exit'.");

        System.out.println();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter a query: ");
        String query = bufferedReader.readLine();

        while (!query.equals("exit")) {
            client.sendQuery(query);

            System.out.print("Enter a query: ");
            query = bufferedReader.readLine();
        }
    }
}
