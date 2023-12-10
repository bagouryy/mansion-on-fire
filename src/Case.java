public abstract class Case { // ne peut être instanciée
        public final int lig, col;
        public Case(int l, int c) {
            this.lig = l;
            this.col = c;
        }
        public abstract boolean estTraversable(); // chaque classe qui étend Case doit fournir une implémentation de cette methode
}
