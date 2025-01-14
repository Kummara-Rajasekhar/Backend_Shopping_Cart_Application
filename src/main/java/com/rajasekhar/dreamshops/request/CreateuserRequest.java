package com.rajasekhar.dreamshops.request;


import lombok.Data;

@Data
public class CreateuserRequest {

    public String firstName;
    public String lastName;
    public String email;
    public String password;
}
