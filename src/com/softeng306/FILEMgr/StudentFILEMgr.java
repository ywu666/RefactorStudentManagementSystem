package com.softeng306.FILEMgr;

import com.softeng306.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentFILEMgr implements FILEMgr<Student>{
    /**
     * The index of the student ID in studentFile.csv.
     */
    private static final int studentIdIndex = 0;

    /**
     * The index of the student name in studentFile.csv.
     */
    private static final int studentNameIndex = 1;

    /**
     * The index of the student school in studentFile.csv.
     */
    private static final int studentSchoolIndex = 2;

    /**
     * The index of the student gender in studentFile.csv.
     */
    private static final int studentGenderIndex = 3;

    /**
     * The index of the student GPA in studentFile.csv.
     */
    private static final int studentGPAIndex = 4;

    /**
     * The index of the student year in studentFile.csv.
     */
    private static final int studentYearIndex = 5;

    /**
     * The header of studentFile.csv.
     */
    private static final String student_HEADER = "studentID,studentName,studentSchool,studentGender,studentGPA,studentYear";

    /**
     * The file name of studentFile.csv.
     */
    private static final String studentFileName = "data/studentFile.csv";

    @Override
    public void writeIntoFile(Student student) {
        File file;
        FileWriter fileWriter = null;
        try {
            file = new File(studentFileName);
            //initialize file header if have not done so
            fileWriter = new FileWriter(studentFileName, true);
            if (file.length() == 0) {
                fileWriter.append(student_HEADER);
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
            fileWriter.append(student.getStudentID());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(student.getStudentName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(student.getStudentSchool());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(student.getGender());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(student.getGPA()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(student.getStudentYear()));
            fileWriter.append(NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error in adding a student to the file.");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error in flushing or closing the file.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Student> loadFromFile() {
        BufferedReader fileReader = null;
        ArrayList<Student> students = new ArrayList<Student>(0);
        try {
            String line;
            fileReader = new BufferedReader(new FileReader(studentFileName));
            fileReader.readLine();//read the header to skip it
            int recentStudentID = 0;
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    recentStudentID = Math.max(recentStudentID, Integer.parseInt(tokens[studentIdIndex].substring(1, 8)));
                    Student student = new Student(tokens[studentIdIndex], tokens[studentNameIndex]);
                    student.setStudentSchool(tokens[studentSchoolIndex]);
                    student.setGender(tokens[studentGenderIndex]);
                    student.setGPA(Double.parseDouble(tokens[studentGPAIndex]));
                    student.setStudentYear(Integer.parseInt(tokens[studentYearIndex]));
                    students.add(student);
                }
            }
            // Set the recent student ID, let the newly added student have the ID onwards.
            // If there is no student in DB, set recentStudentID to 1800000 (2018 into Uni)

            Student.setIdNumber(recentStudentID > 0 ? recentStudentID : 1800000);
        } catch (Exception e) {
            System.out.println("Error occurs when loading students.");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error occurs when closing the fileReader.");
                e.printStackTrace();
            }
        }
        return students;
    }
}
