package org.acme;

import service.CargaCsvService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    CargaCsvService csvService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/testCsv")
    @Produces(MediaType.TEXT_PLAIN)
    public String testCsv(){
        String respuesta = " ";
        respuesta = csvService.cargaCsv();
        return respuesta;
    }
}