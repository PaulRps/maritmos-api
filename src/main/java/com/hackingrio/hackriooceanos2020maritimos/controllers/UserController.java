package com.hackingrio.hackriooceanos2020maritimos.controllers;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.User;
import com.hackingrio.hackriooceanos2020maritimos.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
    origins = {"*"},
    maxAge = 3600)
@RestController
@RequestMapping("user")
public class UserController {

  @Autowired UserService userService;

  @GetMapping("{id}")
  ResponseEntity<User> getById(@PathVariable String id) {
    return ResponseEntity.ok(userService.find(id).get());
  }

  @GetMapping
  ResponseEntity<List<User>> getAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @PostMapping
  ResponseEntity<User> save(@RequestBody User user) {
    return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
  }

  @PutMapping
  ResponseEntity<Void> update(@RequestBody User user) {
    userService.update(user);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("{id}")
  ResponseEntity<Void> delete(@PathVariable String id) {
    userService.delete(id);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
