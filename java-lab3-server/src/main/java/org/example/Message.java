package org.example;

import java.io.Serializable;

public class Message implements Serializable {
    private final int number;
    private final String content;

    public Message(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public int getNumber() {
        return this.number;
    }

    public String getContent() {
        return this.content;
    }
}
