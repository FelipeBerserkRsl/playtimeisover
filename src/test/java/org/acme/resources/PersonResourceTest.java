package org.acme.resources;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.ws.rs.core.Response;
import org.acme.models.Person;
import org.acme.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class PersonResourceTest {

    @InjectMocks
    PersonResource personResource;

    @Mock
    PersonService personService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPersons() {
        List<Person> persons = Arrays.asList(new Person(), new Person());
        when(personService.getAllPersons()).thenReturn(persons);

        Response response = personResource.getAllPersons();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(persons, response.getEntity());
    }

    @Test
    public void testGetPerson() {
        Person person = new Person();
        when(personService.getPerson(1L)).thenReturn(person);

        Response response = personResource.getPerson(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(person, response.getEntity());
    }

    @Test
    public void testGetPersonNotFound() {
        when(personService.getPerson(1L)).thenReturn(null);

        Response response = personResource.getPerson(1L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person();
        when(personService.createPerson(person)).thenReturn(person);

        Response response = personResource.createPerson(person);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(person, response.getEntity());
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        when(personService.getPerson(1L)).thenReturn(person);
        when(personService.updatePerson(1L, person)).thenReturn(person);

        Response response = personResource.updatePerson(1L, person);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(person, response.getEntity());
    }

    @Test
    public void testUpdatePersonNotFound() {
        when(personService.getPerson(1L)).thenReturn(null);

        Response response = personResource.updatePerson(1L, new Person());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person();
        when(personService.getPerson(1L)).thenReturn(person);

        Response response = personResource.deletePerson(1L);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(personService, times(1)).deletePerson(1L);
    }

    @Test
    public void testDeletePersonNotFound() {
        when(personService.getPerson(1L)).thenReturn(null);

        Response response = personResource.deletePerson(1L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}