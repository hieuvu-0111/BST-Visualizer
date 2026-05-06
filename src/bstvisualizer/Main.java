package bstvisualizer;

import bstvisualizer.view.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Entry point. Launches the Swing GUI on the Event Dispatch Thread (EDT).
 * Never create Swing components outside the EDT.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}