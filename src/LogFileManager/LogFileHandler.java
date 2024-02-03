package LogFileManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogFileHandler {
    private static final Logger logger = LogManager.getLogger("SystemLogger");
    static LogFileManager logFileManager = new LogFileManager();

    public static void startLogFileHandler() {
       

        logger.info("LogFileHandler started");

        User user;

        System.out.print("Are you an admin or a normal user? (admin/normal): ");
        String userTypeInput = logFileManager.getScanner().nextLine().toLowerCase();

        if ("admin".equals(userTypeInput)) {
            boolean correctPassword = false;

            while (!correctPassword) {
                System.out.print("Enter admin password: ");
                String password = logFileManager.getScanner().nextLine();
                user = new AdminUser();

                if (user.authenticate(password)) {
                	openLogFile();
                } else {
                    System.out.println("Wrong password. Try again.");
                }
            }
        } else if ("normal".equals(userTypeInput)) {
            user = new NormalUser();
            openLogFile();
        } else {
            System.out.println("Invalid user type. Exiting...");
            return;
        }

        logger.info("LogFileHandler ended");
    }
    
    private static void openLogFile() {
        

        while (true) {
            System.out.println("Enter a command ('openLogFile' to open a log file, 'exit' to exit): ");
            String command = logFileManager.getScanner().nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                break;
            }

            try {
                System.out.println("Choose either ('charging_stations', 'energy_sources', 'system'):");
                String folder = logFileManager.getScanner().nextLine();

                logFileManager.logFileExistsAndOpen(folder);
            } catch (Exception e) {
                logger.error("Error handling command: " + command, e);
            }
        }
    }
}
