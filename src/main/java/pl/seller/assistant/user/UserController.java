package pl.seller.assistant.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.seller.assistant.user.repository.UserEntity;

@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
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
