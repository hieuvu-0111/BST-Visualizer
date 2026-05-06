package bstvisualizer.view;

import bstvisualizer.controller.BSTController;
import bstvisualizer.model.BSTree;
import bstvisualizer.util.RandomTreeUtil;
import java.awt.*;
import javax.swing.*;

// The west bar 

// Area for user to input node value 
public class InputPanel extends JPanel {

    private final BSTree tree;
    private final TreeCanvas canvas;
    private final BSTController controller;
    private final JTextArea dataArea;

    public InputPanel(BSTree tree, TreeCanvas canvas, BSTController controller) {
        this.tree       = tree;
        this.canvas     = canvas;
        this.controller = controller;

        setLayout(new BorderLayout(6, 6));
        setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

        // Label 
        JLabel lbl = new JLabel("Data (space-separated):");
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 12f));

        // Text area 
        dataArea = new JTextArea(5, 16);
        dataArea.setLineWrap(true);
        dataArea.setWrapStyleWord(false);
        dataArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        dataArea.setToolTipText("Enter integers separated by spaces, e.g.: 50 30 70 20 40");
        JScrollPane scroll = new JScrollPane(dataArea);

        // Buttons 
        // Visualize button 
        JButton visualizeBtn = new JButton("Visualize");
        visualizeBtn.setToolTipText("Build tree from the values above");
        visualizeBtn.addActionListener(e -> onVisualize());

        // Random button 
        JButton randomBtn = new JButton("Random");
        randomBtn.setToolTipText("Fill with a random tree and visualize");
        randomBtn.addActionListener(e -> onRandom());

        JPanel btnRow = new JPanel(new GridLayout(1, 2, 6, 0));
        btnRow.add(visualizeBtn);
        btnRow.add(randomBtn);

        // Layout 
        JPanel top = new JPanel(new BorderLayout(0, 4));
        top.add(lbl,    BorderLayout.NORTH);
        top.add(scroll, BorderLayout.CENTER);
        top.add(btnRow, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
    }

    // Actions 

    private void onVisualize() {
        String text = dataArea.getText().trim();
        if (text.isEmpty()) return;

        String[] tokens = text.split("\\s+");
        tree.clear();
        for (String t : tokens) {
            try {
                tree.insert(Integer.parseInt(t));
            } catch (NumberFormatException ignored) {
                // skip non-integer tokens silently
            }
        }
        // Reset all highlights and repaint
        controller.resetAllHighlights();
        canvas.layoutAndRepaint();
    }

    // action for generating random tree 
    private void onRandom() {
        int[] values = RandomTreeUtil.generateRandom(10, 1, 99);
        StringBuilder sb = new StringBuilder();
        for (int v : values) sb.append(v).append(" ");
        dataArea.setText(sb.toString().trim());
        onVisualize();
    }
}