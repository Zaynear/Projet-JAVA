package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String ans;
        Scanner scanner = new Scanner(System.in);
        Questions[] listeQuestions = new Questions[10];
        int numQuestion = 1;
        int numTheme = 0;
        String type;

        // initialisation du tableau contenant la liste des questions
        for(int i=0; i<10; i++) {
            listeQuestions[i] = new Questions();
        }

        //RECUPERATION DES QUESTIONS DEPUIS UN FICHIER
        try {
            // RECUPERATION DU FICHIER
            FileInputStream file = new FileInputStream("src" + File.separator + "com" + File.separator + "company"+ File.separator +"questions.txt");

            //LECTURE DU FICHIER
            Scanner scannerFichier = new Scanner(file);

            // on s'assure qu'on lit bien un fichier avec le bon en-tête
            if(scannerFichier.nextLine().equalsIgnoreCase("Question pour un champion !")) {
                // on recupère les questions tant qu'on n'a pas parcouru tout le fichier
                while(scannerFichier.hasNextLine()) {
                    TypeQuestion q = new TypeQuestion("",0,"");
                    // nouveau theme
                    if(scannerFichier.nextLine().equalsIgnoreCase("Theme !")) {
                        System.out.println("numTheme " + numTheme);
                        numQuestion = 1;
                        numTheme ++;
                        scannerFichier.nextLine();
                        scannerFichier.nextLine();
                    }

                    type = scannerFichier.nextLine();

                    // question QCM
                    switch (type) {
                        case "QCM":
                            System.out.println("ok qcm");
                            q = new QuestionQCM(scannerFichier.nextLine(), //texte
                                    numQuestion,                            //numéro de la question
                                    scannerFichier.nextLine(),                     //réponse correcte
                                    scannerFichier.nextLine(),                     //indice
                                    scannerFichier.nextLine(),                     //choix 1
                                    scannerFichier.nextLine(),                     //choix 2
                                    scannerFichier.nextLine()                      //choix 3
                            );
                            break;

                        // question Vrai/Faux
                        case "VF":
                            System.out.println("ok vf");
                            q = new QuestionVF(scannerFichier.nextLine(),                          //texte
                                    numQuestion,                                        //numéro de la question
                                    Boolean.parseBoolean(scannerFichier.nextLine()),    //réponse correcte (true/false)
                                    scannerFichier.nextLine()                           //indice
                            );
                            break;

                        // question à Réponse Courte
                        case "RC":
                            System.out.println("ok rc");
                            q = new QuestionRC(scannerFichier.nextLine(),  //texte
                                    numQuestion,                            //numéro de la question
                                    scannerFichier.nextLine(),                     //réponse correcte
                                    scannerFichier.nextLine()                      //indice
                            );
                            break;
                    }

                    listeQuestions[numTheme].ajoutQuestion(q);
                    System.out.println("num question " + numQuestion);
                    numQuestion ++;
                }
            }
            scannerFichier.close();

            System.out.println("Les questions ont été importées.");
        }
        // SI LE FICHIER N'A PAS ETE TROUVE
        catch(IOException e) {
            System.out.println("Le fichier n'a pas été trouvé !");
            e.printStackTrace();
        }

        // creation de la liste des 20 joueurs
        Joueurs listeJoueurs = new Joueurs(20);
        Joueur[] participant = new Joueur[4];


        ///////////////////////// BOUCLE PRINCIPALE/////////////////////////////////:
        while(true) {
            // selection des 4 joueurs pour la phase 1
            System.out.print("Les joueurs choisis sont: ");

            for(int i=0; i<4; i++) {
                participant[i] = listeJoueurs.selectAlea();
                participant[i].setEtat("participant");
                System.out.print(participant[i].getNom() + " ");
            }

            System.out.println('\n');

            // DEBUT DE LA PHASE 1
            Phase phase1 = new Phase(participant);
            Themes themes = new Themes();
            int theme1 = themes.selection1();
            int minScore;
            Joueur minParticipant;
            numQuestion = 0;

            System.out.println("Le thème choisi est : " + themes.getThemes(theme1));
            System.out.println();

            for(int i=0; i<3; i++) {
                for (int j = 0; j < phase1.getJoueurs().length; j++) {
                    System.out.println("La question est pour " + phase1.getJoueurs()[j].getNom() + ": ");
                    System.out.print(listeQuestions[theme1].getQuestions().get(numQuestion).getTexte());

                    // question VF
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionVF) {
                        System.out.println(" True / False ?");

                        // choix du joueur
                        ans = scanner.nextLine();

                        // entree incorrecte
                        while(!ans.equalsIgnoreCase("True") && !ans.equalsIgnoreCase("False")) {
                            System.out.println("Choix incorrect, merci de choisir True ou False: ");
                            ans = scanner.nextLine();
                        }

                        // test de la réponse
                        if(Boolean.parseBoolean(ans) == ((QuestionVF) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte()) {
                            phase1.getJoueurs()[j].setScore(phase1.getJoueurs()[j].getScore() + 2);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase1.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase1.getJoueurs()[j].getScore() + "\n");
                        }
                    }

                    // question RC
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionRC) {
                        System.out.println();

                        // choix du joueur
                        ans = scanner.nextLine();

                        // test de la réponse
                        if(ans.equalsIgnoreCase(((QuestionRC) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte())) {
                            phase1.getJoueurs()[j].setScore(phase1.getJoueurs()[j].getScore() + 2);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase1.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase1.getJoueurs()[j].getScore());
                            System.out.println("La bonne réponse était: " + ((QuestionRC) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte() + "\n");
                        }
                    }

                    // question QCM
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionQCM) {
                        System.out.println( "\n" + ((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getReponses());

                        // choix du joueur
                        ans = scanner.nextLine();

                        // test de la réponse
                        if(ans.equalsIgnoreCase(((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte())) {
                            phase1.getJoueurs()[j].setScore(phase1.getJoueurs()[j].getScore() + 2);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase1.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase1.getJoueurs()[j].getScore());
                            System.out.println("La bonne réponse était: " + ((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte() + "\n");
                        }
                    }
                    numQuestion ++;
                }
            }

            // FIN DE PHASE 1
            System.out.println("Les scores sont les suivants: ");
            minScore = phase1.getJoueurs()[0].getScore();
            minParticipant = phase1.getJoueurs()[0];

            for(int i=0; i<phase1.getJoueurs().length; i++) {
                if(phase1.getJoueurs()[i].getScore() <= minScore) {
                    minScore = phase1.getJoueurs()[i].getScore();
                    minParticipant = phase1.getJoueurs()[i];
                }
                System.out.print(phase1.getJoueurs()[i].getNom() + ": ");
                System.out.println(phase1.getJoueurs()[i].getScore() + " points \n");
            }
            System.out.println("Le joueur " + minParticipant.getNom() + " est éliminé, les autres passent à la phase 2.\n");
            Joueur[] participant2 = new Joueur[3];
            int k = 0;

            // nouveau tableau de participant
            for(int i=0; i<phase1.getJoueurs().length; i++) {
                if(phase1.getJoueurs()[i] != minParticipant) {
                    participant2[k] = phase1.getJoueurs()[i];
                    k++;
                }
            }

            // DEBUT DE LA PHASE 2
            Phase phase2 = new Phase(participant2);
            int theme6[] = themes.selection6();
            numQuestion = 4;

            for(int i=0; i<2; i++) {
                for (int j = 0; j < phase2.getJoueurs().length; j++) {
                    //On tire un thème aléatoire pour chaque question
                    theme1 = theme6[getRandomNumber(0,5)];
                    System.out.println("Le thème choisi est : " + themes.getThemes(theme1));
                    System.out.println();

                    System.out.println("La question est pour " + phase2.getJoueurs()[j].getNom() + ": ");
                    System.out.print(listeQuestions[theme1].getQuestions().get(numQuestion).getTexte());

                    // question VF
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionVF) {
                        System.out.println(" True / False ?");

                        // choix du joueur
                        ans = scanner.nextLine();

                        // entree incorrecte
                        while(!ans.equalsIgnoreCase("True") && !ans.equalsIgnoreCase("False")) {
                            System.out.println("Choix incorrect, merci de choisir True ou False: ");
                            ans = scanner.nextLine();
                        }

                        // test de la réponse
                        if(Boolean.parseBoolean(ans) == ((QuestionVF) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte()) {
                            phase2.getJoueurs()[j].setScore(phase2.getJoueurs()[j].getScore() + 3);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase2.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase2.getJoueurs()[j].getScore() + "\n");
                        }
                    }

                    // question RC
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionRC) {
                        System.out.println();

                        // choix du joueur
                        ans = scanner.nextLine();

                        // test de la réponse
                        if(ans.equalsIgnoreCase(((QuestionRC) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte())) {
                            phase2.getJoueurs()[j].setScore(phase2.getJoueurs()[j].getScore() + 3);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase2.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase2.getJoueurs()[j].getScore());
                            System.out.println("La bonne réponse était: " + ((QuestionRC) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte() + "\n");
                        }
                    }

                    // question QCM
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionQCM) {
                        System.out.println( "\n" + ((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getReponses());

                        // choix du joueur
                        ans = scanner.nextLine();

                        // test de la réponse
                        if(ans.equalsIgnoreCase(((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte())) {
                            phase2.getJoueurs()[j].setScore(phase2.getJoueurs()[j].getScore() + 3);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase2.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase2.getJoueurs()[j].getScore());
                            System.out.println("La bonne réponse était: " + ((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte() + "\n");
                        }
                    }
                    numQuestion ++;
                }
            }

            // FIN DE PHASE 2
            System.out.println("Les scores sont les suivants: ");
            minScore = phase2.getJoueurs()[0].getScore();
            minParticipant = phase2.getJoueurs()[0];

            for(int i=0; i<phase2.getJoueurs().length; i++) {
                if(phase2.getJoueurs()[i].getScore() <= minScore) {
                    minScore = phase2.getJoueurs()[i].getScore();
                    minParticipant = phase2.getJoueurs()[i];
                }
                System.out.print(phase2.getJoueurs()[i].getNom() + ": ");
                System.out.println(phase2.getJoueurs()[i].getScore() + " points \n");
            }
            System.out.println("Le joueur " + minParticipant.getNom() + " est éliminé, les autres passent à la phase 3. \n");
            Joueur[] participant3 = new Joueur[2];
            k = 0;

            // nouveau tableau de participant
            for(int i=0; i<phase2.getJoueurs().length; i++) {
                if(phase2.getJoueurs()[i] != minParticipant) {
                    participant3[k] = phase2.getJoueurs()[i];
                    k++;
                }
            }

            // DEBUT DE LA PHASE 3
            Phase phase3 = new Phase(participant3);
            int theme3[] = themes.selection3();
            numQuestion = 6;

            for(int i=0; i<2; i++) {
                for (int j = 0; j < phase3.getJoueurs().length; j++) {
                    //On tire un thème aléatoire pour chaque question
                    theme1 = theme3[getRandomNumber(0,2)];
                    System.out.println("Le thème choisi est : " + themes.getThemes(theme1));
                    System.out.println();

                    System.out.println("La question est pour " + phase3.getJoueurs()[j].getNom() + ": ");
                    System.out.print(listeQuestions[theme1].getQuestions().get(numQuestion).getTexte());

                    // question VF
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionVF) {
                        System.out.println(" True / False ?");

                        // choix du joueur
                        ans = scanner.nextLine();

                        // entree incorrecte
                        while(!ans.equalsIgnoreCase("True") && !ans.equalsIgnoreCase("False")) {
                            System.out.println("Choix incorrect, merci de choisir True ou False: ");
                            ans = scanner.nextLine();
                        }

                        // test de la réponse
                        if(Boolean.parseBoolean(ans) == ((QuestionVF) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte()) {
                            phase3.getJoueurs()[j].setScore(phase3.getJoueurs()[j].getScore() + 3);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase3.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase3.getJoueurs()[j].getScore() + "\n");
                        }
                    }

                    // question RC
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionRC) {
                        System.out.println();

                        // choix du joueur
                        ans = scanner.nextLine();

                        // test de la réponse
                        if(ans.equalsIgnoreCase(((QuestionRC) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte())) {
                            phase3.getJoueurs()[j].setScore(phase3.getJoueurs()[j].getScore() + 3);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase3.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase3.getJoueurs()[j].getScore());
                            System.out.println("La bonne réponse était: " + ((QuestionRC) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte() + "\n");
                        }
                    }

                    // question QCM
                    if(listeQuestions[theme1].getQuestions().get(numQuestion) instanceof QuestionQCM) {
                        System.out.println( "\n" + ((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getReponses());

                        // choix du joueur
                        ans = scanner.nextLine();

                        // test de la réponse
                        if(ans.equalsIgnoreCase(((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte())) {
                            phase3.getJoueurs()[j].setScore(phase3.getJoueurs()[j].getScore() + 3);
                            System.out.println("Bonne réponse, ton score est désormais de: " + phase3.getJoueurs()[j].getScore() + "\n");
                        }
                        else {
                            System.out.println("Mauvaise réponse, ton score reste à: " + phase3.getJoueurs()[j].getScore());
                            System.out.println("La bonne réponse était: " + ((QuestionQCM) listeQuestions[theme1].getQuestions().get(numQuestion)).getRepCorrecte() + "\n");
                        }
                    }
                    numQuestion ++;
                }
            }

            // FIN DE PHASE 3
            System.out.println("Les scores sont les suivants: ");
            minScore = phase3.getJoueurs()[0].getScore();
            minParticipant = phase3.getJoueurs()[0];

            for(int i=0; i<phase3.getJoueurs().length; i++) {
                if(phase3.getJoueurs()[i].getScore() >= minScore) {
                    minScore = phase3.getJoueurs()[i].getScore();
                    minParticipant = phase3.getJoueurs()[i];
                }
                System.out.print(phase3.getJoueurs()[i].getNom() + ": ");
                System.out.println(phase3.getJoueurs()[i].getScore() + " points \n");
            }
            System.out.println("Le joueur " + minParticipant.getNom() + " a gagné, bravo à toi. \n \n \n");

            // FIN DE PARTIE
            System.out.println("Souhaitez vous jouer une nouvelle partie ? - o/n ?");
            ans = scanner.nextLine();
            // entree incorrecte
            while(!ans.equalsIgnoreCase("o") && !ans.equalsIgnoreCase("n")) {
                System.out.println("Choix incorrect, merci de choisir o ou n: ");
                ans = scanner.nextLine();
            }
            // pas de nouvelle partie
            if(ans.equalsIgnoreCase("n")) {
                System.out.println("Merci d'avoir joué ! Au revoir.");
                return;
            }
            // nouvelle partie -> retour au debut de la boucle while
        }
    }

    // tirer un entier entre min et max compris
    public static int getRandomNumber(int min, int max){
        return (int) (Math.random() * (max - min));
    }
}
