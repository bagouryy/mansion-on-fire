public class Porte extends CaseTraversable {
    public boolean closed;

    public Porte(int l, int c, boolean closed){
        super(l,c);
        this.closed = closed;
        chaleur = 0;
    }
    @Override
    public boolean estTraversable() {
        return !closed;
    }
    @Override
    public void entre(Joueur j){
        if(!closed) {
            joueur = j;
        }
    }
}