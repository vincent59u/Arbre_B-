package com.mv2016.noeud;

/**
 * Created by Matthieu on 22/09/2016.
 * @author Matthieu VINCENT
 */
public class NoeudIndex extends Noeud{
    private Noeud[] fils;
    private short ordre;

    /**
     * Constructeur de la classe NoeudIndex
     * @param ordre
     */
    public NoeudIndex(short ordre){
        super(ordre);
        this.ordre = ordre;
        this.clef = new Object[ordre + 1];
        this.fils = new Noeud[ordre + 2];
    }

    /**
     * Getter qui return le type de noeud
     * @return TypeNoeud
     */
    public TypeNoeud getTypeNoeud(){
        return TypeNoeud.Index;
    }

    /**
     * Getter qui return le fils du noeud à la position donnée
     * @param pos
     * @return Object
     */
    public Noeud getFils(int pos) {
        return fils[pos];
    }

    /**
     * Getter de la classe NoeudFeuille
     * @param i
     * @return Object
     */
    public Object getValeur(int i) {
        throw new UnsupportedOperationException();
    }

    /**
     * Setter qui permet de mettre à jour la fils d'un noeud à la position donnée
     * @param pos
     * @param fils
     */
    public void setFils(int pos, Noeud fils){
        this.fils[pos] = fils;
        if (fils != null) {
            fils.setParent(this);
        }
    }

    /**
     * Setter qui permet de mettre à jour la clef d'un noeud à la position donnée
     * @param pos
     * @param clef
     */
    public void setClef(int pos, Object clef){
        this.clef[pos] = clef;
    }

    /**
     * Méthode qui permet de return la position d'une clef dans un noeud
     * @param clef
     * @return int
     */
    public int chercher(Object clef) {
        int index;
        // On parcours le noeud de la gauche vers la droite
        for (index = 0; index < this.getNombreClef(); index++) {
            // On test si la clef est supérieur à la clef passée en paramètre
            int compare = comparer(clef, this.getClef(index));
            if (compare == 0) {
                // Si la clef est égale, alors on return la position de la clef dans le noeud
                return index + 1;
            }
            if (compare < 0){
                return index;
            }
        }
        // Sinon on return l'indice à la position courante
        return index;
    }

    /**
     * Méthode qui insère un couple clef / fils à une position placée en paramètre
     * @param pos
     * @param clef
     * @param n1
     * @param n2
     */
    public void insererIndex(int pos, Object clef, Noeud n1, Noeud n2){
        // On recherche quelle place on va inserer la clef et le fils.
        // On parcourt la noeud de la droite vers la gauche pour ne pas a retourner en arrière pour déplacer des clef / fils
        for (int i = this.getNombreClef(); i > pos; i--){
            // On déplace la clef pour faire de la place pour la clef que l'on va insérer
            this.setClef(i, this.getClef(i - 1));
        }
        for(int i = this.getNombreClef() + 1; i > pos; i--){
            // On déplace le fils de la clef que l'on va déplacer
            this.setFils(i, this.getFils(i - 1));
        }

        // On insere la clef et les fils à l'indice placer en paramètre
        this.setClef(pos, clef);
        this.setFils(pos, n1);
        this.setFils(pos + 1, n2);
        // On incremente le nombre de clé du noeud car une nouvelle clef a été inserée.
        this.nombreClef++;
    }

    /**
     * Méthode qui n'est pas utilisé dans les noeuds index de l'arbre.
     */
    public void supprimer(int pos){
        throw new UnsupportedOperationException();
    }

    /**
     * Méthode qui transfère une clef dans le noeud frère de droite ou de gauche
     * @param index
     * @param n1
     * @param n2
     */
    public void transfererFils(int index, Noeud n1, Noeud n2) {
        int indexFils = 0;

        while (indexFils <= this.getNombreClef() && this.getFils(indexFils) != n1){
            indexFils++;
        }

        if (index == 0) {
            // On prend une clef du fils droit
            Object clef = n1.trafererAvecFrere(index, this.getClef(indexFils), n2);
            this.setClef(indexFils, clef);
        }else{
            // On prend une clef du fils gauche
            Object clef = n1.trafererAvecFrere(index, this.getClef(indexFils - 1), n2);
            this.setClef(indexFils - 1, clef);
        }
    }

    /**
     * Méthode qui permet de fusionner deux noeud frère en un seul noeud
     * @param filsGauche
     * @param filsDroit
     * @return Noeud
     */
    public Noeud fusionnerFils(Noeud filsGauche, Noeud filsDroit) {
        int index = 0;
        while (index < this.getNombreClef() && this.getFils(index) != filsGauche) {
            ++index;
        }
        Object clef = this.getClef(index);

        // On fusionne les deux fils que l'on insere dans le fils gauche
        filsGauche.fusionnerAvecFrere(clef, filsDroit);

        // On supprime les éléments qui ont été fusionné
        this.supprimer(index);

        // On vérifie si on doit faire des modofication sur le parent
        if (this.estPasAssezPlein()) {
            if (this.getParent() == null) {
                // On supprime les clef du noeud racine
                if (this.getNombreClef() == 0) {
                    filsGauche.setParent(null);
                    return filsGauche;
                } else {
                    return null;
                }
            }
            return this.modificationPasAssezPlein();
        }
        return null;
    }

    /**
     * Méthode qui permet la fusion de deux noeud frère
     * @param clef
     * @param filsDroit
     */
    public void fusionnerAvecFrere(Object clef, Noeud filsDroit) {
        // On récupère le nombre de clef du noeud courant
        int nbClef = this.getNombreClef();
        this.setClef(nbClef + 1, clef);

        // On déplace l'ensemble des clefs concerner par la fusion
        for (int i = 0; i < filsDroit.getNombreClef(); ++i) {
            this.setClef(nbClef + i, filsDroit.getClef(i));
        }
        // On déplace l'ensembles des fils concerner par la fusion
        for (int i = 0; i < filsDroit.getNombreClef() + 1; ++i) {
            this.setFils(nbClef + i, filsDroit.getFils(i));
        }
        // On modifie le nombre de clefs du noeud fusionné
        this.nombreClef += 1 + filsDroit.getNombreClef();

        // On effectue les modifications concernant les fils du noeud concerner par la fusion
        this.setFilsDroit(filsDroit.filsDroit);
        if (filsDroit.filsDroit != null) {
            filsDroit.filsDroit.setFilsGauche(this);
        }
    }

    /**
     * Méthode qui transfère une clef dans le noeud frère
     * @param index
     * @param clef
     * @param frere
     * @return Object
     */
    public Object trafererAvecFrere(int index, Object clef, Noeud frere) {
        Object clefATransferer;
        if (index == 0) {
            // On prends la première clef du frere de droite
            int nbClef = this.getNombreClef();
            this.setClef(nbClef, clef);
            this.setFils(nbClef + 1, frere.getFils(index));
            this.nombreClef++;
            // On récupère la clef que l'on va transferer
            clefATransferer = this.getClef(0);
        }
        else {
            // On prend la derniere clef du frere de gauche et on la place en tete du frere de droite
            this.insererIndex(0, clef, frere.getFils(index + 1), this.getFils(0));
            clefATransferer = this.getClef(index);
        }
        // On return la clef que l'on doit transferer
        return clefATransferer;
    }

    /**
     * Méthode permet de remonter une clef dans le noeud parent si le noeud courant est concerné par un débordement
     * @param clefARemonter
     * @param filsGauche
     * @param filsDroit
     * @return Noeud
     */
    protected Noeud remonterClef(Object clefARemonter, Noeud filsGauche, Noeud filsDroit) {
        // On cherche la place de la clef que l'on va remonter
        int index = this.chercher(clefARemonter);

        // On insert la nouvelle clef
        this.insererIndex(index, clefARemonter, filsGauche, filsDroit);

        // On vérifie si le noeud dans lequel on a remonter la clef est plein
        if (this.estPlein()) {
            return this.modificationEstPlein();
        }else {
            if (this.getParent() == null) {
                return this;
            } else {
                return null;
            }
        }
    }

    /**
     * Méthode qui divise un noeud en deux et déplace les couples clefs / fils situé a droite de la séparation dans un nouveau noeud
     * @return Noeud
     */
    public NoeudIndex separation(){
        // On récupère l'index du milieu dans le noeud
        int milieu = this.getNombreClef() / 2;

        NoeudIndex ni = new NoeudIndex(ordre);

        for(int i = milieu + 1; i < this.getNombreClef(); i++) {
            ni.setClef(i - milieu - 1, this.getClef(i));
            this.setClef(i, null);
        }
        for (int i = milieu + 1; i <= this.getNombreClef(); i++){
            ni.setFils(i - milieu - 1, this.getFils(i));
            ni.getFils(i - milieu - 1).setParent(ni);
            this.setFils(i, null);
        }
        this.setClef(milieu, null);
        // On modifie la variable qui contient le nombre de clefs dans un noeud
        ni.nombreClef = this.getNombreClef() - milieu - 1;
        this.nombreClef = milieu;
        // On return le nouveau noeud feuille
        return ni;
    }
}
