package panels;

import MainFrame.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import GameLogic.User;

public class UserInfo extends JPanel {
    private JTextField nameField;
    private JButton startGame;
    private JButton backHomeButton;

    public UserInfo(MainFrame frame, User currentUser) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 15, 0);

        // Description label above the name field
        JLabel descriptionLabel = new JLabel("Get ready to flap! Enter your name to begin.");
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(80, 80, 80));
        gbc.gridy = 0;
        add(descriptionLabel, gbc);

        // Name text field with placeholder-style border
        nameField = new JTextField(15);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(new CompoundBorder(
            new LineBorder(Color.DARK_GRAY, 2),
            new EmptyBorder(8, 15, 8, 15)
        ));
        nameField.setText("Enter your name");
        nameField.setForeground(Color.GRAY);
        
        // Clear placeholder on focus
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (nameField.getText().equals("Enter your name")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("Enter your name");
                    nameField.setForeground(Color.GRAY);
                }
            }
        });
        
        gbc.gridy = 1;
        add(nameField, gbc);

        // Large round green Start button
        startGame = new JButton("Start") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Green fill
                g2.setColor(new Color(76, 175, 80));
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                
                // Darker green border
                g2.setColor(new Color(56, 142, 60));
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(1, 1, getWidth() - 3, getHeight() - 3);
                
                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), textX, textY);
                
                g2.dispose();
            }
            
            @Override
            public boolean contains(int x, int y) {
                // Make only the circular area clickable
                int radius = getWidth() / 2;
                return Math.pow(x - radius, 2) + Math.pow(y - radius, 2) <= Math.pow(radius, 2);
            }
        };
        startGame.setPreferredSize(new Dimension(100, 100));
        startGame.setFont(new Font("SansSerif", Font.BOLD, 16));
        startGame.setFocusPainted(false);
        startGame.setBorderPainted(false);
        startGame.setContentAreaFilled(false);
        startGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startGame.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.equals("Enter your name") && !name.isEmpty()) {
                currentUser.setUserName(name);
                frame.showScreen(PanelIndex.Game);
            }
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 30, 0);
        add(startGame, gbc);

        // Back button
        backHomeButton = new JButton("Back");
        backHomeButton.setPreferredSize(new Dimension(100, 35));
        backHomeButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backHomeButton.setFocusPainted(false);
        backHomeButton.setBorder(new LineBorder(Color.DARK_GRAY, 2));
        backHomeButton.setContentAreaFilled(false);
        backHomeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backHomeButton.addActionListener(e -> frame.showScreen(PanelIndex.Home));
        gbc.gridy = 3;
        gbc.insets = new Insets(15, 0, 15, 0);
        add(backHomeButton, gbc);

        // Reset field when panel is hidden
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                nameField.setText("Enter your name");
                nameField.setForeground(Color.GRAY);
            }
        });
    }
}
