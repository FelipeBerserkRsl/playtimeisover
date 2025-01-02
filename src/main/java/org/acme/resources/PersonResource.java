package org.acme.resources;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.acme.models.Person;
import org.acme.services.PersonService;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonService personService;

    @GET
    public Response getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        return Response.ok(persons).build();
    }
        

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") Long id) {
        if (personService.getPerson(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(personService.getPerson(id)).build();
    }

    @POST
    @Transactional
    public Response createPerson(Person person) {
        return Response.ok(personService.createPerson(person)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updatePerson(@PathParam("id") Long id, Person person) {
        if (personService.getPerson(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(personService.updatePerson(id, person)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePerson(@PathParam("id") Long id) {
        if (personService.getPerson(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        personService.deletePerson(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}