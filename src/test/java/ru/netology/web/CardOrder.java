package ru.netology.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrder {

    @Test
    @DisplayName("Все поля заполнены верно, завяка успешно отправлена")
    void shouldTestPositive() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Пупкин Василий");
        $("[data-test-id=phone] input.input__control").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер " +
                                                                          "свяжется с вами в ближайшее время."));
    }
    @Test
    @DisplayName("Не заполнено поле Имя, ошибка")
    void shouldTestEmptyName() {
        open("http://localhost:9999/");
        $("[data-test-id=phone] input.input__control").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Фамилия и имя Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("Не заполнено поле Телефон, ошибка")
    void shouldTestEmptyPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Пупкин Василий");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Мобильный телефон Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/IrrelevantName2.csv", numLinesToSkip = 1)
    void shouldTestIrrelevantName(String name, String phoneNumber, String message) {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue(name);
        $("[data-test-id=phone] input.input__control").setValue(phoneNumber);
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Фамилия и имя Имя и Фамилия указаные неверно. Допустимы " +
                                                            "только русские буквы, пробелы и дефисы."));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/IrrelevantPhoneNumber.csv", numLinesToSkip = 1)
    void shouldTestIrrelevantPhoneNumber(String name, String phoneNumber, String message) {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue(name);
        $("[data-test-id=phone] input.input__control").setValue(phoneNumber);
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Мобильный телефон Телефон указан неверно. Должно быть 11" +
                                                            " цифр, например, +79012345678."));
    }
    @Test
    @DisplayName("Не нажат чекбокс \"согласие на обработку данных\", ошибка")
    void shouldTestNotChecked() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Пупкин Василий");
        $("[data-test-id=phone] input.input__control").setValue("+79270000000");
        $("button").click();
        $(".input_invalid");
    }
}
