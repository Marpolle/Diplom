package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.page.CreditPage;
import ru.netology.page.PaymentPage;
import ru.netology.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

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
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should approved card payment by credit")
    void shouldCreditPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }

    @Test
    @DisplayName("Should declined payment")
    void shouldCardPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should declined payment by credit")
    void shouldCreditPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    //Форма "Оплата по карте":

    //Номер карты:

    //пустое поле номера карты
    @Test
    public void shouldCardIfEmpty() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }
    //номер карты, не зарегистрированный в базе данных
    @Test
    public void shouldCardNumberNotRegistered() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }
    //номер карты, состоящий из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 5 цифр
    @Test
    public void shouldCardNumberOfFiveDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFiveDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //номер карты, состоящий из 18 цифр
    @Test
    public void shouldCardNumberOfEighteenDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberOfEighteenDigits());
        form.paymentDeclined();
    }

    //номер карты с использованием специальных символов
    @Test
    public void shouldCardWithSpecialSymbols() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //номер карты кириллицей
    @Test
    public void shouldCardWithCyrillic() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //номер карты латиницей
    @Test
    public void shouldCardWithLatin() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //номер карты иероглифами
    @Test
    public void shouldCardWithHieroglyphs() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCardNumberWithHieroglyphs());
        form.incorrectCardNumberVisible();
    }

    // Поле месяц:
//пустое поле месяц
    @Test
    public void shouldMonthIfEmpty() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }
    //несуществующий месяц
    @Test
    public void shouldMonthIfNotExist() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundary() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZero() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbols() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц кириллицей
    @Test
    public void shouldMonthWithCyrillic() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithCyrillic());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц латиницей
    @Test
    public void shouldMonthWithLatin() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithLatin());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц арабский
    @Test
    public void shouldMonthWithArabicLigature() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithArabicLigature());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц иероглифами
    @Test
    public void shouldMonthWithHieroglyphs() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getMonthWithHieroglyphs());
        form.incorrectMonthVisible("Неверный формат");
    }

// Поле год:

//пустое поле "Год"
@Test
public void shouldYearIfEmpty() {
    var orderpage = new OrderPage();
    orderpage.buyByCard();
    var form = new PaymentPage();
    form.completedForm(DataHelper.getYearIfEmpty());
    form.incorrectYearVisible("Неверный формат");
}
    //прошедший год
    @Test
    public void shouldLastYear() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    //год на 25 лет превышающий текущий
    @Test
    public void shouldYear25YearsMore() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible("Неверный формат");
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZero() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible("Неверный формат");
    }

    //год с использованием специальных символов
    @Test
    public void shouldYearWithSpecialSymbols() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible("Неверный формат");
    }

    //год кириллицей
    @Test
    public void shouldYearWithCyrillic() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithCyrillic());
        form.incorrectYearVisible("Неверный формат");
    }

    //год латиницей
    @Test
    public void shouldYearWithLatin() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithLatin());
        form.incorrectYearVisible("Неверный формат");
    }

    //год арабской вязью
    @Test
    public void shouldYearWithArabicLigature() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithArabicLigature());
        form.incorrectYearVisible("Неверный формат");
    }

    //год иероглифами
    @Test
    public void shouldYearWithHieroglyphs() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getYearWithHieroglyphs());
        form.incorrectYearVisible("Неверный формат");
    }

    // Поле Владелец:

//пустое поле "Владелец"
    @Test
    public void shouldHolderIfEmpty() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }
    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetter() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLetters() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillic() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigits() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbols() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    // Поле Кода CVV:

    //пустое поле кода
    @Test
    public void shouldCVCCVVIfEmpty() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }
    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigit() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //код из специальных символов
    @Test
    public void shouldCVCCVVWithSpecialSymbols() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithSpecialSymbols());
        form.incorrectCodeVisible();
    }

    //код кириллицей
    @Test
    public void shouldCVCCVVWithCyrillic() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithCyrillic());
        form.incorrectCodeVisible();
    }

    //код латиницей
    @Test
    public void shouldCVCCVVWithLatin() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithLatin());
        form.incorrectCodeVisible();
    }

    //код арабский
    @Test
    public void shouldCVCCVVWithArabicLigature() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithArabicLigature());
        form.incorrectCodeVisible();
    }

    //код иероглифами
    @Test
    public void shouldCVCCVVWithHieroglyphs() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.completedForm(DataHelper.getCVCCVVWithHieroglyphs());
        form.incorrectCodeVisible();
    }


    //пустая форма
    @Test
    void shouldFormIfEmpty() {
        var orderpage = new OrderPage();
        orderpage.buyByCard();
        var form = new PaymentPage();
        form.emptyForm();
    }

    //Форма "Кредит по данным карты":

    //не заполнение номера карты
    @Test
    public void shouldCardIfEmptyByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }
    //номер карты, состоящий из 1 цифры
    @Test
    public void shouldCardNumberOfOneDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 2 цифр
    @Test
    public void shouldCardNumberOfTwoDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfTwoDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 5 цифр
    @Test
    public void shouldCardNumberOfFiveDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFiveDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 15 цифр
    @Test
    public void shouldCardNumberOfFifteenDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    //номер карты, состоящий из 17 цифр
    @Test
    public void shouldCardNumberOfSeventeenDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentDeclined();
    }

    //номер карты, состоящий из 18 цифр
    @Test
    public void shouldCardNumberOfEighteenDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberOfEighteenDigits());
        form.paymentDeclined();
    }

    //номер карты, не зарегистрированный в базе данных
    @Test
    public void shouldCardNumberNotRegisteredByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    //номер карты с использованием специальных символов
    @Test
    public void shouldCardWithSpecialSymbolsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithSpecialSymbols());
        form.incorrectCardNumberVisible();
    }

    //номер карты кириллицей
    @Test
    public void shouldCardWithCyrillicByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithCyrillic());
        form.incorrectCardNumberVisible();
    }

    //номер карты латиницей
    @Test
    public void shouldCardWithLatinByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithLatin());
        form.incorrectCardNumberVisible();
    }

    //номер карты арабский
    @Test
    public void shouldCardWithArabicLigatureByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithArabicLigature());
        form.incorrectCardNumberVisible();
    }

    //номер карты иероглифами
    @Test
    public void shouldCardWithHieroglyphsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCardNumberWithHieroglyphs());
        form.incorrectCardNumberVisible();
    }



    //несуществующий месяц
    @Test
    public void shouldMonthIfNotExistByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //несуществующий месяц в пределах граничных значений
    @Test
    public void shouldMonthIfNotExistBoundaryByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    //месяц равный двум нулям
    @Test
    public void shouldMonthDoubleZeroByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 3 цифр
    @Test
    public void shouldMonthOfThreeDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц из 1 цифры
    @Test
    public void shouldMonthOfOneDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц, с использованием специальных символов
    @Test
    public void shouldMonthWithSpecialSymbolsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithSpecialSymbols());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц кириллицей
    @Test
    public void shouldMonthWithCyrillicByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithCyrillic());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц латиницей
    @Test
    public void shouldMonthWithLatinByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithLatin());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц арабский
    @Test
    public void shouldMonthWithArabicLigatureByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithArabicLigature());
        form.incorrectMonthVisible("Неверный формат");
    }

    //месяц иероглифами
    @Test
    public void shouldMonthWithHieroglyphsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthWithHieroglyphs());
        form.incorrectMonthVisible("Неверный формат");
    }

    //пустое поле месяца
    @Test
    public void shouldMonthIfEmptyByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }

    //прошедший год
    @Test
    public void shouldLastYearByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    //год  превышающий текущий
    @Test
    public void shouldYear25YearsMoreByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYear25YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    //год из 1 цифры
    @Test
    public void shouldYearOfOneDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    //год из 3 цифр
    @Test
    public void shouldYearOfThreeDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.incorrectYearVisible("Неверный формат");
    }

    //год со значением равным нулю
    @Test
    public void shouldYearIfZeroByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfZero());
        form.incorrectYearVisible("Неверный формат");
    }

    //год с использованием специальных символов
    @Test
    public void shouldYearWithSpecialSymbolsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithSpecialSymbols());
        form.incorrectYearVisible("Неверный формат");
    }

    //год кириллицей
    @Test
    public void shouldYearWithCyrillicByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithCyrillic());
        form.incorrectYearVisible("Неверный формат");
    }

    //год латиницей
    @Test
    public void shouldYearWithLatinByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithLatin());
        form.incorrectYearVisible("Неверный формат");
    }

    //год арабский
    @Test
    public void shouldYearWithArabicLigatureByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithArabicLigature());
        form.incorrectYearVisible("Неверный формат");
    }

    //год иероглифами
    @Test
    public void shouldYearWithHieroglyphsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearWithHieroglyphs());
        form.incorrectYearVisible("Неверный формат");
    }

    //пустое поле "Год"
    @Test
    public void shouldYearIfEmptyByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }

    //поле "Владелец", состоящее из 1 буквы
    @Test
    public void shouldHolderOfOneLetterByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    //поле "Владелец", состоящее из 60 букв
    @Test
    public void shouldHolderOfSixtyLettersByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderOfSixtyLetters());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" кириллицей
    @Test
    public void shouldHolderWithCyrillicByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" цифрами
    @Test
    public void shouldHolderWithDigitsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //поле "Владелец" специальными символами
    @Test
    public void shouldHolderSpecialSymbolsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    //пустое поле "Владелец"
    @Test
    public void shouldHolderIfEmptyByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    //код из 1 цифры
    @Test
    public void shouldCVCCVVOnOneDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    //код из 2 цифр
    @Test
    public void shouldCVCCVVOnTwoDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //код из 4 цифр
    @Test
    public void shouldCVCCVVOnFourDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.incorrectCodeVisible();
    }

    //код из 5 цифр
    @Test
    public void shouldCVCCVVOnFiveDigitByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVOnFiveDigits());
        form.incorrectCodeVisible();
    }

    //код из специальных символов
    @Test
    public void shouldCVCCVVWithSpecialSymbolsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithSpecialSymbols());
        form.incorrectCodeVisible();
    }

    //код кириллицей
    @Test
    public void shouldCVCCVVWithCyrillicByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithCyrillic());
        form.incorrectCodeVisible();
    }

    //код латиницей
    @Test
    public void shouldCVCCVVWithLatinByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithLatin());
        form.incorrectCodeVisible();
    }

    //код арабский
    @Test
    public void shouldCVCCVVWithArabicLigatureByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithArabicLigature());
        form.incorrectCodeVisible();
    }

    //код иероглифами
    @Test
    public void shouldCVCCVVWithHieroglyphsByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVWithHieroglyphs());
        form.incorrectCodeVisible();
    }

    //пустое поле кода
    @Test
    public void shouldCVCCVVIfEmptyByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    //пустая форма
    @Test
    void shouldFormIfEmptyByCredit() {
        var orderpage = new OrderPage();
        orderpage.buyByCreditCard();
        var form = new CreditPage();
        form.emptyForm();
    }
}