import java.util.ArrayList;
import java.util.Random;

public class CaseTraversable extends Case {
    protected int chaleur;
    private Joueur joueur;

    public CaseTraversable(int l, int c) {
        super(l, c);
    }

    public boolean estTraversable(){
        return true;
    }

    public void entre(Joueur j){
        joueur = j; // enregistre le joueur actuel sur la case
    }
    public void vide(){
        joueur = null;
    }


    public CaseTraversable(int l, int c, int chaleur) {
        super(l, c);
        this.chaleur = chaleur;
    }

    public void brule(ArrayList<CaseTraversable> voisin){
        int sum = 0;
        for (CaseTraversable cT: voisin) { // Calcul de la somme des chaleurs des cases voisines
            sum += cT.getChaleur();
        }
        Random rand = new Random(); // Génération d'un nombre aléatoire entre 0 et 199
        int ligne = rand.nextInt(199);
        if (ligne < sum){ // Incrémentation de la chaleur de la case actuelle si le nombre aléatoire est inférieur à la somme
            if(chaleur != 10){chaleur++;} //on incrémente de 1 (maximum 10)
        }else if(ligne > 190){
            if(chaleur != 0){chaleur--;} // on décrément de 1 (minimum 0)
        }

    }



    public int getChaleur() {
        return chaleur;
    }

    public void editChaleur(int i){
        int res = chaleur + i; // calcul de la nouvelle chaleur
        if(res < 0){
            chaleur = 0; // Si la nouvelle valeur est négative, la chaleur est fixée à 0
        }else chaleur = Math.min(res, 10); // Sinon, la chaleur est fixée à la nouvelle valeur, avec une limite de 10
    }
}
