# Проект GraphQL Demo

Этот проект - пример приложения на основе GraphQL, реализованного с использованием Spring Boot и Spring GraphQL.
Представляет из себя хранилище для книг и их авторов.

## Запуск проекта
Для запуска проекта с использованием docker-compose вам понадобится установленный Docker и Docker Compose на вашем компьютере.

1. Убедитесь, что у вас установлены Docker и Docker Compose. Если нет, вы можете установить их, следуя инструкциям для вашей операционной системы на [официальном сайте Docker](https://docs.docker.com/get-docker/).

2. Склонируйте репозиторий проекта, выполнив следующую команду в терминале (или командной строке):

```git clone https://github.com/your-username/project-repo.git```

3. Перейдите в директорию проекта: ```cd project-repo```

4. Запустите контейнеры, выполнив следующую команду: ```docker-compose up```

5. После запуска контейнеров GraphiQL интерфейс приложения будет доступен по адресу `http://localhost:8080/graphiql`

## Используемые технологии
- Spring Boot: Основной фреймворк для создания веб-приложения.
- Spring GraphQL: Поддержка GraphQL в Spring Boot приложении.
- Spring Data JPA: Упрощает работу с базами данных и реализацию слоя доступа к данным.
- PostgreSQL: СУБД для хранения данных проекта.
- Lombok: Библиотека для уменьшения количества бойлерплейта в Java классах.
- MapStruct: Инструмент для генерации мапперов между различными Java классами.
- Testcontainers: Помогает создавать изолированные тестовые контейнеры для интеграционного тестирования.

## GraphQL Схема
Проект определяет следующую GraphQL схему:
[schema.graphqls](src/main/resources/graphql/schema.graphqls)

## Примеры запросов GraphQL:
- [SaveAuthor](graphql-examples/mutation_SaveAuthor.graphql): Сохранение автора
- [SaveBook](graphql-examples/mutation_SaveBook.graphql): Сохранение книги
- [GetAllBooks](graphql-examples/query_GetAllBooks.graphql): Получить все книги
- [GetAuthor](graphql-examples/query_GetAuthor.graphql): Вывод автора по имени со списком книг
- [GetBooksByAuthor](graphql-examples/query_GetBooksByAuthor.graphql): Получить список книг по автору

## Техзадание
Ссылка на задание:
[Техническое задание](technical_task.docx)
