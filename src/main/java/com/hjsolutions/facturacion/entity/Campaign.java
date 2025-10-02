package com.hjsolutions.facturacion.entity;

import java.util.List;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "campaign")
public class Campaign {
    private String ano;

    private String mes;

    private String name;

    private List<SendMail> sendMail;

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SendMail> getSendMail() {
        return sendMail;
    }

    public void setSendMail(List<SendMail> sendMail) {
        this.sendMail = sendMail;
    }

    
}
