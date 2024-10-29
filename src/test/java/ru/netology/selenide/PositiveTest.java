package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PositiveTest {

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void ShouldSendFormToBank() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Уфа");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Сергей Иванов-Петров");
        $("[data-test-id=phone] input").setValue("+79269112233");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
