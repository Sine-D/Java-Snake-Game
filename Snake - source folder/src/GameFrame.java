import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame(Difficulty difficulty) {
        this.add(new GamePanel(difficulty));
        this.setTitle("Modern Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
