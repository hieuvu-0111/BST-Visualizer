
package bstvisualizer.controller;

import bstvisualizer.model.AnimationStep;
import bstvisualizer.model.BSTree;
import bstvisualizer.view.AnimationEngine;
import bstvisualizer.view.TreeCanvas;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles DELETE.
 *
 * TODO (teammate): implement execute().
 * Suggested frames:
 *   1. Show search path to the node being deleted (PATH, yellow).
 *   2. Highlight the node to delete (TARGET, green) with a pause.
 *   3. If replacing: highlight successor (SUCCESSOR, orange) or
 *      predecessor (PREDECESSOR, purple).
 *   4. Call tree.delete(key, useSuccessor), re-layout, repaint, done.
 */
public class DeleteController {

    private final BSTree          tree;
    private final TreeCanvas      canvas;
    private final AnimationEngine engine;

    public DeleteController(BSTree tree, TreeCanvas canvas, AnimationEngine engine) {
        this.tree   = tree;
        this.canvas = canvas;
        this.engine = engine;
    }

    public void execute(int key, boolean useSuccessor, Runnable onComplete) {
        // TODO: replace stub with real implementation
        List<AnimationStep> steps = new ArrayList<>();
        steps.add(AnimationStep.path(-1, "Delete not yet implemented."));
        engine.play(steps, onComplete);
    }
}