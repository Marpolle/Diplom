## Порядок запуска Дипломного проекта профессии "Тестировщик". 
### Необходимое программное обеспечение
* Операционная система: Windows 11 23H2
* Браузер Google Chrome Версия 128.0.6613.115 (Официальная сборка), (64 бит)
* IntelliJ IDEA 2024.1.2 (Community Edition)
* Java: OpenJDK 11
* Docker Desktop 4.32.0

  
**1. Запустить Docker Desktop**

**2. Запустить контейнеры командой в терминале:**

```
docker compose up
```
**3. Запустить Java-приложение командой в терминале:**

**MySQL:**

```
java -jar aqa-shop.jar
```

**PostgreSQL:**

```
java -jar aqa-shop.jar -Dspring.datasourse.url=jdbc:postgresql://localhost:5432/app
```
**Запуск автотестов и генерация отчета Allure**
* Запустить автотесты командой в терминале:

**MySQL:**

```
./gradlew clean test
```
**PostgreSQL:**

```
./gradlew clean test -D db.url=jdbc:postgresql://localhost:5432/app
```
* Запустите генерацию отчета Allure и их автоматического открытия в браузере командой в терминале:

```
./gradlew allureServe
```
**После завершения прогона автотестов и получения отчета:**
- Завершить обработку отчета сочетанием клавиш `CTRL + C`, в терминале нажать клавишу `Y`, нажать `Enter`.
- Закрыть приложение сочитанием клавиш `CTRL + C` в терминале запуска.
- Остановить работу контейнеров командой `docker-compose down`.
