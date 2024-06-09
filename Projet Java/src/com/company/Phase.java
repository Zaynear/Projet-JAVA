package com.company;

public class Phase {
    private Joueur[] joueurs;

    // constructeur
    public Phase(Joueur[] joueurs) {
        this.joueurs = joueurs;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }
}
