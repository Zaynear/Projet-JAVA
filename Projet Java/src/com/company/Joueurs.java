package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class Joueurs {
    private final int SIZE;
    Joueur[] joueurs;

    // constructeur
    public Joueurs(int SIZE) {
        this.SIZE = SIZE;
        joueurs = new Joueur[SIZE];
        creerJoueurs();
    }

    // tirer un joueur aléatoire du tableau
    public Joueur selectAlea() {
        int n = getRandomNumber(0,SIZE-1);

        while(!joueurs[n].getEtat().equals("en attente"))
            n = getRandomNumber(0,SIZE-1);

        return joueurs[n];
    }

    // remplir le tableau
    private void creerJoueurs() {
        for(int i=1; i<SIZE + 1; i++)
            joueurs[i-1] = new Joueur("joueur" + i);
    }

    // tirer un entier entre min et max compris
    public static int getRandomNumber(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @Override
    public String toString() {
        StringBuilder str;

        str = new StringBuilder("joueurs : | ");

        for(int i=0; i<SIZE; i++)
            str.append(joueurs[i].getNom()).append(" | ");

        return str.toString();
    }
}
