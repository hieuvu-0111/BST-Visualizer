package bstvisualizer.view;

import bstvisualizer.controller.BSTController;
import bstvisualizer.model.BSTree;
import java.awt.*;
import javax.swing.*;

// Main frame (model, canvas and controller) for the entire window
// Each component is handled in seperate file 

public class MainFrame extends JFrame {

    private final BSTree tree;
    private final TreeCanvas canvas;
    private final JLabel statusBar;
    private final BSTController controller;

    public MainFrame() {
        super("Binary Search Tree Visualizer");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);

        
        tree = new BSTree();
        // Visualizing space (center)
        canvas = new TreeCanvas(tree);

        // Status bar (south) (one-line messages from AnimationEngine)
        statusBar = new JLabel(" Ready.", SwingConstants.LEFT);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        statusBar.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Controller (wires model -> canvas -> status) 
        controller = new BSTController(tree, canvas, statusBar);

        //  ControlPanel north (Insert/Search/Traverse/Delete buttons + dropdowns)
        ControlPanel controlPanel = new ControlPanel(controller);
        InputPanel inputPanel = new InputPanel(tree, canvas, controller);

        // Legend (bottom-left inside west panel)
        LegendPanel legend = new LegendPanel();
        
        // Input panel west (data field, Visualize button, Random button)
        JPanel west = new JPanel(new BorderLayout());
        west.setPreferredSize(new Dimension(200, 0));
        west.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        west.add(inputPanel, BorderLayout.NORTH);
        west.add(legend, BorderLayout.SOUTH);

        // Assemble 
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(west, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }
}