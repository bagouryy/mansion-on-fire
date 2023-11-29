public class Sortie extends CaseTraversable{
    public Sortie(int l, int c){
        super(l,c);
    }

    public Sortie(int l, int c, int chaleur){
        super(l,c);
        this.chaleur = chaleur;
    }

    @Override
    public boolean estTraversable() {
        return true;
    }

    @Override
    public void entre(Joueur j) {
        super.entre(j);
        j.exit();
    }
}