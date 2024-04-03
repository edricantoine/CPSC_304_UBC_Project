package group.project.App;

import group.project.delegates.TransactionDelegate;
import group.project.model.AvgLevelModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NestedAggPanel {
    private TransactionDelegate delegate = null;
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;

    public JPanel getNestedAggPanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();

        JButton doButton = new JButton("Apply");
        doButton.addActionListener(e -> doQuery(panel));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(this.mainPanel, this.frame));

        panel.add(doButton);
        panel.add(backButton);

        this.thisPanel = panel;
        return panel;
    }

    private void doQuery(JPanel panel) {
        ArrayList<AvgLevelModel> alms = delegate.getAvgLevelInGuild();
        String[] headers = {"Guild Name", "Average Player Level"};
        DefaultTableModel model = new DefaultTableModel(headers, 0);

        JTable table = new JTable(model);

        for(AvgLevelModel alm : alms) {
            Object[] rowData = {alm.gname, alm.avgLevel};
            model.addRow(rowData);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, sp, "Quest Information", JOptionPane.PLAIN_MESSAGE);

    }

    private void switchScreen(JPanel panel, JFrame frame) {
        // Remove main panel from frame
        frame.remove(this.thisPanel);

        // Add second panel to frame
        frame.add(panel);

        // Repaint frame
        frame.revalidate();
        frame.repaint();
    }
}
