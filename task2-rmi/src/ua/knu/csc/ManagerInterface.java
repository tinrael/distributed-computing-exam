package ua.knu.csc;

import java.math.BigInteger;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ManagerInterface extends Remote {
    List<Customer> getAllCustomersInAlphabetOrder() throws RemoteException;
    List<Customer> getCustomersWithCardNumberInRange(BigInteger lowerBound, BigInteger upperBound) throws RemoteException;
}
