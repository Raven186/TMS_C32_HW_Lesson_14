package validator;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class   DocumentValidator {

    public static void validateDocuments(Path inputPath) {
        // Пути для отчетов
        String docnumOutputPath = "valid_docnum.txt";
        String contractOutputPath = "valid_contract.txt";
        String invalidOutputPath = "invalid_documents.txt";

        System.out.println("Processing file: " + inputPath);

        // Регулярные выражения для проверки валидности документа
        Pattern validDocnumPattern = Pattern.compile("^docnum[\\w\\d]{9}$", Pattern.CASE_INSENSITIVE);
        Pattern validContractPattern = Pattern.compile("^contract[\\w\\d]{7}$", Pattern.CASE_INSENSITIVE);

        try {
            // Открываем файлы для чтения и записи
            try (BufferedReader reader = Files.newBufferedReader(inputPath);
                 BufferedWriter docnumWriter = Files.newBufferedWriter(Paths.get(docnumOutputPath));
                 BufferedWriter contractWriter = Files.newBufferedWriter(Paths.get(contractOutputPath));
                 BufferedWriter invalidWriter = Files.newBufferedWriter(Paths.get(invalidOutputPath))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim(); // Убираем лишние пробелы
                    if (line.isEmpty()) continue;

                    if (validDocnumPattern.matcher(line).matches()) {
                        docnumWriter.write(line);
                        docnumWriter.newLine();
                    } else if (validContractPattern.matcher(line).matches()) {
                        contractWriter.write(line);
                        contractWriter.newLine();
                    } else {
                        // Генерируем причину для невалидного документа
                        String reason = determineInvalidReason(line);
                        invalidWriter.write(line + " - " + reason);
                        invalidWriter.newLine();
                    }
                }
                System.out.println("Validation completed. Reports generated.");
            }
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    /**
     * Метод для определения причины невалидности номера документа
     */
    private String determineInvalidReason(String line) {
        if (line.length() != 15) {
            return "Invalid length (expected 15 characters)";
        }
        if (!line.startsWith("docnum") && !line.startsWith("contract")) {
            return "Invalid prefix (should start with 'docnum' or 'contract')";
        }
        if (!line.matches("^[\\w\\d]+$")) {
            return "Contains invalid characters (only letters and digits allowed)";
        }
        return "Unknown reason";
    }
}
