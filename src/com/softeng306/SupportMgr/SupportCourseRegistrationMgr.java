package com.softeng306.SupportMgr;

import com.softeng306.CourseRegistration;
import com.softeng306.Main;

import java.util.List;
import java.util.stream.Collectors;

public class SupportCourseRegistrationMgr extends SupportDepartmentMgr{

    /**
     * Checks whether this course registration record exists.
     * @param studentID The inputted student ID.
     * @param courseID The inputted course ID.
     * @return the existing course registration record or else null.
     */
    public static CourseRegistration checkCourseRegistrationExists(String studentID, String courseID){
        List<CourseRegistration> courseRegistrations = Main.courseRegistrations.stream().filter(cr->studentID.equals(cr.getStudent().getStudentID())).filter(cr->courseID.equals(cr.getCourse().getCourseID())).collect(Collectors.toList());
        if(courseRegistrations.size() == 0){
            return null;
        }
        System.out.println("Sorry. This student already registers this course.");
        return courseRegistrations.get(0);

    }
}
