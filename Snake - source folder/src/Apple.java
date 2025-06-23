import java.awt.*;
import java.util.Random;

public class Apple {
    int x, y;
    int unitSize;

    public Apple(int unitSize, int width, int height) {
        this.unitSize = unitSize;
        Random random = new Random();
        x = random.nextInt(width / unitSize) * unitSize;
        y = random.nextInt(height / unitSize) * unitSize;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, unitSize, unitSize);
    }
}
