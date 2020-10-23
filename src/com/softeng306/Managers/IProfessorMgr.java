package com.softeng306.Managers;

import com.softeng306.Professor;

import java.util.List;

public interface IProfessorMgr {

    Professor addProfessor();

    List<String> printProfInDepartment();

    boolean checkValidProfIDValidation();

    boolean checkValidPersonNameInputValidation();
}
