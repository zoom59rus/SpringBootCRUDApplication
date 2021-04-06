[![Build Status](https://travis-ci.org/zoom59rus/SpringBootCRUDApplication.svg?branch=master)](https://travis-ci.org/zoom59rus/SpringBootCRUDApplication)
# Module_2_5
# Реализовать SpringBoot RESTfull API приложение

## Постанока задачи
Необходимо реализовать REST API приложение, которое взаимодействует с файловым хранилищем AWS S3 и предоставляет возможность получать доступ к файлам и истории загрузок.  
Сущности:      
1. User  
2. Account  
3. AccountStatus  
4. Event  
5. File  
6. FileStatus (enum ACTIVE, BANNED, DELETED)  
User -> List<File> files + List<Events> + Account account  

В качестве хранилища данных необходимо использовать реляционную базу данных.

## Требования  
1.  Придерживаться подхода MVC;  
2.  Инициализация БД должна быть реализована с помощью Liquibase;  
3.  Для взаимодействия с БД - Spring Data, конфигурирование через аннотации;
4.  Репозиторий должен иметь бейдж сборки travis(https://travis-ci.com/);  
4.  Для сборки  проекта использовать Maven;  
5.  Взаимодействие с пользователем необходимо реализовать с помощью Postman (https://www.getpostman.com/);  
6.  Рабочее приложение должно быть развернуто на heroku.com;  
7.  Взаимодействие с AWS через AWS SDK.  
8. Уровни доступа:  
- ADMIN - полный доступ к приложению  
- MODERATOR - добавление и удаление файлов  
- USER - только чтение всех данных кроме User + Account  

Технологии: Java, MySQL, Spring (IoC, Data, Sercurity), AWS SDK, MySQL, Travis, Docker, JUnit, Mockito, Gradle, Swagger.   

## Результат 
Результатом выполнения задания должен быть репозиторий на github, с использованием Travis (https://travis-ci.org/) и отображением статуса сборки проекта.  

## Описание REST API

