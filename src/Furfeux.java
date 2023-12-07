import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Furfeux{

    Terrain terrain;
    Joueur joueur;

    public Furfeux(String f) {
        this.terrain = new Terrain(f);
        this.joueur = terrain.getJoueur();
    }

    public void tour() {
        for (int i = 1; i < terrain.getHauteur() - 1; i++) {
            for (int j = 1; j < terrain.getLargeur() - 1; j++) {
                if(terrain.getCase(i,j) instanceof CaseTraversable){
                    ((CaseTraversable) terrain.getCase(i,j)).brule(terrain.getVoisinesTraversables(i,j));
                }
            }
        }
        joueur.burn(joueur.getCase().getChaleur());
    }

    public boolean partieFinie() {
        return joueur.estSortie() || joueur.getResistance() <= 0;
    }

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
