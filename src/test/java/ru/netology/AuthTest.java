package ru.netology;

import org.junit.jupiter.api.*;
import ru.netology.data.RegistrationInfo;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.Registration.*;
import static ru.netology.data.DataGenerator.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginRegisteredActiveUser() {
        RegistrationInfo user = getRegisteredUser("active");
        $("[data-test-id='login'] .input__control").setValue(user.getLogin());
        $("[data-test-id='password'] .input__control").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(exactText("Личный кабинет"));
    }

    @Test
    void shouldNotLoginNonRegisteredUser() {
        RegistrationInfo user = getRandomUser("active");
        $("[data-test-id='login'] .input__control").setValue(user.getLogin());
        $("[data-test-id='password'] .input__control").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginRegisteredActiveUserWithWrongLogin() {
        RegistrationInfo user = getRegisteredUser("active");
        String login = generateLogin();
        $("[data-test-id='login'] .input__control").setValue(login);
        $("[data-test-id='password'] .input__control").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginRegisteredActiveUserWithWrongPassword() {
        RegistrationInfo user = getRegisteredUser("active");
        String password = generatePassword();
        $("[data-test-id='login'] .input__control").setValue(user.getLogin());
        $("[data-test-id='password'] .input__control").setValue(password);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(exactText("Ошибка Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotLoginRegisteredBlockedUser() {
        RegistrationInfo user = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__control").setValue(user.getLogin());
        $("[data-test-id='password'] .input__control").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(exactText("Ошибка Ошибка! Пользователь заблокирован"));
    }
}
