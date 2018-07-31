package com.fredoliveira.rest;


import com.fredoliveira.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/object")
public class PersonResource {

    @GetMapping()
    public ResponseEntity findCurrentUser() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity save(@Valid @RequestBody Person person) throws Exception {
        return ResponseEntity.ok(person);
    }

}
