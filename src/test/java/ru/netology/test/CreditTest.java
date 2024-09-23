package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import java.net.URL;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class CreditTest {
    String url = System.getProperty("sut.url");
    private CreditPage buy;
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        String baseUrl = System.getProperty("baseUrl", "http://localhost:8080");
        open(baseUrl);
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
    }
    @Test
    @DisplayName("Should declined payment by credit")
    void shouldCreditPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(15), getValidHolder(), getValidCVCCVV());
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }
    //не заполнение номера карты
    @Test
    public void shouldCardIfEmptyByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }
    //номер карты, состоящий из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 5 цифр
    @Test
    public void shouldCardNumberOfFiveDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFiveDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //номер карты, состоящий из 18 цифр
    @Test
    public void shouldCardNumberOfEighteenDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfEighteenDigits());
        form.paymentDeclined();
    }

    //номер карты, не зарегистрированный в базе данных
    @Test
    public void shouldCardNumberNotRegisteredByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    //номер карты с использованием специальных символов
    @Test
    public void shouldCardWithSpecialSymbolsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //номер карты кириллицей
    @Test
    public void shouldCardWithCyrillicByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //номер карты латиницей
    @Test
    public void shouldCardWithLatinByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //номер карты арабский
    @Test
    public void shouldCardWithArabicLigatureByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithArabicLigature());
        form.incorrectCardNumberVisible();
    }

    //номер карты иероглифами
    @Test
    public void shouldCardWithHieroglyphsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithHieroglyphs());
        form.incorrectCardNumberVisible();
    }



    //несуществующий месяц
    @Test
    public void shouldMonthIfNotExistByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundaryByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZeroByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbolsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц кириллицей
    @Test
    public void shouldMonthWithCyrillicByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithCyrillic());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц латиницей
    @Test
    public void shouldMonthWithLatinByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithLatin());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц арабский
    @Test
    public void shouldMonthWithArabicLigatureByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithArabicLigature());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц иероглифами
    @Test
    public void shouldMonthWithHieroglyphsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithHieroglyphs());
        form.incorrectMonthVisible("Неверный формат");
    }

    //пустое поле месяца
    @Test
    public void shouldMonthIfEmptyByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }

    //прошедший год
    @Test
    public void shouldLastYearByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    //год  превышающий текущий
    @Test
    public void shouldYear25YearsMoreByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible("Неверный формат");
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZeroByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» нечисловым  значением:
    @Test
    @DisplayName ("Should display an error if  year is non numeric value")
    public void shouldYearNonNumericValueByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = mainpage.buyByCreditCard();
        form.completedForm(DataHelper.getYearNonNumericValue());
        form.incorrectYearVisible("Неверный формат");
    }

    //пустое поле "Год"
    @Test
    public void shouldYearIfEmptyByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }

    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetterByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLettersByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillicByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigitsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbolsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    //пустое поле "Владелец"
    @Test
    public void shouldHolderIfEmptyByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigitByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //код из специальных символов
    @Test
    public void shouldCVCCVVWithSpecialSymbolsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithSpecialSymbols());
        form.incorrectCodeVisible();
    }

    //код кириллицей
    @Test
    public void shouldCVCCVVWithCyrillicByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithCyrillic());
        form.incorrectCodeVisible();
    }

    //код латиницей
    @Test
    public void shouldCVCCVVWithLatinByCredit() {
        var mainpage= new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithLatin());
        form.incorrectCodeVisible();
    }

    //код арабский
    @Test
    public void shouldCVCCVVWithArabicLigatureByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithArabicLigature());
        form.incorrectCodeVisible();
    }

    //код иероглифами
    @Test
    public void shouldCVCCVVWithHieroglyphsByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithHieroglyphs());
        form.incorrectCodeVisible();
    }

    //пустое поле кода
    @Test
    public void shouldCVCCVVIfEmptyByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    //пустая форма
    @Test
    void shouldFormIfEmptyByCredit() {
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.emptyForm();
    }

}
