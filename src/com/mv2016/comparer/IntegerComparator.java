package com.mv2016.comparer;

/**
 * Created by Matthieu on 05/10/2016.
 * @author Matthieu
 */
public class IntegerComparator implements Comparable {
    protected Integer valeur;

    /**
     * Constructeur de la classe IntegerComparator
     * @param valeur
     */
    public IntegerComparator(Integer valeur){
        this.valeur = valeur;
    }

    public int compareTo(Object o) {
        Integer result = this.valeur - ((Integer)o);
        if (result == 0) return 0;
        if (result < 0) return -1;
        return 1;
    }
}
