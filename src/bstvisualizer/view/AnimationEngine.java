package bstvisualizer.view;

import bstvisualizer.model.AnimationStep;
import bstvisualizer.model.BSTNode;
import java.util.List;
import javax.swing.*;

// This drives a step-by-step animation over a List<AnimationStep>
// Uses a Swing Timer so every frame update happens on the EDT

/* Each tick:
 *   1. Applies the current AnimationStep's visual state to the canvas nodes.
 *   2. Updates the status bar label.
 *   3. Repaints the canvas.
 *   4. Advances to the next step.
 *
 * After the last step the timer stops and onComplete.run() is called
 * (the controller uses this to do any post-animation cleanup).
 */
public class AnimationEngine {

    private static final int DEFAULT_DELAY_MS = 600; // ms between frames

    private final TreeCanvas canvas;
    private final JLabel statusBar;

    public AnimationEngine(TreeCanvas canvas, JLabel statusBar) {
        this.canvas = canvas;
        this.statusBar = statusBar;
    }

    /**
     * Start animating the given steps.
     *
     * @param steps      ordered list of frames to play
     * @param onComplete called on the EDT after the last frame
     */
    public void play(List<AnimationStep> steps, Runnable onComplete) {
        if (steps == null || steps.isEmpty()) {
            if (onComplete != null) onComplete.run();
            return;
        }

        int[] index = {0}; // mutable counter captured in lambda

        Timer timer = new Timer(DEFAULT_DELAY_MS, null);
        timer.addActionListener(e -> {
            if (index[0] >= steps.size()) {
                timer.stop();
                if (onComplete != null) onComplete.run();
                return;
            }

            AnimationStep step = steps.get(index[0]);
            applyStep(step);
            index[0]++;
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    // Private

    private void applyStep(AnimationStep step) {
        // Mark PATH nodes
        for (int key : step.pathKeys) {
            canvas.setNodeState(key, BSTNode.VisualState.PATH);
        }

        // Mark TARGET / special nodes based on step type
        for (int key : step.targetKeys) {
            BSTNode.VisualState s = switch (step.type) {
                case NODE_INSERTED, NODE_FOUND, NODE_DELETED -> BSTNode.VisualState.TARGET;
                case SUCCESSOR_FOUND   -> BSTNode.VisualState.SUCCESSOR;
                case PREDECESSOR_FOUND -> BSTNode.VisualState.PREDECESSOR;
                default                -> BSTNode.VisualState.PATH;
            };
            canvas.setNodeState(key, s);
        }

        statusBar.setText("  " + step.message);
        canvas.repaint();
    }
}