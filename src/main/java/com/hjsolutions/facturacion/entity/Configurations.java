package com.hjsolutions.facturacion.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "configurations")
public class Configurations extends PanacheMongoEntity {

    public String key;
    public String code;
    public String value;
    public long createdAt;
}
