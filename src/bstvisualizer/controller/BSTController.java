package bstvisualizer.controller;

import bstvisualizer.model.BSTree;
import bstvisualizer.view.AnimationEngine;
import bstvisualizer.view.TreeCanvas;

import javax.swing.*;

// The central controller/router 
// It holds one instance of each controller and routes request from GUI to them
// It owns the AnimationEngine so that 
// only one animation can run at a time (calling a second operation while
// one is in progress is blocked via the `busy` flag).
// This is the object that InputPanel, ControlPanel, and MainFrame
// hold a reference to

public class BSTController {

    private final BSTree tree;
    private final TreeCanvas canvas;
    private final JLabel statusBar;
    private final AnimationEngine engine;

    // Operation-specific controllers
    private final InsertController    insertCtrl;
    private final SearchController    searchCtrl;
    private final DeleteController    deleteCtrl;
    private final TraverseController  traverseCtrl;

    // True while an animation is running
    private boolean busy = false;

    public BSTController(BSTree tree, TreeCanvas canvas, JLabel statusBar) {
        this.tree      = tree;
        this.canvas    = canvas;
        this.statusBar = statusBar;
        this.engine    = new AnimationEngine(canvas, statusBar);

        insertCtrl   = new InsertController(tree, canvas, engine);
        searchCtrl   = new SearchController(tree, canvas, engine);
        deleteCtrl   = new DeleteController(tree, canvas, engine);
        traverseCtrl = new TraverseController(tree, canvas, engine);
    }

    // Public API (called by GUI panels)

    public void requestInsert(int key) {
        if (!acquireLock()) return;
        resetAllHighlights();
        insertCtrl.execute(key, this::releaseLock);
    }

    public void requestSearch(int key) {
        if (!acquireLock()) return;
        resetAllHighlights();
        searchCtrl.execute(key, this::releaseLock);
    }

    public void requestDelete(int key, boolean useSuccessor) {
        if (!acquireLock()) return;
        resetAllHighlights();
        deleteCtrl.execute(key, useSuccessor, this::releaseLock);
    }

    public void requestTraverse(String mode) {
        if (!acquireLock()) return;
        resetAllHighlights();
        traverseCtrl.execute(mode, this::releaseLock);
    }

    // Clear all node highlights and repaint
    public void resetAllHighlights() {
        canvas.resetAllStates();
    }

    public void requestClearAll() {
        if (busy) return; 
            tree.clear();
            canvas.resetAllStates();
            canvas.layoutAndRepaint();
    }

    // Lock helpers

    private boolean acquireLock() {
        if (busy) {
            statusBar.setText("  Animation in progress – please wait.");
            return false;
        }
        busy = true;
        return true;
    }

    private void releaseLock() {
        busy = false;
    }
}