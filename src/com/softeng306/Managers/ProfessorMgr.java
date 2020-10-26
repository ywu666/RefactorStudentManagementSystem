package com.softeng306.Managers;

import com.softeng306.FILEMgr.ProfessorFILEMgr;
import com.softeng306.Professor;
import com.softeng306.SupportMgr.SupportProfessorMgr;
import com.softeng306.SupportMgr.SupportStudentMgr;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Manages all the professor related operations
 *

 */
public class ProfessorMgr implements IProfessorMgr {
    private static SupportProfessorMgr supportProfessorMgr = new SupportProfessorMgr();
    private ProfessorFILEMgr profFileMgr = new ProfessorFILEMgr();
    /**
     * A list of all the registered professors.
     */
    private  List<Professor> professors = profFileMgr.loadFromFile();

    private ICourseMgr courseMgr;
    private IStudentMgr studentMgr;

    /**
     * Adds a professor.
     *
     * @return a newly added professor
     */
    public Professor addProfessor() {
        String department, profID;
        while (true) {
            System.out.println("Give this professor an ID: ");
            profID = scanner.nextLine();
            if (checkValidProfIDInput(profID)) {
                if (checkProfExists(profID) == null) {
                    break;
                }
            }
        }

        String profName;
        while (true) {
            System.out.println("Enter the professor's name: ");
            profName = scanner.nextLine();
            if (studentMgr.checkValidPersonNameInput(profName)) {
                break;
            }
        }

        Professor professor = new Professor(profID, profName);
        while (true) {
            System.out.println("Enter professor's Department: ");
            System.out.println("Enter -h to print all the departments.");
            department = scanner.nextLine();
            while (department.equals("-h")) {
                courseMgr.getAllDepartment();
                department = scanner.nextLine();
            }
        }
    }


    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public List<String> printProfInDepartment(String department, boolean printOut) {
        if (supportProfessorMgr.checkDepartmentValidation(department)) {
            List<String> validProfString = professors.stream().filter(p -> String.valueOf(department).equals(p.getProfDepartment())).map(p -> p.getProfID()).collect(Collectors.toList());
            if (printOut) {
                validProfString.forEach(System.out::println);
            }
            return validProfString;
        }
        System.out.println("None.");
        return null;

    }

    public void setCourseMgr(ICourseMgr courseMgr) {
        this.courseMgr = courseMgr;
    }

    public void setStudentMgr(IStudentMgr studentMgr) {
        this.studentMgr = studentMgr;
    }

}
