package com.mv2016.noeud;


/**
 * Created by Matthieu on 21/09/2016.
 * @author Matthieu VINCENT
 */

public class BPlus {
    private Noeud racine;
    private short ordre;

    /**
     * Constructeur de la classe BPlus
     * @param ordre
     */
    public BPlus(short ordre){
        this.ordre = ordre;
        this.racine = new NoeudFeuille(this.ordre);
    }

    /**
     * Méthode qui permet de cherche la valeur associée à la clef passée en paramètre
     * @param clef
     * @return Object
     */
    public Object chercher(Object clef){
        // On cherche la clef dans les noeuds feuille de l'arbre
        NoeudFeuille nf = this.trouverClefDansFeuille(clef);
        // On return la valeur qui est associée à la clef passée en paramètre
        int index = nf.chercher(clef);
        // Si la méthode de recherche return -1, alors la clef passée en paramètre n'est pas présente dans l'arbre
        if (index == -1){
            return null;
        }else{
            // Sinon en return la valeur associée
            return nf.getValeur(index);
        }
    }

    /**
     * Méthode qui insère un couple clef / valeur dans l'arbre
     * @param clef
     * @param valeur
     */
    public void inserer(Object clef, Object valeur){
        // On cherche la clef dans les noeuds feuille de l'arbre
        NoeudFeuille nf = this.trouverClefDansFeuille(clef);
        // On insère la clef est la valeur dans le noeuf feuille dans lequel la clef est rangée
        nf.insererClef(clef, valeur);
        // On verifie que le noeud n'est pas surcharger
        if (nf.estPlein()){
            // Si il est surcharger, on effectue les modifications necessaires
            Noeud n = nf.modificationEstPlein();
            if (n != null){
                this.racine = n;
            }
        }
    }

    /**
     * Méthode qui permet de supprimer de l'arbre le couple clef / valeur dont la clef est passée en paramètre
     * @param clef
     */
    public void supprimer(Object clef) throws Exception {
        // On cherche la clef dans les noeuds feuille de l'arbre
        NoeudFeuille nf = this.trouverClefDansFeuille(clef);
        // On verifie que la suppression est possible est que le noeud est pas assez plein
        if (nf.estSupprimer(clef) && nf.estPasAssezPlein()) {
            // Si c'est le cas, on effectue les modification necessaires
            Noeud n = nf.modificationPasAssezPlein();
            if (n != null) {
                this.racine = n;
            }
        }
    }

    /**
     * Méthode qui return le noeud feuille qui contient la clef passée en paramètre
     * @param clef
     * @return NoeudFeuille
     */
    private NoeudFeuille trouverClefDansFeuille(Object clef) {
        // On pars de la racine
        Noeud n = this.racine;
        // Tant que les noeud sont des noeud index, on descend dans les fils
        while (n.getTypeNoeud() == TypeNoeud.Index) {
            n = n.getFils(n.chercher(clef));
        }
        // On return le noeud feuille dans lequel se trouve la clef placée en paramètre
        return (NoeudFeuille)n;
    }
}
