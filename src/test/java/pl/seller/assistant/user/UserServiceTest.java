package pl.seller.assistant.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.seller.assistant.mother.UserMother.testUser;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import pl.seller.assistant.user.repository.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Test
  void should_create_user() {
    // given
    UserEntity userEntity = testUser();

    // when
    UserEntity createdUser = userService.addUser(userEntity);

    // then
    assertNotNull(createdUser.getId());
    assertEquals(userEntity.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getPassword());
  }

  @Test
  void should_not_create_duplicate_user() {
    // given
    UserEntity originalUsername = UserEntity.builder()
        .username("SameName")
        .password("TEST")
        .email("TEST")
        .role("ROLE_USER").build();
    UserEntity duplicateUsername = UserEntity.builder()
        .username("SameName")
        .password("TEST")
        .email("TEST")
        .role("ROLE_USER").build();

    // when
    userService.addUser(originalUsername);

    // then
    assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(duplicateUsername));
  }
}
