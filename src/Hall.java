public class Hall extends CaseTraversable {
	private boolean key;
	private boolean apple;
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

	public Hall(int l, int c, boolean key, boolean apple) {
		super(l,c);
		this.key = key;
		this.apple = apple;
	}

	public Hall(int l, int c, int chaleur, boolean key, boolean apple) {
		super(l,c);
		this.key = key;
		this.chaleur = chaleur;
		this.apple = apple;
	}

	public boolean containsKey(){
		return key;
	}

	public boolean containsApple(){
		return apple;
	}

	@Override
	public void entre(Joueur j) {
		super.entre(j);
		if(containsKey()){
			j.takeKey();
			key = false;
		}
		if(containsApple()){
			j.takeApple();
			apple = false;
		}
	}
}