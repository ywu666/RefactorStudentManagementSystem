package com.softeng306.FILEMgr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This abstract class is intended for individual file managers to extend so they can access shared methods.
 * @param <T>
 */
public abstract class FILEMgr<T> {
    /**
     * The string of {@code COMMA_DELIMITER}.
     */
    protected static final String COMMA_DELIMITER = ",";

    /**
     * The string of {@code NEW_LINE_SEPARATOR}.
     */
    protected static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * The string of {@code LINE_DELIMITER}.
     */
    protected static final String LINE_DELIMITER = "|";

    /**
     * The string of {@code EQUAL_SIGN}.
     */
    protected static final String EQUAL_SIGN = "=";

    /**
     * The string of {@code HYPHEN}.
     */
    protected static final String HYPHEN = "-";

    /**
     * The string of {@code SLASH}.
     */
    protected static final String SLASH = "/";

    public abstract List<T> loadFromFile();

    public abstract void writeIntoFile(T object);

    /**
     * Initialises the FileWriter for file managers.
     * @param fileName String name of the file being accessed.
     * @param header String header of the file being accessed.
     * @return FileWriter object
     * @throws IOException
     */
    public FileWriter initialiseFileWriter(String fileName, String header) throws IOException {
        File file = new File(fileName);
        //initialize file header if have not done so
        FileWriter fileWriter = new FileWriter(fileName, true);
        if (file.length() == 0) {
            fileWriter.append(header);
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        return fileWriter;
    }

    /**
     * Attempt to close the FileWriter
     * @param fileWriter
     */
    public void printFinallyBlock(FileWriter fileWriter) {
        if (fileWriter == null) return;

        try {
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurs in flushing or closing the file.");
            e.printStackTrace();
        }
    }

    /**
     * Attempt to close the FileReader
     * @param fileReader
     */
    public void printFinallyBlock(BufferedReader fileReader) {
        if (fileReader == null) return;

        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error occurs when closing the fileReader.");
            e.printStackTrace();
        }
    }
}
