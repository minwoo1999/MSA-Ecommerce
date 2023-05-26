package com.example.userservicegradle.vo;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotEmpty(message ="hello")
    @Size(min =2 , message ="Email not be less than tow characters")
    @Email
    private String email;

    @NotNull(message ="Email cannot be null")
    @Size(min =2 , message="Email not be less than two characters")
    private String name;
    @NotNull(message ="Password cannot be null")
    @Size(min =8 ,message ="Password must be equal or grater then 8 characters")
    private String pwd;


}
