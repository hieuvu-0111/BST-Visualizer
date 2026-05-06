package bstvisualizer.controller;

import bstvisualizer.model.AnimationStep;
import bstvisualizer.model.BSTree;
import bstvisualizer.view.AnimationEngine;
import bstvisualizer.view.TreeCanvas;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles TRAVERSE.
 *
 * TODO (teammate): implement execute() for each traversal mode.
 * The mode string matches the ControlPanel combo items:
 *   "BFS (level-order)"  → tree.bfs()
 *   "Preorder (DFS)"     → tree.preorder()
 *   "Inorder (DFS)"      → tree.inorder()
 *   "Postorder (DFS)"    → tree.postorder()
 *
 * Each visited node should be one AnimationStep with StepType.TRAVERSE_PATH.
 */
public class TraverseController {

    private final BSTree          tree;
    private final TreeCanvas      canvas;
    private final AnimationEngine engine;

    public TraverseController(BSTree tree, TreeCanvas canvas, AnimationEngine engine) {
        this.tree   = tree;
        this.canvas = canvas;
        this.engine = engine;
    }

    public void execute(String mode, Runnable onComplete) {
        // TODO: replace stub with real implementation
        List<AnimationStep> steps = new ArrayList<>();
        steps.add(AnimationStep.path(-1, "Traverse not yet implemented: " + mode));
        engine.play(steps, onComplete);
    }
}
