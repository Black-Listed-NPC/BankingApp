package com.bank;

import com.bank.ui.LoginFrame;
import com.bank.server.BankServer;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Start localhost server (WAP: Networking)
        BankServer.startServer();
        // Launch GUI
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
