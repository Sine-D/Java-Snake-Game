import javax.swing.*;

public class DifficultySelector {
    public static Difficulty chooseDifficulty() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose Difficulty:",
                "Snake Game - Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        switch (choice) {
            case 0: return Difficulty.EASY;
            case 1: return Difficulty.MEDIUM;
            case 2: return Difficulty.HARD;
            default: return Difficulty.MEDIUM;
        }
    }
}
