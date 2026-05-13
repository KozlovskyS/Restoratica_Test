# Тесты для приложения Restoratica https://git.infra.cloveri.com/cloveri.start/restoratica/frontend
## Подготовка к запуску
- Склонировать репозиторий
- Установить Allure (проверка allure --version)

## Запуск автотестов
- Открыть проект в IDE
- Синхронизировать проект
- При необходимости включаем headless-режим для Selenide в build.gradle:
  systemProperty 'selenide.headless', 'true'
- убедиться в доступности приложения по адресу https://resto.skroy.ru/reservation/ 
- либо запустить приложение локально, после этого изменить в тесте соответствующий адрес

  open("http://localhost:3000/");
  //open("https://resto.skroy.ru/");
- Запустить выполнение
./gradlew clean test

## Просмотр отчета
- Сгенерировать отчет 
allure generate allure-results -o allure-report --clean
- Открыть отчёт: откройте allure-report/index.html в браузере или используйте allure serve
