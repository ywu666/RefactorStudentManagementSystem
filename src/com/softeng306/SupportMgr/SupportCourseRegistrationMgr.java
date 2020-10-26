package com.softeng306.SupportMgr;

import com.softeng306.Entities.CourseRegistration;
import com.softeng306.Managers.ICourseMgr;
import com.softeng306.Managers.ICourseRegistrationMgr;

import java.util.List;
import java.util.stream.Collectors;

public class SupportCourseRegistrationMgr extends SupportDepartmentMgr {

    private ICourseRegistrationMgr courseRegistrationMgr;

    public SupportCourseRegistrationMgr(ICourseRegistrationMgr courseRegistrationMgr, ICourseMgr courseMgr) {
        this.courseRegistrationMgr = courseRegistrationMgr;
        this.courseMgr = courseMgr;
    }

    /**
     * Checks whether this course registration record exists.
     *
     * @param studentID The inputted student ID.
     * @param courseID  The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public CourseRegistration checkCourseRegistrationExists(String studentID, String courseID) {
        List<CourseRegistration> courseRegistrations = courseRegistrationMgr.getCourseRegistrations().stream().filter(cr -> studentID.equals(cr.getStudent().getStudentID())).filter(cr -> courseID.equals(cr.getCourse().getCourseID())).collect(Collectors.toList());
        if (courseRegistrations.size() == 0) {
            return null;
        }
        System.out.println("Sorry. This student already registers this course.");
        return courseRegistrations.get(0);

    }

    /**
     * Prompts the user to input an existing department.
     *
     * @return the inputted department.
     */
    public String checkCourseDepartmentExists() {
        String courseDepartment;
        while (true) {
            System.out.println("Which department's courses are you interested? (-h to print all the departments)");
            courseDepartment = scanner.nextLine();
            while ("-h".equals(courseDepartment)) {
                printAllDepartment();
                courseDepartment = scanner.nextLine();
            }

            if (checkDepartmentValidation(courseDepartment)) {

                List<String> validCourseString;
                System.setOut(dummyStream);
                validCourseString = printCourseInDepartment(courseDepartment);

                System.out.println("validCourseString = " + validCourseString);
                System.out.println("validCourseString size = " + validCourseString.size());

                System.setOut(originalStream);
                if (validCourseString.size() == 0) {
                    System.out.println("Invalid choice of department.");
                } else {
                    break;
                }
            }
        }
        return courseDepartment;
    }
}
