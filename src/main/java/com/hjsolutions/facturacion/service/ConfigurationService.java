package com.hjsolutions.facturacion.service;

import com.hjsolutions.facturacion.entity.Configurations;
import com.hjsolutions.facturacion.repository.ConfigurationRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ConfigurationService {
    
    @Inject
    ConfigurationRepository configurationRepository;

    public Configurations save(Configurations configuration){

        return configurationRepository.addConfiguration(configuration);
    }
}
