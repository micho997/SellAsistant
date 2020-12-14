package pl.seller.assistant.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.seller.assistant.user.repository.UserEntity;
import pl.seller.assistant.user.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public UserEntity addUser(UserEntity userEntity) {
    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    userEntity.setRole("ROLE_USER");
    return userRepository.save(userEntity);
  }
}
