package com.bank.ui;

import com.bank.model.SavingsAccount;
import com.bank.db.Database;
import com.bank.exceptions.InsufficientFundsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrame extends JFrame implements ActionListener {

    private SavingsAccount account;
    private JLabel  lblBalance, lblStatus;
    private JTextField txtAmount, txtTargetAcc;

    public DashboardFrame(SavingsAccount account) {
        this.account = account;
        setTitle("SecureBank - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(15, 30, 70));

        JPanel main = new JPanel(new BorderLayout(0, 0));
        main.setPreferredSize(new Dimension(520, 620));
        main.setBackground(new Color(25, 42, 90));

        // ── Top bar ──
        JPanel topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(0, 75));
        topBar.setBackground(new Color(15, 28, 65));

        JLabel logo = new JLabel("🏦 SecureBank");
        logo.setBounds(20, 10, 200, 28);
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 17));
        logo.setForeground(new Color(0, 180, 255));

        JLabel welcome = new JLabel("Welcome, " + account.getHolderName());
        welcome.setBounds(20, 42, 280, 20);
        welcome.setFont(new Font("Arial", Font.PLAIN, 12));
        welcome.setForeground(new Color(140, 170, 220));

        JButton btnLogout = makeBtn("Logout", new Color(180, 40, 40));
        btnLogout.setBounds(410, 22, 90, 32);
        btnLogout.addActionListener(this);

        topBar.add(logo); topBar.add(welcome); topBar.add(btnLogout);

        // ── Balance card ──
        JPanel balCard = new JPanel(null);
        balCard.setBackground(new Color(0, 80, 180));
        balCard.setPreferredSize(new Dimension(0, 110));

        JLabel lbType = new JLabel(account.getAccountType() + "  |  " + account.getAccountNumber());
        lbType.setBounds(25, 15, 400, 18);
        lbType.setFont(new Font("Arial", Font.PLAIN, 12));
        lbType.setForeground(new Color(180, 210, 255));

        JLabel lbTitle = new JLabel("Available Balance");
        lbTitle.setBounds(25, 36, 200, 18);
        lbTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lbTitle.setForeground(new Color(180, 210, 255));

        lblBalance = new JLabel("Rs. " + String.format("%.2f", account.getBalance()));
        lblBalance.setBounds(25, 55, 400, 38);
        lblBalance.setFont(new Font("Arial", Font.BOLD, 28));
        lblBalance.setForeground(Color.WHITE);

        balCard.add(lbType); balCard.add(lbTitle); balCard.add(lblBalance);

        // ── Center panel ──
        JPanel center = new JPanel(null);
        center.setBackground(new Color(25, 42, 90));

        // Amount field
        addLabel(center, "Amount (Rs.)", 30, 20);
        txtAmount = new JTextField();
        styleField(txtAmount);
        txtAmount.setBounds(30, 42, 460, 38);
        center.add(txtAmount);

        // Deposit / Withdraw
        JButton btnDep = makeBtn("⬆  Deposit", new Color(0, 150, 80));
        JButton btnWit = makeBtn("⬇  Withdraw", new Color(200, 70, 0));
        btnDep.setBounds(30,  98, 220, 42);
        btnWit.setBounds(268, 98, 222, 42);
        btnDep.addActionListener(this);
        btnWit.addActionListener(this);
        center.add(btnDep); center.add(btnWit);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setBounds(30, 158, 460, 2);
        sep.setForeground(new Color(50, 70, 130));
        center.add(sep);

        // Transfer
        addLabel(center, "Transfer to Account No.", 30, 170);
        txtTargetAcc = new JTextField();
        styleField(txtTargetAcc);
        txtTargetAcc.setBounds(30, 192, 330, 38);
        JButton btnTransfer = makeBtn("Transfer", new Color(0, 100, 210));
        btnTransfer.setBounds(370, 192, 120, 38);
        btnTransfer.addActionListener(this);
        center.add(txtTargetAcc); center.add(btnTransfer);

        // Divider
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(30, 248, 460, 2);
        sep2.setForeground(new Color(50, 70, 130));
        center.add(sep2);

        // Quick action buttons
        JButton btnStatement  = makeBtn("📋  Mini Statement",  new Color(60, 60, 140));
        JButton btnInterest   = makeBtn("💰  View Interest",   new Color(100, 60, 0));
        JButton btnChangePwd  = makeBtn("🔒  Change Password", new Color(80, 30, 100));

        btnStatement .setBounds(30,  260, 220, 40);
        btnInterest  .setBounds(268, 260, 222, 40);
        btnChangePwd .setBounds(30,  312, 460, 40);

        btnStatement .addActionListener(this);
        btnInterest  .addActionListener(this);
        btnChangePwd .addActionListener(this);

        center.add(btnStatement); center.add(btnInterest); center.add(btnChangePwd);

        // Status
        lblStatus = new JLabel("", SwingConstants.CENTER);
        lblStatus.setBounds(0, 365, 520, 22);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        center.add(lblStatus);

        main.add(topBar,   BorderLayout.NORTH);
        main.add(balCard,  BorderLayout.CENTER);
        main.add(center,   BorderLayout.SOUTH);

        bg.add(main);
        add(bg);
        setVisible(true);
    }

    private void addLabel(JPanel p, String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, 400, 18);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(new Color(140, 170, 220));
        p.add(l);
    }

    private void styleField(JTextField f) {
        f.setBackground(new Color(35, 55, 110));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setFont(new Font("Arial", Font.PLAIN, 14));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    private JButton makeBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Logout"))           { dispose(); new LoginFrame(); return; }
        if (cmd.contains("Mini Statement")) { new StatementFrame(account); return; }
        if (cmd.contains("Change Password")){ new ChangePasswordFrame(account); return; }
        if (cmd.contains("View Interest")) {
            double interest = account.calculateInterest();
            showStatus("Yearly interest @ " + account.getInterestRate() + "% = Rs." + String.format("%.2f", interest), false);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(txtAmount.getText().trim());
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showStatus("Enter a valid positive amount.", true); return;
        }

        if (cmd.contains("Deposit")) {
            runInThread(() -> {
                account.setBalance(account.getBalance() + amount);
                Database.updateAccount(account);
                Database.logTransaction(account.getAccountNumber(), "DEPOSIT", amount);
                SwingUtilities.invokeLater(() -> { refreshBalance(); showStatus("Deposited Rs." + String.format("%.2f", amount) + " successfully!", false); });
            });
        }

        if (cmd.contains("Withdraw")) {
            runInThread(() -> {
                try {
                    if (amount > account.getBalance()) throw new InsufficientFundsException("Insufficient balance!");
                    account.setBalance(account.getBalance() - amount);
                    Database.updateAccount(account);
                    Database.logTransaction(account.getAccountNumber(), "WITHDRAW", amount);
                    SwingUtilities.invokeLater(() -> { refreshBalance(); showStatus("Withdrawn Rs." + String.format("%.2f", amount) + " successfully!", false); });
                } catch (InsufficientFundsException ex) {
                    SwingUtilities.invokeLater(() -> showStatus(ex.getMessage(), true));
                }
            });
        }

        if (cmd.equals("Transfer")) {
            String target = txtTargetAcc.getText().trim().toUpperCase();
            runInThread(() -> {
                try {
                    if (!Database.accountExists(target))          throw new Exception("Target account not found!");
                    if (target.equals(account.getAccountNumber())) throw new Exception("Cannot transfer to same account!");
                    if (amount > account.getBalance())             throw new InsufficientFundsException("Insufficient balance!");

                    SavingsAccount dest = Database.getAccount(target);
                    account.setBalance(account.getBalance() - amount);
                    dest.setBalance(dest.getBalance() + amount);
                    Database.updateAccount(account);
                    Database.updateAccount(dest);
                    Database.logTransaction(account.getAccountNumber(), "TRANSFER TO " + target, amount);
                    SwingUtilities.invokeLater(() -> { refreshBalance(); showStatus("Transferred Rs." + String.format("%.2f", amount) + " to " + target, false); });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> showStatus(ex.getMessage(), true));
                }
            });
        }
    }

    private void runInThread(Runnable r) {
        showStatus("Processing...", false);
        new Thread(r).start();
    }

    private void refreshBalance() {
        lblBalance.setText("Rs. " + String.format("%.2f", account.getBalance()));
        txtAmount.setText("");
    }

    private void showStatus(String msg, boolean err) {
        lblStatus.setForeground(err ? new Color(255, 80, 80) : new Color(0, 220, 120));
        lblStatus.setText(msg);
    }
}
