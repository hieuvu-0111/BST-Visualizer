package bstvisualizer.controller;

import bstvisualizer.model.AnimationStep;
import bstvisualizer.model.BSTNode;
import bstvisualizer.model.BSTree;
import bstvisualizer.view.AnimationEngine;
import bstvisualizer.view.TreeCanvas;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles SEARCH.
 *
 * Triển khai build các AnimationStep để mô phỏng quá trình tìm kiếm nút.
 * Khi thành công, bước cuối cùng sử dụng StepType.NODE_FOUND.
 * Khi thất bại, sử dụng StepType.NODE_NOT_FOUND.
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
        List<AnimationStep> steps = new ArrayList<>();
        List<Integer> visited = new ArrayList<>();
        
        BSTNode current = tree.getRoot();

        if (current == null) {
            steps.add(new AnimationStep(
                AnimationStep.StepType.NODE_NOT_FOUND, 
                List.of(), List.of(), 
                "Not found. The tree is empty. " + key));
        } else {
            while (current != null) {
                visited.add(current.key);
                
                if (key == current.key) {
                    steps.add(new AnimationStep(
                        AnimationStep.StepType.NODE_FOUND, 
                        new ArrayList<>(visited), 
                        List.of(key), 
                        "Founded " + key + "!"));
                    break;
                } else if (key < current.key) {
                    steps.add(new AnimationStep(
                        AnimationStep.StepType.TRAVERSE_PATH, 
                        new ArrayList<>(visited), 
                        List.of(), 
                        key + " < " + current.key + " → Move Left"));
                    current = current.left;
                } else {
 
                    steps.add(new AnimationStep(
                        AnimationStep.StepType.TRAVERSE_PATH, 
                        new ArrayList<>(visited), 
                        List.of(), 
                        key + " > " + current.key + " → Move Right"));
                    current = current.right;
                }

                if (current == null) {
                    steps.add(new AnimationStep(
                        AnimationStep.StepType.NODE_NOT_FOUND, 
                        new ArrayList<>(visited), 
                        List.of(), 
                        "Not Found " + key + " in Tree."));
                }
            }
        }

        engine.play(steps, onComplete);
    }
}