public class Joueur {
    private CaseTraversable c;
    private int resistance;
    private int cles;
    public Joueur(CaseTraversable c, int r, int k) {
        this.c = c;
        this.resistance = r;
        this.cles = k;
    }

    public void bouge(Case cible) {
        /* À compléter */
    }

    public int getResistance(){
        return resistance;
    }

    public CaseTraversable getCase(){
        return c;
    }

}
