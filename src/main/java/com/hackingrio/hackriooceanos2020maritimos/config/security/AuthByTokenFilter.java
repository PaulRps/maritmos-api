package com.hackingrio.hackriooceanos2020maritimos.config.security;

import com.hackingrio.hackriooceanos2020maritimos.domain.entities.User;
import com.hackingrio.hackriooceanos2020maritimos.services.TokenService;
import com.hackingrio.hackriooceanos2020maritimos.services.UserService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class AuthByTokenFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = getToken(request);

    boolean isValid = tokenService.isValidToken(token);

    if (isValid) {
      authenticateUser(token);
    }

    filterChain.doFilter(request, response);
  }

  private void authenticateUser(String token) {

    String userId = tokenService.getUserId(token);
    Optional<User> user = userService.find(userId);

    if (user.isPresent()) {
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
  }

  private String getToken(HttpServletRequest request) {

    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith(tokenService.getTokenType())) {
      return null;
    }

    return token.substring(7);
  }
}
