package swing;

import java.awt.*;

public class Character extends Rectangle {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    private static final int START_X = Window.WIDTH / 2;
    private static final int START_Y = Window.HEIGHT / 2;

    // pixels per frame
    private static final double MAX_FALL_VELOCITY = 12;
    // pixels per frame squared
    private static final double GRAVITY = 50;

    private boolean canFlap;
    private double velocity;
    private boolean alive;

    private static Character instance;
    public static Character getInstance() {
        if (instance == null) {
            instance = new Character(WIDTH, HEIGHT);
        }
        return instance;
    }

    private Character(int width, int height) {
        super(START_X, START_Y, width, height);
        alive = true;
    }

    public void update() {
        if (alive) {
            velocity += GRAVITY * Window.PERIODIC_FRAME;
            if (velocity > MAX_FALL_VELOCITY) velocity = MAX_FALL_VELOCITY;
            if (!canFlap) canFlap = (velocity >= MAX_FALL_VELOCITY / 10 && !KeyBindings.jump);
            if (canFlap && KeyBindings.jump) {
                velocity = -15;
                canFlap = false;
            }
            translate(0, (int) (velocity));
        } else {
            KeyBindings.escape = true;
        }
    }

    public void updateLife(PipeSet pipeSet) {
        alive = !pipeSet.intersects(this) && y > 0 && y < Window.HEIGHT - Window.GROUND_HEIGHT && alive;
    }
}
