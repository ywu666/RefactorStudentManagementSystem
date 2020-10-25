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

    private static SupportProfessorMgr supportProfessorMgr = new SupportProfessorMgr();


    /**
     * Displays all the professors in the inputted department.
     *
     * @param department The inputted department.
     * @param printOut Represents whether print out the professor information or not
     * @return A list of all the names of professors in the inputted department or else null.
     */
    public static List<String> printProfInDepartment(String department, boolean printOut) {
        if (supportProfessorMgr.checkDepartmentValidation(department)) {
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
