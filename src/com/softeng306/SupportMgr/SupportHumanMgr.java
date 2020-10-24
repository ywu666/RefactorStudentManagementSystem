package com.softeng306.SupportMgr;

import java.util.regex.Pattern;

public class SupportHumanMgr extends SupportMgr{

    /**
     * Checks whether the inputted person name is in the correct format.
     * This person can be professor or student.
     * @param personName The inputted person name.
     * @return boolean indicates whether the inputted person name is valid.
     */
    public boolean checkValidPersonNameInput(String personName){
        String REGEX = "^[ a-zA-Z]+$";
        boolean valid =  Pattern.compile(REGEX).matcher(personName).matches();
        if(!valid){
            System.out.println("Wrong format of name.");
        }
        return valid;
    }


}
