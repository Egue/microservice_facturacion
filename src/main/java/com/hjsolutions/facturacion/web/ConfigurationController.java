package com.hjsolutions.facturacion.web;

import com.hjsolutions.facturacion.entity.Configurations;
import com.hjsolutions.facturacion.service.ConfigurationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/micro/invoice/config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigurationController {
    
    @Inject
    ConfigurationService configurationService;

    @POST
    public Response create(Configurations configurations){
        
        Configurations config = configurationService.save(configurations);

        return Response.ok(config).build();
    }

}
