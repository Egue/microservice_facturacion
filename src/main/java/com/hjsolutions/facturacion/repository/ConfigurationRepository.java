package com.hjsolutions.facturacion.repository;
 
import jakarta.enterprise.context.ApplicationScoped;
 

import com.hjsolutions.facturacion.entity.Configurations;

@ApplicationScoped
public class ConfigurationRepository {

    public Configurations addConfiguration(Configurations configuration){
        configuration.createdAt = System.currentTimeMillis();
        configuration.persist();

        return configuration;
    }

}
