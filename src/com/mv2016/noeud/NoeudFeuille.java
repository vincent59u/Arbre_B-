package com.mv2016.noeud;

/**
 * Created by Matthieu on 22/09/2016.
 * @author Matthieu VINCENT
 */
public class NoeudFeuille extends Noeud {
    // Tableau d'object qui contient les valeurs que l'on stock dans le noeud
    private Object[] valeur;

    /**
     * Constructeur de la classe noeud feuille
     * @param ordre
     */
    public NoeudFeuille(short ordre){
        super(ordre);
        this.clef = new Object[ordre + 1];
        this.valeur = new Object[ordre + 1];
    }

    /**
     * Getter qui return le type du noeud
     * @return TypeNoeud
     */
    public TypeNoeud getTypeNoeud(){
        return TypeNoeud.Feuille;
    }

    /**
     * Getter qui permet de récuperer la valeur du noeud à la position pos
     * @param pos
     * @return Object
     */
    public Object getValeur(int pos) {
        return valeur[pos];
    }

    /**
     * Setter qui permet de mettre à jour la valeur d'un noeud à la position donnée
     * @param pos
     * @param valeur
     */
    public void setValeur(int pos, Object valeur){
        this.valeur[pos] = valeur;
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
        // On parcours le noeud de la gauche vers la droite
        for (int i = 0; i < this.getNombreClef(); ++i) {
            // On test si la clef est égal à la clef passée en paramètre
            int compare = comparer(clef, this.getClef(i));
            if (compare == 0) {
                // Si la clef est égale, alors on return la position de la clef dans le noeud
                return i;
            }else if (compare < 0){
                return -1;
            }
        }
        // Sinon on return -1 pour dire que la clef n'existe pas dans ce noeud
        return -1;
    }

    /**
     * Méthode qui insère une clef et une valeur dans le noeud
     * @param clef
     * @param valeur
     */
    public void insererClef(Object clef, Object valeur){
        int i = 0;
        while (comparer(clef, this.getClef(i)) > 0 && i < this.getNombreClef()){
            i++;
        }
        this.insererFeuille(i, clef, valeur);
    }

    /**
     * Méthode qui insère un couple clef / valeur à une position précise
     * Cette position est déterminer dans la méthode insererClef
     * @param index
     * @param clef
     * @param valeur
     */
    public void insererFeuille(int index, Object clef, Object valeur){
        // On recherche quelle place on va inserer la clef et la valeur.
        // On parcourt la noeud de la droite vers la gauche pour ne pas a retourner en arrière pour déplacer des clef / valeur
        for(int i = this.getNombreClef() - 1; index <= i; i--){
            // On déplace la clé vers la droite afin de faire une place pour la nouvelle clef à inserer
            this.setClef(i + 1, this.getClef(i));
            // On fait de même pour la valeur associé à la clef
            this.setValeur(i + 1, this.getValeur(i));
        }

        // On insere la clef et la valeur à l'indice placer en paramètre
        this.setClef(index, clef);
        this.setValeur(index, valeur);
        // On incremente le nombre de clé du noeud car une nouvelle clef a été inserée.
        this.nombreClef++;
    }

    /**
     * Méthode qui return un boolean pour notifier la suppression d'un noeud
     * @param clef
     * @return boolean
     */
    public boolean estSupprimer(Object clef){
        // On recherche ou se trouve la clef que l'on veux supprimer
        int index = this.chercher(clef);
        // Si la clef n'est pas présente dans le noeud, on return false
        if (index == -1){
            return false;
        }

        // Si la clef est présente dans le noeud, on appelle la méthode qui va supprimer le couple clef / val et return true
        this.supprimer(index);
        return true;
    }

    /**
     * Méthode qui supprime un couple clef / val à un index donné. Toutes les clefs et valeurs sont repositionnée lors de la suppression
     * @param index
     */
    public void supprimer(int index){
        // On parcourt le noeud de la gauche vers la droite
        for (int i = index; i < this.getNombreClef() - 1; i++){
            // On deplace la clef et la valeur pour que le noeud soit ordonné après la suppression
            this.setClef(i, getClef(i + 1));
            this.setValeur(i, getValeur(i + 1));
        }
        this.setClef(this.getNombreClef(), null);
        this.setValeur(this.getNombreClef(), null);
        // On decremente le nombre de clef du lien
        this.nombreClef--;
    }

    /**
     * Méthode de la classe NoeudIndex
     * @param index
     * @return Noeud
     */
    public Noeud getFils(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Méthode qui transfère une clef dans le noeud frère de droite ou de gauche
     * @param index
     * @param n1
     * @param n2
     */
    public void transfererFils(int index, Noeud n1, Noeud n2) {
        throw new UnsupportedOperationException();
    }

    /**
     * Méthode qui permet de fusionner deux noeud frère en un seul noeud
     * @param filsGauche
     * @param filsDroit
     * @return Noeud
     */
    public Noeud fusionnerFils(Noeud filsGauche, Noeud filsDroit) {
        throw new UnsupportedOperationException();
    }

    /**
     * Méthode qui permet la fusion de deux noeud frère
     * @param clef
     * @param frereDroit
     */
    public void fusionnerAvecFrere(Object clef, Noeud frereDroit) {
        // On déplace les clefs et les valeurs du noeud frère
        for (int i = 0; i < frereDroit.getNombreClef(); ++i) {
            this.setClef(this.getNombreClef() + i, frereDroit.getClef(i));
            this.setValeur(this.getNombreClef() + i, frereDroit.getValeur(i));
        }
        // On modifie le nombre de clef du noeud concerné par la fusion
        this.nombreClef += frereDroit.getNombreClef();

        // On effectue les modifications concernant les fils du noeud concerner par la fusion
        this.setFilsDroit(frereDroit.filsDroit);
        if (frereDroit.filsDroit != null) {
            frereDroit.filsDroit.setFilsGauche(this);
        }
    }

    /**
     * Méthode qui permet de fusionner deux noeud frère en un seul noeud
     * @param index
     * @param clef
     * @param frere
     * @return Noeud
     */
    public Object trafererAvecFrere(int index, Object clef, Noeud frere) {
        // On insère la clef dans le noeud frère
        this.insererClef(frere.getClef(index), frere.getValeur(index));
        // On supprime la clef que l'on vient de retirer du noeud frère
        frere.supprimer(index);

        // On return la clef que l'on a transféré
        if(index == 0){
            return frere.getClef(0);
        }else{
            return this.getClef(0);
        }
    }

    /**
     * Méthode qui permet de remonter une clef d'un noeud concerné par un débordement
     * @param clefARemonter
     * @param filsGauche
     * @param filsDroit
     * @return Noeud
     */
    protected Noeud remonterClef(Object clefARemonter, Noeud filsGauche, Noeud filsDroit) {
        throw new UnsupportedOperationException();
    }

    /**
     * Méthode qui divise un noeud en deux et déplace les couples clefs / valeurs situé a droite de la séparation dans un nouveau noeud
     * @return Noeud
     */
    public Noeud separation(){
        // On récupère l'index du milieu dans le noeud
        int milieu = this.getNombreClef() / 2;

        // On déclare un nouveau noeud feuille
        NoeudFeuille nf = new NoeudFeuille(this.ordre);
        for (int i = milieu; i < this.getNombreClef(); i++){
            // On ajoute toutes les clefs et valeurs supérieur à la médiane du noeud séperé
            nf.setClef(i - milieu, this.getClef(i));
            nf.setValeur(i - milieu, this.getValeur(i));
            // On supprime les clefs et valeurs qui ont été ajoutée dans le nouveau noeud
            this.setClef(i, null);
            this.setValeur(i, null);
        }

        // On modifie la variable qui contient le nombre de clefs dans un noeud
        nf.nombreClef = this.getNombreClef() - milieu;
        this.nombreClef = milieu;
        // On return le nouveau noeud feuille
        return nf;
    }
}
