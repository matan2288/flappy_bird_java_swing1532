package panels;

import MainFrame.*;
import javax.swing.*;
import java.awt.*;
import GameLogic.*;

public class GamePanel extends JPanel {
    private Bird bird;
    private java.util.List<Pipes> pipes;
    private Keyboard keyboard;
    private Timer gameLoop;

    private JLabel userNameLabel;
    private JLabel scoreLabel;
    private JLabel difficultyLevelLabel;
    private JButton continueButton;

    private Pipes currentPipeSet = null;
    private Pipes upcomingPipeSet = null;
    private int PIPE_SPAWN_INTERVAL = 120; // Spawn new pipe (starts slow for easy beginning)
    private int pipeSpawnTimer = 0;
    private int score = 0;
    private int speedByScore = 3; // Starts slow for easy beginning
    private boolean isGameOver = false;
    private AudioPlayer audioPlayer;

    public GamePanel(MainFrame frame, User currentUser) {
        setOpaque(false);
        setLayout(new BorderLayout());

        bird = new Bird();
        pipes = new java.util.ArrayList<>();
        pipes.add(new Pipes());
        keyboard = new Keyboard();

        // ===== STYLED TOP PANEL =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        topPanel.setBackground(new Color(40, 40, 40, 220));

        // ===== STYLED LABELS =====
        scoreLabel = createStyledLabel("Score: 0");
        userNameLabel = createStyledLabel("User: ");
        difficultyLevelLabel = createStyledLabel("Difficulty: 5");

        topPanel.add(scoreLabel);
        topPanel.add(userNameLabel);
        topPanel.add(difficultyLevelLabel);

        // ===== STYLED BUTTONS =====
        JButton stopGameButton = createStyledButton("Stop", new Color(220, 53, 69));
        JButton restartGameButton = createStyledButton("Restart", new Color(40, 167, 69));
        continueButton = createStyledButton("Continue", new Color(0, 123, 255));
        continueButton.setEnabled(false); // Disabled until bird dies

        topPanel.add(stopGameButton);
        topPanel.add(restartGameButton);
        topPanel.add(continueButton);

        add(topPanel, BorderLayout.NORTH);

            // Listen to keyboard pressing
        setFocusable(true);
        addKeyListener(keyboard);

            // Start game loop
        gameLoop = new Timer(16, e -> { // Runs every 16ms (~60 FPS)
            // Handle bird movement
            bird.handleBirdMovement(keyboard.isSpaceClicked());

            // Handle pipe spawning
            pipeSpawnTimer++;
            if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
                pipes.add(new Pipes());
                pipeSpawnTimer = 0;
            }

            // Move pipes, detect scoring and cleanup
            for (int i = pipes.size() - 1; i >= 0; i--) {
                currentPipeSet = pipes.get(i);
                currentPipeSet.movePipesHorizontally(setDifficulityByScore(score));

                // Check if the pipe is before the bird (for collision check)
                if (currentPipeSet.getCurrentPipePositionX() > 100) {
                    // The latest pipe before the bird (closest OBSTACLE)
                    upcomingPipeSet = currentPipeSet;
                } else {
                    // Pipe just passed the bird, increase score
                    score++;
                }
            }

            // Remove pipes that have moved off the left side
            if (currentPipeSet.pipesPositionX < -currentPipeSet.pipesWidth) {
                pipes.remove(0);
            }

            // Update user data and labels
            currentUser.setUserScore(score);
            currentUser.setUserDifficulty(speedByScore);
            scoreLabel.setText("Score: " + score);
            userNameLabel.setText("User: " + currentUser.getUserName());
            difficultyLevelLabel.setText("Difficulty: " + speedByScore);

            // Check bird state - enable Continue when dead
            if (bird.isBirdDead(upcomingPipeSet)) {
                ((Timer) e.getSource()).stop();
                continueButton.setEnabled(true);
                isGameOver = true;
                // Stop music when game over
                if (audioPlayer != null) {
                    audioPlayer.stop();
                }
            }

            // Redraw
            repaint();
        });

        // ===== BUTTON ACTIONS =====
        stopGameButton.addActionListener(e -> {
            gameLoop.stop();
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        });

        restartGameButton.addActionListener(e -> {
            initializeGame();
            startGame();
        });

        continueButton.addActionListener(e -> frame.showScreen(PanelIndex.YourScore));

        // Listen for when panel becomes visible/hidden
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                // Initialize and Start the game when panel becomes visible
                initializeGame();
                startGame();
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e) {
                // Stop music and game when panel is hidden
                gameLoop.stop();
                if (audioPlayer != null) {
                    audioPlayer.stop();
                }
                initializeGame();
            }
        });
    }

    // Helper method to create styled labels
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        return label;
    }

    // Helper method to create styled buttons with hover effects
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        Color originalColor = bgColor;
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(bgColor.brighter());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        return button;
    }

    public void initializeGame() {
        // Reset bird and keyboard
        bird = new Bird();
        keyboard = new Keyboard();

        // Reset score
        score = 0;
        speedByScore = 3;
        PIPE_SPAWN_INTERVAL = 120;

        // Reset pipes
        pipes.clear();
        pipes.add(new Pipes());

        // Reset pipe spawn timer
        pipeSpawnTimer = 0;

        // Disable Continue button on restart
        continueButton.setEnabled(false);

        // Reset game over state
        isGameOver = false;

        // Re-add keyboard listener
        for (java.awt.event.KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }
        addKeyListener(keyboard);
        requestFocusInWindow();
    }

    public void startGame() {
        // Start background music
        if (audioPlayer == null) {
            audioPlayer = new AudioPlayer();
        }
        audioPlayer.loadAndPlay("assets/Super Mario Bros. medley.wav");
        gameLoop.start();
    }

    // Gradually increase difficulty based on score
    public int setDifficulityByScore(int score) {
        if (score < 100) {
            speedByScore = 3;
            PIPE_SPAWN_INTERVAL = 120;
        } else if (score < 300) {
            speedByScore = 4;
            PIPE_SPAWN_INTERVAL = 110;
        } else if (score < 600) {
            speedByScore = 5;
            PIPE_SPAWN_INTERVAL = 100;
        } else if (score < 1000) {
            speedByScore = 6;
            PIPE_SPAWN_INTERVAL = 90;
        } else if (score < 1500) {
            speedByScore = 7;
            PIPE_SPAWN_INTERVAL = 85;
        } else if (score < 2000) {
            speedByScore = 8;
            PIPE_SPAWN_INTERVAL = 80;
        } else if (score < 3000) {
            speedByScore = 9;
            PIPE_SPAWN_INTERVAL = 75;
        } else if (score < 4000) {
            speedByScore = 10;
            PIPE_SPAWN_INTERVAL = 70;
        } else {
            speedByScore = 11;
            PIPE_SPAWN_INTERVAL = 65;
        }
        return speedByScore;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the bird based on the image and the dimensions
        g.drawImage(bird.getBirdImage(), bird.x, bird.y, bird.width, bird.height, null);

        // Draw pipes from the pipes array using images
        for (Pipes pipe : pipes) {
            g.drawImage(pipe.getTopPipeImage(), pipe.pipesPositionX, pipe.topPipePositionY, pipe.pipesWidth, pipe.topPipeHeight, null);
            g.drawImage(pipe.getBottomPipeImage(), pipe.pipesPositionX, pipe.bottomPipePositionY, pipe.pipesWidth, pipe.bottomPipeHeight, null);
        }

        // Draw Game Over message in the center when bird dies
        if (isGameOver) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Get panel dimensions
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Draw semi-transparent overlay
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, panelWidth, panelHeight);

            // Draw "GAME OVER" text
            g2d.setFont(new Font("SansSerif", Font.BOLD, 48));
            g2d.setColor(new Color(220, 53, 69)); // Red color
            String gameOverText = "GAME OVER";
            FontMetrics fm = g2d.getFontMetrics();
            int textX = (panelWidth - fm.stringWidth(gameOverText)) / 2;
            int textY = panelHeight / 2 - 30;
            g2d.drawString(gameOverText, textX, textY);

            // Draw score text below
            g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
            g2d.setColor(Color.WHITE);
            String scoreText = "Final Score: " + score;
            fm = g2d.getFontMetrics();
            textX = (panelWidth - fm.stringWidth(scoreText)) / 2;
            textY = panelHeight / 2 + 20;
            g2d.drawString(scoreText, textX, textY);

            // Draw instruction text
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
            g2d.setColor(new Color(200, 200, 200));
            String instructionText = "Press Restart or Continue";
            fm = g2d.getFontMetrics();
            textX = (panelWidth - fm.stringWidth(instructionText)) / 2;
            textY = panelHeight / 2 + 60;
            g2d.drawString(instructionText, textX, textY);
        }
    }
}
