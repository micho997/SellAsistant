package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.ExampleData.EXAMPLE_USERNAME;

import pl.seller.assistant.user.repository.UserEntity;

public class UserMother {

  public static UserEntity testUser() {
    return UserEntity.builder()
        .username(EXAMPLE_USERNAME)
        .password("TestPassword").build();
  }
}
