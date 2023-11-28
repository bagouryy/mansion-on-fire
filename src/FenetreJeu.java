import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FenetreJeu extends JPanel {
    private Terrain terrain;
    private int tailleCase = 36;
    private int hauteur, largeur;
    private JFrame frame;

    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));

        JFrame frame = new JFrame("Furfeux");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Case[][] carte = terrain.getCarte();
        int lig = terrain.getJoueur().getCase().lig - 3;
        int col = terrain.getJoueur().getCase().col - 3;
        for (int i = 1; i < 9 + 1; i++) {
            for (int j = 1; j < 9 + 1; j++) {
                if((i == 1 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 6) || (i == 1 && j == 7) || (i == 6 && j == 1) || (i == 7 && j == 1) || (i == 7 && j == 2) || (i == 6 && j == 7) || (i == 7 && j == 7) || (i == 7 && j == 6)){

                }else{
                    if(carte[lig + i][col + j] instanceof Mur){
                        g.setColor(Color.BLACK);
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
}
