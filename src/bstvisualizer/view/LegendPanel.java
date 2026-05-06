package bstvisualizer.view;

import java.awt.*;
import javax.swing.*;


// Small color-key panel, called legend, at the bottom of the west sidebar
public class LegendPanel extends JPanel {

    private static final Color COLOR_PATH = new Color(0xFF, 0xF1, 0x76);
    private static final Color COLOR_TARGET = new Color(0x66, 0xBB, 0x6A);
    private static final Color COLOR_SUCCESSOR = new Color(0xFF, 0xA7, 0x26);
    private static final Color COLOR_PREDECESSOR = new Color(0xCE, 0x93, 0xD8);

    public LegendPanel() {
        setLayout(new GridLayout(5, 1, 0, 4));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 8, 12, 8)));

        add(header("Legend"));
        add(entry(COLOR_PATH, "Search path"));
        add(entry(COLOR_TARGET, "Inserted / Found"));
        add(entry(COLOR_SUCCESSOR, "Successor"));
        add(entry(COLOR_PREDECESSOR, "Predecessor"));
    }

    private JLabel header(String text) {
        JLabel l = new JLabel(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 11f));
        return l;
    }

    private JPanel entry(Color c, String label) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);

        JPanel swatch = new JPanel();
        swatch.setPreferredSize(new Dimension(14, 14));
        swatch.setBackground(c);
        swatch.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel lbl = new JLabel(label);
        lbl.setFont(lbl.getFont().deriveFont(11f));

        p.add(swatch);
        p.add(lbl);
        return p;
    }
}