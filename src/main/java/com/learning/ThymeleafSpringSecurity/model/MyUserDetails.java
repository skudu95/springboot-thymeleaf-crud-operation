package com.learning.ThymeleafSpringSecurity.model;

import com.learning.ThymeleafSpringSecurity.validation.FieldMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
// the registration part
public class MyUserDetails {

    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 5, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 5, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    //validating the email address
  //  @ValidEmail
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    //using @Email instead of @ValidEmail since @Email is a default class
    @Email
    private String email;
}
