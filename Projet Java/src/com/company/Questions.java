package com.company;

import java.util.ArrayList;

public class Questions {
    private ArrayList<TypeQuestion> questions;

    public Questions() {
        questions = new ArrayList<>();
    }

    public void ajoutQuestion(TypeQuestion question) {
        questions.add(question);
    }

    public ArrayList<TypeQuestion> getQuestions() {
        return questions;
    }
}
