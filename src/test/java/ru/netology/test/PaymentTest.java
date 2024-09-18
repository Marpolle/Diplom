package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class PaymentTest {
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
        open("http://localhost:8080");
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
    }
    @Test
    @DisplayName("Should approved card payment")
    void shouldCardPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should approved card payment by credit")
    void shouldCreditPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var mainpage = new MainPage();
        mainpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Should declined payment")
    void shouldCardPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }
    //Форма "Оплата по карте":

    //Номер карты:

    //пустое поле номера карты
    @Test
    public void shouldCardIfEmpty() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }
    //номер карты, не зарегистрированный в базе данных
    @Test
    public void shouldCardNumberNotRegistered() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }
    //номер карты, состоящий из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 5 цифр
    @Test
    public void shouldCardNumberOfFiveDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFiveDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты,состоящий из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //номер карты, состоящий из 18 цифр
    @Test
    public void shouldCardNumberOfEighteenDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfEighteenDigits());
        form.paymentDeclined();
    }

    //номер карты с использованием специальных символов
    @Test
    public void shouldCardWithSpecialSymbols() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //номер карты кириллицей
    @Test
    public void shouldCardWithCyrillic() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //номер карты латиницей
    @Test
    public void shouldCardWithLatin() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }
    //номер карты иероглифами
    @Test
    public void shouldCardWithHieroglyphs() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithHieroglyphs());
        form.incorrectCardNumberVisible();
    }

    // Поле месяц:
//пустое поле месяц
    @Test
    public void shouldMonthIfEmpty() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }
    //несуществующий месяц
    @Test
    public void shouldMonthIfNotExist() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundary() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZero() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbols() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц кириллицей
    @Test
    public void shouldMonthWithCyrillic() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithCyrillic());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц латиницей
    @Test
    public void shouldMonthWithLatin() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithLatin());
        form.incorrectMonthVisible("Неверный формат");
    }
    //месяц арабский
    @Test
    public void shouldMonthWithArabicLigature() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithArabicLigature());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц иероглифами
    @Test
    public void shouldMonthWithHieroglyphs() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithHieroglyphs());
        form.incorrectMonthVisible("Неверный формат");
    }

// Поле год:

    //пустое поле "Год"
    @Test
    public void shouldYearIfEmpty() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }
    //прошедший год
    @Test
    public void shouldLastYear() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    //год на 25 лет превышающий текущий
    @Test
    public void shouldYear25YearsMore() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible("Неверный формат");
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZero() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible("Неверный формат");
    }

    //год с использованием специальных символов
    @Test
    public void shouldYearWithSpecialSymbols() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible("Неверный формат");
    }

    //год кириллицей
    @Test
    public void shouldYearWithCyrillic() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithCyrillic());
        form.incorrectYearVisible("Неверный формат");
    }

    //год латиницей
    @Test
    public void shouldYearWithLatin() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithLatin());
        form.incorrectYearVisible("Неверный формат");
    }

    //год арабской вязью
    @Test
    public void shouldYearWithArabicLigature() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithArabicLigature());
        form.incorrectYearVisible("Неверный формат");
    }

    //год иероглифами
    @Test
    public void shouldYearWithHieroglyphs() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithHieroglyphs());
        form.incorrectYearVisible("Неверный формат");
    }

    // Поле Владелец:

    //пустое поле "Владелец"
    @Test
    public void shouldHolderIfEmpty() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }
    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetter() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLetters() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillic() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigits() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbols() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    // Поле Кода CVV:

    //пустое поле кода
    @Test
    public void shouldCVCCVVIfEmpty() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }
    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigit() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //код из специальных символов
    @Test
    public void shouldCVCCVVWithSpecialSymbols() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithSpecialSymbols());
        form.incorrectCodeVisible();
    }

    //код кириллицей
    @Test
    public void shouldCVCCVVWithCyrillic() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithCyrillic());
        form.incorrectCodeVisible();
    }

    //код латиницей
    @Test
    public void shouldCVCCVVWithLatin() {
        var mainpage= new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithLatin());
        form.incorrectCodeVisible();
    }

    //код арабский
    @Test
    public void shouldCVCCVVWithArabicLigature() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithArabicLigature());
        form.incorrectCodeVisible();
    }

    //код иероглифами
    @Test
    public void shouldCVCCVVWithHieroglyphs() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithHieroglyphs());
        form.incorrectCodeVisible();
    }

    //пустая форма
    @Test
    void shouldFormIfEmpty() {
        var mainpage = new MainPage();
        mainpage.buyByCard();
        var form = new PaymentPage();
        form.emptyForm();
    }

}
