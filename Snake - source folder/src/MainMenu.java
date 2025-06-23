import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    private JFrame parentFrame;

    public MainMenu(JFrame frame) {
        this.parentFrame = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(15, 15, 25)); // background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label 
        JLabel titleLabel = new JLabel("SNAKE ARENA");
        titleLabel.setFont(new Font("Orbitron", Font.BOLD, 48));
        titleLabel.setForeground(new Color(0, 191, 255)); 
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1; 

        // Difficulty 
        JLabel diffLabel = new JLabel("Select Difficulty:");
        diffLabel.setForeground(new Color(135, 206, 250)); 
        diffLabel.setFont(new Font("Orbitron", Font.PLAIN, 18));
        gbc.gridy++;
        add(diffLabel, gbc);

        // Difficulty box 
        String[] difficulties = {"Easy", "Medium", "Hard"};
        JComboBox<String> diffBox = new JComboBox<>(difficulties);
        styleComboBox(diffBox, new Color(0, 191, 255));
        gbc.gridx = 1;
        add(diffBox, gbc);

        // Theme Label
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel themeLabel = new JLabel("Select Theme:");
        themeLabel.setForeground(new Color(135, 206, 250));
        themeLabel.setFont(new Font("Orbitron", Font.PLAIN, 18));
        add(themeLabel, gbc);

        // Theme Box 
        String[] themes = {"Classic", "Neon", "Night"};
        JComboBox<String> themeBox = new JComboBox<>(themes);
        styleComboBox(themeBox, new Color(0, 191, 255));
        gbc.gridx = 1;
        add(themeBox, gbc);

        // Start Game Button 
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton startButton = new JButton("START ARENA");
        styleButton(startButton, new Color(0, 191, 255), new Color(0, 147, 197));
        add(startButton, gbc);

        // How to Play Button 
        gbc.gridy++;
        JButton howToPlayButton = new JButton("HOW TO PLAY");
        styleButton(howToPlayButton, new Color(147, 112, 219), new Color(123, 104, 238)); 
        add(howToPlayButton, gbc);

        // Exit Button
        gbc.gridy++;
        JButton exitButton = new JButton("EXIT");
        styleButton(exitButton, new Color(255, 99, 71), new Color(220, 20, 60)); 
        add(exitButton, gbc);

        // Action for Start Game
        startButton.addActionListener(e -> {
            String selectedDiff = (String) diffBox.getSelectedItem();
            Difficulty diff = switch (selectedDiff) {
                case "Medium" -> Difficulty.MEDIUM;
                case "Hard" -> Difficulty.HARD;
                default -> Difficulty.EASY;
            };

            String selectedTheme = (String) themeBox.getSelectedItem();
            switch (selectedTheme) {
                case "Neon" -> ThemeManager.setTheme(Theme.NEON);
                case "Night" -> ThemeManager.setTheme(Theme.NIGHT);
                default -> ThemeManager.setTheme(Theme.CLASSIC);
            }

            parentFrame.setContentPane(new GamePanel(diff));
            parentFrame.revalidate();
        });

        // Action for How to Play
        howToPlayButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    """
                    🐍 HOW TO PLAY 🐍
                    
                    - Use arrow keys to move the snake.
                    - Eat red or gold apples to grow.
                    - Avoid hitting walls or yourself.
                    
                    🧑‍💻 CONTROLS:
                    Arrow Keys = Move
                    P = Pause / Resume
                    
                    Good luck!
                    """,
                    "How to Play",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Action for Exit
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void styleComboBox(JComboBox<String> comboBox, Color borderColor) {
        comboBox.setFont(new Font("Orbitron", Font.PLAIN, 16));
        comboBox.setBackground(new Color(25, 25, 40));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? new Color(25, 25, 40) : new Color(15, 15, 25));
                label.setForeground(isSelected ? borderColor : Color.WHITE);
                return label;
            }
        });
    }

    private void styleButton(JButton button, Color baseColor, Color hoverColor) {
        button.setFont(new Font("Orbitron", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }
}
