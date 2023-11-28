public class CaseTraversable extends Case {
    public int chaleur;
    public Joueur joueur;

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


}