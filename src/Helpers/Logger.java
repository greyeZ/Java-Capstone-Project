package Helpers;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

public class Logger {

    // Create File
    public static void log(String text) {
        try {
            System.out.println("Writing to file...");
            var fileName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Logs/" + fileName + ".txt"));
            writer.newLine();
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file");
        }
    }

    // Move File
    public static boolean move(String sourcePath, String targetPath) {
        try {
            var source = Paths.get(sourcePath);
            var target = Paths.get(targetPath);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Delete File
    public static boolean delete(String fileName) {
        try {
            var path = Paths.get(fileName);
            Files.delete(path);
            return true;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", fileName);
            return false;
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", fileName);
            return false;
        } catch (IOException x) {
            System.err.println(x);
            return false;
        }
    }

    // Archive File
    public static boolean archive(String fileName) {
        try {
            // Move file to Archives folder
            var archivePath = "/archives/" + fileName;
            return move(fileName, archivePath);
        } catch (Exception x) {
            System.err.println(x);
            return false;
        }
    }

    public static void copyBytes() throws IOException {
        FileInputStream testIn = null;
        FileOutputStream testOut = null;
        try {
            testIn = new FileInputStream("xanadu.txt");
            testOut = new FileOutputStream("outagain.txt");
            int c;

            while ((c = testIn.read()) != -1) {
                testOut.write(c);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (testIn != null) {
                testIn.close();
            }
            if (testOut != null) {
                testOut.close();
            }
        }
    }

}