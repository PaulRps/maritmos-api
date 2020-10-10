package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  Optional<User> find(String userId);

  Optional<User> findByName(String name);

  List<User> findAll();

  User save(User u);

  void update(User u);

  void delete(String id);
}
