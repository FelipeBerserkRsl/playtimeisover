package org.acme;


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

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonRepository personRepository;

    @GET
    public List<Person> getAllPersons() {
        return personRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Person getPerson(@PathParam("id") Long id) {
        return personRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createPerson(Person person) {
        if (person.id != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        person.getAddresses().forEach(address -> address.setPerson(person));

        personRepository.persist(person);


        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updatePerson(@PathParam("id") Long id, Person person) {
        Person entity = personRepository.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        entity.setName(person.getName());
        entity.setUserName(person.getUserName());
        entity.setPassword(person.getPassword());
   
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePerson(@PathParam("id") Long id) {
        Person entity = personRepository.findById(id); 
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        personRepository.delete(entity);
        return Response.noContent().build();
    }
}