package bstvisualizer.controller;

import bstvisualizer.model.AnimationStep;
import bstvisualizer.model.BSTree;
import bstvisualizer.view.AnimationEngine;
import bstvisualizer.view.TreeCanvas;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles SEARCH.
 *
 * TODO (teammate): implement buildSteps() to produce one AnimationStep
 * per node visited during search.  On success the final step should use
 * StepType.NODE_FOUND with the found key in targetKeys.
 * On failure use StepType.NODE_NOT_FOUND with an empty targetKeys.
 */
public class SearchController {

    private final BSTree          tree;
    private final TreeCanvas      canvas;
    private final AnimationEngine engine;

    public SearchController(BSTree tree, TreeCanvas canvas, AnimationEngine engine) {
        this.tree   = tree;
        this.canvas = canvas;
        this.engine = engine;
    }

    public void execute(int key, Runnable onComplete) {
        // TODO: replace stub with real implementation
        List<AnimationStep> steps = new ArrayList<>();
        steps.add(AnimationStep.path(-1, "Search not yet implemented."));
        engine.play(steps, onComplete);
    }
}