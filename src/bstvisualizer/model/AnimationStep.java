package bstvisualizer.model;

import java.util.ArrayList;
import java.util.List;

// Animation frame: which node is colored by which color

public class AnimationStep {

    public enum StepType {
        TRAVERSE_PATH,   // highlight a node on the search/insert path
        NODE_INSERTED,   // highlight the newly inserted node
        NODE_FOUND,      // highlight a found node
        NODE_NOT_FOUND,  // signal failed search
        NODE_DELETED,    // highlight the node about to be removed
        SUCCESSOR_FOUND, // highlight successor
        PREDECESSOR_FOUND// highlight predecessor
    }

    public final StepType type;
    public final List<Integer> pathKeys;   // keys highlighted as PATH
    public final List<Integer> targetKeys; // keys highlighted as TARGET/SUCCESSOR/PREDECESSOR
    public final String message;

    public AnimationStep(StepType type,
                         List<Integer> pathKeys,
                         List<Integer> targetKeys,
                         String message) {
        this.type = type;
        this.pathKeys = new ArrayList<>(pathKeys);
        this.targetKeys = new ArrayList<>(targetKeys);
        this.message = message;
    }

    // Shorthand for a step that only highlights one path node
    public static AnimationStep path(int pathKey, String msg) {
        return new AnimationStep(StepType.TRAVERSE_PATH,
                List.of(pathKey), List.of(), msg);
    }

    // Shorthand for the insertion completion step
    public static AnimationStep inserted(List<Integer> path, int newKey) {
        return new AnimationStep(StepType.NODE_INSERTED,
                path, List.of(newKey),
                "Inserted " + newKey + " — done!");
    }
}