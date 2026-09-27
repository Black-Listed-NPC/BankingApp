package com.bank.model;

// WAP: Implementation of Inheritance
public class SavingsAccount extends Account {
    private double interestRate = 4.0; // 4% per year

    public SavingsAccount(String accountNumber, String holderName, double balance, String password) {
        super(accountNumber, holderName, balance, password);
    }

    public double getInterestRate() { return interestRate; }

    public double calculateInterest() {
        return getBalance() * interestRate / 100;
    }

    public String getAccountType() {
        return "Savings Account";
    }
}
