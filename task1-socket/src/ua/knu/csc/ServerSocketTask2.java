package ua.knu.csc;

import java.math.BigInteger;

import java.util.List;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.sql.SQLException;

class Handler extends Thread {
    private final Socket socket;

    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    private final Manager manager;

    private final int STATUS_CODE_SUCCESS = 0;

    public Handler(Socket socket) throws IOException, SQLException {
        this.socket = socket;

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        manager = new Manager();
    }

    private void processQuery(Operation operation) throws IOException, ClassNotFoundException {
        switch (operation) {
            case GET_ALL_CUSTOMERS_IN_ALPHABET_ORDER -> {
                List<Customer> customers = manager.getAllCustomersInAlphabetOrder();

                objectOutputStream.writeInt(STATUS_CODE_SUCCESS);
                objectOutputStream.writeObject(customers);
            }
            case GET_CUSTOMERS_WITH_CARD_NUMBER_IN_RANGE -> {
                BigInteger lowerBound = (BigInteger) objectInputStream.readObject();
                BigInteger upperBound = (BigInteger) objectInputStream.readObject();

                List<Customer> customers = manager.getCustomersWithCardNumberInRange(lowerBound, upperBound);

                objectOutputStream.writeInt(STATUS_CODE_SUCCESS);
                objectOutputStream.writeObject(customers);
            }
            default -> objectOutputStream.writeInt(-1);
        }
        objectOutputStream.flush();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                int code = objectInputStream.readInt();
                Operation operation = Operation.getOperation(code);
                processQuery(operation);
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}

public class ServerSocketTask2 {
    private final ServerSocket serverSocket;

    public ServerSocketTask2(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            try {
                Handler handler = new Handler(socket);
                handler.start();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                socket.close();
            }
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        ServerSocketTask2 serverSocketTask2 = new ServerSocketTask2(12345);
        try {
            serverSocketTask2.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketTask2.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
