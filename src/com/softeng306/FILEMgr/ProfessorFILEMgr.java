package com.softeng306.FILEMgr;

import com.softeng306.Professor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorFILEMgr extends FILEMgr<Professor> {
    /**
     * The index of professor ID in professorFile.csv.
     */
    private static final int professorIdIndex = 0;

    /**
     * The index of professor name in professorFile.csv.
     */
    private static final int professorNameIndex = 1;

    /**
     * The index of professor department in professorFile.csv.
     */
    private static final int professorDepartmentIndex = 2;

    /**
     * The header of professorFile.csv.
     */
    private static final String professor_HEADER = "professorID,professorName,profDepartment";

    /**
     * The file name of professorFile.csv.
     */
    private static final String professorFileName = "data/professorFile.csv";

    @Override
    public List<Professor> loadFromFile() {
        BufferedReader fileReader = null;
        ArrayList<Professor> professors = new ArrayList<Professor>(0);
        try {
            String line;
            fileReader = new BufferedReader(new FileReader(professorFileName));
            //read the header to skip it
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    Professor professor = new Professor(tokens[professorIdIndex], tokens[professorNameIndex]);
                    professor.setProfDepartment(tokens[professorDepartmentIndex]);
                    professors.add(professor);
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurs when loading professors.");
            e.printStackTrace();
        } finally {
            printFinallyBlock(fileReader);
        }
        return professors;
    }
}
