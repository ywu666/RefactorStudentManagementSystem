package com.softeng306.SupportMgr;

import com.softeng306.Main;
import com.softeng306.Managers.CourseMgr;
import com.softeng306.Professor;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SupportProfessorMgr extends SupportHumanMgr {
    @Override
    public boolean checkValidIDInput(String profID) {
        String REGEX = "^P[0-9]{7}[A-Z]$";
        boolean valid =  Pattern.compile(REGEX).matcher(profID).matches();
        if(!valid){
            System.out.println("Wrong format of prof ID.");
        }
        return valid;
    }


    /**
     * Checks whether this professor ID is used by other professors.
     * @param profID The inputted professor ID.
     * @return the existing professor or else null.
     */
    public static Professor checkProfExists(String profID){
        List<Professor> anyProf = Main.professors.stream().filter(p->profID.equals(p.getProfID())).collect(Collectors.toList());
        if(anyProf.size() == 0){
            return null;
        }
        System.out.println("Sorry. The professor ID is used. This professor already exists.");
        return anyProf.get(0);

    }



}

