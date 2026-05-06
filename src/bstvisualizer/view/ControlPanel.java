package bstvisualizer.view;

import bstvisualizer.controller.BSTController;
import java.awt.*;
import javax.swing.*;

// The north bar 

public class ControlPanel extends JPanel {

    public ControlPanel(BSTController controller) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // 1. INSERT: value text field + Enter button
        add(boldLabel("Insert:"));
        JTextField insertField = numericField();
        add(insertField);
        JButton insertBtn = new JButton("Enter");
        insertBtn.addActionListener(e -> {
            String txt = insertField.getText().trim();
            if (!txt.isEmpty()) {
                try {
                    controller.requestInsert(Integer.parseInt(txt));
                    insertField.setText("");
                } catch (NumberFormatException ex) {
                    showError("Insert value must be an integer.");
                }
            }
        });
        // Also trigger on Enter key
        insertField.addActionListener(e -> insertBtn.doClick());
        add(insertBtn);

        add(separator());

        // 2. SEARCH: value text field + Enter button
        add(boldLabel("Search:"));
        JTextField searchField = numericField();
        add(searchField);
        JButton searchBtn = new JButton("Enter");
        searchBtn.addActionListener(e -> {
            String txt = searchField.getText().trim();
            if (!txt.isEmpty()) {
                try {
                    controller.requestSearch(Integer.parseInt(txt));
                    searchField.setText("");
                } catch (NumberFormatException ex) {
                    showError("Search value must be an integer.");
                }
            }
        });
        searchField.addActionListener(e -> searchBtn.doClick());
        add(searchBtn);

        add(separator());

        // 3. TRAVERSE: BFS/Preorder/Inorder/Postorder) + Go button
        add(boldLabel("Traverse:"));
        String[] traverseModes = {"BFS (level-order)", "Preorder (DFS)", "Inorder (DFS)", "Postorder (DFS)"};
        JComboBox<String> traverseCombo = new JComboBox<>(traverseModes);
        traverseCombo.setPreferredSize(new Dimension(160, 26));
        add(traverseCombo);
        JButton traverseBtn = new JButton("Go");
        traverseBtn.addActionListener(e ->
                controller.requestTraverse((String) traverseCombo.getSelectedItem()));
        add(traverseBtn);

        add(separator());

        // 4. DELETE: value text field + replacement choice + Enter button
        add(boldLabel("Delete:"));
        JTextField deleteField = numericField();
        add(deleteField);
        String[] replaceStrats = {"By successor", "By predecessor"};
        JComboBox<String> deleteCombo = new JComboBox<>(replaceStrats);
        deleteCombo.setPreferredSize(new Dimension(130, 26));
        add(deleteCombo);
        JButton deleteBtn = new JButton("Enter");
        deleteBtn.addActionListener(e -> {
            String txt = deleteField.getText().trim();
            if (!txt.isEmpty()) {
                try {
                    boolean useSucc = deleteCombo.getSelectedIndex() == 0;
                    controller.requestDelete(Integer.parseInt(txt), useSucc);
                    deleteField.setText("");
                } catch (NumberFormatException ex) {
                    showError("Delete value must be an integer.");
                }
            }
        });
        deleteField.addActionListener(e -> deleteBtn.doClick());
        add(deleteCombo);
        add(deleteBtn);
    }

    // Helpers

    private JLabel boldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        return l;
    }

    private JTextField numericField() {
        JTextField f = new JTextField(5);
        f.setHorizontalAlignment(JTextField.CENTER);
        return f;
    }

    private JSeparator separator() {
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 30));
        return sep;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Input Error",
                JOptionPane.WARNING_MESSAGE);
    }
}