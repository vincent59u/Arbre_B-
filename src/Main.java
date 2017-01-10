import com.mv2016.noeud.*;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Matthieu on 22/09/2016.
 * @author Matthieu
 */
public class Main {
    public static void main(String[] args) {
        short ordre;
        boolean quitter = false;
        char choix;
        Scanner sc = new Scanner(System.in);

        System.out.println("++ PROJET IMPLEMENTATION D'ARBRES B+ PAR MATTHIEU VINCENT ++");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        do{
            System.out.println("Veuillez saisir un ordre pour votre arbre b+ : ( >0 )");
            ordre = sc.nextShort();
        }while (ordre <= 0);

        BPlus bpAge = new BPlus(ordre);
        BPlus bpPrenom = new BPlus(ordre);
        try{
            FileReader fr = new FileReader("fichierTest/test.txt");
            BufferedReader br = new BufferedReader(fr);
            String ligne;
            while ((ligne = br.readLine()) != null){
                String[] tab = ligne.split(":");
                Integer age = Integer.parseInt(tab[1]);
                String prenom = tab[0];
                bpAge.inserer(age, prenom);
                bpPrenom.inserer(prenom, age);
            }
            br.close();
        }
        catch (Exception e){
            System.out.println("Erreur de lecture dans le fichier : " + e.toString());
        }

        System.out.println("Deux arbre b+ viennent d'être créer grâce au fichier de test fourni." +
                "\nUn arbre a pour clef des âges et l'autre a pour clef des prénoms." +
                "\nVous pouvez maintenant éffectuer des opérations sur ces arbres (Lire le README.pdf)");

        do {
            do {
                System.out.println("1 - Insérer un nouveau couple âge / prénom");
                System.out.println("2 - Rechercher un prénom grâce à un âge");
                System.out.println("3 - Rechercher un âge grâce à un prénom");
                System.out.println("4 - Supprimer un prénom grâce à un âge");
                System.out.println("5 - Supprimer un âge grâce à un prénom");
                System.out.println("Q - Quitter l'application");

                choix = sc.next().charAt(0);

                if (choix == '1'){
                    System.out.println("Saisir un âge : ");
                    Integer age = sc.nextInt();
                    System.out.println("Saisir un prénom : ");
                    String prenom = sc.next();
                    try{
                        bpAge.inserer(age, prenom);
                        bpPrenom.inserer(prenom, age);
                        System.out.println("Le couple " + age + " / " + prenom + " a bien été inséré\n");
                    }catch(Exception e){
                        System.out.println("Une erreur est survenue lors de l'insérsion");
                    }
                }

                if (choix == '2'){
                    System.out.println("Saisir un âge :");
                    Integer age = sc.nextInt();
                    String prenom = (String)bpAge.chercher(age);
                    if (prenom != null){
                        System.out.println("La personne qui a " + age + " ans est " + prenom + "\n");
                    }else{
                        System.out.println("Personne n'a " + age + " ans dans l'arbre\n");
                    }
                }

                if (choix == '3'){
                    System.out.println("Saisir un prénom :");
                    String prenom = sc.next();
                    Integer age = (Integer)bpPrenom.chercher(prenom);
                    if (age != null){
                        System.out.println(prenom + " a " + age + " ans\n");
                    }else{
                        System.out.println(prenom + " n'est pas présent dans l'arbre\n");
                    }
                }

                if (choix == '4'){
                    System.out.println("Saisir un âge :");
                    Integer age = sc.nextInt();
                    try {
                        String prenom = (String)bpAge.chercher(age);
                        bpAge.supprimer(age);
                        bpPrenom.supprimer(prenom);
                        System.out.println("Suppression réussi\n");
                    }catch (Exception e){
                        System.out.println("Une erreur est survenue lors de la suppression\n");
                    }
                }

                if (choix == '5'){
                    System.out.println("Saisir un prénom :");
                    String prenom = sc.next();
                    try {
                        Integer age = (Integer)bpPrenom.chercher(prenom);
                        bpPrenom.supprimer(prenom);
                        bpAge.supprimer(age);
                        System.out.println("Suppression réussi\n");
                    }catch (Exception e){
                        System.out.println("Une erreur est survenue lors de la suppression\n");
                    }
                }

                if (choix == 'Q'){
                    quitter = true;
                }

            }while(choix != '1' && choix != '2' && choix != '3' && choix != '4' && choix == '5' && choix != 'Q');
        }while (!quitter);

        System.out.println("Au revoir...");
    }
}