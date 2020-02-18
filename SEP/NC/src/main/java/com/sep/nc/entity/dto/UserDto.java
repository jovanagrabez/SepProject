package com.sep.nc.entity.dto;

import com.sep.nc.entity.User;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String city;
    private String country;

    public UserDto(){}
    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.city = user.getCity();
        this.country = user.getCountry();
    }
}
