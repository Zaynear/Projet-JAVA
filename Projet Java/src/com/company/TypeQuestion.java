package com.company;

public class TypeQuestion extends Questions {
    private String texte;
    private int num;
    private String indiceTheme;

    public TypeQuestion(String texte, int num, String indiceTheme) {
        this.texte = texte;
        this.num = num;
        this.indiceTheme = indiceTheme;
    }

    public int getNum() {
        return num;
    }

    public String getTexte() {
        return texte;
    }
}

