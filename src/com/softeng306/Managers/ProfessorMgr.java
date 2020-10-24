package com.softeng306.Managers;

import com.softeng306.Main;
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
public class ProfessorMgr {

    private SupportProfessorMgr supportProfessorMgr;
    private SupportStudentMgr supportStudentMgr;

    private Scanner scanner = new Scanner(System.in);

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
                if (supportProfessorMgr.checkValidIDInput(profID)) {
                if (supportProfessorMgr.checkProfExists(profID) == null) {
                    break;
                }
            }
        }

        String profName;
        while (true) {
            System.out.println("Enter the professor's name: ");
            profName = scanner.nextLine();
            if (supportProfessorMgr.checkValidPersonNameInput(profName)) {
                break;
            }
        }

        Professor professor = new Professor(profID, profName);
        while (true) {
            System.out.println("Enter professor's Department: ");
            System.out.println("Enter -h to print all the departments.");
            department = scanner.nextLine();
            while (department.equals("-h")) {
                CourseMgr.getAllDepartment();
                department = scanner.nextLine();
            }

            if (CourseMgr.checkDepartmentValidation(department)) {
                professor.setProfDepartment(department);
                break;
            }
        }


        return professor;
    }



    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public static List<String> printProfInDepartment(String department, boolean printOut) {
        if (CourseMgr.checkDepartmentValidation(department)) {
            List<String> validProfString = Main.professors.stream().filter(p -> String.valueOf(department).equals(p.getProfDepartment())).map(p -> p.getProfID()).collect(Collectors.toList());
            if (printOut) {
                validProfString.forEach(System.out::println);
            }
            return validProfString;
        }
        System.out.println("None.");
        return null;

    }


}
