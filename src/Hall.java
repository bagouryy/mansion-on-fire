public class Hall extends CaseTraversable {
	public boolean key;
	public Hall(int l, int c){
		super(l, c);
	}

	@Override
	public boolean estTraversable() {
		return true;
	}

	public Hall(int l, int c, int chaleur) {
		super(l, c);
		this.chaleur = chaleur;
	}

	public Hall(int l, int c, boolean key) {
		super(l,c);
		this.key = key;
	}

	public Hall(int l, int c, int chaleur, boolean key) {
		super(l,c);
		this.key = key;
		this.chaleur = chaleur;
	}

}