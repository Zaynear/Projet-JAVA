package com.company;

public class QuestionQCM extends TypeQuestion {
    private String rep1,rep2,rep3,repCorrecte;

    public QuestionQCM(String texte,int num, String repCorrecte, String indiceTheme, String rep1, String rep2, String rep3) {
        super(texte, num, indiceTheme);
        this.rep1 = rep1;
        this.rep2 = rep2;
        this.rep3 = rep3;
        this.repCorrecte = repCorrecte;
    }

    public String getReponses() {
        return rep1 + '\n' + rep2 + '\n' + rep3;
    }

    public String getRepCorrecte() {
        return repCorrecte;
    }
}
