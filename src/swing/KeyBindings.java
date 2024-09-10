package swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyBindings implements KeyListener {

    public static boolean jump;
    public static boolean escape;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();
        if (pressed == KeyEvent.VK_SPACE) jump = true;
        if (pressed == KeyEvent.VK_ESCAPE) escape = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int released = e.getKeyCode();
        if (released == KeyEvent.VK_SPACE) jump = false;
    }
}
