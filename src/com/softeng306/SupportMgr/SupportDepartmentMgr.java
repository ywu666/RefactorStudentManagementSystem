package com.softeng306.SupportMgr;

import com.softeng306.Course;
import com.softeng306.Enum.Department;
import com.softeng306.Main;
import com.softeng306.Managers.ICourseMgr;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class SupportDepartmentMgr {

    protected static Scanner scanner = new Scanner(System.in);
    protected static PrintStream originalStream = System.out;
    protected static PrintStream dummyStream = new PrintStream(new OutputStream() {
        public void write(int b) {
            // NO-OP
        }
    });
    protected ICourseMgr courseMgr;

    /**
     * Checks whether the inputted department is valid.
     * @param department The inputted department.
     * @return boolean indicates whether the inputted department is valid.
     */
    public boolean checkDepartmentValidation(String department){
        if(getAllDepartment().contains(department)){
            return true;
        }
        System.out.println("The department is invalid. Please re-enter.");
        return false;
    }

    /**
     * Gets all the departments as an array list.
     *
     * @return an array list of all the departments.
     */
    public List<String> getAllDepartment() {
        Set<Department> departmentEnumSet = EnumSet.allOf(Department.class);
        List<String> departmentStringList = new ArrayList<String>(0);
        Iterator iter = departmentEnumSet.iterator();
        while (iter.hasNext()) {
            departmentStringList.add(iter.next().toString());
        }
        return departmentStringList;
    }


    /**
     * Displays a list of all the departments.
     */
    public void printAllDepartment() {
        int index = 1;
        for (Department department : Department.values()) {
            System.out.println(index + ": " + department);
            index++;
        }

    }

    /**
     * Displays a list of all the courses in the inputted department.
     *
     * @param department The inputted department.
     * @return a list of all the department values.
     */
    public List<String> printCourseInDepartment(String department) {
        List<Course> validCourses = courseMgr.getCourses().stream().filter(c -> department.equals(c.getCourseDepartment())).collect(Collectors.toList());
        List<String> validCourseString = validCourses.stream().map(c -> c.getCourseID()).collect(Collectors.toList());
        validCourseString.forEach(System.out::println);
        if (validCourseString.size() == 0) {
            System.out.println("None.");
        }
        return validCourseString;
    }
}
