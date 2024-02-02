package LogFileManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class LogFileHandler {
    private static final Logger logger = LogManager.getLogger("SystemLogger");

    public static void startLogFileHandler() {
        Scanner scanner = new Scanner(System.in);

        LogFileManager logFileManager = new LogFileManager();

        logger.info("LogFileHandler started");

        while (true) {
            System.out.println("Enter a command ('openLogFile' to open a log file, 'exit' to exit): ");
            String command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                break;
            }

            try {
                handleCommand(command, logFileManager);
            } catch (Exception e) {
                logger.error("Error handling command: " + command, e);
            }
        }

        logger.info("LogFileHandler ended");
    }

    private static void handleCommand(String command, LogFileManager logFileManager) {
        if ("openLogFile".equalsIgnoreCase(command)) {
            System.out.println("Enter the name of the equipment or date to open the log file:");
            String userInput = new Scanner(System.in).nextLine();
            System.out.println("Enter the type of equipment ('charging_station', 'energy_source', 'system'):");
            String equipmentType = new Scanner(System.in).nextLine();

            logFileManager.logFileExistsAndOpen(userInput, equipmentType);
        } else {
            System.out.println("Unknown command: " + command);
        }
    }
}
