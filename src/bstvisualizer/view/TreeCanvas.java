package bstvisualizer.view;

import bstvisualizer.model.BSTNode;
import bstvisualizer.model.BSTree;
import java.awt.*;
import javax.swing.*;

// The central drawing interface of the BST 

public class TreeCanvas extends JPanel {

    private static final int NODE_RADIUS = 22;
    private static final int LEVEL_HEIGHT = 70; // px between tree levels
    private static final int MARGIN = 40;

    private final BSTree tree;
    private final NodeRenderer renderer = new NodeRenderer(NODE_RADIUS);

    public TreeCanvas(BSTree tree) {
        this.tree = tree;
        setBackground(new Color(250, 250, 252));
    }

    // Recalculate node positions, then repaint. Call after any tree mutation
    public void layoutAndRepaint() {
        if (tree.getRoot() != null) {
            layoutNode(tree.getRoot(), MARGIN, getWidth() - MARGIN,
                       MARGIN + NODE_RADIUS);
        }
        repaint();
    }

    // Layout 

    /*
     * Recursively assign pixel (x, y) to each node.
     * Node sits at horizontal centre of [xMin, xMax].
     * Left child gets [xMin, x], right child gets [x, xMax].
     */
    private void layoutNode(BSTNode node, int xMin, int xMax, int y) {
        if (node == null) return;
        node.x = (xMin + xMax) / 2;
        node.y = y;
        layoutNode(node.left,  xMin, node.x, y + LEVEL_HEIGHT);
        layoutNode(node.right, node.x, xMax, y + LEVEL_HEIGHT);
    }

    // Painting 

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Re-layout on every paint so resize is handled automatically
        if (tree.getRoot() != null) {
            layoutNode(tree.getRoot(), MARGIN, getWidth() - MARGIN,
                       MARGIN + NODE_RADIUS);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw edges first (so they appear behind nodes)
        drawEdges(g2, tree.getRoot());

        // Draw nodes on top
        drawNodes(g2, tree.getRoot());
    }

    private void drawEdges(Graphics2D g2, BSTNode node) {
        if (node == null) return;
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(new Color(180, 180, 190));

        if (node.left != null) {
            g2.drawLine(node.x, node.y, node.left.x, node.left.y);
            drawEdges(g2, node.left);
        }
        if (node.right != null) {
            g2.drawLine(node.x, node.y, node.right.x, node.right.y);
            drawEdges(g2, node.right);
        }
    }

    private void drawNodes(Graphics2D g2, BSTNode node) {
        if (node == null) return;
        renderer.draw(g2, node);
        drawNodes(g2, node.left);
        drawNodes(g2, node.right);
    }

    // Public helpers

    // Set the visual state of a single node identified by key
    public void setNodeState(int key, BSTNode.VisualState state) {
        setNodeStateRec(tree.getRoot(), key, state);
    }

    private void setNodeStateRec(BSTNode node, int key, BSTNode.VisualState state) {
        if (node == null) return;
        if (node.key == key) { node.state = state; return; }
        setNodeStateRec(node.left,  key, state);
        setNodeStateRec(node.right, key, state);
    }

    // Reset every node's visual state to NORMAL
    public void resetAllStates() {
        resetStatesRec(tree.getRoot());
        repaint();
    }

    private void resetStatesRec(BSTNode node) {
        if (node == null) return;
        node.state = BSTNode.VisualState.NORMAL;
        resetStatesRec(node.left);
        resetStatesRec(node.right);
    }
}