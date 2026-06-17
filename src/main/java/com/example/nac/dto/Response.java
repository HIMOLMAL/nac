package com.example.nac.dto;

public class Response {
    private Long id;
    private String message;

    public Response(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}