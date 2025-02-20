package com.hyd.hym.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class HymUser {
    private long id;
    private String username;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
