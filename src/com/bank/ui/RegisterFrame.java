package com.bank.ui;

import com.bank.db.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// WAP: Create New Account - Registration
public class RegisterFrame extends JFrame implements ActionListener {

    private JTextField     txtName, txtDeposit;
    private JPasswordField txtPass, txtConfirmPass;
    private JLabel         lblStatus;

    public RegisterFrame() {
        setTitle("SecureBank - Create New Account");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(15, 30, 70));

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(440, 530));
        card.setBackground(new Color(25, 42, 90));
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 255, 80), 1));

        // Header
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 440, 80);
        header.setBackground(new Color(15, 28, 65));

        JLabel logo = new JLabel("🏦 SecureBank", SwingConstants.CENTER);
        logo.setBounds(0, 12, 440, 30);
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        logo.setForeground(new Color(0, 180, 255));

        JLabel sub = new JLabel("Create New Account", SwingConstants.CENTER);
        sub.setBounds(0, 46, 440, 20);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(140, 170, 220));

        header.add(logo); header.add(sub);

        // Fields
        int y = 100;
        JTextField[] fields = { txtName = new JTextField(), txtDeposit = new JTextField() };
        String[] labels = { "Full Name", "Initial Deposit (Rs.)" };

        card.add(header);

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setBounds(40, y, 360, 18);
            lbl.setFont(new Font("Arial", Font.PLAIN, 12));
            lbl.setForeground(new Color(140, 170, 220));
            styleField(fields[i]);
            fields[i].setBounds(40, y + 20, 360, 38);
            card.add(lbl); card.add(fields[i]);
            y += 75;
        }

        JLabel lblP = new JLabel("Password");
        lblP.setBounds(40, y, 360, 18);
        lblP.setFont(new Font("Arial", Font.PLAIN, 12));
        lblP.setForeground(new Color(140, 170, 220));
        styleField(txtPass = new JPasswordField());
        txtPass.setBounds(40, y + 20, 360, 38);
        card.add(lblP); card.add(txtPass);
        y += 75;

        JLabel lblC = new JLabel("Confirm Password");
        lblC.setBounds(40, y, 360, 18);
        lblC.setFont(new Font("Arial", Font.PLAIN, 12));
        lblC.setForeground(new Color(140, 170, 220));
        styleField(txtConfirmPass = new JPasswordField());
        txtConfirmPass.setBounds(40, y + 20, 360, 38);
        card.add(lblC); card.add(txtConfirmPass);
        y += 75;

        JButton btnCreate = new JButton("Create Account");
        btnCreate.setBounds(40, y, 360, 44);
        btnCreate.setBackground(new Color(0, 160, 80));
        btnCreate.setForeground(Color.WHITE);
        btnCreate.setFont(new Font("Arial", Font.BOLD, 14));
        btnCreate.setFocusPainted(false);
        btnCreate.setBorderPainted(false);
        btnCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCreate.addActionListener(this);
        card.add(btnCreate);
        y += 55;

        JButton btnBack = new JButton("← Back to Login");
        btnBack.setBounds(40, y, 360, 36);
        btnBack.setBackground(new Color(50, 65, 110));
        btnBack.setForeground(new Color(140, 170, 220));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> { dispose(); new LoginFrame(); });
        card.add(btnBack);
        y += 46;

        lblStatus = new JLabel("", SwingConstants.CENTER);
        lblStatus.setBounds(0, y, 440, 22);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        card.add(lblStatus);

        bg.add(card);
        add(bg);
        setVisible(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String name    = txtName.getText().trim();
        String deposit = txtDeposit.getText().trim();
        String pass    = new String(txtPass.getPassword());
        String confirm = new String(txtConfirmPass.getPassword());

        if (name.isEmpty() || deposit.isEmpty() || pass.isEmpty()) {
            showStatus("Please fill all fields.", true); return;
        }
        if (!pass.equals(confirm)) {
            showStatus("Passwords do not match!", true); return;
        }
        if (pass.length() < 6) {
            showStatus("Password must be at least 6 characters.", true); return;
        }

        double dep;
        try {
            dep = Double.parseDouble(deposit);
            if (dep < 500) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showStatus("Minimum initial deposit is Rs. 500.", true); return;
        }

        String accNo = Database.createAccount(name, dep, pass);
        showStatus("Account created! Your Account No: " + accNo, false);

        Timer t = new Timer(2500, ev -> { dispose(); new LoginFrame(); });
        t.setRepeats(false); t.start();
    }

    private void showStatus(String msg, boolean err) {
        lblStatus.setForeground(err ? new Color(255, 80, 80) : new Color(0, 220, 120));
        lblStatus.setText(msg);
    }
}
