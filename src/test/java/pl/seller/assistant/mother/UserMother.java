package pl.seller.assistant.mother;

import pl.seller.assistant.user.repository.UserEntity;

public class UserMother {

  public static UserEntity testUser() {
    return UserEntity.builder()
        .username("TestUser")
        .password("TestPassword").build();
  }
}
