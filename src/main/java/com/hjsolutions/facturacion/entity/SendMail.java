package com.hjsolutions.facturacion.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "send_mail")
public class SendMail {
    
    private String client;

    private String document;

    private String consecutivo;

    private String status;

    private String response;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    
}
