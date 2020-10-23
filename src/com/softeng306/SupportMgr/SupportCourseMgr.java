package com.softeng306.SupportMgr;

import com.softeng306.HelpInfoMgr;

import java.util.regex.Pattern;

public class SupportCourseMgr extends SupportMgr {

    @Override
    public boolean checkValidIDInput(String courseID) {
        String REGEX = "^[A-Z]{2}[0-9]{3,4}$";
        boolean valid = Pattern.compile(REGEX).matcher(courseID).matches();
        if(!valid){
            System.out.println("Wrong format of course ID.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted group name is in the correct format.
     * @param groupName The inputted group name.
     * @return boolean indicates whether the inputted group name is valid.
     */
    public static boolean checkValidGroupNameInput(String groupName){
        String REGEX = "^[a-zA-Z0-9]+$";
        boolean valid =  Pattern.compile(REGEX).matcher(groupName).matches();
        if(!valid){
            System.out.println("Wrong format of group name.");
        }
        return valid;
    }

    /**
     * Checks whether the inputted course type is valid.
     * @param courseType The inputted course type.
     * @return boolean indicates whether the inputted course type is valid.
     */
    public static boolean checkCourseTypeValidation(String courseType){
        if(HelpInfoMgr.getAllCourseType().contains(courseType)){
            return true;
        }
        System.out.println("The course type is invalid. Please re-enter.");
        return false;
    }

}
