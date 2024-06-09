package com.company;

public class QuestionRC extends TypeQuestion {
    private String repCorrecte;

    public QuestionRC(String texte, int num, String repCorrecte, String indiceTheme) {
        super(texte, num, indiceTheme);
        this.repCorrecte = repCorrecte;
    }

    public String getRepCorrecte() {
        return repCorrecte;
    }
}
