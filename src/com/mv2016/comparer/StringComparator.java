package com.mv2016.comparer;

/**
 * Created by Matthieu on 05/10/2016.
 * @author Matthieu
 */
public class StringComparator implements Comparable {
    protected String text;

    /**
     * Constructeur de la classe StringComparator
     * @param text
     */
    public StringComparator(String text){
        this.text = text;
    }

    public int compareTo(Object o) {
        return this.text.compareTo((String)o);
    }
}
