package pl.seller.assistant.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static pl.seller.assistant.mother.UserMother.testUser;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.user.repository.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class MyUserDetailsServiceTest {

  @Autowired
  private MyUserDetailsService userDetailsService;
  @Autowired
  private UserService userService;

  @Test
  void should_get_existing_user() {
    // given
    UserEntity user = userService.addUser(testUser());
    UserDetails userInDatabase = userDetailsService.loadUserByUsername(user.getUsername());

    // then
    assertNotNull(userInDatabase);
    assertEquals(user.getUsername(), userInDatabase.getUsername());
    assertEquals(user.getPassword(), userInDatabase.getPassword());
  }

  @Test
  void should_not_get_user_with_incorrect_username() {
    // given
    UserDetails userInDatabase = userDetailsService.loadUserByUsername("Incorrect");

    // then
    assertNull(userInDatabase);
  }
}
