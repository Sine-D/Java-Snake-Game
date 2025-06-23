import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    // Screen & gameplay settings
    int screenWidth = 600, screenHeight = 600;
    int unitSize, gameUnits, delay;
    int[] x, y;
    int bodyParts = 3, applesEaten; // Changed initial snake length
    int appleX, appleY;
    AppleType currentAppleType;
    Direction direction = Direction.RIGHT;
    boolean running = false, paused = false;
    Timer timer;
    Random random;
    Difficulty currentDifficulty;
    JFrame parentFrame;

    // Sound and Music 
    Music bgMusic = new Music();

    public GamePanel(Difficulty diff) {
        currentDifficulty = diff;
        unitSize = diff.unitSize;
        delay = diff.delay;
        gameUnits = (screenWidth * screenHeight) / (unitSize * unitSize);
        x = new int[gameUnits];
        y = new int[gameUnits];
        random = new Random();

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(ThemeManager.getTheme().backgroundColor);
        setFocusable(true); 
        addKeyListener(new MyKeyAdapter());

        
        SwingUtilities.invokeLater(() -> {
            parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            requestFocusInWindow(); 
        });

        bgMusic.start("Sound/music.wav"); // Start background music
        startGame();
    }

    public void startGame() {
        bodyParts = 3; // Changed snake length to 3
        applesEaten = 0;
        direction = Direction.RIGHT;
        paused = false;
        running = true;
        newApple();
        timer = new Timer(delay, this);
        timer.start();
        requestFocusInWindow(); 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (running) {
            g.setColor(currentAppleType == AppleType.RED ? Color.RED : Color.YELLOW);
            g.fillOval(appleX, appleY, unitSize, unitSize);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    
                    GradientPaint headGradient = new GradientPaint(
                        x[i], y[i], new Color(150, 150, 150),
                        x[i] + unitSize, y[i] + unitSize, new Color(100, 100, 100)
                    );
                    g2d.setPaint(headGradient);
                    g2d.fillRoundRect(x[i], y[i], unitSize, unitSize, unitSize / 2, unitSize / 2);
                    
                    g2d.setColor(new Color(255, 255, 255, 100));
                    g2d.fillRoundRect(x[i] + unitSize / 8, y[i] + unitSize / 8, 
                        unitSize / 4, unitSize / 4, unitSize / 4, unitSize / 4);
                } else {
                   
                    GradientPaint bodyGradient = new GradientPaint(
                        x[i], y[i], new Color(0, 150, 0),
                        x[i] + unitSize, y[i] + unitSize, new Color(0, 80, 0)
                    );
                    g2d.setPaint(bodyGradient);
                    g2d.fillRoundRect(x[i], y[i], unitSize, unitSize, unitSize / 3, unitSize / 3);
                }
            }

            // Modern score display 
            g2d.setFont(new Font("Orbitron", Font.BOLD, 24));
            String scoreText = "SCORE: " + applesEaten;
            GradientPaint scoreGradient = new GradientPaint(
                0, 0, new Color(0, 191, 255), 
                screenWidth, 0, new Color(147, 112, 219) 
            );
            g2d.setPaint(scoreGradient);
            g2d.drawString(scoreText,
                    (screenWidth - g2d.getFontMetrics().stringWidth(scoreText)) / 2,
                    30);

            // Modern pause 
            g2d.setColor(new Color(0, 191, 255)); 
            g2d.setFont(new Font("Orbitron", Font.PLAIN, 14));
            System.out.println("\n");
            String pauseHint = "TOGGLE PAUSE: [P] ";
            g2d.drawString(pauseHint, screenWidth - 16 - g2d.getFontMetrics().stringWidth(pauseHint), 20);
            g2d.setColor(new Color(0, 191, 255, 100)); 
            g2d.drawString(pauseHint, screenWidth - 15 - g2d.getFontMetrics().stringWidth(pauseHint), 21);

            if (paused) {
                g2d.setColor(new Color(255, 165, 0, 200)); 
                g2d.setFont(new Font("Orbitron", Font.BOLD, 40));
                g2d.drawString("PAUSED",
                        (screenWidth - g2d.getFontMetrics().stringWidth("PAUSED")) / 2,
                        screenHeight / 2);
            }
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt(screenWidth / unitSize) * unitSize;
        appleY = random.nextInt(screenHeight / unitSize) * unitSize;
        currentAppleType = random.nextInt(100) < 70 ? AppleType.RED : AppleType.GOLD;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case UP -> y[0] -= unitSize;
            case DOWN -> y[0] += unitSize;
            case LEFT -> x[0] -= unitSize;
            case RIGHT -> x[0] += unitSize;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            Sound.play("Sound/food.wav");
            if (currentAppleType == AppleType.RED) {
                bodyParts++;
                applesEaten++;
            } else {
                bodyParts += 2;
                applesEaten += 3;
            }
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) running = false;
        }
        if (x[0] < 0 || x[0] >= screenWidth || y[0] < 0 || y[0] >= screenHeight) running = false;
        if (!running) {
            timer.stop();
            Sound.play("Sound/gameover.wav");
            bgMusic.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));
        String scoreText = "Score: " + applesEaten;
        g.drawString(scoreText,
                (screenWidth - g.getFontMetrics().stringWidth(scoreText)) / 2,
                g.getFont().getSize());

        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        g.drawString("Game Over",
                (screenWidth - g.getFontMetrics().stringWidth("Game Over")) / 2,
                screenHeight / 2);

        int choice = JOptionPane.showOptionDialog(parentFrame,
                "Play again?",
                "Game Over",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Play Again", "Main Menu", "Exit"},
                "Play Again");

        if (choice == 0) {
            parentFrame.setContentPane(new GamePanel(currentDifficulty));
            parentFrame.revalidate();
        } else if (choice == 1) {
            parentFrame.setContentPane(new MainMenu(parentFrame));
            parentFrame.revalidate();
        } else {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!paused) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> { if (direction != Direction.RIGHT) direction = Direction.LEFT; }
                    case KeyEvent.VK_RIGHT -> { if (direction != Direction.LEFT) direction = Direction.RIGHT; }
                    case KeyEvent.VK_UP -> { if (direction != Direction.DOWN) direction = Direction.UP; }
                    case KeyEvent.VK_DOWN -> { if (direction != Direction.UP) direction = Direction.DOWN; }
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_P && running) {
                paused = !paused;
            }
        }
    }
}
