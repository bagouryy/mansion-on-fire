import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Terrain {
    private int hauteur, largeur;
    private Case[][] carte;
    private Joueur joueur;

    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            int resistanceJoueur = sc.nextInt();
            int cles = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            for (int l=0; l<hauteur; l++) {
                String line = sc.nextLine();
                for (int c=0; c<largeur; c++) {
                    Case cc;
                    Character ch = line.charAt(c);
                    switch (ch) {
                        case '#': cc = new Mur(l, c); break;
                        case ' ': cc = new Hall(l, c); break;
                        case '+': cc = new Hall(l, c, true,false); break;
                        case 'a': cc = new Hall(l,c, false,true); break;
                        case 'à': cc = new Hall(l,c,true,true);break;
                        case '1': case '2': case '3': case '4':
                            cc = new Hall(l, c, (int)ch-(int)'0'); break;
                        case 'O': cc = new Sortie(l, c, 0); break;
                        case '@': cc = new Porte(l, c, false); break;
                        case '.': cc = new Porte(l, c, true); break;
                        case 'H':
                            if (this.joueur != null) throw new IllegalArgumentException("carte avec deux joueurs");
                            cc = new Hall(l, c);
                            this.joueur = new Joueur((CaseTraversable) cc, resistanceJoueur, cles);
                            ((Hall) cc).entre(joueur);
                            break;
                        default:  cc = null; break;
                    }
                    carte[l][c] = cc;
                }
            }
            sc.close();
        }
        catch (IOException e) { e.printStackTrace(); System.exit(1); }
    }

    public Joueur getJoueur() { return this.joueur; }

    public ArrayList<CaseTraversable> getVoisinesTraversables(int lig, int col) {
        ArrayList<CaseTraversable> res = new ArrayList<>();
        for (int i = -1; i < 2 ; i++) {
            for (int j = -1; j < 2; j++) {
                if(carte[lig + i][col + j] instanceof CaseTraversable){
                    res.add((CaseTraversable) carte[lig + i][col + j]);
                }
            }

        }
        return res;
    }



    public int getHauteur(){
        return hauteur;
    }

    public int getLargeur(){
        return largeur;
    }

    public Case[][] getCarte() {
        return carte;
    }

    public Case getCase(int l, int c){
        return carte[l][c];
    }

    public Case cible(Case c, Direction d) {
        switch (d) {
            case nord:
                return this.getCase(c.lig - 1, c.col);
            case sud:
                return this.getCase(c.lig + 1, c.col);
            case est:
                return this.getCase(c.lig, c.col + 1);
            case ouest:
                return this.getCase(c.lig, c.col - 1);
            default:
                return null;
        }
    }

}
