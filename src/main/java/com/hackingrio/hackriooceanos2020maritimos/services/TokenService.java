package com.hackingrio.hackriooceanos2020maritimos.services;

import org.springframework.security.core.Authentication;

public interface TokenService {

  String createToken(Authentication auth);

  boolean isValidToken(String token);

  String getUserId(String token);

  String getTokenType();
}
