package bstvisualizer.model;

// DEFINE A NODE IN A BST 

public class BSTNode {

    // Tree structure
    public int key;
    public BSTNode left;
    public BSTNode right;

    // Layout coords (set by TreeCanvas before each repaint) 
    public int x;   // pixel centee-x on the canvas
    public int y;   // pixel center-y on the canvas

    // Visual state (used by NodeRenderer/TreeCanvas to decide fill color)
    /* 
    *  NORMAL      – default, no highlight
    *  PATH        – node is on the current traversal / search path
    *  TARGET      – the node that was inserted / found / deleted
    *  SUCCESSOR   – successor node during delete-by-successor
    *  PREDECESSOR – predecessor node during delete-by-predecessor
    */
    public enum VisualState { NORMAL, PATH, TARGET, SUCCESSOR, PREDECESSOR }
    public VisualState state = VisualState.NORMAL;

    public BSTNode(int key) {
        this.key = key;
    }

    // Reset node's visual state to NORMAL
    public void resetState() {
        state = VisualState.NORMAL;
    }
}