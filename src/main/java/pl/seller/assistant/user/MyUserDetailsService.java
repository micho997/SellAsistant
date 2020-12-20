package pl.seller.assistant.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.seller.assistant.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }
}
