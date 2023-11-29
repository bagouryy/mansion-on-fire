import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArrowMovement extends JFrame implements KeyListener {

    private Terrain terrain;

    public ArrowMovement(Terrain t) {
        terrain = t;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newL, newC;
        Case cible;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                newL = terrain.getJoueur().getCase().lig;
                newC = terrain.getJoueur().getCase().col + 1;
                break;
            case KeyEvent.VK_LEFT:
                newL = terrain.getJoueur().getCase().lig;
                newC = terrain.getJoueur().getCase().col - 1;
                break;
            case KeyEvent.VK_UP:
                newL = terrain.getJoueur().getCase().lig - 1;
                newC = terrain.getJoueur().getCase().col;
                break;
            case KeyEvent.VK_DOWN:
                newL = terrain.getJoueur().getCase().lig + 1;
                newC = terrain.getJoueur().getCase().col;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
        }
        cible = terrain.getCase(newL,newC);
        terrain.getJoueur().bouge(cible);

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}
