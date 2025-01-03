package org.acme.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.acme.models.Address;
import org.acme.models.Person;
import org.acme.repositories.AddressRepository;
import org.acme.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PersonServiceTest {

    @InjectMocks
    PersonService personService;

    @Mock
    PersonRepository personRepository;

    @Mock
    AddressRepository addressRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person();
        Person createdPerson = personService.createPerson(person);
        assertEquals(person, createdPerson);
        verify(personRepository, times(1)).persist(person);
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setUserName("johndoe");
        person.setPassword("securepassword");

        Address address1 = new Address("123 Main St", "Anytown", "456", person);
        address1.id = 1L;
        Address address2 = new Address("456 Elm St", "Othertown", "789", person);
        address2.id = 2L;
        person.setAddresses(Arrays.asList(address1, address2));

        when(personRepository.findById(1L)).thenReturn(person);
        when(addressRepository.findById(1L)).thenReturn(address1);
        when(addressRepository.findById(2L)).thenReturn(address2);

        Person updatedPerson = personService.updatePerson(1L, person);
        assertEquals(person, updatedPerson);
        verify(personRepository, times(1)).persist(person);
    }

    @Test
    public void testUpdatePersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(null);

        Person person = new Person();
        person.setId(1L);
        person.setName("John Doe");
        person.setUserName("johndoe");
        person.setPassword("securepassword");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(1L, person);
        });

        assertEquals("Person not found", exception.getMessage());
    }

    @Test
    public void testDeletePerson() {
        personService.deletePerson(1L);
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetPerson() {
        Person person = new Person();
        when(personRepository.findById(1L)).thenReturn(person);

        Person foundPerson = personService.getPerson(1L);
        assertEquals(person, foundPerson);
    }

    @Test
    public void testGetAllPersons() {
        List<Person> persons = Arrays.asList(new Person(), new Person());
        when(personRepository.listAll()).thenReturn(persons);

        List<Person> allPersons = personService.getAllPersons();
        assertEquals(persons, allPersons);
    }
}