package com.softeng306.Managers;

import com.softeng306.Professor;

import java.util.List;

public interface IProfessorMgr {

    Professor addProfessor();

    List<String> printProfInDepartment(String department, boolean printOut);

    boolean checkValidProfIDInput(String profID);

    void setCourseMgr(ICourseMgr courseMgr);

    void setStudentMgr(IStudentMgr studentMgr);

    Professor checkProfExists(String profID);

}
