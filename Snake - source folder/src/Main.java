import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game By - Sineth Dinsara");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(new MainMenu(frame));
        frame.setVisible(true);
    }
}
