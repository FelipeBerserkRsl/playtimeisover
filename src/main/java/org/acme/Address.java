package org.acme;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Address extends PanacheEntity {

    public String street;

    public String city;

    public String number;

    @ManyToOne
    @JoinColumn(name="Person_ID", nullable=false)
    @JsonBackReference
    public Person person;

    public Address() {
    }

    @PrePersist
    public void prePersist() {
        if (this.person == null) {
            throw new IllegalStateException("Address must be associated with a Person");
        }
    }

    public Address(String street, String city, String number) {
        this.street = street;
        this.city = city;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}

    
