# BST-Visualizer
Binary Search Tree visualizer with methods including traversal, searching, insertion and deletion

## Example Flow for Insertion:
```
User types "42", clicks Enter
        │
        ▼
ControlPanel.insertBtn listener
        │  calls
        ▼
BSTController.requestInsert(42)
        │  checks busy flag, clears highlights, calls
        ▼
InsertController.execute(42, onComplete)
        │
        ├─ Phase 1: buildPathSteps(42)
        │   walks tree, returns List<AnimationStep> (path frames)
        │
        ├─ engine.play(pathFrames, insertAndFinish)
        │   Timer fires every 600ms:
        │     canvas.setNodeState(key, PATH) → repaint → next step
        │
        └─ when path frames done → insertAndFinish() runs:
                tree.insert(42)           ← model mutated here
                canvas.layoutAndRepaint() ← new node gets (x,y)
                engine.play(finalFrame, onComplete)
                  finalFrame: path=yellow, new node=green
                when done → BSTController.releaseLock()
```
