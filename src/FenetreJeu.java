import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FenetreJeu extends JPanel implements KeyListener {
    private Terrain terrain;
    private final int tailleCase = 36;
    private final int hauteur, largeur;
    private JFrame frame;
    private Joueur joueur;
    private JLabel key;
    private JLabel score;


    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;
        this.joueur = t.getJoueur();

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));
        JFrame frame = new JFrame("Furfeux");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel InfoBoard = new JPanel();
        InfoBoard.setPreferredSize(new Dimension(9 * tailleCase , 50 ));
        InfoBoard.setBackground(Color.DARK_GRAY);
        score = new JLabel("Health: " + joueur.getResistance());
        score.setFont(new Font("Arial",Font.PLAIN,20));
        score.setAlignmentX(Component.LEFT_ALIGNMENT);
        InfoBoard.add(score,BorderLayout.WEST);
//        frame.add(panel1,BorderLayout.SOUTH);
//        panel1.setVisible(true);

        key = new JLabel("Keys: " + joueur.getKeys());
        key.setFont(new Font("Arial",Font.PLAIN,20));
        key.setAlignmentX(Component.LEFT_ALIGNMENT);
        InfoBoard.add(key,BorderLayout.EAST);



        frame.getContentPane().add(InfoBoard,BorderLayout.NORTH);
        frame.getContentPane().add(this,BorderLayout.SOUTH);
        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Case[][] carte = terrain.getCarte();
        int lig = joueur.getCase().lig - 4;
        int col = joueur.getCase().col - 4;
        for (int i = 1; i < 8 ; i++) {
            for (int j = 1; j < 8; j++) {
                int newL = lig + i;
                int newC = col +  j;
                if((i == 1 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 6) || (i == 1 && j == 7) || (i == 6 && j == 1) || (i == 7 && j == 1) || (i == 7 && j == 2) || (i == 6 && j == 7) || (i == 7 && j == 7) || (i == 7 && j == 6)){

                }else if( newL < hauteur && newC < largeur && newL >= 0 && newC >= 0 ){
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
        g.fillOval(4 * tailleCase, 4 * tailleCase, tailleCase, tailleCase);
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

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.est));break;
            case KeyEvent.VK_LEFT:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.ouest));break;
            case KeyEvent.VK_UP:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.nord));break;
            case KeyEvent.VK_DOWN:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.sud));break;
            default:break;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void updateInfoBoard(int health, int keys) {
        score.setText("Health: " + health);

        key.setText("Keys: " + keys);
    }

    public void updateGUI() {
        this.updateInfoBoard(joueur.getResistance(), joueur.getKeys());
    }
}
