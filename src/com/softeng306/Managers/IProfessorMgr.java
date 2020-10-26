package com.softeng306.Managers;

import com.softeng306.Entities.Professor;
import com.softeng306.SupportMgr.SupportProfessorMgr;

import java.util.List;

public interface IProfessorMgr {

    List<String> printProfInDepartment(String department, boolean printOut);

    //boolean checkValidProfIDInput(String profID);

    List<Professor> getProfessors();

    void setSupportProfessorMgr(SupportProfessorMgr professorMgr);
}
