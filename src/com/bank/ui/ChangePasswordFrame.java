package com.bank.ui;

import com.bank.db.Database;
import com.bank.model.SavingsAccount;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// WAP: Change Password
public class ChangePasswordFrame extends JFrame implements ActionListener {

    private SavingsAccount account;
    private JPasswordField txtOld, txtNew, txtConfirm;
    private JLabel lblStatus;

    public ChangePasswordFrame(SavingsAccount account) {
        this.account = account;
        setTitle("SecureBank - Change Password");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(15, 30, 70));

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(420, 420));
        card.setBackground(new Color(25, 42, 90));
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 255, 80), 1));

        // Header
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 420, 75);
        header.setBackground(new Color(15, 28, 65));

        JLabel title = new JLabel("🔒 Change Password", SwingConstants.CENTER);
        title.setBounds(0, 12, 420, 28);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        title.setForeground(new Color(0, 180, 255));

        JLabel sub = new JLabel(account.getHolderName() + " | " + account.getAccountNumber(), SwingConstants.CENTER);
        sub.setBounds(0, 44, 420, 18);
        sub.setFont(new Font("Arial", Font.PLAIN, 12));
        sub.setForeground(new Color(140, 170, 220));

        header.add(title); header.add(sub);
        card.add(header);

        String[] lbls = {"Current Password", "New Password", "Confirm New Password"};
        txtOld     = new JPasswordField();
        txtNew     = new JPasswordField();
        txtConfirm = new JPasswordField();
        JPasswordField[] pfs = {txtOld, txtNew, txtConfirm};

        int y = 95;
        for (int i = 0; i < lbls.length; i++) {
            JLabel l = new JLabel(lbls[i]);
            l.setBounds(40, y, 340, 18);
            l.setFont(new Font("Arial", Font.PLAIN, 12));
            l.setForeground(new Color(140, 170, 220));
            styleField(pfs[i]);
            pfs[i].setBounds(40, y + 20, 340, 38);
            card.add(l); card.add(pfs[i]);
            y += 75;
        }

        JButton btnChange = new JButton("Update Password");
        btnChange.setBounds(40, y, 340, 44);
        btnChange.setBackground(new Color(0, 120, 255));
        btnChange.setForeground(Color.WHITE);
        btnChange.setFont(new Font("Arial", Font.BOLD, 14));
        btnChange.setFocusPainted(false);
        btnChange.setBorderPainted(false);
        btnChange.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnChange.addActionListener(this);
        card.add(btnChange);
        y += 55;

        JButton btnBack = new JButton("← Back to Dashboard");
        btnBack.setBounds(40, y, 340, 34);
        btnBack.setBackground(new Color(50, 65, 110));
        btnBack.setForeground(new Color(140, 170, 220));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> dispose());
        card.add(btnBack);
        y += 44;

        lblStatus = new JLabel("", SwingConstants.CENTER);
        lblStatus.setBounds(0, y, 420, 22);
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
        String oldPass = new String(txtOld.getPassword());
        String newPass = new String(txtNew.getPassword());
        String confirm = new String(txtConfirm.getPassword());

        if (!account.getPassword().equals(oldPass)) {
            showStatus("Current password is incorrect!", true); return;
        }
        if (newPass.length() < 6) {
            showStatus("New password must be at least 6 characters.", true); return;
        }
        if (!newPass.equals(confirm)) {
            showStatus("New passwords do not match!", true); return;
        }
        if (newPass.equals(oldPass)) {
            showStatus("New password must be different from old.", true); return;
        }

        account = new com.bank.model.SavingsAccount(
            account.getAccountNumber(), account.getHolderName(),
            account.getBalance(), newPass);
        Database.updateAccount(account);

        showStatus("Password updated successfully!", false);
        Timer t = new Timer(1500, ev -> dispose());
        t.setRepeats(false); t.start();
    }

    private void showStatus(String msg, boolean err) {
        lblStatus.setForeground(err ? new Color(255, 80, 80) : new Color(0, 220, 120));
        lblStatus.setText(msg);
    }
}
