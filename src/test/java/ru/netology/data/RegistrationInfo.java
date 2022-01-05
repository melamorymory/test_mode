package ru.netology.data;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RegistrationInfo {
    String login;
    String password;
    String status;
}
