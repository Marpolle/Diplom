## План автоматизации тестирования покупки тура
## Перечень автоматизируемых сценариев.
**Валидные данные:**
Номер карты: значение цифрами в формате XXXX XXXX XXXX XXXX.

Номер действующей карты: 4444 4444 4444 4441,

номер не действующей карты: 4444 4444 4444 4442.

Месяц: значение цифрами в формате XX от 01 до 12

Год: значение цифрами в формате XX, не ранее текущего года

Владелец: значение латиницей от 1 до 64 символов, допускается пробел и тире

CVC/CVV: значение цифрами в формате XXX

**Общее предусловие:**

открыта страница http://localhost:8080/

## Позитивные сценарии:

**Сценарий 1.** Заполнение формы «Оплата по карте» со статусом карты «APPROVED»

Кликнуть на кнопку «Купить»

Заполнить поля формы «Оплата по карте» валидными значениями:

Номер карты со статусом «APPROVED» (например: 4444 4444 4444 4441)

Номер месяца должен быть указан цифрами в диапазоне от 01 до 12 (например: 01 (январь), 09 (сентябрь))

Год должен быть указан двумя последними цифрами и должен быть актуальным или будущим (например: 25)

Владелец (имя владельца на латинице, например: Maria Bond)

CVC/CVV (код из 3 цифр, например: 123)

Кликнуть на кнопку «Продолжить»

Ожидаемый результат: Появление сообщения об успешной операции. Форма успешно отправлена. В базе данных регистрируется новая транзакция со статусом "APPROVED"


**Сценарий 2.** Заполнение формы «Кредит по данным карты» со статусом карты «APPROVED»

Кликнуть на кнопку «Купить в кредит»

Заполнить поля формы «Кредит по данным карты» валидными значениями:

Номер карты со статусом «APPROVED»

Номер месяца

Год

Владелец

CVC/CVV

Кликнуть на кнопку «Продолжить»

Ожидаемый результат: Появление сообщения об успешной операции. Форма успешно отправлена. В базе данных регистрируется новая транзакция со статусом "APPROVED"


**Сценарий 3.** Заполнение формы «Оплата по карте» со статусом карты «DECLINED»

Кликнуть на кнопку «Купить»

Заполнить поля формы «Оплата по карте» валидными значениями:

Номер карты со статусом «DECLINED» (например: 4444 4444 4444 4442)

Номер месяца

Год

Владелец

CVC/CVV

Кликнуть на кнопку «Продолжить»

Ожидаемый результат: Появление сообщения об отклонении операции. Форма не отправлена. В базе данных регистрируется новая транзакция со статусом "DECLINED"


**Сценарий 4.** Заполнение формы «Кредит по данным карты» со статусом карты «DECLINED»

Кликнуть на кнопку «Купить в кредит»

Заполнить поля формы «Кредит по данным карты» валидными значениями:

Номер карты со статусом «DECLINED»

Номер месяца

Год

Владелец 2.5. CVC/CVV

Кликнуть на кнопку «Продолжить»

Ожидаемый результат: Появление сообщения об отклонении операции. Форма не отправлена. В базе данных регистрируется новая транзакция со статусом "DECLINED"

## Негативные сценарии:

**Сценарий 1:** Ввести корректное имя пользователя, заполнить остальные поля корректными данными, ожидаем результат уведомление об успешной операции;

**Сценарий 2:** Ввод некорректных данных, ожидаемый результат - ошибка;

**Сценарий 3:** Не заполнен номер карты, ожидаем результат - ошибка, поле обязательно для заполнения;

**Сценарий 4:** Не заполнен месяц, ожидаемый результат - ошибка, поле обязательно для заполнения;

**Сценарий 5:** Не заполнен год, ожидаемый результат - ошибка, поле обязательно для заполнения;

**Сценарий 6:** Не заполнен владелец, ожидаемый результат - ошибка, поле обязательно для заполнения;

**Сценарий 7:** Не заполнен код CVC, ожидаемый результат - ошибка, поле обязательно для заполнения;

**Сценарий 8:** Некорректный номер карты, ожидаемый результат - ошибка, неверный формат;

**Сценарий 9:** Срок карты истёк, ожидаемый результат - ошибка, истёк срок действия карты;

**Сценарий 10:** Невалидный месяц, ожидаемый результат - ошибка, неверный формат;

**Сценарий 11:** Некорректный месяц, ожидаемый результат - ошибка, неверный формат;

**Сценарий 12:** Некорректный год, ожидаемый результат - ошибка, неверный формат;

**Сценарий 13:** Превышен срок карты, ожидаемый результат - ошибка, неверно указан срок действия карты;

**Сценарий 14:** Некорректный владелец, ожидаемый результат - ошибка, неверный формат;

**Сценарий 15:** Короткое имя владельца, ожидаемый результат - ошибка, неверный формат;

**Сценарий 16:** Длинное имя владельца, ожидаемый результат - ошибка, неверный формат;

**Сценарий 17:** Некорректный код CVC - нули, ожидаемый результат - ошибка, неверный формат;

**Сценарий 18:** Некорректный код CVC - 2 цифры, ожидаемый результат - ошибка, неверный формат;



## Перечень используемых инструментов:

**IntelliJ IDEA**— интегрированная среда разработки программного обеспечения. Данная программа является одной из самых удобных и многофункциональной в данной сфере. Подходит для работы на Java, JavaScript, Python.

**Java 11** — один из самых популярных языков кодирования, автотесты написаны на этом языке. Данный язык очень удобный так как поддерживает большое количество библиотек для написания автотестов.

**Gradle** — система управления зависимостями. Проект создан на базе Gradle.

**JUnit** — платформа для написания автотестов и их запуска. Легко подключается в файле build.gradle в зависимостях.

**Selenide** — это фреймворк для автоматизированного тестирования веб- приложений на основе Selenium WebDriver. Удобен тем, что легко добавляется в зависимости в файл build.gradle. Не требует отдельной загрузки браузера. Элементы страницы удобно находятся через CSS=селекторы.

**Docker** - система контейнеризации. Позволит подключить базы данных MySQL и PostgresSQL.

**WebDriver** - стандартный инструмент для управления браузерами, совместимый с Selenide.

**Allure** - фреймворк, позволяет создать визуально понятный и наглядный отчёт выполнения автотестов.

**Библиотека Lombok** - библиотека, уменьшающая количество шаблонного кода в Java-приложениях, автоматизируя создание геттеров, сеттеров, конструкторов, и других стандартных методов. Это повышает читаемость и упрощает поддержку кода.

**Git** - распределённая система управлениями версиями. Позволяет удобно создавать специализированные системы контроля версий на базе Git или пользовательские интерфейсы.

**GitHub** — удобен для использования хранения кода, в том числе автотестов.

## Перечень и описание возможных рисков при автоматизации:

Отсутствие документации;

Потеря актуальности автоматизированных тестов при изменениях в коде

Неверно рассчитано время выполнения работ;

Недостаточное тестовое покрытие;

Неправильно подобранные инструменты.

## ИНТЕРВАЛЬНАЯ ОЦЕНКА С УЧЁТОМ РИСКОВ В ЧАСАХ:

Исследование и анализ: 6-10 часов:

Настройка окружения (Docker, Docker Compose): 4-6 часов.

Разработка тестовых сценариев: 10-12 часов.

Написание автотестов: 30-50 часов.

Тестирование и отладка: 16-24 часов.

Подготовка отчётных документов по итогам автоматизированного тестирования: 4 часа.

Подготовка отчётных документов по итогам автоматизации: 4 часа.


**Итого:** 74-110 часов

## ПЛАН СДАЧИ РАБОТ:
**Автотесты:** будут готовы через 2 недели.

**Результаты прогона автотестов:** будут готовы через 2 недели и 5 дней.

 **Отчёт по автоматизации:** будет загружен в репозиторий через 3 недели.
