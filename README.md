# Task Management System

Система управления задачами на Spring Boot с JWT аутентификацией.

## Технологии

- Java 17
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Docker
- Swagger/OpenAPI

## Требования

- JDK 17+
- Maven
- Docker и Docker Compose
- PostgreSQL (если запускаете без Docker)

## Запуск приложения

### Через Docker (рекомендуется)

1. Клонируйте репозиторий:
```bash
git clone <repository-url>
cd TaskManagementSystem
```

2. Соберите проект:
```bash
./mvnw clean package -DskipTests
```

3. Запустите контейнеры:
```bash
docker-compose up --build
```

Приложение будет доступно по адресу: http://localhost:8080

### Локальный запуск

1. Создайте базу данных PostgreSQL:
```sql
CREATE DATABASE taskdb;
```

2. Настройте подключение к базе данных в `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Запустите приложение:
```bash
./mvnw spring-boot:run
```

## API Documentation

Swagger UI доступен по адресу: http://localhost:8080/swagger-ui/index.html

## Использование API

### 1. Регистрация пользователя

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "password123"
  }'
```

### 2. Получение токена (логин)

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "password123"
  }'
```

### 3. Использование защищенных эндпоинтов

Добавьте полученный токен в заголовок Authorization:

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Task",
    "description": "Description",
    "status": "WAITING",
    "priority": "HIGH",
    "authorId": 1,
    "executorId": 1
  }'
```

## Основные эндпоинты

### Аутентификация
- POST /auth/register - Регистрация нового пользователя
- POST /auth/login - Вход в систему

### Задачи
- GET /tasks - Получение списка задач
- POST /tasks - Создание новой задачи (только ADMIN)
- PUT /tasks/{id} - Обновление задачи
- DELETE /tasks/{id} - Удаление задачи (только ADMIN)

### Пользователи
- GET /users/id/{id} - Получение пользователя по ID
- GET /users/email/{email} - Получение пользователя по email
- PUT /users/{id}/role - Изменение роли пользователя (только ADMIN)

### Комментарии
- GET /tasks/{taskId}/comments - Получение комментариев задачи
- POST /tasks/{taskId}/comments - Добавление комментария
- PUT /tasks/{taskId}/comments/{commentId} - Обновление комментария
- DELETE /tasks/{taskId}/comments/{commentId} - Удаление комментария

## Роли и права доступа

- ADMIN - полный доступ ко всем функциям
- USER - может просматривать задачи и обновлять назначенные ему задачи

## Запуск тестов

```bash
./mvnw test
``` 