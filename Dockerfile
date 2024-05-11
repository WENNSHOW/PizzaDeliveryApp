# Используем официальный образ Java
FROM maven:3.8.4-openjdk-17 as builder

# Устанавливаем рабочую директорию в /app
WORKDIR /app

# Копируем скомпилированные файлы приложения в контейнер
COPY target/pizzaapp-0.0.1-SNAPSHOT.jar /app/pizzaapp-0.0.1-SNAPSHOT.jar

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "pizzaapp-0.0.1-SNAPSHOT.jar"]
