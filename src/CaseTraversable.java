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
        joueur = j;
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
        for (CaseTraversable cT: voisin) {
            sum += cT.getChaleur();
        }
        Random rand = new Random();
        int ligne = rand.nextInt(199);
        if (ligne < sum){
            if(chaleur != 10){chaleur++;}
        }else if(ligne > 190){
            if(chaleur != 0){chaleur--;}
        }

    }



    public int getChaleur() {
        return chaleur;
    }
}