package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class LoginThroughTheWebInterfaceTest {

    //для запуска SUT: java -jar app-ibank.jar -P:profile=test
    //java -jar C:\Users\79168\Desktop\SoftwareTestingEngineer\AutomationHomework\TestMode\artifacts\app-ibank.jar -P:profile=test

    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        //Открытие формы
        open("http://localhost:9999");
    }


    // 1 - Наличие активного пользователя (успешный вход)
    @Test
    public void shouldLoginRegisteredUser() {
        $("[data-test-id=login] .input__control").setValue("vasya");
        $("[data-test-id=password] .input__control").setValue("password");
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Личный кабинет")).shouldBe(visible);
    }

    // 2 - Наличие заблокированного пользователя
    @Test
    public void shouldNotLoginIfUserIsBlocked() {
        $("[data-test-id=login] .input__control").setValue("Ivan");
        $("[data-test-id=password] .input__control").setValue("passwordG");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    // 3 - Отсутствие пользователя
    @Test
    public void shouldNotLoginIfUserDoesNotExist() {
        $("[data-test-id=login] .input__control").setValue("ggg");
        $("[data-test-id=password] .input__control").setValue("fw44w3");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 4 - Невалидный логин активного пользователя
    @Test
    public void shouldNotLoginIfActiveUserWithInvalidLogin() {
        $("[data-test-id=login] .input__control").setValue("vasy");
        $("[data-test-id=password] .input__control").setValue("password");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 5 - Невалидный пароль активного пользователя
    @Test
    public void shouldNotLoginIfActiveUserWithInvalidPassword() {
        $("[data-test-id=login] .input__control").setValue("vasya");
        $("[data-test-id=password] .input__control").setValue("greg");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 6 - Невалидный логин заблокированного пользователя
    @Test
    public void shouldNotLoginIfBlockedUserWithInvalidLogin() {
        $("[data-test-id=login] .input__control").setValue("Iva");
        $("[data-test-id=password] .input__control").setValue("passwordG");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 7 - Невалидный пароль заблокированного пользователя
    @Test
    public void shouldNotLoginIfBlockedUserWithInvalidPassword() {
        $("[data-test-id=login] .input__control").setValue("Ivan");
        $("[data-test-id=password] .input__control").setValue("htrh");
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

}
