import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTests {

    @Test
    @DisplayName("Должен успешно отправлять заявку при валидных данных")
    void shouldSubmitRequest() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] input.input__control").setValue("+79991112233");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    @DisplayName("Должен указывать на ошибку при вводе невалидных фамилии и имени")
    void shouldNotSubmitWithWrongName() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input.input__control").setValue("+79991112233");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Должен указывать на ошибку при вводе невалидного номера телефона")
    void shouldNotSubmitWithWrongPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Иванов Иван");
        $("[data-test-id=phone] input.input__control").setValue("+799911122");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    @DisplayName("Должен указывать на ошибку при пустом поле имени")
    void shouldNotSubmitWithEmptyName() {
        open("http://localhost:9999/");
        $("[data-test-id=phone] input.input__control").setValue("+79991112233");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Должен указывать на ошибку при пустом поле номера телефона")
    void shouldNotSubmitWithEmptyPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input.input__control").setValue("Иванов Иван");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $(".input_invalid").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
}
