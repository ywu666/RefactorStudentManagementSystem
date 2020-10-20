package com.softeng306.FILEMgr;

import java.util.List;

public interface FILEMgr<T> {
    /**
     * The string of {@code COMMA_DELIMITER}.
     */
    public static final String COMMA_DELIMITER = ",";

    /**
     * The string of {@code NEW_LINE_SEPARATOR}.
     */
    public static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * The string of {@code LINE_DELIMITER}.
     */
    public static final String LINE_DELIMITER = "|";

    /**
     * The string of {@code EQUAL_SIGN}.
     */
    public static final String EQUAL_SIGN = "=";

    /**
     * The string of {@code HYPHEN}.
     */
    public static final String HYPHEN = "-";

    /**
     * The string of {@code SLASH}.
     */
    public static final String SLASH = "/";

    public void writeIntoFile(T object);

    public List<T> loadFromFile();
}
