import java.awt.*;

public enum Theme {
    CLASSIC(Color.GREEN, Color.BLACK, new Color(45, 180, 0)),
    NEON(Color.CYAN, Color.DARK_GRAY, Color.MAGENTA),
    NIGHT(Color.WHITE, new Color(10, 10, 40), new Color(80, 80, 160));

    public final Color headColor;
    public final Color backgroundColor;
    public final Color bodyColor;

    Theme(Color headColor, Color backgroundColor, Color bodyColor) {
        this.headColor = headColor;
        this.backgroundColor = backgroundColor;
        this.bodyColor = bodyColor;
    }
}
