import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FenetreJeu extends JPanel implements KeyListener{
    private Terrain terrain;
    private int tailleCase = 36;
    private int hauteur, largeur;
    private KeyListenerTest frame;

    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));

        KeyListenerTest frame = new KeyListenerTest();
        this.frame = frame;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().add(this);
        this.frame.addKeyListener(this);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setFocusable(true);
        this.frame.requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Case[][] carte = terrain.getCarte();
        int lig = terrain.getJoueur().getCase().lig - 4;
        int col = terrain.getJoueur().getCase().col - 4;
        for (int i = 1; i < 8 ; i++) {
            for (int j = 1; j < 8; j++) {
                int newL = lig + i;
                int newC = col + j;
                if((i == 1 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 6) || (i == 1 && j == 7) || (i == 6 && j == 1) || (i == 7 && j == 1) || (i == 7 && j == 2) || (i == 6 && j == 7) || (i == 7 && j == 7) || (i == 7 && j == 6)){

                }else if( newL < carte.length && newC < carte[i].length ){
                    Case curr = carte[newL][newC];
                    if(curr instanceof Mur){
                        g.setColor(Color.BLACK);
                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    } else if(curr instanceof Hall){
                        g.setColor(Color.WHITE);
                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        int intensity = ((Hall) curr).getChaleur();
                        g.setColor(new Color(255,0,0, intensity * 25));
                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        if(((Hall) curr).containsKey()){
                            g.setColor(Color.GRAY);
                            g.fillRect(j * tailleCase + 12, i * tailleCase + 12, tailleCase/3, tailleCase/3);

                        }
                    }else if(curr instanceof Porte){
                        if (!(((Porte) curr).isOpen())){
                            g.setColor(Color.GREEN);
                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        }else{
                            g.setColor(Color.WHITE);
                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);

                            int intensity = ((Porte) curr).getChaleur();
                            g.setColor(new Color(255,0,0, intensity * 25));
                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        }
                        g.setColor(Color.BLACK);
                        g.drawRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    }else if(curr instanceof Sortie){
                        g.setColor(Color.BLUE);
                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    }
                }
            }
        }

        g.setColor(Color.GRAY);
        g.fillOval(4*tailleCase, 4*tailleCase, tailleCase, tailleCase);
    }


    public void ecranFinal(int n) {
        frame.remove(this);
        JLabel label = new JLabel("Score " + n);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }

    /**
    @Override
    public void keyTyped(KeyEvent e) {
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

     */

    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyChar());
        int newL;
        int newC;
        Case cible;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                System.out.print("Typed");
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
