package com.hackingrio.hackriooceanos2020maritimos.controllers;

import com.hackingrio.hackriooceanos2020maritimos.domain.dto.LoginFormDto;
import com.hackingrio.hackriooceanos2020maritimos.domain.dto.TokenDto;
import com.hackingrio.hackriooceanos2020maritimos.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private static AuthService authService;

  @Autowired
  AuthController(final AuthService authService) {
    AuthController.authService = authService;
  }

  @PostMapping
  public ResponseEntity<TokenDto> auth(@RequestBody LoginFormDto dto) {

    return ResponseEntity.ok(authService.getToken(dto));
  }
}
