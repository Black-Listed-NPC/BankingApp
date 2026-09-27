package com.bank.db;

import com.bank.model.SavingsAccount;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static HashMap<String, SavingsAccount> accounts = new HashMap<>();
    private static int accCounter = 4;

    static {
        accounts.put("ACC001", new SavingsAccount("ACC001", "Rahul Sharma",  50000, "rahul123"));
        accounts.put("ACC002", new SavingsAccount("ACC002", "Priya Patel",   30000, "priya123"));
        accounts.put("ACC003", new SavingsAccount("ACC003", "Amit Kumar",    75000, "amit123"));
    }

    public static SavingsAccount getAccount(String accNo) {
        return accounts.get(accNo);
    }

    public static boolean accountExists(String accNo) {
        return accounts.containsKey(accNo);
    }

    public static void updateAccount(SavingsAccount acc) {
        accounts.put(acc.getAccountNumber(), acc);
    }

    public static String createAccount(String name, double initialDeposit, String password) {
        String accNo = "ACC" + String.format("%03d", accCounter++);
        SavingsAccount acc = new SavingsAccount(accNo, name, initialDeposit, password);
        accounts.put(accNo, acc);
        logTransaction(accNo, "ACCOUNT OPENED", initialDeposit);
        return accNo;
    }

    public static List<SavingsAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public static void logTransaction(String accNo, String type, double amount) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("transactions.txt", true);
            fw.write(new java.util.Date() + " | " + accNo + " | " + type + " | Rs." + String.format("%.2f", amount) + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Log error: " + e.getMessage());
        }
    }

    public static List<String> getTransactions(String accNo) {
        List<String> list = new ArrayList<>();
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("transactions.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(accNo)) list.add(line);
            }
            br.close();
        } catch (Exception e) { }
        return list;
    }
}
