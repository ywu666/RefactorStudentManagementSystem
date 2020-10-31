package com.softeng306.SupportMgr;

import com.softeng306.Managers.IProfessorMgr;
import com.softeng306.Entities.Professor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is intended to support the ProfessorMgr with peripheral functions, such as input validation.
 */
public class SupportProfessorMgr extends SupportDepartmentMgr {

    private IProfessorMgr professorMgr;

    public SupportProfessorMgr(IProfessorMgr professorMgr) {
        this.professorMgr = professorMgr;
    }

    /**
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public Professor checkProfExists(String profID){
        System.out.println(professorMgr.equals(1));
        List<Professor> anyProf = professorMgr.getProfessors().stream().filter(p->profID.equals(p.getProfID())).collect(Collectors.toList());
        if(anyProf.size() == 0){
            return null;
        }
        System.out.println("Sorry. The professor ID is used. This professor already exists.");
        return anyProf.get(0);
    }
}

