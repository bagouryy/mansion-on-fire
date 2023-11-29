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
        if(cible instanceof Porte && !((Porte) cible).isOpen()){
            if(cles >= 1){
                ((Porte) cible).ouvrePorte();
                cles--;
            }
        }

        if (cible.estTraversable()) {
            this.c.vide();
            ((CaseTraversable)cible).entre(this);
            this.c = (CaseTraversable) cible;
        }
    }



    public int getResistance(){
        return resistance;
    }

    public CaseTraversable getCase(){
        return c;
    }

}
