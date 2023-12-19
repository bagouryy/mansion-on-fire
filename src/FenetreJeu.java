import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class FenetreJeu extends JPanel implements KeyListener {
    private final Terrain terrain;
    private final int tailleCase = 70;
    private final int hauteur, largeur;
    private final JFrame frame; // fenetre

    private final Joueur joueur;
    private final String user;

    private final JLabel key;//"etiquette" graphique
    private final JLabel score;
    private final JLabel info;

    private Image keyImg,appleImg,doorImg,character,wallImg,floorImg,fireImg,portalImg;
    private boolean mario = false;

    public FenetreJeu(Terrain t, String user) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;
        this.joueur = t.getJoueur();
        this.user = user;

        setBackground(Color.LIGHT_GRAY);//configuration du panneau
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));

        JFrame frame = new JFrame("Furfeux"); // Création d'une nouvelle fenêtre (JFrame) avec le titre "Furfeux"
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Chargement des images à partir de fichiers
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

        // configuration du panneau d'information ( en haut )
        JPanel InfoBoard = new JPanel();
        InfoBoard.setPreferredSize(new Dimension(9 * tailleCase , 50 ));
        InfoBoard.setBackground(Color.RED);
        // Label pour afficher le score du joueur
        score = new JLabel("Health: " + joueur.getResistance());
        score.setFont(new Font("Arial",Font.PLAIN,20));
        score.setAlignmentX(Component.LEFT_ALIGNMENT);
        InfoBoard.add(score,BorderLayout.WEST);
        //Label pour afficher le nombre de clés et seaux du joueur
        key = new JLabel("Keys: " + joueur.getKeys() + " Bucket: " + joueur.getBucket());
        key.setFont(new Font("Arial",Font.PLAIN,20));
        key.setAlignmentX(Component.LEFT_ALIGNMENT);
        InfoBoard.add(key,BorderLayout.EAST);
        // configuration du panneau d'information ( en bas )
        JPanel Info = new JPanel();
        Info.setPreferredSize(new Dimension(9 * tailleCase , 50 ));
        Info.setBackground(Color.RED);
        //Label pour afficher des instructions ou informations suplémentaires
        info = new JLabel("Press 'M' for Mario, 'S' for Sonic");
        info.setFont(new Font("Arial",Font.PLAIN,20));
        Info.add(info);
        // Ajout des panneaux à la fenêtre principale
        frame.getContentPane().add(InfoBoard,BorderLayout.NORTH);
        frame.getContentPane().add(this);
        frame.getContentPane().add(Info,BorderLayout.SOUTH);
        // // Enregistrement de la classe courante comme KeyListener
        frame.addKeyListener(this);
        //Configuration et affichage de la fenêtre
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);// Appelle la méthode paintComponent de la classe mère pour effectuer les opérations de base de dessin
        Case[][] carte = terrain.getCarte();
        int lig = joueur.getCase().lig - 4; // centrer la vue sur le joueur
        int col = joueur.getCase().col - 4;
        for (int i = 1; i < 8 ; i++) {
            for (int j = 1; j < 8; j++) {
                int newL = lig + i;
                int newC = col +  j;
                // Vérifie les positions spécifiques où rien ne doit être affiché (espaces vides dans la grille)
                if((i == 1 && j == 1) || (i == 2 && j == 1) || (i == 1 && j == 2) || (i == 1 && j == 6) || (i == 1 && j == 7) || (i == 6 && j == 1) || (i == 7 && j == 1) || (i == 7 && j == 2) || (i == 6 && j == 7) || (i == 7 && j == 7) || (i == 7 && j == 6) ||(i == 2 && j == 7) ){
                    // Ne rien faire si c'est une position spécifique
                }else if( newL < hauteur && newC < largeur && newL >= 0 && newC >= 0 ){
                    // Vérifie si les indices ne dépassent pas les limites de la carte
                    Case curr = carte[newL][newC];
                    if(curr instanceof Mur){
                        g.setColor(Color.BLACK);
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(wallImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                    } else if(curr instanceof Hall){
                        g.setColor(Color.WHITE);
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(floorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);

                        // gestion de la chaleur dans le couloir
                        int intensity = ((Hall) curr).getChaleur();
                        g.setColor(new Color(255,0,0, intensity * 25));
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(fireImg,j * tailleCase ,i * tailleCase + tailleCase,tailleCase,-(int)3.6 * intensity, this);

                        // Affiche une clé  si la case contient une clé
                        if(((Hall) curr).containsKey()){
                            g.setColor(Color.GRAY);
//                            g.fillRect(j * tailleCase + 12, i * tailleCase + 12, tailleCase/3, tailleCase/3);
                            g.drawImage(keyImg,j * tailleCase ,i * tailleCase,25,25,this);
                        }

                        // Affiche une pomme si la case contient une pomme
                        if(((Hall) curr).containsApple()) {
                            g.setColor(Color.RED);
                            int appleSize = tailleCase / 3;
                            int appleX = (j * tailleCase) + (2 * tailleCase / 3);
                            int appleY = (i * tailleCase);
                            g.drawImage(appleImg,j * tailleCase + 18, i * tailleCase + 18,14,14,this);
//                            g.fillOval(appleX, appleY, appleSize, appleSize);
                        }
                    }else if(curr instanceof Porte){
                        // affiche une porte
                        if (!(((Porte) curr).isOpen())){
                            g.setColor(Color.GREEN);
                            g.drawImage(doorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
//                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        }else{
                            // Affiche un couloir si la porte est ouverte
                            g.setColor(Color.WHITE);
//                            g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                            g.drawImage(floorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);

                            // Gestion de la chaleur dans le couloir
                            int intensity = ((Porte) curr).getChaleur();
                            g.drawImage(fireImg,j * tailleCase ,i * tailleCase + tailleCase,tailleCase,-(int)3.6 * intensity, this);
                        }
                        g.setColor(Color.BLACK);
                        g.drawRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                    }else if(curr instanceof Sortie){
                        //affiche la sortie
                        g.setColor(Color.BLUE);
//                        g.fillRect(j * tailleCase, i * tailleCase, tailleCase, tailleCase);
                        g.drawImage(floorImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                        g.drawImage(portalImg,j * tailleCase, i * tailleCase, tailleCase, tailleCase,this);
                    }
                }
            }
        }

        // affiche le joueur
        g.setColor(Color.GRAY);
//        g.fillOval(4 * tailleCase, 4 * tailleCase, tailleCase, tailleCase);
        if(this.joueur.isEast()) {
            g.drawImage(character, 4 * tailleCase, 4 * tailleCase, tailleCase, tailleCase, this);
        }else {
            g.drawImage(character, 4 * tailleCase + tailleCase, 4 * tailleCase, -tailleCase, tailleCase, this);
        }
    }



    public void ecranFinal(int n, long time) {
        frame.remove(this); // supprime le paneau actuel de la fenêtre
        //crée un label affichant le score
        JLabel label = new JLabel("Votre score est " + n);
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());

        JLabel label2 = new JLabel("Fait en " + time +"ms");
        label2.setFont(new Font("Verdana", Font.PLAIN, 20));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setSize(this.getSize());

        //ajoute le label a la fenêtre
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);


        // redessine la fenêtre
        frame.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //gestion des touches du clavier
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT: // deplacement vers la droite
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.est));
                this.joueur.setEast();break;
            case KeyEvent.VK_LEFT: // deplacement vers la gauche
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.ouest));
                this.joueur.setWest();break;
            case KeyEvent.VK_UP: // deplacement vers le haut
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.nord));break;
            case KeyEvent.VK_DOWN: // deplacement vers le bas
                this.joueur.bouge(this.terrain.cible(this.joueur.getCase(), Direction.sud));break;
            case KeyEvent.VK_SPACE: // utilise un bucket avec Space
                this.drop_bucket(joueur.getCase());break;
            case KeyEvent.VK_M: // chargement de l'image du personnage a mario
                try {
                    character = ImageIO.read(new File("media/mario-icon.png"));
                    mario = true;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }break;
            case KeyEvent.VK_S: // chargement de l'image du personnage a sonic
                try {
                    character = ImageIO.read(new File("media/sonic-icon.png"));
                    mario=false;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }break;
            default:break;
        }

        //redessine l'interface utilisateur
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void updateInfoBoard(int health, int keys, int buckets) {
        //// Met à jour les informations affichées dans le panneau d'information (InfoBoard)
        int res = Math.max(health, 0);
        score.setText("Health: " + res);

        key.setText("Keys: " + keys + " Buckets: " + buckets);
    }

    public void updateGUI() {
        this.updateInfoBoard(joueur.getResistance(), joueur.getKeys(), joueur.getBucket());
    }
    // Met à jour l'interface utilisateur
    public void drop_bucket(CaseTraversable c) {
        // Largue un seau d'eau à la position de la case traversable donnée
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

    public boolean getMario() {
        return mario;
    }
}
