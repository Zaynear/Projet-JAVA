package com.company;

public class QuestionVF extends TypeQuestion {
    private boolean repCorrecte;

    public QuestionVF(String texte, int num, boolean repCorrecte, String indiceTheme) {
        super(texte, num, indiceTheme);
        this.repCorrecte = repCorrecte;
    }

    public boolean getRepCorrecte() {
        return repCorrecte;
    }
}
