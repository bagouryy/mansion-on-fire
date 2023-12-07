import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class FenetreJeu extends JPanel implements KeyListener {
    private final Terrain terrain;
    private final int tailleCase = 36;
    private final int hauteur, largeur;
    private final JFrame frame;
    private final Joueur joueur;
    private final JLabel key;
    private final JLabel score;
    private final JLabel info;

    private Image keyImg,appleImg,doorImg,character,wallImg,floorImg,fireImg,portalImg;

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

        try {
            keyImg = ImageIO.read(new File("media/key-icon.png"));
            appleImg = ImageIO.read(new File("media/apple-icon.png"));
            doorImg = ImageIO.read(new File("media/door-icon.png"));
            character = ImageIO.read(new File("media/sonic-icon.png"));
            wallImg = ImageIO.read(new File("media/wall-icon.png"));
            floorImg = ImageIO.read(new File("media/floor-icon2.png"));
            fireImg = ImageIO.read(new File("media/fire-icon2.png"));
            portalImg = ImageIO.read(new File("media/portal-icon.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        JPanel InfoBoard = new JPanel();
        InfoBoard.setPreferredSize(new Dimension(9 * tailleCase , 50 ));
        InfoBoard.setBackground(Color.BLUE);
        score = new JLabel("Health: " + joueur.getResistance());
        score.setFont(new Font("Arial",Font.PLAIN,20));
        score.setAlignmentX(Component.LEFT_ALIGNMENT);
        InfoBoard.add(score,BorderLayout.WEST);

        key = new JLabel("Keys: " + joueur.getKeys() + " Bucket: " + joueur.getBucket());
        key.setFont(new Font("Arial",Font.PLAIN,20));
        key.setAlignmentX(Component.LEFT_ALIGNMENT);
        InfoBoard.add(key,BorderLayout.EAST);

        JPanel Info = new JPanel();
        Info.setPreferredSize(new Dimension(9 * tailleCase , 50 ));
        Info.setBackground(Color.RED);
        info = new JLabel("Press 'M' for Mario, 'S' for Sonic");
        info.setFont(new Font("Arial",Font.PLAIN,20));
        Info.add(info);

        frame.getContentPane().add(InfoBoard,BorderLayout.NORTH);
        frame.getContentPane().add(this);
        frame.getContentPane().add(Info,BorderLayout.SOUTH);

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
                if((i == 1 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 6) || (i == 1 && j == 7) || (i == 6 && j == 1) || (i == 7 && j == 1) || (i == 7 && j == 2) || (i == 6 && j == 7) || (i == 7 && j == 7) || (i == 7 && j == 6) ||(i == 2 && j == 7) ){
                }else if( newL < hauteur && newC < largeur && newL >= 0 && newC >= 0 ){
                    Case curr = carte[newL][newC];
                    if(curr instanceof Mur){
                        g.setColor(Color.BLACK);
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(wallImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                    } else if(curr instanceof Hall){
                        g.setColor(Color.WHITE);
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(floorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                        int intensity = ((Hall) curr).getChaleur();
                        g.setColor(new Color(255,0,0, intensity * 25));
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(fireImg,j * tailleCase ,i * tailleCase + tailleCase,tailleCase,-(int)3.6 * intensity, this);
                        if(((Hall) curr).containsKey()){
                            g.setColor(Color.GRAY);
//                            g.fillRect(j * tailleCase + 12, i * tailleCase + 12, tailleCase/3, tailleCase/3);
                            g.drawImage(keyImg,j * tailleCase ,i * tailleCase,25,25,this);
                        }
                        if(((Hall) curr).containsApple()) {
                            g.setColor(Color.RED);
                            int appleSize = tailleCase / 3;
                            int appleX = (j * tailleCase) + (2 * tailleCase / 3);
                            int appleY = (i * tailleCase);
                            g.drawImage(appleImg,j * tailleCase + 18, i * tailleCase + 18,14,14,this);
//                            g.fillOval(appleX, appleY, appleSize, appleSize);
                        }
                    }else if(curr instanceof Porte){
                        if (!(((Porte) curr).isOpen())){
                            g.setColor(Color.GREEN);
                            g.drawImage(doorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
//                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        }else{
                            g.setColor(Color.WHITE);
//                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                            g.drawImage(floorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                            int intensity = ((Porte) curr).getChaleur();
                            g.drawImage(fireImg,j * tailleCase ,i * tailleCase + tailleCase,tailleCase,-(int)3.6 * intensity, this);
                        }
                        g.setColor(Color.BLACK);
                        g.drawRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    }else if(curr instanceof Sortie){
                        g.setColor(Color.BLUE);
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(floorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                        g.drawImage(portalImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                    }
                }
            }
        }

        g.setColor(Color.GRAY);
//        g.fillOval(4 * tailleCase, 4 * tailleCase, tailleCase, tailleCase);
        if(this.joueur.isEast()) {
            g.drawImage(character, 4 * tailleCase, 4 * tailleCase, tailleCase, tailleCase, this);
        }else {
            g.drawImage(character, 4 * tailleCase + tailleCase, 4 * tailleCase, -tailleCase, tailleCase, this);
        }
    }



    public void ecranFinal(int n) {
        frame.remove(this);
        JLabel label = new JLabel("Score " + n);
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label);
        frame.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.est));
                this.joueur.setEast();break;
            case KeyEvent.VK_LEFT:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.ouest));
                this.joueur.setWest();break;
            case KeyEvent.VK_UP:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.nord));break;
            case KeyEvent.VK_DOWN:
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.sud));break;
            case KeyEvent.VK_SPACE:
                this.drop_bucket(joueur.getCase());break;
            case KeyEvent.VK_M:
                try {
                    character = ImageIO.read(new File("media/mario-icon.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }break;
            case KeyEvent.VK_S:
                try {
                    character = ImageIO.read(new File("media/sonic-icon.png"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }break;
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

    public void updateInfoBoard(int health, int keys, int buckets) {
        int res = Math.max(health, 0);
        score.setText("Health: " + res);

        key.setText("Keys: " + keys + " Buckets: " + buckets);
    }

    public void updateGUI() {
        this.updateInfoBoard(joueur.getResistance(), joueur.getKeys(), joueur.getBucket());
    }

    public void drop_bucket(CaseTraversable c) {
        if (joueur.getBucket() > 0) {
            int lig = c.lig;
            int col = c.col;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newLig = lig + i;
                    int newCol = col + j;
                    if (i != 0 || j != 0) { // Skip the center cell

                        if (terrain.getCase(newLig, newCol) instanceof CaseTraversable) {
                            int chaleur = (i != 0 && j != 0) ? -5 : -7;
                            ((CaseTraversable) terrain.getCase(newLig, newCol)).editChaleur(chaleur);
                        }
                    } else {
                        ((CaseTraversable) terrain.getCase(newLig, newCol)).editChaleur(-10);
                    }
                }
            }
            joueur.useBucket();
        }
    }
}
