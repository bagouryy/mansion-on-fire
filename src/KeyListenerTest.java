import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerTest extends JFrame implements KeyListener {

    public KeyListenerTest() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KeyListenerTest::new);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed: " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released: " + e.getKeyChar());
    }

}
