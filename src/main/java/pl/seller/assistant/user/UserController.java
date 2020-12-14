package pl.seller.assistant.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.seller.assistant.user.repository.UserEntity;
import pl.seller.assistant.user.repository.UserRepository;

@Controller
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping("/sing-up")
  public String singUp(Model model) {
    model.addAttribute("user", new UserEntity());
    return "sing-up";
  }

  @PostMapping("/register")
  public String register(UserEntity userEntity) {
    userService.addUser(userEntity);
    return "sing-up";
  }
}
