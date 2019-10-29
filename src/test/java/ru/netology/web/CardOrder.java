package ru.netology.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("Не заполенено поле Имя, ошибка")
    void shouldTestEmptyName() {
        open("http://localhost:9999/");
        $("[data-test-id=phone] input.input__control").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Фамилия и имя Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("Не заполенено поле Телефон, ошибка")
    void shouldTestEmptyPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Пупкин Василий");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Мобильный телефон Поле обязательно для заполнения"));
    }
    @Test
    @DisplayName("Поле Имя заполенно не корректно, ошибка")
    void shouldTestIrrelevantName() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Pupkin Vasilii");
        $("[data-test-id=phone] input.input__control").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(exactText("Фамилия и имя Имя и Фамилия указаные неверно. Допустимы " +
                                                            "только русские буквы, пробелы и дефисы."));
    }
    @Test
    @DisplayName("Поле Телефон заполенно не корректно, ошибка")
    void shouldTestIrrelevantPhoneNumber() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Пупкин Василий");
        $("[data-test-id=phone] input.input__control").setValue("7-927-000-0000");
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
