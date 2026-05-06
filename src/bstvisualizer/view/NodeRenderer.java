package bstvisualizer.view;

import bstvisualizer.model.BSTNode;
import java.awt.*;

// For aesthetic purpose, it paints one BST node (circle + key label)

public class NodeRenderer {

    private static final Color COLOR_NORMAL      = Color.WHITE;
    private static final Color COLOR_PATH        = new Color(0xFF, 0xF1, 0x76); // yellow
    private static final Color COLOR_TARGET      = new Color(0x66, 0xBB, 0x6A); // green
    private static final Color COLOR_SUCCESSOR   = new Color(0xFF, 0xA7, 0x26); // orange
    private static final Color COLOR_PREDECESSOR = new Color(0xCE, 0x93, 0xD8); // purple

    private static final Color BORDER_COLOR = new Color(70, 70, 90);
    private static final Color TEXT_COLOR   = new Color(20, 20, 30);

    private final int radius;

    public NodeRenderer(int radius) {
        this.radius = radius;
    }

    
    // Draw node centered at (node.x, node.y).
    
    public void draw(Graphics2D g2, BSTNode node) {
        int x = node.x - radius;
        int y = node.y - radius;
        int d = radius * 2;

        // Fill colour based on state
        g2.setColor(fillColor(node.state));
        g2.fillOval(x, y, d, d);

        // Border (thicker for highlighted nodes)
        float strokeWidth = (node.state == BSTNode.VisualState.NORMAL) ? 1.5f : 2.5f;
        g2.setStroke(new BasicStroke(strokeWidth));
        g2.setColor(BORDER_COLOR);
        g2.drawOval(x, y, d, d);

        // Key label
        g2.setColor(TEXT_COLOR);
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        String label = String.valueOf(node.key);
        int tx = node.x - fm.stringWidth(label) / 2;
        int ty = node.y + fm.getAscent() / 2 - 1;
        g2.drawString(label, tx, ty);
    }

    private Color fillColor(BSTNode.VisualState state) {
        return switch (state) {
            case PATH        -> COLOR_PATH;
            case TARGET      -> COLOR_TARGET;
            case SUCCESSOR   -> COLOR_SUCCESSOR;
            case PREDECESSOR -> COLOR_PREDECESSOR;
            default          -> COLOR_NORMAL;
        };
    }
}