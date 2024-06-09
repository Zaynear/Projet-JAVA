package com.company;

public class Themes {
    private String theme;
    private int indice;
    private String[] themes = new String[10];

    public Themes() {
        indice = 10;

        themes[0] = "Sciences";
        themes[1] = "Sport";
        themes[2] = "Histoire";
        themes[3] = "Geographie";
        themes[4] = "CultureGenerale";
        themes[5] = "Mythologie";
        themes[6] = "Litterature";
        themes[7] = "Cinema";
        themes[8] = "Economie";
        themes[9] = "Musique";
    }

    public int selection1() {
        indice ++;

        if (indice > 9)
            indice = 0;
        return indice;
    }

    public int[] selection6() {
        int[] indice = new int[6];

        for(int i = 0; i<6; i++) {
            this.indice ++;

            if (this.indice > 9)
                this.indice = 0;

            indice[i] = this.indice;
        }
        return indice;
    }

    public int[] selection3() {
        int[] indice = new int[3];

        for(int i = 0; i<3; i++) {
            this.indice ++;

            if (this.indice > 9)
                this.indice = 0;

            indice[i] = this.indice;
        }
        return indice;
    }

    public String getThemes(int i) {
        return themes[i];
    }

    public String toString() {
        String str = "";
        for(int i=1; i<11; i++)
            str += i + " - " + themes[i-1] + '\n';

        return str;
    }
}
