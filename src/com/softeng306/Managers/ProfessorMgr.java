package com.softeng306.Managers;

import com.softeng306.FILEMgr.ProfessorFILEMgr;
import com.softeng306.Entities.Professor;
import com.softeng306.SupportMgr.SupportProfessorMgr;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages all the professor related operations
 */
public class ProfessorMgr implements IProfessorMgr {
    private SupportProfessorMgr supportProfessorMgr = new SupportProfessorMgr(this);
    private ProfessorFILEMgr profFileMgr = new ProfessorFILEMgr();
    /**
     * A list of all the registered professors.
     */
    private  List<Professor> professors = profFileMgr.loadFromFile();

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

    /**
     * Assign support professor manager
     * @param supportProfessorMgr
     */
    public void setSupportProfessorMgr(SupportProfessorMgr supportProfessorMgr) {
        this.supportProfessorMgr = supportProfessorMgr;
    }

    /**
     * Retrieves all professors in the system
     * @return List of professor objects.
     */
    public List<Professor> getProfessors() {
        return professors;
    }

}
