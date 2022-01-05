package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static final Faker faker = new Faker(new Locale("en"));

    static void setUpAll(RegistrationInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users");
    }

    public static String generateLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String generatePassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationInfo getRandomUser(String status) {
            RegistrationInfo randomUser = new RegistrationInfo(
                    generateLogin(),
                    generatePassword(),
                    status);
            return randomUser;
        }

        public static RegistrationInfo getRegisteredUser(String status) {
            RegistrationInfo registeredUser = getRandomUser(status);
            setUpAll(registeredUser);
            return registeredUser;
        }
    }
}
