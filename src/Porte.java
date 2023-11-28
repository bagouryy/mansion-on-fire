public class Porte extends CaseTraversable {
    private boolean open;

    public Porte(int l, int c, boolean open){
        super(l,c);
        this.open = open;
        chaleur = 0;
    }
    @Override
    public boolean estTraversable() {
        return open;
    }

    public void ouvrePorte() {
        open = true;
    }

    public boolean isOpen() {
        return open;
    }
}