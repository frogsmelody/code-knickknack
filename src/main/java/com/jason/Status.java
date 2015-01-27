package com.jason;

public enum Status {
    RE_FETCH(1),
    PENDING(2),
    CANCELLED(3);

    private int score;

    Status(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
