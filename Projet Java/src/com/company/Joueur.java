package com.company;

public class Joueur {
    //Attributs joueur
    private int numero;
    private String nom;
    private int score;
    private String etat;
    private static int cptj = 100;

    //Constructeur par défaut de la classe joueur
    public Joueur() {

    }

    //Constructeur de la classe joueur
    public Joueur(String nom) {
        this.numero = cptj;
        cptj+= 10;
        this.score = 0;
        this.nom = nom;
        this.etat = "en attente";
    }

    // getters
    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public String getEtat() {
        return etat;
    }

    //setters
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "joueur{" + "numéro=" + numero + ", nom='" + nom + '\'' + ", score=" + score + ", état='" + etat + '\'' + '}';
    }
}
