package com.hackingrio.hackriooceanos2020maritimos.services;

import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component("UserPasswordEncrypter")
public class PasswordEncrypterImpl implements PasswordEncrypter {

  @Override
  public String encrypt(String password) {
    return Optional.ofNullable(password)
        .map(pwd -> new BCryptPasswordEncoder().encode(pwd))
        .orElseThrow(() -> new NullPointerException("password is null"));
  }
}
