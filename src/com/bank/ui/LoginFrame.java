package com.bank.ui;

import com.bank.db.Database;
import com.bank.model.SavingsAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame implements ActionListener, KeyListener, MouseListener {

    private JTextField     txtAccNo;
    private JPasswordField txtPassword;
    private JButton        btnLogin, btnRegister;
    private JLabel         lblStatus;

    public LoginFrame() {
        setTitle("Online Banking Portal - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(15, 30, 70));

        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(440, 520));
        card.setBackground(new Color(25, 42, 90));
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 255, 80), 1));

        // ── Logo / Header ──
        JPanel logoPanel = new JPanel(null);
        logoPanel.setBounds(0, 0, 440, 110);
        logoPanel.setBackground(new Color(15, 28, 65));

        JLabel logoIcon = new JLabel("🏦", SwingConstants.CENTER);
        logoIcon.setBounds(0, 10, 440, 40);
        logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        JLabel appName = new JLabel("SecureBank", SwingConstants.CENTER);
        appName.setBounds(0, 52, 440, 30);
        appName.setFont(new Font("Arial", Font.BOLD, 22));
        appName.setForeground(new Color(0, 180, 255));

        JLabel tagline = new JLabel("Online Banking Portal", SwingConstants.CENTER);
        tagline.setBounds(0, 82, 440, 20);
        tagline.setFont(new Font("Arial", Font.PLAIN, 12));
        tagline.setForeground(new Color(140, 170, 220));

        logoPanel.add(logoIcon); logoPanel.add(appName); logoPanel.add(tagline);

        // ── Form ──
        JLabel lblAccTitle = new JLabel("Account Number");
        lblAccTitle.setBounds(40, 130, 200, 18);
        lblAccTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblAccTitle.setForeground(new Color(140, 170, 220));

        txtAccNo = new JTextField();
        txtAccNo.setBounds(40, 150, 360, 38);
        txtAccNo.setBackground(new Color(35, 55, 110));
        txtAccNo.setForeground(Color.WHITE);
        txtAccNo.setCaretColor(Color.WHITE);
        txtAccNo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtAccNo.setFont(new Font("Arial", Font.PLAIN, 14));
        txtAccNo.addKeyListener(this);

        JLabel lblPassTitle = new JLabel("Password");
        lblPassTitle.setBounds(40, 205, 200, 18);
        lblPassTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPassTitle.setForeground(new Color(140, 170, 220));

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 225, 360, 38);
        txtPassword.setBackground(new Color(35, 55, 110));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.addKeyListener(this);

        btnLogin = makeButton("Login", new Color(0, 120, 255));
        btnLogin.setBounds(40, 290, 360, 44);
        btnLogin.addActionListener(this);
        btnLogin.addMouseListener(this);

        JLabel lblOr = new JLabel("─────── or ───────", SwingConstants.CENTER);
        lblOr.setBounds(40, 345, 360, 20);
        lblOr.setForeground(new Color(100, 130, 180));
        lblOr.setFont(new Font("Arial", Font.PLAIN, 12));

        btnRegister = makeButton("Create New Account", new Color(0, 160, 80));
        btnRegister.setBounds(40, 372, 360, 40);
        btnRegister.addActionListener(this);
        btnRegister.addMouseListener(this);

        lblStatus = new JLabel("", SwingConstants.CENTER);
        lblStatus.setBounds(0, 425, 440, 22);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel hint = new JLabel("Demo: ACC001 / rahul123   |   Admin: ADMIN / admin123", SwingConstants.CENTER);
        hint.setBounds(0, 455, 440, 18);
        hint.setForeground(new Color(100, 130, 180));
        hint.setFont(new Font("Arial", Font.ITALIC, 11));

        JLabel copy = new JLabel("© 2025 SecureBank. All rights reserved.", SwingConstants.CENTER);
        copy.setBounds(0, 490, 440, 18);
        copy.setForeground(new Color(80, 110, 160));
        copy.setFont(new Font("Arial", Font.PLAIN, 10));

        card.add(logoPanel);
        card.add(lblAccTitle); card.add(txtAccNo);
        card.add(lblPassTitle); card.add(txtPassword);
        card.add(btnLogin);
        card.add(lblOr);
        card.add(btnRegister);
        card.add(lblStatus);
        card.add(hint);
        card.add(copy);

        bg.add(card);
        add(bg);
        setVisible(true);
    }

    private JButton makeButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin)    doLogin();
        if (e.getSource() == btnRegister) { dispose(); new RegisterFrame(); }
    }

    private void doLogin() {
        String accNo = txtAccNo.getText().trim().toUpperCase();
        String pass  = new String(txtPassword.getPassword());

        if (accNo.isEmpty() || pass.isEmpty()) {
            showStatus("Please fill in all fields.", true); return;
        }

        // Admin login check
        if (accNo.equalsIgnoreCase("ADMIN") && pass.equals("admin123")) {
            showStatus("Admin login successful! Loading...", false);
            Timer t = new Timer(700, ev -> { dispose(); new AdminFrame(); });
            t.setRepeats(false); t.start();
            return;
        }

        SavingsAccount acc = Database.getAccount(accNo);
        if (acc == null) {
            showStatus("Account not found!", true);
        } else if (!acc.getPassword().equals(pass)) {
            showStatus("Incorrect password!", true);
        } else {
            showStatus("Login successful! Loading...", false);
            Timer t = new Timer(700, ev -> { dispose(); new DashboardFrame(acc); });
            t.setRepeats(false); t.start();
        }
    }

    private void showStatus(String msg, boolean err) {
        lblStatus.setForeground(err ? new Color(255, 80, 80) : new Color(0, 220, 120));
        lblStatus.setText(msg);
    }

    @Override public void keyPressed(KeyEvent e)  { if (e.getKeyCode() == KeyEvent.VK_ENTER) doLogin(); }
    @Override public void keyTyped(KeyEvent e)    {}
    @Override public void keyReleased(KeyEvent e) {}

    @Override public void mouseEntered(MouseEvent e) { ((JButton)e.getSource()).setOpaque(true); ((JButton)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); }
    @Override public void mouseExited(MouseEvent e)  { ((JButton)e.getSource()).setBorderPainted(false); }
    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
}
