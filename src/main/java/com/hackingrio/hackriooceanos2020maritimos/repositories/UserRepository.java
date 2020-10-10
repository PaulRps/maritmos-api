package com.hackingrio.hackriooceanos2020maritimos.repositories;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

  User findByName(String name);

  User findByEmail(String email);
}
