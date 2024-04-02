package group.project.App;

import group.project.delegates.TransactionDelegate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DeletePanel {
    private TransactionDelegate delegate = null;
    private JTextField nidsField = null;
    private JTextField nnamesField = null;

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel thisPanel;
    public JPanel getDeletePanel(TransactionDelegate delegate, JFrame frame, JPanel mainPanel) {
        this.delegate = delegate;
        this.frame = frame;
        this.mainPanel = mainPanel;
        JPanel panel = new JPanel();
        this.thisPanel = panel;
        panel.setLayout(new GridLayout(0, 2));

        JLabel nidsLabel = new JLabel("NPC IDs to delete (separate with comma)");
        JLabel nnamesLabel = new JLabel("NPC names to delete (separate with comma)");

        JTextField nidsField = new JTextField(50);
        JTextField nnamesField = new JTextField(50);

        this.nidsField = nidsField;
        this.nnamesField = nnamesField;

        panel.add(nidsLabel);
        panel.add(nidsField);
        panel.add(nnamesLabel);
        panel.add(nnamesField);

        JButton doButton = new JButton("Delete NPCs");
        doButton.addActionListener(e -> doQuery(panel));
        panel.add(doButton);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> switchScreen(mainPanel, frame));
        panel.add(backButton);

        return panel;
    }

    private void doQuery(JPanel panel) {
        String stringNids = this.nidsField.getText();
        String stringNames = this.nnamesField.getText();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> nids = new ArrayList<Integer>();

        if(!stringNames.isEmpty()) {
            String[] tempNames = stringNames.split(",");
            names.addAll(Arrays.asList(tempNames));
        }

        try {
            if (!stringNids.isEmpty()) {
                String[] splitNids = stringNids.split(",");
                for (String splitNid : splitNids) {
                    nids.add(Integer.parseInt(splitNid));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(panel, "Only enter integers as NPC IDs.");
            return;
        }
        delegate.deleteNPC(nids, names);

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
