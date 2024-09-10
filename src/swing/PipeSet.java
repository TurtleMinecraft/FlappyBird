package swing;

import java.awt.*;

public class PipeSet {

    public static final int WIDTH = 80;
    public static final int GAP_HEIGHT = 250;

    private static final int ADVANCE_PIXELS_PER_FRAME = 3;

    private final Rectangle bottomPipe;
    private final Rectangle topPipe;
//    private final Rectangle scoreGate;

    public PipeSet(int bottomGapLocation) {
        bottomPipe = new Rectangle(Window.WIDTH, bottomGapLocation, WIDTH,
                Window.HEIGHT - bottomGapLocation);
        topPipe = new Rectangle(Window.WIDTH, 0, WIDTH, Window.HEIGHT - bottomPipe.height - GAP_HEIGHT);
//        scoreGate = new Rectangle(Window.WIDTH, , 1, topPipe.height)
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.fill(bottomPipe);
        g2.fill(topPipe);
    }

    public void advance() {
        bottomPipe.translate(-ADVANCE_PIXELS_PER_FRAME, 0);
        topPipe.translate(-ADVANCE_PIXELS_PER_FRAME, 0);
    }

    public Rectangle getTopPipe() {
        return topPipe;
    }

    public Rectangle getBottomPipe() {
        return bottomPipe;
    }

    public boolean offScreen() {
        return topPipe.x < -WIDTH;
    }

    public boolean intersects(Rectangle other) {
        return (other.intersects(bottomPipe) || other.intersects(topPipe));
    }
}
