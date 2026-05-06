package bstvisualizer.model;

// Wrapper used by traverse controller 
// to store single visited key and the traversal label
// shown in the status bar 

public class TraversalStep {
    public final int key;
    public final String label;

    public TraversalStep(int key, String label) {
        this.key = key;
        this.label = label;
    }
}