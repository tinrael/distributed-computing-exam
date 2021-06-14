package ua.knu.csc;

import java.math.BigInteger;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customerId;

    private String forename;
    private String surname;
    private String address;

    private BigInteger cardNumber;
    private BigInteger bankAccountNumber;

    public Customer() { }

    public Customer(int customerId, String forename, String surname, String address, BigInteger cardNumber, BigInteger bankAccountNumber) {
        this.customerId = customerId;

        this.forename = forename;
        this.surname = surname;
        this.address = address;

        this.cardNumber = cardNumber;
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(BigInteger cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigInteger getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(BigInteger bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public String toString() {
        return "[customerId: " + customerId + ", forename: " + forename + ", surname: " + surname + ", address: " + address + ", cardNumber: " + cardNumber + ", bankAccountNumber: " + bankAccountNumber + "]";
    }
}
