public class Joueur {
    private CaseTraversable c;
    private int resistance;
    private int keys;
    private boolean sortie;

    public Joueur(CaseTraversable c, int r, int k) {
        this.c = c;
        this.resistance = r;
        this.keys = k;
    }

    public void bouge(Case cible) {
        if(cible instanceof Porte && !((Porte) cible).isOpen()){
            if(keys >= 1){
                ((Porte) cible).ouvrePorte();
                keys--;
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

    public void burn(int chaleur){
        resistance -= chaleur;
    }

    public void takeKey(){this.keys++;}

    public void exit(){
        c.vide();
        sortie =true;
    }

    public boolean estSortie(){return sortie;}




}
