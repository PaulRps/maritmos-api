package com.hackingrio.hackriooceanos2020maritimos.services;

import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiException;
import com.hackingrio.hackriooceanos2020maritimos.config.exceptions.ApiMessageEnum;
import com.hackingrio.hackriooceanos2020maritimos.domain.entities.User;
import com.hackingrio.hackriooceanos2020maritimos.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  private static UserRepository userRepository;
  private static PasswordEncrypter passwordEncrypter;

  @Autowired
  UserServiceImpl(final UserRepository userRepository, final PasswordEncrypter passwordEncrypter) {
    UserServiceImpl.userRepository = userRepository;
    UserServiceImpl.passwordEncrypter = passwordEncrypter;
  }

  @Override
  public Optional<User> findByName(String name) {
    Optional.ofNullable(name)
        .orElseThrow(
            () -> {
              log.error("Parameter USER_NAME is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "USER_NAME");
            });

    try {
      log.debug("querying user[name={}]", name);
      return Optional.ofNullable(userRepository.findByName(name));
    } catch (Exception e) {
      log.error(e.getMessage(), e);

      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, e, String.format("User[name=%s]", name));
    }
  }

  @Override
  public Optional<User> find(String userId) {
    Optional.ofNullable(userId)
        .orElseThrow(
            () -> {
              log.error("Parameter USER_ID is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "USER_ID");
            });
    try {
      log.debug("querying user[id={}]", userId);
      return userRepository.findById(userId);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, e, String.format("User[id=%d]", userId));
    }
  }

  @Override
  public List<User> findAll() {
    try {
      log.debug("querying all users");
      return userRepository.findAll();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(ApiMessageEnum.ERROR_INTERNAL_SERVER, e, "all users");
    }
  }

  @Override
  public User save(User u) {

    Optional.ofNullable(u)
        .orElseThrow(
            () -> {
              log.error("Parameter user is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "user");
            });

    try {

      log.debug("creating user {}}", u.toString());
      u.setPassword(passwordEncrypter.encrypt(u.getPassword()));
      return userRepository.save(u);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error(u.toString());
      throw new ApiException(ApiMessageEnum.ERROR_ON_SAVE_ENTITY, e, u.toString());
    }
  }

  @Override
  public void update(User u) {

    Optional.ofNullable(u)
        .orElseThrow(
            () -> {
              log.error("Parameter user is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "user");
            });

    Optional.ofNullable(u.getId())
        .orElseThrow(
            () -> {
              log.error("USER_ID parameter is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "USER_ID");
            });

    try {

      log.debug("updating user {}", u.toString());

      userRepository.save(u);

      log.debug("updated user");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error(u.toString());
      throw new ApiException(ApiMessageEnum.ERROR_ON_SAVE_ENTITY, e, u.toString());
    }
  }

  @Override
  public void delete(String id) {

    Optional.ofNullable(id)
        .orElseThrow(
            () -> {
              log.error("USER_ID parameter is null");
              return new ApiException(ApiMessageEnum.ERROR_PARAMETER_NOT_PRESENT, "USER_ID");
            });

    if (!userRepository.existsById(id)) {
      log.error("User[id={}] do not exist", id);
      throw new ApiException(
          ApiMessageEnum.ERROR_RESOURCE_NOT_FOUND, String.format("User[id=%d]", id));
    }

    try {

      log.debug("deleting user [id={}]", id);
      userRepository.deleteById(id);
      log.debug("deleted user");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ApiException(
          ApiMessageEnum.ERROR_ON_DELETE_ENTITY, e, String.format("User[id=%d]", id));
    }
  }
}
