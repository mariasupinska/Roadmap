package com.example.roadmap;

public abstract class State {
    protected ItemCourseActivity activity;
    protected boolean isMarked;

    public State(ItemCourseActivity activity) {
        this.activity = activity;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    abstract void loadPractiseQuiz();

    abstract void loadMarkedQuiz();
}
