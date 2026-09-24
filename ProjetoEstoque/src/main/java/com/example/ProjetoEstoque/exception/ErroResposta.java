package com.example.ProjetoEstoque.exception;

import java.time.LocalDateTime;

public class ErroResposta {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String mensagem;

    public ErroResposta(int status, String error, String mensagem) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.mensagem = mensagem;
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

    public String getMensagem() {
        return mensagem;
    }
}
