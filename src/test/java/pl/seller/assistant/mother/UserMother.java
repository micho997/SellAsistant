package pl.seller.assistant.mother;

import pl.seller.assistant.user.repository.UserEntity;
import pl.seller.assistant.user.repository.UserEntity.UserEntityBuilder;

public class UserMother {

  public static UserEntity testUser() {
    return UserEntityBuilder.anUser()
        .username("TestUser")
        .password("TestPassword").build();
  }
}
