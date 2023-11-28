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
        c.vide();
        ((Hall) cible).entre(this);
        c = (CaseTraversable) cible;
    }

    /**
    public void bouge(Case cible) {
        if(cible instanceof Hall){
            c.vide();
            ((Hall) cible).entre(this);
            c = (CaseTraversable) cible;
        }else if(cible instanceof Porte){
            if(((Porte) cible).isOpen()){
                c.vide();
                ((Porte) cible).entre(this);
                c = (CaseTraversable) cible;
            } else{
                if (cles >= 1){
                    cles--;
                    ((Porte) cible).ouvrePorte();
                    c.vide();
                    ((Porte) cible).entre(this);
                    c = (CaseTraversable) cible;
                }
            }
        }else if(cible instanceof Sortie){
            c.vide();
            c = (CaseTraversable) cible;
        }
    }

     **/

    public int getResistance(){
        return resistance;
    }

    public CaseTraversable getCase(){
        return c;
    }

}
