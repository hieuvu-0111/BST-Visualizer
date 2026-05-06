package bstvisualizer.controller;

import bstvisualizer.model.AnimationStep;
import bstvisualizer.model.BSTree;
import bstvisualizer.view.AnimationEngine;
import bstvisualizer.view.TreeCanvas;
import java.util.ArrayList;
import java.util.List;

// Handle the insert operation entirely 
/**
 * Algorithm visualisation steps:
 *   Step 1  – highlight root (comparison start)
 *   Step 2…n – highlight each node on the search path down to the
 *              insertion point, with a message showing which direction
 *              the algorithm turned (left / right)
 *   Step n+1 – tree is mutated (node added to model), canvas is
 *              re-laid out, and the new node is highlighted green (TARGET)
 *              while the full path remains yellow (PATH)
 *
 * Why split "show path" and "insert" into separate frames?
 * The viewer needs to see the algorithm navigate the tree BEFORE the
 * new node appears, matching the mental model of BST insertion.
 *
 * Threading:
 *   execute() is called on the EDT.
 *   All work (including tree mutation) happens inside AnimationEngine's
 *   Swing Timer callbacks – always on the EDT.  No threads needed.
 */
public class InsertController {

    private final BSTree          tree;
    private final TreeCanvas      canvas;
    private final AnimationEngine engine;

    public InsertController(BSTree tree, TreeCanvas canvas, AnimationEngine engine) {
        this.tree   = tree;
        this.canvas = canvas;
        this.engine = engine;
    }

    // Entry point called by BSTController
    
    public void execute(int key, Runnable onComplete) {

        // Phase 1: compute the search path without mutating the tree
        List<AnimationStep> steps = buildPathSteps(key);

        // Phase 2: mutate the tree after the path animation completes
        Runnable insertAndFinish = () -> {
            boolean inserted = tree.insert(key);
            List<Integer> fullPath = tree.getLastPath(); // includes new node

            if (!inserted) {
                // Duplicate key: show a "already exists" message, done.
                canvas.layoutAndRepaint();
                onComplete.run();
                return;
            }

            // Re-layout so the new node gets screen coordinates
            canvas.layoutAndRepaint();

            // Build final highlight frame: entire path stays yellow,
            // the new node flips to green (TARGET).
            List<Integer> pathWithoutNew = new ArrayList<>(fullPath);
            pathWithoutNew.remove(pathWithoutNew.size() - 1); // remove new node

            List<AnimationStep> finalStep = List.of(
                AnimationStep.inserted(pathWithoutNew, key)
            );

            engine.play(finalStep, onComplete);
        };

        // Play the path animation, then trigger insertAndFinish.
        engine.play(steps, insertAndFinish);
    }

    // Private helpers 

    /**
     * Walk the existing tree (without inserting) and produce one
     * AnimationStep per node visited.  The tree is NOT modified here.
     *
     * Each step highlights nodes visited so far as PATH and shows
     * a descriptive message in the status bar.
     */
    private List<AnimationStep> buildPathSteps(int key) {
        List<AnimationStep> steps = new ArrayList<>();

        if (tree.getRoot() == null) {
            // Empty tree – the new node will become root.
            steps.add(new AnimationStep(
                    AnimationStep.StepType.TRAVERSE_PATH,
                    List.of(), List.of(),
                    "Tree is empty – " + key + " will become the root."));
            return steps;
        }

        // Simulate the search path
        List<Integer> visited = new ArrayList<>();
        var current = tree.getRoot();

        while (current != null) {
            visited.add(current.key);

            String msg;
            if (key == current.key) {
                msg = key + " already exists – duplicate, not inserted.";
                steps.add(new AnimationStep(
                        AnimationStep.StepType.TRAVERSE_PATH,
                        new ArrayList<>(visited), List.of(), msg));
                return steps; // stop early
            } else if (key < current.key) {
                msg = key + " < " + current.key + " → go LEFT";
                steps.add(new AnimationStep(
                        AnimationStep.StepType.TRAVERSE_PATH,
                        new ArrayList<>(visited), List.of(), msg));
                if (current.left == null) break; // insertion point found
                current = current.left;
            } else {
                msg = key + " > " + current.key + " → go RIGHT";
                steps.add(new AnimationStep(
                        AnimationStep.StepType.TRAVERSE_PATH,
                        new ArrayList<>(visited), List.of(), msg));
                if (current.right == null) break; // insertion point found
                current = current.right;
            }
        }

        return steps;
    }
}