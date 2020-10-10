package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiException;
import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiMessageEnum;
import com.hackingrio.hackriooceanos2020maritimos.domain.dto.LoginFormDto;
import com.hackingrio.hackriooceanos2020maritimos.domain.dto.TokenDto;
import com.hackingrio.hackriooceanos2020maritimos.domain.entities.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

  private static UserService userService;
  private static AuthenticationManager authManager;
  private static TokenService tokenService;

  @Autowired
  AuthService(
      final UserService userService,
      final AuthenticationManager authManager,
      final TokenService tokenService) {
    AuthService.userService = userService;
    AuthService.authManager = authManager;
    AuthService.tokenService = tokenService;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> user = userService.findByName(s);

    if (user.isPresent()) {
      return user.get();
    }

    throw new UsernameNotFoundException(String.format("Invalid data - User: %s not found", s));
  }

  public TokenDto getToken(LoginFormDto dto) {

    UsernamePasswordAuthenticationToken loginData = dto.convert();
    try {

      Authentication authenticate = authManager.authenticate(loginData);
      String token = tokenService.createToken(authenticate);
      User user = (User) authenticate.getPrincipal();
      return TokenDto.builder()
          .id(user.getId())
          .value(token)
          .name(user.getName())
          .email(user.getEmail())
          .type(tokenService.getTokenType())
          .build();

    } catch (AuthenticationException e) {
      throw new ApiException(ApiMessageEnum.ERROR_JWT_AUTH, e, dto.toString());
    }
  }
}
