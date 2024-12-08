import validator.DocumentValidator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Запрашиваем путь к документу у пользователя
        System.out.println("Введите путь к документу:");
        Scanner scanner = new Scanner(System.in);
        String inputFilePath = scanner.nextLine().trim(); // Получаем введенный пользователем путь
        scanner.close();

        // Проверяем, существует ли файл
        Path inputPath = Paths.get(inputFilePath).toAbsolutePath();
        if (!Files.exists(inputPath)) {
            System.err.println("Файл по указанному пути не найден: " + inputPath);
            return;
        }

        // Создаем объект валидатора и запускаем логику
        DocumentValidator validator = new DocumentValidator();
        validator.validateDocuments(inputPath);
    };

}
