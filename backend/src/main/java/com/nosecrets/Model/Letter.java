package com.nosecrets.model;

import java.time.LocalDateTime;

public class Letter {
    private int id;
    private boolean stat;            // true = respondida
    private String message;
    private String reply;            // null si no está respondida
    private int sender;
    private Integer replier;         // null si no está respondida
    private LocalDateTime sentAt;
    private LocalDateTime repliedAt; // null si no está respondida

    public Letter() {}

    public Letter(int id, boolean stat, String message, String reply,
                  int sender, Integer replier,
                  LocalDateTime sentAt, LocalDateTime repliedAt) {
        this.id = id;
        this.stat = stat;
        this.message = message;
        this.reply = reply;
        this.sender = sender;
        this.replier = replier;
        this.sentAt = sentAt;
        this.repliedAt = repliedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isStat() { return stat; }
    public void setStat(boolean stat) { this.stat = stat; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public int getSender() { return sender; }
    public void setSender(int sender) { this.sender = sender; }

    public Integer getReplier() { return replier; }
    public void setReplier(Integer replier) { this.replier = replier; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public LocalDateTime getRepliedAt() { return repliedAt; }
    public void setRepliedAt(LocalDateTime repliedAt) { this.repliedAt = repliedAt; }
}
