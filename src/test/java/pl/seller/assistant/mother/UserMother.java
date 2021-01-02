package pl.seller.assistant.mother;

import static pl.seller.assistant.mother.ExampleData.EXAMPLE_ID;
import static pl.seller.assistant.mother.ExampleData.EXAMPLE_USERNAME;

import pl.seller.assistant.user.repository.UserEntity;

public class UserMother {

  public static UserEntity testUser() {
    return UserEntity.builder()
        .id(EXAMPLE_ID)
        .username(EXAMPLE_USERNAME)
        .password("TestPassword")
        .email("micho997@o2.pl").build();
  }
}
