package bstvisualizer.model;

import java.util.ArrayList;
import java.util.List;

// DEFINE BINARY TREE DATA STRUCTURE 

public class BSTree {

    private BSTNode root;

    // The node keys visited (in order) during the most recent operation
    // Controllers read this to build their AnimationStep lists
    private final List<Integer> lastPath = new ArrayList<>();

    // Accessors 
    public BSTNode getRoot() { return root; }

    // Returns a copy of the path recorded by the last operation
    public List<Integer> getLastPath() { return new ArrayList<>(lastPath); }

    // Insert 
    
    public boolean insert(int key) {
        lastPath.clear();
        if (root == null) {
            root = new BSTNode(key);
            lastPath.add(key); // Records the traversal path in lastPath
            return true; // sucessfully inserted
        }
        return insertRec(root, key);
    }

    private boolean insertRec(BSTNode node, int key) {
        lastPath.add(node.key);
        if (key == node.key) return false; // duplicated key

        if (key < node.key) { // move to the left child
            if (node.left == null) {
                node.left = new BSTNode(key);
                lastPath.add(key); // Records the traversal path in lastPath
                return true;
            }
            return insertRec(node.left, key);
        } else {                // move to the right child
            if (node.right == null) {
                node.right = new BSTNode(key);
                lastPath.add(key);
                return true;
            }
            return insertRec(node.right, key);
        }
    }

    // Search logic

    // Delete logic 

    // Traversal logic

    // Utilities
    // Clear all tree 
    public void clear() { root = null; lastPath.clear(); }

    private BSTNode minNode(BSTNode n) { while (n.left != null) n = n.left; return n; }
    private BSTNode maxNode(BSTNode n) { while (n.right != null) n = n.right; return n; }
}