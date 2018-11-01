package com.stage1.Models;

public class ChatNode {
    String sender_id;
    String sender_name;
    String sender_img;
    String receiver_id;
    String receiver_name;
    String receiver_img;
    String time;
    String message;

    public ChatNode() {
    }

    public ChatNode(String receiverid, String receiverfirstname, String receoverphoto, String senderid, String senderlastname, String senderphoto, String text, String timestamp) {
        this.sender_id = senderid;
        this.sender_name = senderlastname;
        this.sender_img = senderphoto;
        this.receiver_id = receiverid;
        this.receiver_name = receiverfirstname;
        this.receiver_img = receoverphoto;
        this.message = text;
        this.time = timestamp;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_img() {
        return sender_img;
    }

    public void setSender_img(String sender_img) {
        this.sender_img = sender_img;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_img() {
        return receiver_img;
    }

    public void setReceiver_img(String receiver_img) {
        this.receiver_img = receiver_img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
