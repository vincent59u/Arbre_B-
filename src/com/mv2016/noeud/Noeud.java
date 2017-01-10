package com.mv2016.noeud;

import com.mv2016.comparer.IntegerComparator;
import com.mv2016.comparer.StringComparator;

/**
 * Created by Matthieu on 21/09/2016.
 * @author Matthieu VINCENT
 */
abstract public class Noeud{
    protected Object[] clef;
    protected int nombreClef;
    protected short ordre;
    protected Noeud parent, filsDroit, filsGauche;

    /**
     * Constructeur d'un Noeud
     */
    public Noeud(short ordre){
        this.ordre = ordre;
        this.nombreClef = 0;
        this.parent = null;
        this.filsDroit = null;
        this.filsGauche = null;
    }

    /**
     * Getter qui return le type d'un noeud
     * @return TypeNoeud
     */
    abstract public TypeNoeud getTypeNoeud();

    /**
     * Méthode qui sépare un noeud en deux et transfère les données concernées par la séparation dans le nouveau noeud
     * @return Noeud
     */
    abstract public Noeud separation();

    /**
     * Méthode qui supprime la clef dans un noeud à l'indice placé en paramètre
     * @param pos
     */
    abstract public void supprimer(int pos);

    /**
     * Getter qui return le fils d'un noeud
     * @param index
     * @return Noeud
     */
    abstract public Noeud getFils(int index);

    /**
     * Getter qui return la valeur d'un noeud feuille
     * @param i
     * @return Object
     */
    abstract public Object getValeur(int i);

    /**
     * Méthode qui transfère le fils d'un noeud
     * @param index
     * @param n1
     * @param n2
     */
    abstract public void transfererFils(int index, Noeud n1, Noeud n2);

    /**
     * Méthode qui fusionne le fils d'un noeud
     * @param filsGauche
     * @param filsDroit
     * @return Noeud
     */
    abstract public Noeud fusionnerFils(Noeud filsGauche, Noeud filsDroit);

    /**
     * Méthode qui fusionne deux noeud frère ensemble
     * @param clef
     * @param filsDroit
     */
    abstract public void fusionnerAvecFrere(Object clef, Noeud filsDroit);

    /**
     * Méthode qui transfère les clefs d'un noeud a son frère
     * @param index
     * @param clef
     * @param frere
     * @return Object
     */
    abstract public Object trafererAvecFrere(int index, Object clef, Noeud frere);

    /**
     * Méthode qui permet de remonter une clef dans le noeud parent lors d'un débordement
     * @param clefARemonter
     * @param filsGauche
     * @param filsDroit
     * @return Noeud
     */
    abstract protected Noeud remonterClef(Object clefARemonter, Noeud filsGauche, Noeud filsDroit);

    /**
     * Getter qui return le nombre de clef présent dans le noeud
     * @return int
     */
    public int getNombreClef() {
        return nombreClef;
    }

    /**
     * Getter qui return la clef d'un noeud à une position donnée
     * @param pos
     * @return Object
     */
    public Object getClef(int pos){
        return this.clef[pos];
    }

    /**
     * Setter qui insère une clef à une position donnée
     * @param pos
     * @param clef
     */
    public void setClef(int pos, Object clef){
        this.clef[pos] = clef;
    }

    /**
     * Getter qui return le parent d'un noeud
     * @return Noeud
     */
    public Noeud getParent(){
        return this.parent;
    }

    /**
     * Setter qui instancie le noeud parent d'un noeud donné
     * @param parent
     */
    public void setParent(Noeud parent){
        this.parent = parent;
    }

    /**
     * Setter qui ajoute un fils droit au noeud courant
     * @param fd
     */
    public void setFilsDroit(Noeud fd){
        this.filsDroit = fd;
    }

    /**
     * Setter qui ajoute un fils gauche au noeud courant
     * @param fg
     */
    public void setFilsGauche(Noeud fg){
        this.filsGauche = fg;
    }

    /**
     * Getter qui return le fils droit du noeud courant
     * @return Noeud
     */
    public Noeud getFilsDroit(){
        if (this.filsDroit != null && this.filsDroit.getParent() == this.getParent()) {
            return this.filsDroit;
        }else{
            return null;
        }
    }

    /**
     * Getter qui return le fils gauche du noeud courant
     * @return Noeud
     */
    public Noeud getFilsGauche(){
        if (this.filsGauche != null && this.filsGauche.getParent() == this.getParent()) {
            return this.filsGauche;
        }else{
            return null;
        }
    }

    /**
     * Méthode qui return l'indice de la position à laquelle se trouve la clef placée en paramètre si elle se trouve dans le noeud
     * @param clef
     * @return int
     */
    abstract public int chercher(Object clef);

    /**
     * Méthode qui test si un noeud est plein
     * @return boolean
     */
    public boolean estPlein(){
        return this.getNombreClef() == this.ordre + 1;
    }

    /**
     * Méthode qui test si un noeud peut se séparer en deux
     * @return boolean
     */
    public boolean peutSeSeparer(){
        return !this.estPasAssezPlein();
    }

    /**
     * Méthode qui test si un noeud n'est pas assez plein
     * @return boolean
     */
    public boolean estPasAssezPlein(){
        return this.getNombreClef() < (this.ordre + 1) / 2;
    }

    /**
     * Méthode qui modifie la structure de l'arbre lorsqu'un noeud n'est pas assez plein
     * @return Noeud
     */
    public Noeud modificationPasAssezPlein() {
        // Si le noeud courant n'a pas de parent
        if (this.getParent() == null) {
            // On return null et on va voir ces fils pour faire les modifications
            return null;
        }

        // On essaye de prendre une clef de son fils droit
        Noeud filsDroit = this.getFilsDroit();
        // Si le noeud a un fils droit et que le fils droit peut se séparer
        if (filsDroit != null && filsDroit.peutSeSeparer()) {
            // On precède au transfère du fils droit avec son noeud père
            this.getParent().transfererFils(0, this, filsDroit);
            return null;
        }

        // On essaye de prendre une clef de son fils gauche
        Noeud filsGauche = this.getFilsGauche();
        // Si le noeud a un fils gauche et que le fils gauche peut se séparer
        if (filsGauche != null && filsGauche.peutSeSeparer()){
            // On precède au transfère du fils gauche avec son noeud père
            this.getParent().transfererFils(filsGauche.getNombreClef() - 1, this, filsGauche);
            return null;
        }

        // Si on ne peut pas transferer une clef d'un des fils, on fusionne les deux fils du noeud
        if (filsDroit != null) {
            return this.getParent().fusionnerFils(filsDroit, this);
        }
        else {
            return this.getParent().fusionnerFils(this, filsGauche);
        }
    }

    /**
     * Méthode qui modifie la structure de l'arbre lorsqu'un noeud à dépassé la capacité maximale
     * @return Noeud
     */
    public Noeud modificationEstPlein(){
        int milieu = this.getNombreClef() / 2;

        Object clefARemonter = this.getClef(milieu);

        // On place le resultat du split dans un nouveau noeud
        Noeud n = this.separation();

        // Si le noeud courant n'a pas de parent on lui en donne un pour qu'il prenne la clef que l'on va remonter
        if (this.getParent() == null) {
            this.setParent(new NoeudIndex(this.ordre));
        }

        n.setParent(this.getParent());

        // On effectue les changements pour garder les liens avec les fils du nouveau noeud
        n.setFilsGauche(this);
        n.setFilsDroit(this.filsDroit);
        if (this.getFilsDroit() != null) {
            this.getFilsDroit().setFilsGauche(n);
        }
        this.setFilsDroit(n);
        // On remonte la clef concernée par le split dans le noeud parent
        return this.getParent().remonterClef(clefARemonter, this, n);
    }

    /**
     * Méthode qui permet de comparer deux object
     * @param o
     * @param o1
     * @return int
     */
    public int comparer(Object o, Object o1){
        if (o != null && o1 != null) {
            if (o.getClass().getName() == "java.lang.Integer" && o1.getClass().getName() == "java.lang.Integer") {
                IntegerComparator ic = new IntegerComparator((Integer) o);
                return ic.compareTo(o1);
            } else {
                StringComparator sc = new StringComparator((String) o);
                return sc.compareTo(o1);
            }
        }
        return 0;
    }
}
