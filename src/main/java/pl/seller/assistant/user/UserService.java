package pl.seller.assistant.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.seller.assistant.user.repository.UserEntity;
import pl.seller.assistant.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserEntity addUser(UserEntity userEntity) {
    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    userEntity.setRole("ROLE_USER");
    return userRepository.save(userEntity);
  }
}
