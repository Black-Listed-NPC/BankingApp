package com.bank.ui;

import com.bank.db.Database;
import com.bank.model.SavingsAccount;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

// WAP: Mini Statement Screen
public class StatementFrame extends JFrame {

    public StatementFrame(SavingsAccount account) {
        setTitle("SecureBank - Mini Statement");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel bg = new JPanel(new BorderLayout());
        bg.setBackground(new Color(15, 30, 70));

        // ── Header ──
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(0, 90));
        header.setBackground(new Color(15, 28, 65));

        JLabel title = new JLabel("📋  Mini Statement", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        title.setForeground(new Color(0, 180, 255));

        JLabel sub = new JLabel(account.getHolderName() + "  |  " + account.getAccountNumber(), SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(new Color(140, 170, 220));

        JPanel hInner = new JPanel(new GridLayout(2,1,0,4));
        hInner.setOpaque(false);
        hInner.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        hInner.add(title); hInner.add(sub);
        header.setLayout(new BorderLayout());
        header.add(hInner);

        // ── Table ──
        String[] cols = {"Date & Time", "Account", "Type", "Amount (Rs.)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        List<String> txns = Database.getTransactions(account.getAccountNumber());
        if (txns.isEmpty()) {
            model.addRow(new Object[]{"No transactions yet", "", "", ""});
        } else {
            for (String line : txns) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    model.addRow(new Object[]{
                        parts[0].trim(), parts[1].trim(),
                        parts[2].trim(), parts[3].trim()
                    });
                }
            }
        }

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

        // Color rows for deposit/withdraw
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBackground(sel ? new Color(0,100,200) : (row%2==0 ? new Color(25,42,90) : new Color(30,50,100)));
                setForeground(Color.WHITE);
                setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
                // Color amount column
                if (col == 3 && v != null) {
                    String type = (String) t.getValueAt(row, 2);
                    if (type != null && type.contains("DEPOSIT"))  setForeground(new Color(0, 220, 120));
                    if (type != null && type.contains("WITHDRAW")) setForeground(new Color(255, 100, 100));
                    if (type != null && type.contains("TRANSFER")) setForeground(new Color(255, 200, 50));
                }
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBackground(new Color(15, 30, 70));
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scroll.getViewport().setBackground(new Color(25, 42, 90));

        // ── Bottom bar ──
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottom.setBackground(new Color(15, 28, 65));
        bottom.setPreferredSize(new Dimension(0, 60));

        JLabel balLabel = new JLabel("Current Balance:  Rs. " + String.format("%.2f", account.getBalance()));
        balLabel.setFont(new Font("Arial", Font.BOLD, 15));
        balLabel.setForeground(new Color(0, 220, 120));

        JButton btnClose = new JButton("Close");
        btnClose.setBackground(new Color(0, 120, 255));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.setBorderPainted(false);
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> dispose());

        bottom.add(balLabel); bottom.add(btnClose);

        bg.add(header, BorderLayout.NORTH);
        bg.add(scroll,  BorderLayout.CENTER);
        bg.add(bottom,  BorderLayout.SOUTH);

        add(bg);
        setVisible(true);
    }
}
