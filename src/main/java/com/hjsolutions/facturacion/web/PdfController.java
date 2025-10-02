package com.hjsolutions.facturacion.web;

import com.hjsolutions.facturacion.service.GeneratePdfService;
import com.hjsolutions.facturacion.service.dto.InformatioInvoice;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/micro/invoice/pdf")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PdfController {

    @Inject
    GeneratePdfService generatePdfService;

     @POST
     public Response generate(InformatioInvoice invoice)
     {
        generatePdfService.generatePdfInvoiceCablemag(invoice);

        return Response.ok().build();
     }
    
}
