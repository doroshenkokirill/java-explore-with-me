package ru.practicum.users.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public User toUser(final User user) {
        return user;
    }
}
