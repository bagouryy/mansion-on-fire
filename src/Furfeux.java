import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Furfeux{

    Terrain terrain;
    Joueur joueur;

    public Furfeux(String f) {
        this.terrain = new Terrain(f); // Initialise le terrain à partir d'un fichier de description
        this.joueur = terrain.getJoueur(); // Obtient le joueur à partir du terrain
    }

    public void tour() { // Méthode pour effectuer un tour du jeu
        // Parcourt toutes les cases du terrain
        for (int i = 1; i < terrain.getHauteur() - 1; i++) {
            for (int j = 1; j < terrain.getLargeur() - 1; j++) {
                // Si la case est traversable, applique la propagation du feu à ses voisines
                if(terrain.getCase(i,j) instanceof CaseTraversable){
                    ((CaseTraversable) terrain.getCase(i,j)).brule(terrain.getVoisinesTraversables(i,j));
                }
            }
        }
        joueur.burn(joueur.getCase().getChaleur());  // Applique les dégâts au joueur en fonction de la chaleur de sa case actuelle
    }

    public boolean partieFinie() {
        return joueur.estSortie() || joueur.getResistance() <= 0;
    }
    // Méthode pour vérifier si la partie est terminée

    public static void main(String[] args) {
        int tempo = 100;
        Furfeux jeu = new Furfeux("terrains/new.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        AtomicInteger bucketTimer = new AtomicInteger();
        Timer timer = new Timer(tempo, e -> {
            jeu.tour();
            graphic.updateGUI();
            graphic.repaint();
            bucketTimer.getAndIncrement();
            if(bucketTimer.get() == 50){
                jeu.getJoueur().giveBuckets();
                bucketTimer.set(0);
            }
            if (jeu.partieFinie()) {
                graphic.ecranFinal(Math.max(0, jeu.joueur.getResistance()));
                ((Timer)e.getSource()).stop();
            }
        });
//        timer = new Timer(tempo, e -> );
        timer.start();
    }

    private Joueur getJoueur() {
        return joueur;
    }


}
