package com.main.hooker.hooker.entity;

public class Bubble {
    private static boolean temp = true;
    private boolean left;
    private String character;
    private String content;

    public Bubble() {
        left = temp;
        if (left) {
            character = "Alice";
            content = "I am Alice, a lovely pig.";
        } else {
            character = "John";
            content = "I am John, not a lovely pig.";
        }
        temp = !temp;
    }

    public Bubble(String character, String content) {
        this.character = character;
        this.content = content;
        left = temp;
        temp = !temp;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLeft() {
        return left;
    }
}
