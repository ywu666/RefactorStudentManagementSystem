package com.softeng306.SupportMgr;

import com.softeng306.Enum.Department;

import java.util.*;

public class SupportMgr {

    public boolean checkValidIDInput(String idInput){
        return false;
    }

//    /**
//     * Checks whether the inputted department is valid.
//     * @param department The inputted department.
//     * @return boolean indicates whether the inputted department is valid.
//     */
//    public boolean checkDepartmentValidation(String department){
//        if(getAllDepartment().contains(department)){
//            return true;
//        }
//        System.out.println("The department is invalid. Please re-enter.");
//        return false;
//    }
//
//
//
//    /**
//     * Gets all the departments as an array list.
//     *
//     * @return an array list of all the departments.
//     */
//    public List<String> getAllDepartment() {
//        Set<Department> departmentEnumSet = EnumSet.allOf(Department.class);
//        List<String> departmentStringList = new ArrayList<String>(0);
//        Iterator iter = departmentEnumSet.iterator();
//        while (iter.hasNext()) {
//            departmentStringList.add(iter.next().toString());
//        }
//        return departmentStringList;
//
//    }
//
//
//    /**
//     * Displays a list of all the departments.
//     */
//    public void printAllDepartment() {
//        int index = 1;
//        for (Department department : Department.values()) {
//            System.out.println(index + ": " + department);
//            index++;
//        }
//
//    }
//


}
