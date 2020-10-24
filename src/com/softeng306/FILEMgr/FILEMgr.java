package com.softeng306.FILEMgr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
    public static final String SLASH = "/";

    public abstract List<T> loadFromFile();

    public abstract void writeIntoFile(T object);

    public FileWriter initialiseFileWriter(String fileName, String header) throws IOException {
        File file = new File(fileName);
        //initialize file header if have not done so
        FileWriter fileWriter = new FileWriter(fileName, true);
        if (file.length() != 0) {
            fileWriter.append(header);
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        return fileWriter;
    }

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
