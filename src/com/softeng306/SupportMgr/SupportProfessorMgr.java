package com.softeng306.SupportMgr;

import java.util.regex.Pattern;

public class SupportProfessorMgr extends SupportMgr {
    @Override
    public boolean checkValidIDInput(String profID) {
        String REGEX = "^P[0-9]{7}[A-Z]$";
        boolean valid =  Pattern.compile(REGEX).matcher(profID).matches();
        if(!valid){
            System.out.println("Wrong format of prof ID.");
        }
        return valid;
    }
}
