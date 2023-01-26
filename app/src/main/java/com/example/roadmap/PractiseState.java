package com.example.roadmap;

public class PractiseState extends State {

    public PractiseState(ItemCourseActivity activity) {
        super(activity);
    }

    @Override
    void loadPractiseQuiz() {
        isMarked = false;
    }

    @Override
    void loadMarkedQuiz() {

    }
}
