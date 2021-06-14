package ua.knu.csc;

import java.math.BigInteger;

import java.util.List;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientSocketTask2 {
    private final Socket socket;

    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientSocketTask2(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void initialize() throws IOException {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public int sendQuery(String query) throws IOException, ClassNotFoundException {
        String[] arguments = query.split("#");

        int code = Integer.parseInt(arguments[0]);

        objectOutputStream.writeInt(code);
        objectOutputStream.flush();

        Operation operation = Operation.getOperation(code);
        switch (operation) {
            case GET_ALL_CUSTOMERS_IN_ALPHABET_ORDER -> {
                int status = objectInputStream.readInt();

                if (status >= 0) {
                    List<Customer> customers = (List<Customer>) objectInputStream.readObject();

                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                }

                return status;
            }
            case GET_CUSTOMERS_WITH_CARD_NUMBER_IN_RANGE -> {
                BigInteger lowerBound = new BigInteger(arguments[1]);
                BigInteger upperBound = new BigInteger(arguments[2]);

                objectOutputStream.writeObject(lowerBound);
                objectOutputStream.writeObject(upperBound);
                objectOutputStream.flush();

                int status = objectInputStream.readInt();

                if (status >= 0) {
                    List<Customer> customers = (List<Customer>) objectInputStream.readObject();

                    for (Customer customer : customers) {
                        System.out.println(customer);
                    }
                }

                return status;
            }
            default -> throw new IllegalArgumentException("Undefined operation.");
        }
    }

    public static void main(String[] args) throws IOException {
        ClientSocketTask2 clientSocketTask2 = new ClientSocketTask2("localhost", 12345);

        try {
            clientSocketTask2.initialize();

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

            System.out.println("A non-negative status indicates that an operation executed successfully.");
            System.out.println("A negative status indicates that an error occurred while executing an operation.");

            System.out.println();

            System.out.println("To disconnect from the server enter 'disconnect'.");

            System.out.println();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter a query: ");
            String query = bufferedReader.readLine();

            while (!query.equals("disconnect")) {
                int status = clientSocketTask2.sendQuery(query);
                System.out.println("Status: " + status);

                System.out.print("Enter a query: ");
                query = bufferedReader.readLine();
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            try {
                clientSocketTask2.disconnect();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
