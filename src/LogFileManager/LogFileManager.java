package LogFileManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LogFileManager {
    private final Scanner scanner;

    public LogFileManager() {
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public String findLogFile(String folder) {
        // Adjust the path to the 'logs' folder next to the source folder
        String logFilePath = "./logs/" + folder + "/" + "logs.log";

        System.out.println("Debug: Searching for log file at path: " + logFilePath);

        // Check if the log file exists
        if (fileExists(logFilePath)) {
            return logFilePath;
        }

        return null;
    }

    public void openLogFile(String logFilePath) throws IOException {
        // Logic to open the log file
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public void logFileExistsAndOpen(String folder) {
            String logFilePath = findLogFile(folder);
            if (logFilePath != null) {
                try {
                    openLogFile(logFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error opening log file: " + e.getMessage());
                }
            } else {
                System.out.println("Log file not found for the given input.");
            }
    }

    private boolean fileExists(String filePath) {
        // Check if the file exists
        return java.nio.file.Files.exists(java.nio.file.Paths.get(filePath));
    }

}
