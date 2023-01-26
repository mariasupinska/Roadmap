package com.example.roadmap;

public class MarkedState extends State {

    public MarkedState(ItemCourseActivity activity) {
        super(activity);
    }

    @Override
    void loadPractiseQuiz() {

    }

    @Override
    void loadMarkedQuiz() {
        isMarked = true;
    }
}
