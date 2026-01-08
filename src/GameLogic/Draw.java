package GameLogic;

import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.*;

public class Draw {
    public int x;
    public int y;
    public Image image;
    public AffineTransform transform;

    public Draw() {
    }

    public Draw(int x, int y, String imagePath) {
        Toolkit.getDefaultToolkit().sync();
        this.x = x;
        this.y = y;
        this.image = Toolkit.getDefaultToolkit().getImage(imagePath);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }
}