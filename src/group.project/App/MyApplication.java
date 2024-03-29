import javax.swing.*;
import java.awt.*;

public class MyApplication {
    private JFrame frame;
    private JPanel mainPanel;

    private JPanel updatePanel;

    private JPanel insertPanel;

    public MyApplication() {
        // Initialize the frame
        frame = new JFrame("My Application");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        // DEMO UPDATE - REMAKE THIS
//        JButton button = new JButton("+ Add Item");
//        UpdatePanel up = new UpdatePanel();
//        updatePanel = up.getUpdatePanel();
//        button.addActionListener(e -> switchScreen(updatePanel));

        JButton insertButton = new JButton("Insert Player");
        InsertPanel ip = new InsertPanel();
        insertPanel = ip.getInsertPanel();
        insertButton.addActionListener(e -> switchScreen(insertPanel));

        // Add all Buttons
//        mainPanel.add(button); // replace this
        mainPanel.add(insertButton);

        // Finish
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void switchScreen(JPanel panel) {
        // Remove main panel from frame
        frame.remove(mainPanel);

        // Add second panel to frame
        frame.add(panel);

        // Repaint frame
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        // Create instance of MyApplication
        SwingUtilities.invokeLater(MyApplication::new);
    }
}