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

## Инструкция по запуску  
### Описание начальной инициализации
Инициализация приложения включает в себя создание БД и таблиц, также создаются роли ADMIN, MODERATOR, DEVELOPER, USER.
При инициализации приложения будут созданы пользователи  
1. admin:admin  
2. developer:developer

Рекомендуется изменить пароли.

### Как развернуть приложение
1. Заполните переменный в файле docker/app/Dokerfile
ENV AWS_ACCESS_KEY_ID=${YOU_AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${YOU_AWS_SECRET_ACCESS_KEY}

2. Выполните команды:  
```shell script
$ gradle build
$ cd docker
$ docker-compose up
```
3. Приложение будет доступно по адресу: localhost:8080/api/v1

### Дополнение
Документацию по использованию API можно посмотреть по адресам: 
1. Формат JSON (.yaml) OAS3 - localhost:8080/api/v1/api-docs
2. Swagger - localhost:8080/api/v1/swagger-ui.html

## Описание REST API  
##### Version: 1.0.0 
````
## Описание REST API  
## Version: 1.0.0  

**Contact information:**  
Anton Nazarov  
https://github.com/zoom59rus/SpringBootCRUDApplication  
anton_1@bk.ru  

### /api/v1/registrations/accounts

#### PUT
##### Summary:

Registering a new account.

##### Description:

Любой не аутентифицированный пользователь имеет доступ к регистрации нового аккаунта.

##### Responses

| Code | Description |
| ---- | ----------- |
| 201 | Create a new account. |
| 304 | Account do not created |
| 400 | Invalid request body |

### /api/v1/moderators/upload/{userId}/{bucket}/{filePath}

#### PUT
##### Summary:

Upload file on AWS S3 and put data request into DB.

##### Description:

Allows upload file on AWS S3 and put data request into DB

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userId | path | User id | Yes | long |
| bucket | path | Bucket by AWS S3 | Yes | string |
| filePath | path | File path on upload | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 400 | Bad Request |
| 403 | Forbidden |

### /api/v1/admins/upload/{userId}/{bucket}/{filePath}

#### PUT
##### Summary:

Upload file on AWS S3 and put data request into DB.

##### Description:

Allows upload file on AWS S3 and put data request into DB

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userId | path | User id | Yes | long |
| bucket | path | Bucket name | Yes | string |
| filePath | path | File path on upload | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 400 | Bad Request |
| 403 | Forbidden |

### /api/v1/admins/accounts/{id}/add/user

#### PUT
##### Summary:

Add user profile on account by id.

##### Description:

Allows add user profile on account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Account id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/accounts/create

#### PUT
##### Summary:

Add account on application.

##### Description:

Add account application, redirect to registration controller.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/moderators/set/{userId}/{fileId}

#### POST
##### Summary:

Add a file to user by id.

##### Description:

Allows add a file to user by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userId | path | User id | Yes | long |
| fileId | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 400 | Bad Request |
| 403 | Forbidden |

### /api/v1/authentication/logout

#### POST
##### Summary:

User logout.

##### Description:

Allows user logout.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |

### /api/v1/authentication/login

#### POST
##### Summary:

Authentication user.

##### Description:

Allows authentication user.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/set/{userId}/{fileId}

#### POST
##### Summary:

Add a file to user by id.

##### Description:

Allows add a file to user by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userId | path | User id | Yes | long |
| fileId | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 400 | Bad Request |
| 403 | Forbidden |

### /api/v1/admins/accounts/{id}/changes/user/{userId}

#### POST
##### Summary:

Changes user profile on account by id.

##### Description:

Allows changes user profile on account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Account id | Yes | long |
| userId | path | User id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/accounts/{id}/changes/status/{status}

#### POST
##### Summary:

Changes status on account by id.

##### Description:

Allows changes status on account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Account id | Yes | long |
| status | path | Status id | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/accounts/{id}/changes/role/{roleId}

#### POST
##### Summary:

Changes role on account by id.

##### Description:

Allows changes role on account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Account id | Yes | long |
| roleId | path | Role id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/accounts/{id}/changes/password/{password}

#### POST
##### Summary:

Changes password on account by id.

##### Description:

Allows changes password on account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Account id | Yes | long |
| password | path | New password | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/files/{type}

#### GET
##### Summary:

Get files on type.

##### Description:

Allows get files on type.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| type | path | File type | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/files/{name}

#### GET
##### Summary:

Get file on name.

##### Description:

Allows get file on name.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| name | path | File name | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/files/{id}

#### GET
##### Summary:

Get file on id.

##### Description:

Allows get file on id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/files/

#### GET
##### Summary:

Get all files.

##### Description:

Allows get all files.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/events/{id}

#### GET
##### Summary:

Get event by id.

##### Description:

Allows get event by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Event id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/events/file/{fileName}

#### GET
##### Summary:

Get event by file name.

##### Description:

Allows get event by file name.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| fileName | path | File name | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/events/file/{fileId}

#### GET
##### Summary:

Get event by file id.

##### Description:

Allows get event by file id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| fileId | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/users/events/all

#### GET
##### Summary:

Get all events.

##### Description:

Allows get all events.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/users/{id}

#### GET
##### Summary:

Returns user by id.

##### Description:

Allows returns user by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | User id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/users/all

#### GET
##### Summary:

Returns all users.

##### Description:

Allows returns all users.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/roles/{id}

#### GET
##### Summary:

Returns role by id.

##### Description:

Allows returns role by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Role id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/roles/all

#### GET
##### Summary:

Returns all roles.

##### Description:

Allows returns all roles.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/files/{type}

#### GET
##### Summary:

Get files on type.

##### Description:

Allows get files on type.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| type | path | File type | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/files/{name}

#### GET
##### Summary:

Get file on name.

##### Description:

Allows get file on name.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| name | path | File name | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/files/{id}

#### GET
##### Summary:

Returns file by id.

##### Description:

Allows returns file by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/files/all

#### GET
##### Summary:

Returns all files.

##### Description:

Allows returns all files.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/events/{id}

#### GET
##### Summary:

Get event by id.

##### Description:

Allows get event by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Event id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/events/user/{userId}

#### GET
##### Summary:

Returns events by user id.

##### Description:

Allows returns events by user id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userId | path | User id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/events/file/{fileName}

#### GET
##### Summary:

Get event by file name.

##### Description:

Allows get event by file name.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| fileName | path | File name | Yes | string |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/events/file/{fileId}

#### GET
##### Summary:

Returns events by file id.

##### Description:

Allows returns events by file id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| fileId | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/events/all

#### GET
##### Summary:

Returns all events.

##### Description:

Allows returns all events.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/accounts/{id}

#### GET
##### Summary:

Returns accounts by id.

##### Description:

Allows returns account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Account id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/accounts/all

#### GET
##### Summary:

Returns all accounts.

##### Description:

Allows returns all account.

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 400 | Bad Request |
| 403 | Forbidden |

### /api/v1/moderators/delete/{fileId}

#### DELETE
##### Summary:

Delete file by id.

##### Description:

Allows delete file by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| fileId | path | File id | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/deletes/{userId}

#### DELETE
##### Summary:

Delete user by id.

##### Description:

Allows delete user by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| userId | path | User id on remove | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/deletes/{roleId}

#### DELETE
##### Summary:

Delete role by id.

##### Description:

Allows delete role by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| roleId | path | Role id on remove | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/deletes/{accountId}

#### DELETE
##### Summary:

Delete account by id.

##### Description:

Allows delete account by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| accountId | path | Account id on remove | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 403 | Forbidden |
| 404 | Not Found |

### /api/v1/admins/delete/{fileId}

#### DELETE
##### Summary:

Delete file by id.

##### Description:

Allows delete file by id.

##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| fileId | path | File id on remove | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 304 | Not Modified |
| 403 | Forbidden |
| 404 | Not Found |

