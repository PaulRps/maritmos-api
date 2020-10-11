package com.hackingrio.hackriooceanos2020maritimos.config.security;

import com.hackingrio.hackriooceanos2020maritimos.services.AuthService;
import com.hackingrio.hackriooceanos2020maritimos.services.TokenService;
import com.hackingrio.hackriooceanos2020maritimos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private AuthService authService;

  @Autowired private TokenService tokenService;

  @Autowired private UserService userService;

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  // AUTHENTICATION CONFIGURARTIONS
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Bean
  CorsFilter myCors() {
    return new CorsFilter();
  }

  // AUTHORIZATION CONFIGURATIONS
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(myCors(), SessionManagementFilter.class)
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()
        .antMatchers("/auth")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/user")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/report")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/report")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(
            new AuthByTokenFilter(tokenService, userService),
            UsernamePasswordAuthenticationFilter.class);
  }

  // STATIC FILES (imgs, .js) CONFIGURATIONS
  @Override
  public void configure(WebSecurity web) throws Exception {
    //    web.ignoring().antMatchers("/**");
  }
}
