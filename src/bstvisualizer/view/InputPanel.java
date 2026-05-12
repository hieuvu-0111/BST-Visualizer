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

        // Tạo panel chứa phần nhập liệu và các nút Visualize/Random ở phía trên
        JPanel inputArea = new JPanel(new BorderLayout(0, 4));
        inputArea.add(lbl,      BorderLayout.NORTH);
        inputArea.add(scroll,   BorderLayout.CENTER);
        inputArea.add(btnRow,    BorderLayout.SOUTH);

        // Tạo panel chứa các nút reset ở phía dưới (ngay trên Legend)
        // Đổi thành GridLayout(2, 1) để mỗi nút nằm trên 1 dòng riêng biệt
        JPanel resetBtnRow = new JPanel(new GridLayout(2, 1, 0, 6));
        JButton resetHighlightsBtn = new JButton("Reset Path");
        JButton clearTreeBtn = new JButton("Clear All");

        clearTreeBtn.setForeground(Color.RED); 

        resetHighlightsBtn.addActionListener(e -> controller.resetAllHighlights());

        clearTreeBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Xóa toàn bộ cây và làm trống trang?", 
                "Xác nhận Clear", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.requestClearAll();
            }
        });

        resetBtnRow.add(resetHighlightsBtn);
        resetBtnRow.add(clearTreeBtn);

        // Sắp xếp các thành phần chính vào InputPanel
        add(inputArea, BorderLayout.NORTH);
        add(resetBtnRow, BorderLayout.SOUTH);
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