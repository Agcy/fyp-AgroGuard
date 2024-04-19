package com.api.agroguard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String senderName;
    private String receiverId;
    private String message;
    private String date;
    private Status status;
}
