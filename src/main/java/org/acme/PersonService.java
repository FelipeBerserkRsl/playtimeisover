package org.acme;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public PersonService() {
    }

    public Person createPerson(Person person) {
        personRepository.persist(person);
        return person;
    }

    public Person updatePerson(Long id, Person person) {
        Person entity = personRepository.findById(id);
        entity.setName(person.getName());
        entity.setUserName(person.getUserName());
        entity.setPassword(person.getPassword());
        entity.addresses = person.addresses;
        return entity;
    }
    
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person getPerson(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> getAllPersons() {
        return personRepository.listAll();
    }

    public List<Person> getPersonByName(String name) {
        return personRepository.find("name", name).list();
    }



}
