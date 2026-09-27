package com.bank.model;

// WAP: Implementation of Classes
public class Account {
    private String accountNumber;
    private String holderName;
    private double balance;
    private String password;

    public Account(String accountNumber, String holderName, double balance, String password) {
        this.accountNumber = accountNumber;
        this.holderName    = holderName;
        this.balance       = balance;
        this.password      = password;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName()    { return holderName; }
    public double getBalance()       { return balance; }
    public String getPassword()      { return password; }
    public void   setBalance(double b) { this.balance = b; }
}
