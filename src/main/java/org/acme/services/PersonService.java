package org.acme.services;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.models.Address;
import org.acme.models.Person;
import org.acme.repositories.AddressRepository;
import org.acme.repositories.PersonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonService {

    @Inject
    PersonRepository personRepository;

    @Inject
    AddressRepository addressRepository;

    public PersonService() {
    }

    public Person createPerson(Person person) {
        personRepository.persist(person);
        return person;
    }

    public Person updatePerson(Long id, Person person) {
        Person entity = personRepository.findById(id);

        if (entity == null) {
            throw new IllegalArgumentException("Person not found");
        }

        entity.setName(person.getName());
        entity.setUserName(person.getUserName());
        entity.setPassword(person.getPassword());

        List<Address> updatedAddresses = person.getAddresses().stream().map(address -> {
            if (address.id != null) {
                Address existingAddress = addressRepository.findById(address.id);
                if (existingAddress != null) {
                    existingAddress.setStreet(address.getStreet());
                    existingAddress.setCity(address.getCity());
                    existingAddress.setNumber(address.getNumber());
                    return existingAddress;
                }
            }
            address.setPerson(entity);
            return address;
        }).collect(Collectors.toList());

        entity.setAddresses(updatedAddresses);

        personRepository.persist(entity);

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
}
