package com.example.ProejtoEstoque.exception;

import java.time.LocalDateTime;

public class ErroResposta {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String menssagem;

    public ErroResposta(int status, String error, String menssagem) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.menssagem = menssagem;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMenssagem() {
        return menssagem;
    }
}
