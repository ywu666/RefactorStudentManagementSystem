package com.softeng306.SupportMgr;

import com.softeng306.HelpInfoMgr;

public abstract class SupportMgr {

    public abstract boolean checkValidIDInput(String idInput);

    /**
     * Checks whether the inputted department is valid.
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    public static boolean checkDepartmentValidation(String department){
        if(HelpInfoMgr.getAllDepartment().contains(department)){
            return true;
        }
        System.out.println("The department is invalid. Please re-enter.");
        return false;
    }
}
