package org.mjjaen.springtest.controller;

import lombok.RequiredArgsConstructor;
import org.mjjaen.springtest.businessObject.Person;
import org.mjjaen.springtest.exceptions.ItemNotFoundException;
import org.mjjaen.springtest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/person")
@RequiredArgsConstructor
public class PersonRestController {
    private final PersonService personService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Person>> getAll() {
        return new ResponseEntity(personService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Person> getById(@PathVariable Integer id) {
        return new ResponseEntity(personService.findById(id).orElseThrow(() -> new ItemNotFoundException(id)), HttpStatus.OK);
    }
}
