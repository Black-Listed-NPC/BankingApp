package com.bank.ui;

import com.bank.db.Database;
import com.bank.model.SavingsAccount;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

// WAP: Admin Dashboard - view all customer accounts
public class AdminFrame extends JFrame {

    private DefaultTableModel model;

    public AdminFrame() {
        setTitle("SecureBank - Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel bg = new JPanel(new BorderLayout());
        bg.setBackground(new Color(15, 30, 70));

        // ── Header ──
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 90));
        header.setBackground(new Color(15, 28, 65));

        JLabel title = new JLabel("🛡️  Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        title.setForeground(new Color(0, 180, 255));

        JLabel sub = new JLabel("All Customer Accounts", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(140, 170, 220));

        JPanel hInner = new JPanel(new GridLayout(2, 1, 0, 4));
        hInner.setOpaque(false);
        hInner.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        hInner.add(title);
        hInner.add(sub);
        header.add(hInner);

        // ── Table ──
        String[] cols = {"Account No", "Holder Name", "Account Type", "Balance (Rs.)", "Interest Rate"};
        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        loadAccounts();

        JTable table = new JTable(model);
        table.setBackground(new Color(25, 42, 90));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 70, 120));
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.getTableHeader().setBackground(new Color(15, 28, 65));
        table.getTableHeader().setForeground(new Color(0, 180, 255));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setSelectionBackground(new Color(0, 100, 200));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBackground(sel ? new Color(0, 100, 200) : (row % 2 == 0 ? new Color(25, 42, 90) : new Color(30, 50, 100)));
                setForeground(col == 3 ? new Color(0, 220, 120) : Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scroll.getViewport().setBackground(new Color(25, 42, 90));

        // ── Bottom bar ──
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottom.setBackground(new Color(15, 28, 65));
        bottom.setPreferredSize(new Dimension(0, 60));

        JButton btnRefresh = makeButton("Refresh", new Color(0, 160, 80));
        btnRefresh.addActionListener(e -> loadAccounts());

        JButton btnLogout = makeButton("Logout", new Color(0, 120, 255));
        btnLogout.addActionListener(e -> { dispose(); new LoginFrame(); });

        bottom.add(btnRefresh);
        bottom.add(btnLogout);

        bg.add(header, BorderLayout.NORTH);
        bg.add(scroll, BorderLayout.CENTER);
        bg.add(bottom, BorderLayout.SOUTH);

        add(bg);
        setVisible(true);
    }

    private void loadAccounts() {
        model.setRowCount(0);
        List<SavingsAccount> accounts = Database.getAllAccounts();
        for (SavingsAccount acc : accounts) {
            model.addRow(new Object[]{
                acc.getAccountNumber(),
                acc.getHolderName(),
                acc.getAccountType(),
                String.format("%.2f", acc.getBalance()),
                acc.getInterestRate() + "%"
            });
        }
    }

    private JButton makeButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
