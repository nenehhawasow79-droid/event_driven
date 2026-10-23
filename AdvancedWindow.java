import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * PROG209 - Basic Interactive GUI (advanced version)
 * AdvancedWindow extends JFrame (inheritance).
 * WelcomePanel is a nested class that extends JPanel (inheritance).
 * Features: automatic message rotation, font size slider,
 * background colour dropdown, and a Next button.
 */
public class AdvancedWindow extends JFrame {

    // The messages that the label cycles through
    private static final String[] MESSAGES = {
            "Welcome to Java Swing First Individual Assignment by 'Neneh Hawa Sow'",
            "Java Swing uses components such as JFrame, JPanel and JLabel",
            "Every button click is an event handled by a listener",
            "Inheritance lets a class reuse the code of its parent class"
    };

    // Constructor: calls the parent JFrame constructor first
    public AdvancedWindow() {
        super("Java Swing Assignment");

        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the panel and add it to the frame
        WelcomePanel panel = new WelcomePanel(MESSAGES);
        add(panel);

        setAppIcon("icon.png");
    }

    // Loads the application icon using ImageIcon
    private void setAppIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(icon.getImage());
        } else {
            System.out.println("Icon not found: " + path);
        }
    }

    /**
     * Nested class that extends JPanel.
     * It holds the JLabel and the controls, and it owns the cycling logic.
     */
    static class WelcomePanel extends JPanel {

        private final String[] messages;
        private final JLabel label;
        private final JSlider fontSlider;
        private final JComboBox<String> colorBox;
        private final JButton nextButton;
        private final Timer rotationTimer;
        private int messageIndex = 0;

        private final String[] colorNames = {"Light Blue", "Light Green", "Light Yellow", "Light Pink", "White"};
        private final Color[] colors = {
                new Color(220, 235, 255),
                new Color(220, 255, 220),
                new Color(255, 245, 200),
                new Color(255, 220, 230),
                Color.WHITE
        };

        // Constructor: receives the messages and builds the panel
        WelcomePanel(String[] messages) {
            super(new BorderLayout(10, 10));   // Sets the layout of this panel
            this.messages = messages;

            setBackground(colors[0]);

            // JLabel showing the first message, centred
            label = new JLabel(messages[0], SwingConstants.CENTER);
            label.setFont(new Font("Tahoma", Font.BOLD, 20));
            add(label, BorderLayout.CENTER);

            // Controls panel at the bottom
            JPanel controls = new JPanel(new FlowLayout());
            controls.setOpaque(false);

            // Slider to change the font size from 14 to 32
            fontSlider = new JSlider(14, 32, 20);
            fontSlider.setPreferredSize(new Dimension(160, 40));
            fontSlider.addChangeListener(e ->
                    label.setFont(new Font("Tahoma", Font.BOLD, fontSlider.getValue()))
            );

            // Dropdown to change the background colour
            colorBox = new JComboBox<>(colorNames);
            colorBox.addActionListener(this::changeColor);

            // Button to show the next message manually
            nextButton = new JButton("Next Message");
            nextButton.addActionListener(this::showNextMessage);

            controls.add(new JLabel("Font size:"));
            controls.add(fontSlider);
            controls.add(new JLabel("Background:"));
            controls.add(colorBox);
            controls.add(nextButton);
            add(controls, BorderLayout.SOUTH);

            // Timer changes the message every 4 seconds
            rotationTimer = new Timer(4000, e -> showNextMessage(e));
            rotationTimer.start();
        }

        // Moves to the next message, looping back to the start at the end
        private void showNextMessage(ActionEvent e) {
            messageIndex = (messageIndex + 1) % messages.length;
            label.setText(messages[messageIndex]);
        }

        // Applies the colour chosen in the dropdown
        private void changeColor(ActionEvent e) {
            int selected = colorBox.getSelectedIndex();
            setBackground(colors[selected]);
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdvancedWindow window = new AdvancedWindow();
            window.setVisible(true);
        });
    }
}
