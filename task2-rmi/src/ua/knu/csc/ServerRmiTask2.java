package ua.knu.csc;

import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRmiTask2 {

    public static void main(String[] args) throws RemoteException {
        ManagerImplementation managerImplementation = new ManagerImplementation();

        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("ManagerImplementation", managerImplementation);

        System.out.println("ServerRmiTask2 started.");
    }
}
